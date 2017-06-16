package servidor;

import entidades.Cliente;
import entidades.Operacao;
import entidades.RMIInterface;
import entidades.ServidorEscravo;
import util.Log;
import util.RoundRobin;
import util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Servidor extends UnicastRemoteObject implements Runnable, RMIInterface {
   	private static final long serialVersionUID = -4497401319976778350L;
	private List<ServidorEscravo> servidoresEspeciais;
    private List<ServidorEscravo> servidoresBasicos;
    private ServerSocket servidor;
    private RoundRobin<ServidorEscravo> rrEspeciais;
    private RoundRobin<ServidorEscravo> rrBasicos;
    private Log log;

    //para criar o servidor é necessário informar a porta
    Servidor(int porta) throws IOException {
        servidor = new ServerSocket(porta);

        servidoresEspeciais = new ArrayList<>();
        rrEspeciais = new RoundRobin<>(servidoresEspeciais);

        servidoresBasicos = new ArrayList<>();
        rrBasicos = new RoundRobin<>(servidoresBasicos);

        log = new Log("Servidor Principal");
        mensagem("Servidor principal(SOCKET) Iniciado na porta:" + porta);
        
        Util.criarServidorRMI(Util.SERVIDOR_PRINCIPAL_PORT_RMI, "localhost", this);
        mensagem("Servidor principal (RMI) iniciado na porta :" + Util.SERVIDOR_PRINCIPAL_PORT_RMI);
        mensagem("line");
    }


    public void run() {
        //este while fica sempre se repetindo,
        //para que o servidor aceite mais de 1 cliente
        while (true) {
            try {
                Socket cliente = servidor.accept();
                DataInputStream entrada = new DataInputStream(cliente.getInputStream());
                DataOutputStream saida = new DataOutputStream(cliente.getOutputStream());
                new Thread(new Cliente(saida, entrada, this)).start();
                mensagem("Cliente conectado " + cliente.getLocalAddress().getCanonicalHostName());
                mensagem("line");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public double calcular(Operacao operacao, double... valor) throws Exception {
        double resultado;
        //se não existir nenhum servidor escravo registrado. O Servidor principal faz o calculo
        if (servidoresEspeciais.isEmpty() && servidoresBasicos.isEmpty()) {
            mensagem("Não Existe Nenhum Servidor Escravo Cadastrado");
            mensagem("O Servidor Está Calculando");
            resultado = Util.calcular(operacao, valor);
            mensagem("Resultado: " + resultado);
            mensagem("line");
            return resultado;
        }

        mensagem("Tipo de Operação: (" + operacao.toString() + ") Procurando Servidor (" + Util.getTipoServidor(operacao.isServidorEspecial()) + ")");
        ServidorEscravo servidorEscravo = procurarServidor(operacao);

        try {
            mensagem("Servidor Escravo Encontrado : " + servidorEscravo.getHost() + " porta (" + servidorEscravo.getPorta() + ")");
            RMIInterface rmiEscravo = getRMIServidorEscravo(servidorEscravo);
            resultado = rmiEscravo.calcular(operacao, valor);
            mensagem("Resultado: " + resultado);
            mensagem("line");
            return resultado;
        } catch (Exception e) {
            mensagem("Falha ao Conectar com o servidor escravo  porta (" + servidorEscravo.getPorta() + ") host (" + servidorEscravo.getHost() + ")");
            mensagem("removendo Servidor da lista");
            removeServidorEscravo(servidorEscravo);
            mensagem("reconectando...");
            mensagem("line");
            //inicia novamente o método
            resultado = calcular(operacao, valor);
        }
        return resultado;
    }

    private ServidorEscravo getServidorEscravo(boolean especial) {
        if (especial) {
            return rrEspeciais.iterator().next();
        } else {
            return rrBasicos.iterator().next();
        }
    }


    private void removeServidorEscravo(ServidorEscravo servidorEscravo) {
        if (servidorEscravo.isEspecial()) {
            servidoresEspeciais.remove(servidorEscravo);
        } else {
            servidoresBasicos.remove(servidorEscravo);
        }
    }


    private ServidorEscravo procurarServidor(Operacao operacao) {

        ServidorEscravo servidorEscravo = getServidorEscravo(operacao.isServidorEspecial());
        //se não encontrar um servidor especial tenta encontrar um servidor basico
        // e virse e versa
        if (servidorEscravo == null) {
            mensagem("Servidor (" + Util.getTipoServidor(operacao.isServidorEspecial()) + ") não Encontrado Procurando Servidor (" + Util.getTipoServidor(!operacao.isServidorEspecial()) + ")");
            servidorEscravo = getServidorEscravo(!operacao.isServidorEspecial());
        }
        return servidorEscravo;
    }


    private RMIInterface getRMIServidorEscravo(ServidorEscravo servidorEscravo) throws Exception {
        return (RMIInterface) Naming.lookup(Util.getURLFormat(servidorEscravo.getHost(), servidorEscravo.getPorta()));
    }


    @Override
    public void addServidorEscravo(ServidorEscravo servidorEscravo) throws RemoteException {
        if (servidorEscravo.isEspecial()) {
            servidoresEspeciais.add(servidorEscravo);
        } else {
            servidoresBasicos.add(servidorEscravo);
        }
        mensagem("Servidor Escravo Adicionado - host:(" + servidorEscravo.getHost() + ") porta:(" + servidorEscravo.getPorta() + ") Tipo: " + (Util.getTipoServidor(servidorEscravo.isEspecial())));
        mensagem("Total de Servidor(es) Escravo(s) Especiais : " + servidoresEspeciais.size());
        mensagem("Total de Servidor(es) Escravo(s) Básicos   : " + servidoresBasicos.size());
        mensagem("line");
    }


    private void mensagem(String msg) {
        log.mensagem(msg);
    }
}
