package entidades;

import servidor.Servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * classe responsável por receber e enviar respostas para os clientes
 */
public class Cliente implements Runnable {
    private DataOutputStream enviar;
    private DataInputStream receber;
    private Servidor servidor;

    public Cliente(DataOutputStream enviar, DataInputStream receber, Servidor servidor) throws IOException {
        this.enviar = enviar;
        this.receber = receber;
        this.servidor = servidor;
    }

    public void run() {
        while (true) {
            try {
                System.out.println("resposta Recebida ");
                double resultado;
                int tipoOperacao = receber.readInt();

                Operacao operacao = Operacao.values()[tipoOperacao];

                if (operacao == Operacao.RAIZ) {
                    double valor1 = receber.readDouble();
                    resultado =servidor.calcular(operacao, valor1);
                    servidor.getLog().mensagem("Tipo de Operação: ("+ operacao.toString()+") Valor: ("+ valor1+") Resultado: ("+ resultado +")");
                }else{
                    double valor1 = receber.readDouble();
                    double valor2 = receber.readDouble();
                    resultado =servidor.calcular(operacao, valor1, valor2);
                    servidor.getLog().mensagem("Tipo de Operação: ("+ operacao.toString()+") Valor1: ("+ valor1+") Valor2: ("+ valor2+") Resultado: ("+ resultado +")" );
                }
                System.out.println("enviar resultado " + resultado);
                enviar.writeDouble(resultado);
                enviar.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}