package Server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import Services.Device;
import Services.DeviceService;

public class DeviceServiceImpl extends UnicastRemoteObject implements
		DeviceService, Serializable {

	private static final long serialVersionUID = 1L;
	List<Device> connectedDevices;

	DeviceServiceImpl(List<Device> connectedDevices) throws RemoteException {
		this.connectedDevices = connectedDevices;
	}

	/*
	 * Permite a los dispositivos darse de alta en el servidor para poder ser
	 * controlados por los cliente o enviar notificaciones.
	 */
	public void addDevice(Device device) throws RemoteException {
		connectedDevices.add(device);
		System.out.println("Nuevo dispositivo conectado");
	}
	
	public void removeDevice(Device device) throws RemoteException {
		connectedDevices.remove(device);
		System.out.println("Dispositivo desconectado");
	}
}
