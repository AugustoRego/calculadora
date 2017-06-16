package entidades;

public enum Operacao {
    SOMAR(false),
    SUBTRAIR(false),
    MULTIPLICAR(false),
    DIVIDIR(false),
    PORCENTAGEM(true),
    RAIZ(true),
    POTENCIACAO(true) ;

    private final boolean servidorEspecial;

    Operacao(boolean servidorEspecial) {
        this.servidorEspecial = servidorEspecial;
    }


    public boolean isServidorEspecial() {
        return servidorEspecial;
    }
}
