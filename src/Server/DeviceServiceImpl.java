package Server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import Services.Device;
import Services.DeviceService;

public class DeviceServiceImpl extends UnicastRemoteObject implements
		DeviceService, Serializable {

	private static final long serialVersionUID = 1L;
	List<Device> connectedDevices;
	
	DeviceServiceImpl(List<Device> connectedDevices) throws RemoteException {
		this.connectedDevices = connectedDevices;
	}

	@Override
	public void addDevice(Device device) throws RemoteException {
		connectedDevices.add(device);
	}
}
