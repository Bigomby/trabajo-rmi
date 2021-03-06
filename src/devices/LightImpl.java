package devices;

import interfaces.Device;
import interfaces.Light;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import services.ControllableService;


/*
 * Dispositivo: Bombilla
 * 
 * Representa una bombilla, puede estar encendida o apagada. También puede tomar valores
 * intermedios para controlar la intensidad de la iluminación.
 */

public class LightImpl extends UnicastRemoteObject implements Light {

	private static final long serialVersionUID = 1L;
	private int intensity;
	private static ControllableService srv;

	public static void main(String[] args) throws RemoteException {
		if (args.length != 1) {
			System.out.println("Uso: Light <host>");
		} else {
			System.setProperty("java.security.policy", "file:policies.policy");
			connect(args[0]);
			new LightImpl(args[0]);
		}
	}

	LightImpl(String ip) throws RemoteException {
		intensity = 0;
		srv.addDevice(this);
		Runtime.getRuntime().addShutdownHook(new ShutdownHookLight(srv, this));
	}

	public void turnOn() {
		intensity = 255;
		System.out.println("Bombilla encendida");
	}

	public void turnOff() {
		intensity = 0;
		System.out.println("Bombilla apagada");
	}

	public void toggle() {
		if (intensity > 0) {
			intensity = 0;
			System.out.println("Bombilla apagada");
		} else {
			intensity = 255;
			System.out.println("Bombilla encendida");
		}
	}

	public void setIntensity(int intensity) throws RemoteException {
		if (intensity < 0 && intensity > 255) {
			this.intensity = intensity;
			System.out.println("Intensidad de la bombilla ajustada a: "
					+ intensity);
		}
	}

	private static void connect(String ip) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			System.out.println("//" + ip + ":" + "54321"
					+ "/Controllable");
			srv = (ControllableService) Naming.lookup("//" + ip + ":" + "54321"
					+ "/Controllable");
		} catch (RemoteException e) {
			System.err.println("Error de comunicacion: " + e.toString());
		} catch (Exception e) {
			System.err.println("Excepcion en Light:");
			e.printStackTrace();
		}
	}
}

class ShutdownHookLight extends Thread {

	private ControllableService srv;
	private Device device;

	public ShutdownHookLight(ControllableService srv, Device device) {
		this.srv = srv;
		this.device = device;
	}

	public void run() {
		try {
			srv.removeDevice(device);
			System.out.println("Dispositivo desconectado.");
		} catch (RemoteException e) {
			System.err.println("Error de comunicacion: " + e.toString());
		}
	}
}
