package Devices;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Services.Device;
import Services.DeviceService;
import Services.Light;

/*
 * Dispositivo: Bombilla
 * 
 * Representa una bombilla, puede estar encendida o apagada. También puede tomar valores
 * intermedios para controlar la intensidad de la iluminación.
 */

public class LightImpl extends UnicastRemoteObject implements Light {

	private static final long serialVersionUID = 1L;
	private int intensity;
	private static DeviceService srv;

	public static void main(String[] args) throws RemoteException {
		if (args.length != 1) {
			System.out.println("Uso: Light <host>");
		} else {
			System.setProperty("java.security.policy", "file:policies.policy");
			connect(args[0]);
			new LightImpl(args[0]);
		}
	}

	LightImpl(String ip) throws RemoteException {
		intensity = 0;
		srv.addDevice(this);
		Runtime.getRuntime().addShutdownHook(new ShutdownHookLight(srv, this));
	}

	public void setIntensity(int intensity) throws RemoteException {
		this.intensity = intensity;
		System.out
				.println("Intensidad de la bombilla ajustada a: " + intensity);
		// TODO configurar la intensidad en el arduino
	}
	
	public int getIntensity() throws RemoteException {
		return intensity;
	}

	private static void connect(String ip) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			srv = (DeviceService) Naming.lookup("//" + ip + ":" + "54321"
					+ "/Device");
		} catch (RemoteException e) {
			System.err.println("Error de comunicacion: " + e.toString());
		} catch (Exception e) {
			System.err.println("Excepcion en Light:");
			e.printStackTrace();
		}
	}
}

class ShutdownHookLight extends Thread{
	
	private DeviceService srv;
	private Device device;
	
    public ShutdownHookLight (DeviceService srv, Device device){
    	this.srv = srv;
    	this.device = device;
    }

    public void run() {
        try{
            srv.removeDevice(device);
            System.out.println("Dispositivo desconectado.");
        }
        catch(RemoteException e){
            System.err.println("Error de comunicacion: " + e.toString());
        }
    }
}
