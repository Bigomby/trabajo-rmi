package Services;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceDomotic extends Remote {
	public void test() throws RemoteException;
}
