package cliente;

import entidades.Operacao;

import javax.swing.*;
import java.awt.*;

public class RodarCliente {
    public static void main(String[] args) {
        
    	UIManager.put("OptionPane.background", Color.orange);
        UIManager.put("Panel.background", Color.orange);

        Operacao operacao = (Operacao) JOptionPane.showInputDialog(null, "Opções",
                "Tipo de Cliente ", JOptionPane.WARNING_MESSAGE, null,
                Operacao.values(), // Array of choices
                Operacao.values()[0]); // Initial choice

        String host = JOptionPane.showInputDialog("Informe o host do Servidor","localhost");
        
        String porta = JOptionPane.showInputDialog("Informe a porta do Servidor","9876");

        new ClienteSocket(operacao, host, Integer.parseInt(porta));

    }
}
