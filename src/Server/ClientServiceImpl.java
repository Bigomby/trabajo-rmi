package Server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import Services.ClientService;
import Services.Device;
import Services.Light;

public class ClientServiceImpl extends UnicastRemoteObject implements
		ClientService, Serializable {

	private static final long serialVersionUID = 1L;
	List<Device> connectedDevices;

	public ClientServiceImpl(List<Device> devices) throws RemoteException {
		this.connectedDevices = devices;
	}
	
	public void setLightIntensity(Light light, int intensity) throws RemoteException {
		System.out.println("Intentando configurar la intensidad a: "
				+ intensity);
		light.setIntensity(intensity);
	}

	public List<Device> getDevices() throws RemoteException {
		return connectedDevices;
	}
}
