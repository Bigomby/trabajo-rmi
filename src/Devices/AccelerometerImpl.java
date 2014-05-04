package Devices;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Services.Accelerometer;
import Services.Device;
import Services.DeviceService;

/*
 * Dispositivo: Acelerómetro
 * Activa la alarma en caso de detectar movimiento
 */

public class AccelerometerImpl extends UnicastRemoteObject implements
		Accelerometer {

	private static final long serialVersionUID = 1L;
	private static DeviceService srv;

	public static void main(String[] args) throws RemoteException,
			InterruptedException {
		if (args.length != 1) {
			System.out.println("Uso: AccelerometerImpl <host>");
		} else {
			System.setProperty("java.security.policy", "file:policies.policy");
			connect(args[0]);
			new AccelerometerImpl();
		}
	}

	// Constructor. La alarma está apagada cuando se instancia.
	public AccelerometerImpl() throws RemoteException, InterruptedException {
		srv.addDevice(this);
		Runtime.getRuntime().addShutdownHook(new ShutdownHookAccelerometer(srv, this));
		// TODO Código del acelerómetro
	}

	private static void connect(String ip) {
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

class ShutdownHookAccelerometer extends Thread {

	private DeviceService srv;
	private Device device;

	public ShutdownHookAccelerometer(DeviceService srv, Device device) {
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