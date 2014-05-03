package Server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import Devices.Device;
import Services.ClientService;

public class ClientServiceImpl extends UnicastRemoteObject implements
		ClientService, Serializable {

	private static final long serialVersionUID = 1L;

	public ClientServiceImpl() throws RemoteException {

	}

	public List<Device> getConnectedDevices() {
		return Server.devices;
	}

	public void test() throws RemoteException {
		System.out.println("Hola Mundo, soy un cliente");
	}
}
