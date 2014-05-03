package Server;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import Services.ServiceDomotic;
import Services.ServiceDomoticImpl;

/*
 * Servidor
 * 
 * Recibe las conexiones tanto de los clientes como de los diferentes dispositivos y se encarga
 * de reenviar las peticiones. También recibe notificaciones de los dispositivos en el caso  de 
 * que las envíen y las envía a los clientes que estén suscritos a éstas.
 */

public class Server {
	public static void main(String[] args) {
		System.setProperty("java.security.policy","file:policies.policy");
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}
		try {
			ServiceDomotic srv = new ServiceDomoticImpl();
			Naming.rebind("rmi://localhost:" + args[0] + "/Domotic", srv);
			System.out.println("Servidor Iniciado");
		} catch (RemoteException e) {
			System.out.println("Error de comunicacion: " + e.toString());
			System.exit(1);
		} catch (Exception e) {
			System.out.println("Excepcion en Domotic:");
			e.printStackTrace();
			System.exit(1);
		}
	}
}
