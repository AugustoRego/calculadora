package servidor;

import entidades.Cliente;
import entidades.Operacao;
import entidades.ServidorEscravo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor implements Runnable {
    private List<ServidorEscravo> servidoresEscravos;
    private ServerSocket servidor;

    //para criar o servidor é necessário informar a porta
    Servidor(int porta) throws IOException {
        servidor = new ServerSocket(porta);
        servidoresEscravos = new ArrayList<ServidorEscravo>();
    }


    public void run() {
        //este while fica sempre se repetindo,
        //para que o servidor aceite mais de 1 cliente
        while (true) {
            try {
                System.out.println("aguardando clientes");
                Socket cliente = servidor.accept();
                DataInputStream entrada = new DataInputStream(cliente.getInputStream());
                DataOutputStream saida = new DataOutputStream(cliente.getOutputStream());
                new Thread(new Cliente(saida, entrada, this)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public double calcular(Operacao operacao, double... valor) throws IOException {
        switch (operacao) {
            case SOMAR:
                return valor[0] + valor[1];
            case DIVIDIR:
                return valor[0] / valor[1];
            case SUBTRAIR:
                return valor[0] - valor[1];
            case RAIZ:
                return Math.sqrt(valor[0]);
            case PERCENTUAL:
                return (valor[0]*100)/valor[1];
        }
        return 5;
    }


}
