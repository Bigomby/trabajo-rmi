package devices;

import interfaces.Alarm;
import interfaces.Device;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import serial.SerialWriter;
import services.ControllableService;

/*
 * Dispositivo: Alarma
 * Controla el estado de la alarma
 * El valor puede ser 0 (apagada) o 1 (encendida)
 */

public class AlarmImpl extends UnicastRemoteObject implements Alarm {

	private static final long serialVersionUID = 1L;
	private int status;
	private static ControllableService srv;

	public static void main(String[] args) throws RemoteException,
			InterruptedException {
		if (args.length != 1) {
			System.out.println("Uso: AlarmImpl <host>");
		} else {
			System.setProperty("java.security.policy", "file:policies.policy");
			connect(args[0]);
			new AlarmImpl();
		}
	}

	// Constructor. La alarma está apagada cuando se instancia.
	public AlarmImpl() throws RemoteException, InterruptedException {
		boolean activa = false;
		status = 0;	
		srv.addDevice(this);
		Runtime.getRuntime().addShutdownHook(new ShutdownHookAlarm(srv, this));
		
		SerialWriter writer = new SerialWriter();
		while(true){
			if (status >= 1){
				System.out.println("ALARMA");
				if (!activa){
					writer.startAlarm();
					activa = true;
				}
			} else if (activa){
				activa = false;	
				writer.stopAlarm();
			}
			Thread.sleep(2000);
		}
	}

// Ajusta el estado de la alarm
	public void start() throws RemoteException {
		status = 1;
	}
	
	public void stop() throws RemoteException {
		status = 0;
	}

	private static void connect(String ip) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
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

class ShutdownHookAlarm extends Thread {

	private ControllableService srv;
	private Device device;

	public ShutdownHookAlarm(ControllableService srv, Device device) {
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