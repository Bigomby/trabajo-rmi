package serial;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

/*
 *  Permite enviar datos por puerto serie a un Arduino.
 * 
 *  Concretamente hemos implementado los métodos 'startAlarm()'
 *  y 'stopAlarm()' que envía los comandos para activar y desactivar
 *  la alarma en el Arduino.
 */

public class SerialWriter {
	SerialPort serialPort;
	/** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { "/dev/tty.usbserial-A9007UX1",
			"/dev/ttyACM0", "COM35" };
	private OutputStream output;
	private static final int TIME_OUT = 2000;
	private static final int DATA_RATE = 9600;

	/*
	 * public static void main(String[] args) throws Exception { SerialWriter
	 * main = new SerialWriter(); main.initialize(); Thread t = new Thread() {
	 * public void run() { // the following line will keep this app alive for
	 * 1000 seconds, // waiting for events to occur and responding to them
	 * (printing // incoming messages to console). try { Thread.sleep(1000000);
	 * } catch (InterruptedException ie) { } } }; t.start();
	 * System.out.println("Started"); }
	 */

	public SerialWriter() {
		initialize();
		System.out.println("Started");
	}

	@SuppressWarnings("rawtypes")
	public void initialize() {
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		// First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum
					.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}

		try {
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

			// open the streams
			output = serialPort.getOutputStream();
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	public void startAlarm() {
		try {
			System.out.println("Activando alarma");
			this.output.write('!');
			this.output.write('0');
			this.output.write('0');
			this.output.write('.');
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stopAlarm() {
		try {
			System.out.println("Desactivado alarma");
			this.output.write('!');
			this.output.write('0');
			this.output.write('1');
			this.output.write('.');
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}