package Services;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ClientService extends Remote {
	public List<Device> getDevices() throws RemoteException;
}
