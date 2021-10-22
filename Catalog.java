import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.rmi.registry.LocateRegistry;
import java.util.Random;
import java.util.HashMap;

public class Catalog extends UnicastRemoteObject implements CatalogInterface {

    protected Catalog() throws RemoteException {
        super();
    }
    private static final String rmiServer = ":" + 52370 + "/Catalog";
    private static final String rmiClient = ":" + 52373 + "/Client";


    public static void main(String[] args) throws RemoteException, InterruptedException {

        if (args.length != 1) {
            System.out.println("Usage: java Catalog <ip>");
            System.exit(1);
        }

        try {
            System.setProperty("java.rmi.server.hostname", args[0]);
            LocateRegistry.createRegistry(52370);
            System.out.println("java RMI registry created.");
        } catch (RemoteException e) {
            System.out.println("java RMI registry already exists.");
        }

        try {
            String server = "rmi://" + args[0] + rmiServer;
            Naming.rebind(server, new Catalog());
            System.out.println("Catalog is ready.");
        } catch (Exception e) {
            System.out.println("Catalog failed: " + e);
        }


        while (true) {
            Thread.sleep(1000);
        }
    }

    @Override
    public String visualizar(int id) {
        String str = "Visualizando produto " + id;
        System.out.println(str);
        return str;

    }

    @Override
    public String registrar(int id, String name) {
        String str = String.format("Produto name %d foi registrado....", name);
        System.out.println(str);
        return str;
    }

    @Override
    public String remover(int id) {
        String str = String.format("Produto com o id %s foi removido.", id);
        System.out.println(str);
        return str;

    }

    @Override
    public String vender(int id){
        String str = String.format("Produto %s foi vendido.", id);
        System.out.println(str);
        return str;
    }

}
