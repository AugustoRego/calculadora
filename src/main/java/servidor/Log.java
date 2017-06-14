package servidor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log extends JDialog {

    private JTextArea jTextArea = new JTextArea();
    private JButton sair = new JButton("Sair");

    public Log() {

        jTextArea.setEditable(false);

        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        setUndecorated(true);
        setBounds(100, 100, 600, 600);
        getContentPane().setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPanel.add(jScrollPane, BorderLayout.CENTER);

        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(sair, BorderLayout.SOUTH);


        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        sair.addActionListener(e -> System.exit(-1));

    }

    public static void main(String[] args) {
        try {
            Log dialog = new Log();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void mensagem(final String msg) {
        java.awt.EventQueue.invokeLater(() -> {
            jTextArea.setText(jTextArea.getText() + "\n"+ getHora() +" - "+ msg);
        });

    }

    private String getHora(){
        return new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
    }

}
