package Server;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.Enumeration;

import Services.ServiceDomoticImpl;

/*
 * Servidor
 * 
 * Recibe las conexiones tanto de los clientes como de los diferentes dispositivos y se encarga
 * de reenviar las peticiones. También recibe notificaciones de los dispositivos en el caso  de 
 * que las envíen y las envía a los clientes que estén suscritos a éstas.
 */

public class Server {

	public static void main(String[] args) throws UnknownHostException,
			SocketException {
		String ip = getIP();
		System.setProperty("java.security.policy", "file:policies.policy");
		System.setProperty("java.rmi.server.hostname", ip);

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}
		try {
			ServiceDomoticImpl srv = new ServiceDomoticImpl();
			Naming.rebind("//" + ip + ":" + "54321" + "/Domotic", srv);
			System.out.println("Servidor Iniciado en " + ip);
		} catch (RemoteException e) {
			System.out.println("Error de comunicacion: " + e.toString());
			System.exit(1);
		} catch (Exception e) {
			System.out.println("Excepcion en Domotic:");
			e.printStackTrace();
			System.exit(1);
		}
	}

	private static String getIP() {

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
