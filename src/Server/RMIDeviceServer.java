package Server;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.Enumeration;


public class RMIDeviceServer implements Runnable {
	
	DeviceServiceImpl deviceService;
	
	RMIDeviceServer(DeviceServiceImpl deviceService){
		this.deviceService = deviceService;
	}
	
	public void run() {
		String ip = getIP();
		System.setProperty("java.rmi.server.hostname", ip);

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}
		try {
			DeviceServiceImpl devices = new DeviceServiceImpl();
			Naming.rebind("//" + ip + ":" + "54321" + "/Device", devices);
			
			System.out.println("Servidor de dispositivos iniciado en " + ip);
			
		} catch (RemoteException e) {
			System.out.println("Error de comunicacion: " + e.toString());
			System.exit(1);
		} catch (Exception e) {
			System.out.println("Excepcion en Domotic:");
			e.printStackTrace();
			System.exit(1);
		}
	}

	private String getIP() {
		String ip = null;

		@SuppressWarnings("rawtypes")
		Enumeration d;
		try {
			d = NetworkInterface.getNetworkInterfaces();

			while (d.hasMoreElements()) {
				NetworkInterface n = (NetworkInterface) d.nextElement();
				Enumeration<InetAddress> ee = n.getInetAddresses();
				while (ee.hasMoreElements()) {
					InetAddress i = (InetAddress) ee.nextElement();
					if (i.getHostAddress().toString().startsWith("192.168.1.")) {
						ip = i.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}

		return ip;
	}
}
