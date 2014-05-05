package server;

import interfaces.Device;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/*
 * Servidor
 * 
 * Recibe las conexiones tanto de los dispositivos controlables como de los dispositivos
 * controladores y les ofrece servicios.
 * 
 * Tiene una lista de los dispositivos controlables. Cuando un dispositivo controlador
 * se conecta se le envía la lista de dispositivos controlables.
 * 
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
			Runnable controllableServer = new RMIControllableServer(
					listenerService);

			new Thread(controllerServer).start();
			new Thread(controllableServer).start();

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}