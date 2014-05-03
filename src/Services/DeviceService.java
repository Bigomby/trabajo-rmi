package Services;

import java.rmi.Remote;
import java.rmi.RemoteException;

import Devices.Device;

public interface DeviceService extends Remote {
	public void addDevice(Device device) throws RemoteException;
	public void test() throws RemoteException;
}
