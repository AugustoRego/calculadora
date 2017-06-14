package servidorEscravo;


import entidades.Operacao;
import entidades.ServidorEscravo;

import javax.swing.*;
import java.awt.*;

public class RodarServidorEscravo {

    private enum TipoServidor {ESPECIAL, BASICO}


    public static void main(String[] args) {


        UIManager.put("OptionPane.background", Color.orange);
        UIManager.put("Panel.background", Color.orange);

        TipoServidor tipoServidor = (TipoServidor) JOptionPane.showInputDialog(null, "Opções",
                "Tipo de Servidor ", JOptionPane.WARNING_MESSAGE, null,
                TipoServidor.values(), // Array of choices
                TipoServidor.values()[0]); // Initial choice

        String host = JOptionPane.showInputDialog("Informe o host do Servidor Principal", "localhost");

        String porta = JOptionPane.showInputDialog("Informe a porta do Servidor Principal", "9876");


        if (tipoServidor == TipoServidor.ESPECIAL) {
            new ServidorEscravo(true, Operacao.PERCENTUAL, Operacao.RAIZ);
        } else {
            new ServidorEscravo(false, Operacao.SOMAR, Operacao.SUBTRAIR, Operacao.DIVIDIR, Operacao.MULTIPLICAR);
        }


    }


}
