package Monitor;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import Interfaces.Alarm;
import Interfaces.Device;
import Interfaces.Light;
import Services.ControllerService;

/*
 * Cliente
 * 
 * Se conecta con un servidor para ver los dispositivos que hay activos. Puede enviar órdenes
 * para controlarlos o suscribirse a notificaciones de los dispositivos que los soporten.
 */

public class Monitor {

	static public void main(String args[]) {
		System.setProperty("java.security.policy", "file:policies.policy");

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			ControllerService srv = (ControllerService) Naming.lookup("//" + args[0]
					+ ":" + "54321" + "/Controller");
			boolean found = false;
			List<Device> devices = srv.getControllableDevices();
			Iterator<Device> it = devices.iterator();
			Device device;
			
			if (devices.isEmpty()){
				System.out.println("Ningún dispositivo disponible.");
				System.exit(0);
			}

			if (args[1].contentEquals("light")) {
				Light light;
				int intensity = Integer.parseInt(args[2]);
				while (it.hasNext()) {
					device = it.next();
					if (device instanceof Light) {
						found = true;
						System.out.println("Configurando intensidad a "
								+ intensity);
						light = (Light) device;
						light.setIntensity(intensity);
					}
				}
				if(!found){
					System.out.println("No se han encontrado bombillas");
				}
			} else if (args[1].contentEquals("alarm")) {
				Alarm alarm;
				int status = Integer.parseInt(args[2]);
				if(!it.hasNext()){
					System.out.println("Ninguna alarma disponible.");
				}
				while (it.hasNext()) {
					device = it.next();
					if (device instanceof Alarm) {
						found = true;
						alarm = (Alarm) device;
						alarm.setStatus(status);
						if (status == 0) {
							System.out.println("Alarma apagada");
						} else if (status >= 1) {
							System.out.println("Alarma encendida");
						}
					}
				}
				if(!found){
					System.out.println("No se han encontrado alarmas");
				}
			} else {
				System.out.println("Uso: Client <device> <arg>");
			}

		} catch (RemoteException e) {
			System.err.println("Error de comunicacion: " + e.toString());
		} catch (Exception e) {
			System.err.println("Excepcion en Client:");
			e.printStackTrace();
		}
	}
}