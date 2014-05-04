package Services;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DeviceService extends Remote {
	public void addDevice(Device device) throws RemoteException;
}
