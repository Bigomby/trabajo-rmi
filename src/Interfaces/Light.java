package Interfaces;

import java.rmi.RemoteException;

public interface Light extends Device {
	void setIntensity(int intensity) throws RemoteException;
	void turnOn() throws RemoteException;
	void turnOff() throws RemoteException;
	void toggle() throws RemoteException;
}
