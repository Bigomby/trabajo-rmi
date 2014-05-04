package Server;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.Enumeration;

public class RMIClientServer implements Runnable {

	ClientServiceImpl clientService;

	RMIClientServer(ClientServiceImpl clientService) {
		this.clientService = clientService;
	}

	public void run() {
		String ip = getIP();
		System.setProperty("java.rmi.server.hostname", ip);

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}
		try {

			Naming.rebind("//" + ip + ":" + "54321" + "/Client", clientService);

			System.out.println("Servidor de clientes iniciado en " + ip);

		} catch (RemoteException e) {
			System.out.println("Error de comunicacion: " + e.toString());
			System.exit(1);
		} catch (Exception e) {
			System.out.println("Excepcion en cliente:");
			e.printStackTrace();
			System.exit(1);
		}
	}

	private String getIP() {
		String ip = null;

		@SuppressWarnings("rawtypes")
		Enumeration d;
		try {
			d = NetworkInterface.getNetworkInterfaces();

			while (d.hasMoreElements()) {
				NetworkInterface n = (NetworkInterface) d.nextElement();
				Enumeration<InetAddress> ee = n.getInetAddresses();
				while (ee.hasMoreElements()) {
					InetAddress i = (InetAddress) ee.nextElement();
					if (i.getHostAddress().toString().startsWith("192.168.1.")) {
						ip = i.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}

		return ip;
	}
}
