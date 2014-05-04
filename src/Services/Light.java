package Services;

import java.rmi.RemoteException;

public interface Light extends Device {
	void setIntensity(int intensity) throws RemoteException;
}
