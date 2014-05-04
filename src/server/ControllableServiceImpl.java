package server;

import interfaces.Device;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import services.ControllableService;


/*
 * Presta servicio a los dispositivos controlables.
 */
public class ControllableServiceImpl extends UnicastRemoteObject implements
		ControllableService {

	private static final long serialVersionUID = 1L;
	List<Device> connectedDevices;

	ControllableServiceImpl(List<Device> devices) throws RemoteException {
		this.connectedDevices = devices;
	}

	public void addDevice(Device device) throws RemoteException {
		connectedDevices.add(device);
		System.out.println("Dispositivo conectado");
	}

	public void removeDevice(Device device) throws RemoteException {
		connectedDevices.remove(device);	
		System.out.println("Dispositivo desconectado");
	}
}
