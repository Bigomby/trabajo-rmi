package Services;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServiceDomoticImpl extends UnicastRemoteObject implements ServiceDomotic, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public ServiceDomoticImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void test() throws RemoteException {
		System.out.println("Hola Mundo");
	}
}
