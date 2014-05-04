package Devices;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Services.Alarm;
import Services.DeviceService;

/*
 * Dispositivo: Alarma
 * Controla el estado de la alarma
 * El valor puede ser 0 (apagada) o 1 (encendida)
 */

public class AlarmImpl extends UnicastRemoteObject implements Alarm {

	private static final long serialVersionUID = 1L;
	private int status;
	private static DeviceService srv;
	
	public static void main(String[] args) throws RemoteException, InterruptedException {
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
		status = 0;	
		srv.addDevice(this);
		while(true){
			if (status >= 1){
			System.out.println("ALARMA");
			}
			Thread.sleep(2000);
		}
	}

	// Consulta el estado de la alarma.
	public int getStatus() {
		return status;
	}

	// Ajusta el estado de la alarm
	public void setStatus(int status) {
			this.status = status;
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