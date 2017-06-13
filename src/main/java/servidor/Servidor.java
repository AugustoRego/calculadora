package servidor;

import entidades.Cliente;
import entidades.Operacao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor implements Runnable {
   //o servidor possui uma lista de clientes
    private List<Cliente> lista = new ArrayList<Cliente>();
    // e um objeto serversocket
    private ServerSocket servidor;

    //para criar o servidor é necessário informar a porta
    Servidor(int porta) throws IOException {
        //instancia o serversocket com a porta informada
        servidor = new ServerSocket(porta);
    }


    public void run() {
      //este while fica sempre se repetindo,
      //para que o servidor aceite mais de 1 cliente
        while (true) {
            try {
                System.out.println("aguardando clientes");
                Socket cliente = servidor.accept();                
                DataInputStream entrada = new DataInputStream(cliente.getInputStream());
                DataOutputStream saida = new DataOutputStream(cliente.getOutputStream());
                //recebe o tipo de operação do cliente
                Operacao operacao = Operacao.values()[entrada.readInt()];
                //instancia a classe cliente e adiciona a lista
                lista.add(new Cliente(operacao, saida, entrada));
                //depois volta para o inicio do while, e o processo fica preso na linha 32,
                //onde o servidor fica aguardando novos clientes
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    
    /**
     * Este método procura na lista de clientes, qual dos clientes 
     * possui a operação passada como parametro
     * @param operacao
     * @param valor1
     * @param valor2
     * @return
     * @throws IOException
     */
    public String calcular(Operacao operacao, double valor1, double valor2) throws IOException {
        for (Cliente c : lista) {
            if (c.getOperacao() == operacao) {
                c.getEnvia().writeDouble(valor1);
                c.getEnvia().writeDouble(valor2);
                //envia a pergunta ao cliente
                c.getEnvia().flush();
                //espera a resposta
                return "O Resultado da "+operacao+"\n entre\n"+valor1+" e "+ valor2+" é = "+ c.getRecebe().readDouble()+"";
            }
        }
        //se não encontrar nenhum cliente do tipo da operação então
        //esta mensagem é exibida
        return "Cliente Socket do tipo de operação ("+operacao+") Não Localizado";
    }

}
