package Devices;
/*
 * Dispositivo: calentador de agua
 * Controla la temperatura del agua
 * El valor del calentador puede ser 0 (apagado) o 1 (encendido)
 */

public class Heater {

	private int status;

	// Constructor.
	public Blind() {
		status = 0;
	}

	// Consulta el estado del calentador 
	public int getStatus() {
		return status;
	}

	// Ajusta el status del calentador 
	public void setstatus(int status) {		
		this.status = status;	
	}
}