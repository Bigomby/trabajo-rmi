package Services;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientService extends Remote {
	public void test() throws RemoteException;
}
