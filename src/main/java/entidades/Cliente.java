package entidades;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class Cliente implements Serializable {
  private static final long serialVersionUID = -8493115667862067434L;
  private Operacao operacao;
  private DataOutputStream envia;
  private DataInputStream recebe;

  public Cliente(Operacao operacao, DataOutputStream saida, DataInputStream entrada) throws IOException {
    this.operacao = operacao;
    envia = saida;
    recebe = entrada;
  }


  public Operacao getOperacao() {
    return operacao;
  }

  
  public void setOperacao(Operacao operacao) {
    this.operacao = operacao;
  }


  public DataOutputStream getEnvia() {
    return envia;
  }


  public DataInputStream getRecebe() {
    return recebe;
  }

  
  

  
  
  
  



}
