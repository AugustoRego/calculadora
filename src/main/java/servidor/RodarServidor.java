package servidor;


import entidades.Operacao;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class RodarServidor {

    public static void main(String[] args){
        try {            
            UIManager.put("OptionPane.background", Color.red);
            UIManager.put("Panel.background", Color.red);
            
            String porta = JOptionPane.showInputDialog("Iniciar Servidor na Porta:","9876");
            Servidor servidor = new Servidor(Integer.parseInt(porta));
            new Thread(servidor).start();
            dialogo(servidor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void dialogo(Servidor servidor) throws IOException {
        Operacao input = (Operacao) JOptionPane.showInputDialog(null, 
                "Escolha a Operação",
                "Atenção: ",
                JOptionPane.QUESTION_MESSAGE,
                null,
                Operacao.values(),
                Operacao.values()[0]);

        String valor1 = JOptionPane.showInputDialog("Informe o valor 1");
        String valor2 = JOptionPane.showInputDialog("Informe o valor 2");

        String resultado = servidor.calcular(input,Double.parseDouble(valor1), Double.parseDouble(valor2));
        JOptionPane.showMessageDialog(null, resultado);

        dialogo(servidor);
    }

}
