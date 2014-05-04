package server;

import interfaces.Device;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import services.ControllerService;


/*
 * Presta servicio al cliente.
 */
public class ControllerServiceImpl extends UnicastRemoteObject implements
		ControllerService, Serializable {

	private static final long serialVersionUID = 1L;
	List<Device> connectedDevices;

	ControllerServiceImpl(List<Device> devices) throws RemoteException {
		this.connectedDevices = devices;
	}
	
	public List<Device> getControllableDevices() throws RemoteException {
		return connectedDevices;
	}
}
