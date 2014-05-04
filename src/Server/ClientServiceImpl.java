package Server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import Services.ClientService;
import Services.Device;

/*
 * Presta servicio al cliente.
 */
public class ClientServiceImpl extends UnicastRemoteObject implements
		ClientService, Serializable {

	private static final long serialVersionUID = 1L;
	List<Device> connectedDevices;

	ClientServiceImpl(List<Device> devices) throws RemoteException {
		this.connectedDevices = devices;
	}

	/*
	 * Devuelve al cliente una lista de los dispositivos que hay conectados al
	 * servidor actualmente.
	 */
	public List<Device> getDevices() throws RemoteException {
		return connectedDevices;
	}
}
