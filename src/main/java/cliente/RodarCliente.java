package cliente;

import entidades.Operacao;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class RodarCliente {
    private static ClienteSocket clienteSocket;

    public static void main(String[] args) {
        setColor();
        setCliente();
        try {
            calcular();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }

    private static void setCliente() {
        String host = JOptionPane.showInputDialog("Informe o host do Servidor","localhost");
        String porta = JOptionPane.showInputDialog("Informe a porta do Servidor","9876");
        clienteSocket = new ClienteSocket(host, Integer.parseInt(porta));
    }


    public static void mostrarResultado(double resultado) throws IOException {
        JOptionPane.showMessageDialog(null,"resultado "+ resultado);
        calcular();
    }

    public static void calcular() throws IOException {
        Operacao operacao = (Operacao) JOptionPane.showInputDialog(null,
                "Escolha a Operação",
                "Atenção: ",
                JOptionPane.QUESTION_MESSAGE,
                null,
                Operacao.values(),
                Operacao.values()[0]);

        if(operacao==Operacao.RAIZ){
            String valor1 = JOptionPane.showInputDialog("Informe o valor 1");
            clienteSocket.calcular(operacao, Double.valueOf(valor1));
        } else{
            String valor1 = JOptionPane.showInputDialog("Informe o valor 1");
            String valor2 = JOptionPane.showInputDialog("Informe o valor 2");
            clienteSocket.calcular(operacao, Double.valueOf(valor1), Double.valueOf(valor2));
        }


    }


    private static void setColor() {
        UIManager.put("OptionPane.background", Color.orange);
        UIManager.put("Panel.background", Color.orange);
    }


}
