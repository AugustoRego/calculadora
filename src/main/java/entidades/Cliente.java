package entidades;

import servidor.Servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * classe responsável por receber e enviar respostas para os clientes
 */
public class Cliente implements Runnable {
    private final DataOutputStream enviar;
    private final DataInputStream receber;
    private final Servidor servidor;

    public Cliente(DataOutputStream enviar, DataInputStream receber, Servidor servidor) {
        this.enviar = enviar;
        this.receber = receber;
        this.servidor = servidor;
    }

    public void run() {
        while (true) {
            try {

                System.out.println("resposta Recebida ");
                double resultado;
                //lendo o tipo de operação
                int tipoOperacao = receber.readInt();

                Operacao operacao = Operacao.values()[tipoOperacao];

                if (operacao == Operacao.RAIZ) {
                    //lendo o valor
                    double valor1 = receber.readDouble();
                    //calculando
                    resultado =servidor.calcular(operacao, valor1);
                }else{
                    //lendo os valores
                    double valor1 = receber.readDouble();
                    double valor2 = receber.readDouble();
                    //calculando
                    resultado =servidor.calcular(operacao, valor1, valor2);
                }
                System.out.println("enviar resultado " + resultado);
                enviar.writeDouble(resultado);
                enviar.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}