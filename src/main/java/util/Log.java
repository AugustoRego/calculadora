package util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log extends JFrame {

   
	private static final long serialVersionUID = 7444507275166222624L;
	private final JTextArea jTextArea = new JTextArea();

    public Log(String titulo) {
        jTextArea.setEditable(false);
        setTitle(titulo);
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        setBounds(100, 100, 600, 600);
        getContentPane().setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPanel.add(jScrollPane, BorderLayout.CENTER);

        getContentPane().add(contentPanel, BorderLayout.CENTER);
        JButton sair = new JButton("Sair");
        getContentPane().add(sair, BorderLayout.SOUTH);


        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        sair.addActionListener(e -> System.exit(-1));

    }


    public void mensagem(final String msg) {
        java.awt.EventQueue.invokeLater(() -> {
            if (msg == null || msg.equalsIgnoreCase("line")) {
                jTextArea.setText(jTextArea.getText() + "\n " + "-------------------------------------------------------------------------------------------------------------------");
            } else {
                jTextArea.setText(jTextArea.getText() + "\n " + getHora() + " - " + msg);
            }
        });
    }

    private String getHora() {
        return new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
    }

}
