import java.rmi.Remote;
import java.rmi.RemoteException;

public interface WarehouseInterface extends Remote {
    public String despachar(int id, String endereco) throws RemoteException;

    public String separar(int id) throws RemoteException;

    public String extraviar(int id) throws RemoteException;
}
