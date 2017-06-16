package util;

import entidades.Operacao;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ThreadLocalRandom;


public class Util {
    public static final int SERVIDOR_PRINCIPAL_PORT_RMI = 1099;
    private static final String RMI_SERVICO = "calculadora";


    public static void setColor(Color color) {
        UIManager.put("OptionPane.background", color);
        UIManager.put("Panel.background", color);
    }


    public static String getTipoServidor(boolean val) {
        return val ? "Especial" : "Básico";
    }


    public static boolean available(int port) {
        try (Socket ignored = new Socket("localhost", port)) {
            return false;
        } catch (IOException ignored) {
            return true;
        }
    }


    public static void msgPortaUsada(int porta) {
        JOptionPane.showMessageDialog(null,
                "A porta(" + porta + ") já está em uso",
                "Atenção",
                JOptionPane.ERROR_MESSAGE);
    }


    public static void criarServidorRMI(int porta, String host, UnicastRemoteObject server) throws RemoteException {
        System.setProperty("java.rmi.server.hostname", host);
        Registry reg = LocateRegistry.createRegistry(porta);
        reg.rebind(RMI_SERVICO, server);
        System.err.println("Server ready");
    }


    public static String getURLFormat(String host, int porta) {
        return String.format("//%s:%d/%s", host, porta, Util.RMI_SERVICO);
    }


    public static int random() {
        return ThreadLocalRandom.current().nextInt(10, 9998 + 1);
    }


    public static double calcular(Operacao operacao, double... valor) {
        switch (operacao) {
            case SOMAR:
                return valor[0] + valor[1];
            case DIVIDIR:
                return valor[0] / valor[1];
            case SUBTRAIR:
                return valor[0] - valor[1];
            case RAIZ:
                return Math.sqrt(valor[0]);
            case PORCENTAGEM:
                return (valor[0] * 100) / valor[1];
            case POTENCIACAO:
                //x elevado a y
                return  Math.pow (valor[0], valor[1]);
            default:
                return 0;
        }
    }


}
