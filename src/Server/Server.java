package Server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Devices.Device;

/*
 * Servidor
 * 
 * Recibe las conexiones tanto de los clientes como de los diferentes dispositivos y se encarga
 * de reenviar las peticiones. También recibe notificaciones de los dispositivos en el caso  de 
 * que las envíen y las envía a los clientes que estén suscritos a éstas.
 */

public class Server {

	static List<Device> devices;
	static ClientServiceImpl clientService;
	static DeviceServiceImpl deviceService;

	public static void main(String[] args) {
		try {
			System.setProperty("java.security.policy", "file:policies.policy");
			
			devices = Collections.synchronizedList(new ArrayList<Device>());

			clientService = new ClientServiceImpl();
			deviceService = new DeviceServiceImpl();

			Runnable clientServer = new RMIClientServer(clientService);
			Runnable deviceServer = new RMIDeviceServer(deviceService);

			new Thread(clientServer).start();
			new Thread(deviceServer).start();

		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}
}