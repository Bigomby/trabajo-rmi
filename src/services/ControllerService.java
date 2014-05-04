package services;

import interfaces.Device;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface ControllerService extends Remote {
	public List<Device> getControllableDevices() throws RemoteException;
}
