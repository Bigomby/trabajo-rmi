package Client;

import java.rmi.Naming;
import java.rmi.RemoteException;

import Services.ClientService;

/*
 * Cliente
 * 
 * Se conecta con un servidor para ver los dispositivos que hay activos. Puede enviar órdenes
 * para controlarlos o suscribirse a notificaciones de los dispositivos que los soporten.
 */

class Client {

	static public void main(String args[]) {
		System.setProperty("java.security.policy","file:policies.policy");
		
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			ClientService srv = (ClientService) Naming.lookup("//"
					+ args[0] + ":" + "54321" + "/Client");

			srv.test();
		} catch (RemoteException e) {
			System.err.println("Error de comunicacion: " + e.toString());
		} catch (Exception e) {
			System.err.println("Excepcion en Client:");
			e.printStackTrace();
		}
	}
}