package server;

import interfaces.Device;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/*
 * Servidor
 * 
 * Recibe las conexiones tanto de los clientes como de los diferentes dispositivos y se encarga
 * de reenviar las peticiones. También recibe notificaciones de los dispositivos en el caso  de 
 * que las envíen y las envía a los clientes que estén suscritos a éstas.
 */

public class Server {

	public static void main(String[] args) {
		try {
			System.setProperty("java.security.policy", "file:policies.policy");

			List<Device> connectedDevices = (List<Device>) Collections
					.synchronizedList(new LinkedList<Device>());

			ControllerServiceImpl clientService = new ControllerServiceImpl(
					connectedDevices);
			ControllableServiceImpl listenerService = new ControllableServiceImpl(
					connectedDevices);

			Runnable controllerServer = new RMIControllerServer(clientService);
			Runnable controllableServer = new RMIControllableServer(listenerService);

			new Thread(controllerServer).start();
			new Thread(controllableServer).start();

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}