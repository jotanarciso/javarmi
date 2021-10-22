import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.rmi.registry.LocateRegistry;
import java.util.Random;
import java.util.HashMap;

public class Billing extends UnicastRemoteObject implements BillingInterface {

    protected Billing() throws RemoteException {
        super();
    }

    private static final String rmiServer = ":" + 52371 + "/Billing";
    private static final String rmiClient = ":" + 52373 + "/Client";

    public static void main(String[] args) throws RemoteException, InterruptedException {

        if (args.length != 1) {
            System.out.println("Usage: java Billing <ip>");
            System.exit(1);
        }

        try {
            System.setProperty("java.rmi.server.hostname", args[0]);
            LocateRegistry.createRegistry(52371);
            System.out.println("java RMI registry created.");
        } catch (RemoteException e) {
            System.out.println("java RMI registry already exists.");
        }

        try {
            String server = "rmi://" + args[0] + rmiServer;
            Naming.rebind(server, new Billing());
            System.out.println("Billing is ready.");
        } catch (Exception e) {
            System.out.println("Billing failed: " + e);
        }

        while (true) {
            Thread.sleep(1000);
        }
    }

    @Override
    public String vender(int id, int valor, String metodoPagamento) {
        String str = "O produto" + id + "foi vendido por " + valor + "via" + " " + metodoPagamento;
        System.out.println(str);
        return str;
    }

    @Override
    public String emitirNota(int id, int valor, int cnpj) {
        String str = "Produto: " + id + "\nValor: " + valor + "\nCNPJ: " + cnpj;
        System.out.println(str);
        return str;

    }

    @Override
    public String cancelarVenda(int id) {
        String str = String.format("Produto com o id %s foi cancelado.", id);
        System.out.println(str);
        return str;
    }
}