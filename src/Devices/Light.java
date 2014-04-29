package Devices;

/*
 * Dispositivo: Bombilla
 * 
 * Representa una bombilla, puede estar encendida o apagada. También puede tomar valores
 * intermedios para controlar la intensidad de la iluminación.
 */

public class Light {

	private int intensity;

	// Constructor. La bombilla está apagada cuando se instancia.
	public Light() {
		intensity = 0;
	}

	// Consulta la intensidad de la bombilla.
	public int getIntensity() {
		return intensity;
	}

	// Ajusta la intensidad de la bombilla. Sólo acepta valores entre 0 y 255.
	public void setIntensity(int intensity) {
		if (intensity > 0 && intensity < 255) {
			this.intensity = intensity;
		}
	}
}
