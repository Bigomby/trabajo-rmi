package Services;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import Interfaces.Device;

public interface ControllerService extends Remote {
	public List<Device> getControllableDevices() throws RemoteException;
}
