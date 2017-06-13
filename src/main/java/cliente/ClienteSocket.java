package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

import entidades.Operacao;

public class ClienteSocket   {    
    private Operacao operacao;
    private Socket servidor = null;
    private DataInputStream recebe;//recebe dados do servidor
    private DataOutputStream envia;//envia dados para o servidor

    /**
     * construtor da classe
     * @param operacao
     * @param host
     * @param porta
     */
    ClienteSocket(Operacao operacao, String host, int porta) {
        this.operacao = operacao;
        try {
            //conecta ao servidor
            servidor = new Socket(host, porta);
            recebe = new DataInputStream(servidor.getInputStream());
            envia = new DataOutputStream(servidor.getOutputStream());
            //escreve o tipo de cliente
            envia.writeInt(operacao.ordinal());
            //efetua o envio
            envia.flush();
            new Thread(new Recebedor()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * faz o calculo de 2 numeros,
     * referente ao tipo de operação instanciada em sua criação
     * 
     * @param valor1
     * @param valor2
     * @return
     */
    private double calcular(double valor1, double valor2) {
        switch (operacao) {
            case SOMAR:
                return valor1 + valor2;
            case DIVIDIR:
                return valor1 / valor2;
            case SUBTRAIR:
                return valor1 - valor2;
            case MULTIPLICAR:
                return valor1 * valor2;            
            default:
                return 0;
        }
    }


    class Recebedor implements Runnable {
        public void run() {
          /**
           * este while fica sempre em execução, para que seja feito mais de 1 calculo
           */
            while (true) {
                try {
                    System.out.println("Aguardando requisição do servidor");
                    double valor1 = recebe.readDouble();
                    double valor2 = recebe.readDouble();
                    JOptionPane.showMessageDialog(null, 
                        "Deseja Responder a Pergunta Feita pelo Servidor,\n "+ 
                         operacao +" ("+
                         valor1+" e "+valor2+
                         ")? ");
                    //escreve o resultado
                    envia.writeDouble(calcular(valor1, valor2));
                    //efetua o envio
                    envia.flush();                                        
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
        }
    }

}
