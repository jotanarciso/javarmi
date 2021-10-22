import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CatalogInterface extends Remote {
    public String visualizar(int id) throws RemoteException;

    public String registrar(int id, String name) throws RemoteException;

    public String remover(int id) throws RemoteException;

    public String vender(int id) throws RemoteException;
}
