import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.rmi.registry.LocateRegistry;
import java.util.Random;
import java.util.HashMap;

public class Warehouse extends UnicastRemoteObject implements WarehouseInterface {

    protected Warehouse() throws RemoteException {
        super();
    }
    private static final String rmiServer = ":" + 52372 + "/Warehouse";
    private static final String rmiClient = ":" + 52373 + "/Client";


    public static void main(String[] args) throws RemoteException, InterruptedException {

        if (args.length != 1) {
            System.out.println("Usage: java Warehouse <ip>");
            System.exit(1);
        }

        try {
            System.setProperty("java.rmi.server.hostname", args[0]);
            LocateRegistry.createRegistry(52372);
            System.out.println("java RMI registry created.");
        } catch (RemoteException e) {
            System.out.println("java RMI registry already exists.");
        }

        try {
            String server = "rmi://" + args[0] + rmiServer;
            Naming.rebind(server, new Warehouse());
            System.out.println("Warehouse is ready.");
        } catch (Exception e) {
            System.out.println("Warehouse failed: " + e);
        }


        while (true) {
            Thread.sleep(1000);
        }
    }

    @Override
    public String despachar(int id, String endereco)  {
        String str = "O produto" +  + id + " foi despachado para " + endereco;
        System.out.println(str);
        return str;
    }

    @Override
    public String separar(int id) {
        String str = String.format("Produto id %d foi separado...", id);
        System.out.println(str);
        return str;
    }

    @Override
    public String extraviar(int id) {
        String str = String.format("O produto com o id %s foi extraviado xD.", id);
        System.out.println(str);
        return str;

    }
}
