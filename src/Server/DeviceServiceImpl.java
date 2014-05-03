package Server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Devices.Device;
import Services.DeviceService;

public class DeviceServiceImpl extends UnicastRemoteObject implements
		DeviceService, Serializable {

	private static final long serialVersionUID = 1L;
	
	protected DeviceServiceImpl() throws RemoteException {
		
	}

	public void addDevice(Device device) {
		Server.devices.add(device);
	}

	public void test() {
		System.out.println("Hola Mundo, soy un dispositivo");
	}
}
