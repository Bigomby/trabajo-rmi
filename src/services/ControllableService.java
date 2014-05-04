package services;

import interfaces.Device;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ControllableService extends Remote {
	public void addDevice(Device device) throws RemoteException;
	public void removeDevice(Device device) throws RemoteException;
}
