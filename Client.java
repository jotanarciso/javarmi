import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

public class Client extends UnicastRemoteObject implements ClientInterface {

	protected Client() throws RemoteException {
		super();
	}

	private static final String rmiCatalog = ":" + 52370 + "/Catalog";
	private static final String rmiBilling = ":" + 52371 + "/Billing";
	private static final String rmiWarehouse = ":" + 52372 + "/Warehouse";

	private static volatile CatalogInterface catalog = null;
	private static volatile BillingInterface billing = null;
	private static volatile WarehouseInterface warehouse = null;

	private static final String rmiClient = ":" + 52373 + "/Client";

	public static void main(String[] args) throws InterruptedException, RemoteException {
		String clientIp = args[0];
		String catalogIp = args[1];
		String billingIp = args[2];
		String warehouseIp = args[3];
		int qtdVendas = Integer.parseInt(args[4]);

		if (args.length != 5) {
			System.out.println("Usage: java <client ip> <catalog ip> <billing ip> <warehouse ip> <numero vendas>");
			System.exit(1);
		}

		try {
			System.setProperty("java.rmi.server.hostname", args[0]);
			LocateRegistry.createRegistry(52373);
			System.out.println("java RMI registry created.");
		} catch (RemoteException e) {
			System.out.println("java RMI registry already exists.");
		}

		while (true) {
			try {
				String client = "rmi://" + clientIp + rmiClient;
				Naming.rebind(client, new Client());
				System.out.println("Client is ready.");
				break;
			} catch (Exception e) {
				System.out.println("Client failed");
				e.printStackTrace();
				break;
			}
		}

		String connectCatalog = "rmi://" + catalogIp + rmiCatalog;
		String connectBilling = "rmi://" + billingIp + rmiBilling;
		String connectWarehouse = "rmi://" + warehouseIp + rmiWarehouse;

		try {
			System.out.println("Connecting to catalog at : " + connectCatalog);
			catalog = (CatalogInterface) Naming.lookup(connectCatalog);
			System.out.println("Catalog is ready.");
		} catch (Exception e) {
			System.out.println("Catalog is failed: ");
			e.printStackTrace();
		}

		try {
			System.out.println("Connecting to catalog at : " + connectBilling);
			billing = (BillingInterface) Naming.lookup(connectBilling);
			System.out.println("Billing is ready.");
		} catch (Exception e) {
			System.out.println("Billing is failed: ");
			e.printStackTrace();
		}

		try {
			System.out.println("Connecting to catalog at : " + connectWarehouse);
			warehouse = (WarehouseInterface) Naming.lookup(connectWarehouse);
			System.out.println("Warehouse is ready.");
		} catch (Exception e) {
			System.out.println("Warehouse is failed: ");
			e.printStackTrace();
		}

		String[] formaPagamento = { "Crédito", "Débito", "Dinheiro" };
		int randomPagamento;
		String[] endereco = { "casa 1", "casa 2", "casa 3", "casa 4" };
		int randomEndereco;
		String[] produtos = { "Meia", "Sapato", "Livro", "Notebook", "Mouse" };
		int randomProduto;

		for (int i = 0; i < qtdVendas; i++) {
			int result = new Random().nextInt(4);
			int preco;
			int randomFor = new Random().nextInt(15);

			switch (result) {
			case 0:
				for (int j = 0; j < randomFor; j++) {
					randomPagamento = new Random().nextInt(3);
					randomProduto = new Random().nextInt(5);
					randomEndereco = new Random().nextInt(4);
					preco = new Random().nextInt(5000);
					System.out.println(catalog.visualizar(j));
					System.out.println(warehouse.despachar(j, endereco[randomEndereco]));
					System.out.println(billing.vender(j, preco, formaPagamento[randomPagamento]));
					System.out.println(catalog.remover(j));
				}
				break;

			case 1:
				for (int j = 0; j < new Random().nextInt(15); j++) {
					randomPagamento = new Random().nextInt(3);
					System.out.println(catalog.visualizar(j));
					System.out.println(warehouse.separar(j));
					System.out.println(billing.vender(j, new Random().nextInt(5000), formaPagamento[randomPagamento]));
					System.out.println(billing.cancelarVenda(j));
				}
				break;

			case 2:
				for (int j = 0; j < new Random().nextInt(15); j++) {
					randomPagamento = new Random().nextInt(3);
					randomProduto = new Random().nextInt(5);
					randomEndereco = new Random().nextInt(4);
					System.out.println(catalog.registrar(j, produtos[randomProduto]));
					System.out.println(catalog.visualizar(j));
					System.out.println(billing.vender(j, new Random().nextInt(5000), formaPagamento[randomPagamento]));
					System.out.println(warehouse.separar(j));
					System.out.println(warehouse.despachar(j, endereco[randomEndereco]));
				}
				break;

			default:
				for (int j = 0; j < new Random().nextInt(15); j++) {
					randomPagamento = new Random().nextInt(3);
					System.out.println(billing.vender(j, new Random().nextInt(5000), formaPagamento[randomPagamento]));
					System.out.println(warehouse.separar(j));
					System.out.println(warehouse.extraviar(j));
					System.out.println("O pedido de número " + j + " " + "caiu do caminhão xD");
				}
				break;

			}
		}

		System.exit(1);
	}
}