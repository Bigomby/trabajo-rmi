package interfaces;

import java.rmi.RemoteException;


public interface Alarm extends Device {
	public void start() throws RemoteException;
	public void stop() throws RemoteException;
}
