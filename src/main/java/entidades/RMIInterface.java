package entidades;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote {
    double calcular(Operacao operacao, double... valor) throws Exception;

    void addServidorEscravo(ServidorEscravo servidorEscravo) throws RemoteException;
}