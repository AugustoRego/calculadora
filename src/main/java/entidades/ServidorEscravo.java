package entidades;


import java.io.Serializable;

public class ServidorEscravo implements Serializable {
    private final boolean especial;
    private final int porta;
    private final String host;


    public ServidorEscravo(boolean especial, String host, int porta) {
        this.especial = especial;
        this.porta = porta;
        this.host = host;
    }


    public int getPorta() {
        return porta;
    }


    public String getHost() {
        return host;
    }


    public boolean isEspecial() {
        return especial;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServidorEscravo that = (ServidorEscravo) o;

        return porta == that.porta;
    }

    @Override
    public int hashCode() {
        return porta;
    }
}
