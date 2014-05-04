package Interfaces;

import java.rmi.RemoteException;


public interface Alarm extends Device {
	public int getStatus() throws RemoteException;
	public void setStatus(int status) throws RemoteException;
}
