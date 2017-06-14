package servidor;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class RodarServidor {


    public static void main(String[] args){
        try {
            setColor();
            iniciarServidor();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void iniciarServidor() throws IOException {
        String porta = JOptionPane.showInputDialog("Iniciar Servidor na Porta:","9876");
        Servidor servidor = new Servidor(Integer.parseInt(porta));
        new Thread(servidor).start();
    }


    private static void setColor() {
        UIManager.put("OptionPane.background", Color.red);
        UIManager.put("Panel.background", Color.red);
    }

}
