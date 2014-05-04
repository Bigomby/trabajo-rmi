package Devices;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Services.DeviceService;
import Services.Light;

/*
 * Dispositivo: Bombilla
 * 
 * Representa una bombilla, puede estar encendida o apagada. También puede tomar valores
 * intermedios para controlar la intensidad de la iluminación.
 */

public class LightImpl extends UnicastRemoteObject implements Light {

	private static final long serialVersionUID = 1L;
	private int intensity;
	private DeviceService srv;

	public static void main(String[] args) throws RemoteException {
		if (args.length != 1) {
			System.out.println("Uso: Light <host>");
		} else {
			new LightImpl(args[0]);
		}
	}

	LightImpl(String ip) throws RemoteException {
		System.setProperty("java.security.policy", "file:policies.policy");
		intensity = 0;
		connect(ip);
		
		try {
			srv.addDevice(this);
		} catch (RemoteException e) {
			e.printStackTrace();
			System.out.println("No se ha podido añadir el dispositivo en el servidor");
		}
	}

	public void setIntensity(int intensity) throws RemoteException {
		this.intensity = intensity;
		System.out
				.println("Intensidad de la bombilla ajustada a: " + intensity);
		// TODO configurar la intensidad en el arduino
	}

	private void connect(String ip) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			srv = (DeviceService) Naming.lookup("//" + ip + ":" + "54321"
					+ "/Device");
		} catch (RemoteException e) {
			System.err.println("Error de comunicacion: " + e.toString());
		} catch (Exception e) {
			System.err.println("Excepcion en Light:");
			e.printStackTrace();
		}
	}
}
