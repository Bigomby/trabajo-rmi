package Devices;

import java.rmi.Naming;
import java.rmi.RemoteException;

import Services.DeviceService;

/*
 * Dispositivo: Bombilla
 * 
 * Representa una bombilla, puede estar encendida o apagada. También puede tomar valores
 * intermedios para controlar la intensidad de la iluminación.
 */

public class Light implements Device {

	private static DeviceService srv;

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Uso: Light <host>");
		} else {
			@SuppressWarnings("unused")
			Light light = new Light(args[0]);
		}

	}

	Light(String ip) {
		try {
			connect(ip);
			srv.addDevice(this);
			srv.test();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private void connect(String ip) {
		System.setProperty("java.security.policy", "file:policies.policy");

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			srv = (DeviceService) Naming.lookup("//" + ip + ":" + "54321"
					+ "/Device");
		} catch (RemoteException e) {
			System.err.println("Error de comunicacion: " + e.toString());
		} catch (Exception e) {
			System.err.println("Excepcion en Device:");
			e.printStackTrace();
		}
	}
}
