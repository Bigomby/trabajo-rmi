#Simulación de Sistema Domótico

## Introducción
A continuación exponemos el funcionamiento de un simulador de un sistema de domótica diseñado para la asignatura Sistemas Distribuidos y Servicios Web.

El sistema se componen de varios dispositivos que se comunican entre ellos usando RMI (Remote Method Invocation). En nuestro ejemplo, los sitemas finales también se comunican por puerto serie con un microcontrolador que permite ver de forma más visual el control de los dispositivos.

Vamos a ver cada componente del sistema y describir su funcionamiento.

## Servidor

En primer lugar tenemos nuestro servidor RMI. Su labor es registrar los diferentes dispositivos que pueden ser controlados, en nuestro caso vamos a usar una bombilla y una alarma, y ofrecer unos servicios a los controladores para que puedan interactuar con dichos dispositivos.

El servidor usa dos hilos sincronizados para levantar dos servicios diferentes, uno en `//ip:54321/Controller` y otro en `//ip:54321/Controllable`. De esta forma podemos atender simultáneamente a dispositivos controladores y controlables.

El servidor tiene una lista de los dispositivos controlables conectados en cada momento y que pueden solicitar en cualquier momento los dispositivos controladores.

Como hay dos tipos de dispositivos se implementan dos servicio:

- `ControllableService`: Para dispositivos que serán controlados.
```
public interface ControllableService extends Remote {
	public void addDevice(Device device) throws RemoteException;
	public void removeDevice(Device device) throws RemoteException;
}
```
- `ControllerService`: Para controladores.
```
public interface ControllerService extends Remote {
	public List<Device> getControllableDevices() throws RemoteException;
}
```

## Dispositivos controlables

Como hemos visto anteriormente, tenemos dos tipos de dispositivos. Vamos a ver los dispositivos controlables.

Este tipo de dispositivos no envían información al servidor. Cuando se instancian se conectarán al servidor y se darán de alta en él añadíendose a la lista de dispositivos conectados. Al terminarse la ejecución del código de estos dispositivos serán dados de baja del servidor. Podemos tener tantos dispositivos como queramos.

En nuestro ejemplo hemos definido dos tipos de dispositivos controlables, se podrían haber definido muchos más, pero para una demostración será suficiente.

### Light

El dispositivo controlable `LightImpl` es el más simple. Representa una bombilla que puede estar apagada, encendida o, en genera, tomar un valor entre 0 y 255 que representará la intensidad. Implementa la interfaz `Light`.
```
public interface Light extends Device {
	void setIntensity(int intensity) throws RemoteException;
	void turnOn() throws RemoteException;
	void turnOff() throws RemoteException;
	void toggle() throws RemoteException;
}
```
Como vemos, la interfaz `Light` a su vez hereda de la interfaz `Device`. Todos los dispositivos heredan de esta interfaz.

### AlarmImpl

Nuesto segundo dispositivo de ejemplo es un poco más complejo. Se trata de una alarma que puede tomar el valor 0 o cualquier valor mayor que 1. Cuando se instancia, al igual que todos los dispositivos controlables, se conecta al servidor y se da de alta en la lista de dipositivos conectados. 
Cuando la alarma es activada, empieza a imprimir por pantalla cada dos segundos la palabra "ALARMA", hasta que es desactivada. A continuacion vemos la intefaz implementada por `AlarmaImpl`:
```
public interface Alarm extends Device {
	public void start() throws RemoteException;
	public void stop() throws RemoteException;
}
```

## Dispositivos controladores

Estos dispositivos no reciben información, sólo envian órdenes. Cuando quieran realizar una acción se conectarán al servidor, obtendrán una lista de los dispositivos conectados y buscarán aquel con el que quieran interactuar. Vamos a ver los dos que hemos definido.

### Switch

Básicamente es un interruptor. Su tarea es buscar las bombillas conectadas y ejecutar alguna acción sobre ellas, gracias a que conoce la interfaz que implementa la bombilla.

Los dispositivos controladores no necesitan darse de alta en ningún momento ni implementar ninguna interfaz. Su funcionamiento es parecido al de un cliente simple.

### Accelerometer

Representa un acelerómetro que al detectar un movimiento activaría la alarma. El funcionamiento es análogo al del switch. Este dispositivo esta siendo ejecutado monitorizando un evento y al producirse lanzará una conexión al servidor activando las alarmas que estén en ese momento conectadas al servidor.

Tampoco se da de alta en el servidor, sólo lanza una petición al ocurrir el evento.

## Monitor
A parte de haber definido los dos tipos de dispositivos y el servidor también tenemos un `Monitor`. Dicho monitor nos permitiría comprobar el funcionamiento de los dispositivos ya que nos permite controlarlos a todos, por ejemplo, apagando y encendiendo alarmas  o luces.

También se podría configurar para recibir eventos y mostrarlos por pantallas. Sería como una mezcla de los dos tipos de dispositivos en uno, para monitorizar lo que está ocurriendo.

## Apéndice: Comunicaciones serie
Para ilustrar de forma más visual nuestro trabajo hemos usado microcontroladores que se comunican por puerto serie con los dispositivos finales a fin de que parezca un sistema más "real".

En nuestro ejemplo hemos usado un Arduino que se conecta por puerto serie con el objeto `AlarmaImpl`. Este objeto implementa unas librerías de comunicación serie con el Arduino y cuando la alarma es activada envía un comando y el Arduino hace parpadear un led rojo y emite un pitido.

Durante la presentacion del trabajo intentaremos poder realizar un ejemplo completo con un acelerómetro conectado a un equipo que haga sonar la alarma en otro equipo de forma remota.
