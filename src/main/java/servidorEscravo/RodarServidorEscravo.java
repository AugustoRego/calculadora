package servidorEscravo;


import entidades.Operacao;
import entidades.RMIInterface;
import entidades.ServidorEscravo;
import util.Log;
import util.Util;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

public class RodarServidorEscravo extends UnicastRemoteObject implements RMIInterface {
    private enum TipoServidor {ESPECIAL, BASICO}

    private ServidorEscravo servidorEscravo;
    private final TipoServidor tipoServidor;
    private Log log;


    private RodarServidorEscravo() throws RemoteException, UnknownHostException, MalformedURLException, NotBoundException {

        tipoServidor = (TipoServidor) JOptionPane.showInputDialog(null, "Opções",
                "Tipo de Servidor ", JOptionPane.WARNING_MESSAGE, null,
                TipoServidor.values(), // Array of choices
                TipoServidor.values()[0]); // Initial choice

        String hostServidorPrincipal = JOptionPane.showInputDialog("Informe o host do Servidor Principal", InetAddress.getLocalHost().getHostAddress());

        iniciarServidorEscravo();
        log = new Log("Servidor Escravo (" + Util.getTipoServidor(servidorEscravo.isEspecial()) + ") porta: " + servidorEscravo.getPorta());
        log.mensagem("Servidor Escravo Iniciado na porta " + servidorEscravo.getPorta());
        registrarNoServidorPrincipal(hostServidorPrincipal);
        log.mensagem("Serviço Escravo Registrado No Servidor Principal");
        log.mensagem("line");

    }


    private void iniciarServidorEscravo() throws UnknownHostException, RemoteException {
        String host = InetAddress.getLocalHost().getHostAddress();
        //sugere uma porta
        int porta = Integer.parseInt(JOptionPane.showInputDialog("Informe a porta deste Servidor Escravo", Util.random()));

        if (!Util.available(porta)) {
            Util.msgPortaUsada(porta);
            iniciarServidorEscravo();
        }

        servidorEscravo = new ServidorEscravo(tipoServidor == TipoServidor.ESPECIAL, host, porta);
        Util.criarServidorRMI(servidorEscravo.getPorta(), servidorEscravo.getHost(), this);
    }


    private void registrarNoServidorPrincipal(String host) throws RemoteException, NotBoundException, MalformedURLException {
        RMIInterface servidorPrincipal = (RMIInterface) Naming.lookup(Util.getURLFormat(host, Util.SERVIDOR_PRINCIPAL_PORT_RMI));
        servidorPrincipal.addServidorEscravo(servidorEscravo);
    }


    @Override
    public double calcular(Operacao operacao, double... valores) throws RemoteException {
        log.mensagem("Calculando " + operacao.toString() + " valores " + Arrays.toString(valores));
        double resultado = Util.calcular(operacao, valores);
        log.mensagem("Resultado: "+resultado);
        log.mensagem("line");
        return resultado;
    }

    @Override
    public void addServidorEscravo(ServidorEscravo servidorEscravo) throws RemoteException {
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException, UnknownHostException {
        Util.setColor(Color.blue);
        new RodarServidorEscravo();

    }


}
