package Devices;

/*
 * Dispositivo: Puerta garaje
 * Controla el estado de la cerradura
 * El valor puede ser 0 (cerrado) o 1 (abierto)
 */

public class Gate {

	private int status;

	// Constructor. La puerta está cerrada cuando se instancia.
	public Gate() {
		status = 0;
	}

	// Consulta el estado de la cerradura.
	public int getStatus() {
		return status;
	}

	// Ajusta el estado de la cerradura
	public void setstatus(int status) {
			this.status = status;
	}
}