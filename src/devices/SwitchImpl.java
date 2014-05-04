package devices;

import interfaces.Device;
import interfaces.Light;
import interfaces.Switch;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import services.ControllerService;


/*
 * Dispositivo: Acelerómetro
 * Activa la alarma en caso de detectar movimiento
 */

public class SwitchImpl implements Switch {

	private static ControllerService srv;

	public static void main(String[] args) throws RemoteException,
			InterruptedException {
		System.setProperty("java.security.policy", "file:policies.policy");
		if (args.length > 1) {
			connect(args[0]);
			if (args[1].contentEquals("on"))
				turnOn();
			if (args[1].contentEquals("off"))
				turnOff();
			if (args[1].contentEquals("toggle"))
				toggle();
			if (args[1].contentEquals("set"))
				set(Integer.parseInt(args[2]));
		} else {
			System.out.println("Uso: SwitchImpl <on|off|toggle|set> <value>");
		}

	}

	private static void set(int intensity) throws RemoteException {
		boolean found = false;
		List<Device> devices = srv.getControllableDevices();
		Iterator<Device> it = devices.iterator();
		Device device;
		Light light;

		if (devices.isEmpty()) {
			System.out.println("Ningún dispositivo disponible.");
			System.exit(0);
		}

		while (it.hasNext()) {
			device = it.next();
			if (device instanceof Light) {
				found = true;
				System.out.println("Configurando intensidad a " + intensity);
				light = (Light) device;
				light.setIntensity(intensity);
			}
		}
		if (!found) {
			System.out.println("No se han encontrado bombillas");
		}

	}

	private static void turnOn() throws RemoteException {
		boolean found = false;
		List<Device> devices = srv.getControllableDevices();
		Iterator<Device> it = devices.iterator();
		Device device;
		Light light;

		if (devices.isEmpty()) {
			System.out.println("Ningún dispositivo disponible.");
			System.exit(0);
		}

		while (it.hasNext()) {
			device = it.next();
			if (device instanceof Light) {
				found = true;
				System.out.println("Encendiendo bombilla");
				light = (Light) device;
				light.turnOn();
			}
		}
		if (!found) {
			System.out.println("No se han encontrado bombillas");
		}
	}

	private static void turnOff() throws RemoteException {
		boolean found = false;
		List<Device> devices = srv.getControllableDevices();
		Iterator<Device> it = devices.iterator();
		Device device;
		Light light;

		if (devices.isEmpty()) {
			System.out.println("Ningún dispositivo disponible.");
			System.exit(0);
		}

		while (it.hasNext()) {
			device = it.next();
			if (device instanceof Light) {
				found = true;
				System.out.println("Apagando bombilla");
				light = (Light) device;
				light.turnOff();
			}
		}
		if (!found) {
			System.out.println("No se han encontrado bombillas");
		}
	}

	private static void toggle() throws RemoteException {
		boolean found = false;
		List<Device> devices = srv.getControllableDevices();
		Iterator<Device> it = devices.iterator();
		Device device;
		Light light;

		if (devices.isEmpty()) {
			System.out.println("Ningún dispositivo disponible.");
			System.exit(0);
		}

		while (it.hasNext()) {
			device = it.next();
			if (device instanceof Light) {
				found = true;
				System.out.println("Apagando/Encendiendo bombilla");
				light = (Light) device;
				light.toggle();
			}
		}
		if (!found) {
			System.out.println("No se han encontrado bombillas");
		}
	}

	private static void connect(String ip) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			srv = (ControllerService) Naming.lookup("//" + ip + ":" + "54321"
					+ "/Controller");
		} catch (RemoteException e) {
			System.err.println("Error de comunicacion: " + e.toString());
		} catch (Exception e) {
			System.err.println("Excepcion en Light:");
			e.printStackTrace();
		}
	}
}