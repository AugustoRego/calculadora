package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import entidades.Operacao;

class ClienteSocket   {
    private DataInputStream recebe;//recebe dados do servidor
    private DataOutputStream envia;//envia dados para o servidor
	private Socket servidor;


    ClienteSocket( String host, int porta) {
        try {
            servidor = new Socket(host, porta);
            recebe = new DataInputStream(servidor.getInputStream());
            envia = new DataOutputStream(servidor.getOutputStream());
            new Thread(new Recebedor()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void calcular(Operacao operacao, double... valor) throws IOException {
        envia.writeInt(operacao.ordinal());
        for( double val: valor){
            envia.writeDouble(val);
        }
        envia.flush();
    }


    /**
     * esta classe fica aguardando resposta do servidor
     */
    class Recebedor implements Runnable {
        public void run() {
            while (true) {
                try {
                    System.out.println("Aguardando resposta do servidor");
                    double resultado = recebe.readDouble();
                    RodarCliente.mostrarResultado(resultado);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
        }
    }

}
