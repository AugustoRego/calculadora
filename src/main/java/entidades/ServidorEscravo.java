package entidades;


import java.util.Arrays;
import java.util.List;

public class ServidorEscravo {

    private List<Operacao> operacoes;
    private boolean especial;

    public ServidorEscravo(boolean especial, Operacao... operacoes) {
        this.especial = especial;
        this.operacoes = Arrays.asList(operacoes);
    }

    public List<Operacao> getOperacoes() {
        return operacoes;
    }

    public void setOperacoes(List<Operacao> operacoes) {
        this.operacoes = operacoes;
    }

    public boolean isEspecial() {
        return especial;
    }

    public void setEspecial(boolean especial) {
        this.especial = especial;
    }
}
