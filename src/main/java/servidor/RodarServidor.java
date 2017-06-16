package servidor;


import util.Util;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class RodarServidor {


    public static void main(String[] args) throws IOException {
        Util.setColor(Color.red);
        iniciarServidor();
    }


    private static void iniciarServidor() throws IOException {
        String porta = JOptionPane.showInputDialog("Iniciar Servidor (Socket) na Porta:", "9876");
        int port = Integer.parseInt(porta);
        //verifica se a porta já está sendo usada
        if (!Util.available(port)) {
            Util.msgPortaUsada(port);
            iniciarServidor();
        }
        //instancia o servidor
        Servidor servidor = new Servidor(port);
        //inicia a thread
        new Thread(servidor).start();
    }


}
