package devices;

import interfaces.Accelerometer;
import interfaces.Alarm;
import interfaces.Device;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;

import services.ControllerService;


/*
 * Dispositivo: Acelerómetro
 * Activa la alarma en caso de detectar movimiento
 */

public class AccelerometerImpl implements Accelerometer {

	private static ControllerService srv;

	public static void main(String[] args) throws RemoteException,
			InterruptedException {
		if (args.length != 1) {
			System.out.println("Uso: AccelerometerImpl <host>");
		} else {
			System.setProperty("java.security.policy", "file:policies.policy");
			connect(args[0]);

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
		}
	}

	private static void alert() throws RemoteException {
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
				alarm.setStatus(1);
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