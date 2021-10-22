import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BillingInterface extends Remote {
    public String vender(int id, int valor, String metodoPagamento) throws RemoteException;

    public String emitirNota(int id, int valor, int cnpj) throws RemoteException;

    public String cancelarVenda(int id) throws RemoteException;
}
