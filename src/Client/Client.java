package Client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import Services.ClientService;
import Services.Device;
import Services.Light;

/*
 * Cliente
 * 
 * Se conecta con un servidor para ver los dispositivos que hay activos. Puede enviar órdenes
 * para controlarlos o suscribirse a notificaciones de los dispositivos que los soporten.
 */

class Client {

	static public void main(String args[]) {
		System.setProperty("java.security.policy", "file:policies.policy");

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			ClientService srv = (ClientService) Naming.lookup("//" + args[0]
					+ ":" + "54321" + "/Client");

			List<Device> devices = srv.getDevices();
			Iterator<Device> it = devices.iterator();
			Device device;
			Light light;
			int intensity = Integer.parseInt(args[1]);
			
			while (it.hasNext()) {
				device = it.next();
				if (device instanceof Light) {
					System.out.println("Configurando intensidad a " + intensity);
					light = (Light) device;
					srv.setLightIntensity(light, intensity);
				}
			}

		} catch (RemoteException e) {
			System.err.println("Error de comunicacion: " + e.toString());
		} catch (Exception e) {
			System.err.println("Excepcion en Client:");
			e.printStackTrace();
		}
	}
}