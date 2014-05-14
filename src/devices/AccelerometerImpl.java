package devices;

import interfaces.Alarm;
import interfaces.Device;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Iterator;

import serial.SerialListener;
import services.ControllerService;

/*
 * Dispositivo: Acelerómetro
 * Activa la alarma en caso de detectar movimiento
 */

public class AccelerometerImpl {

	private static ControllerService srv;

	public static void main(String[] args) throws RemoteException,
			InterruptedException {
		if (args.length == 2) {
			System.setProperty("java.security.policy", "file:policies.policy");
			connect(args[0]);

			int option = Integer.parseInt(args[1]);
			if (option == 1) {
				try {
					BufferedReader bufferRead = new BufferedReader(
							new InputStreamReader(System.in));

					while (true) {
						System.out.println("");
						System.out
								.println("Pulsa intro para detectar movimiento...");
						String s = bufferRead.readLine();
						if (s.length() == 0)
							alert();
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (option == 2) {
				SerialListener listener = new SerialListener();
				listener.initialize();
				Thread t = new Thread() {
					public void run() {
						// the following line will keep this app alive for 1000 seconds,
						// waiting for events to occur and responding to them (printing
						// incoming messages to console).
						try {
							Thread.sleep(1000000);
						} catch (InterruptedException ie) {
						}
					}
				};
				t.start();
				System.out.println("Started");
			}
		} else {
			System.out.println("Uso: AccelerometerImpl <host> <nodo>");
		}
	}

	public static void alert() throws RemoteException {
		Alarm alarm;
		Iterator<Device> it = srv.getControllableDevices().iterator();
		Device device;
		boolean found = false;

		System.out.println("¡¡BZZZZ!! ¡Detectado movimiento!");
		System.out.println("");
		if (!it.hasNext()) {
			System.out.println("Ninguna alarma disponible.");
		}
		while (it.hasNext()) {
			device = it.next();
			if (device instanceof Alarm) {
				found = true;
				alarm = (Alarm) device;
				alarm.start();
			}
			if (!found) {
				System.out.println("No se han encontrado alarmas");
			}
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