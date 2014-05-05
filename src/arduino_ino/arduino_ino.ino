char messageBuffer[12], cmd[3];
boolean debug = false;
int index = 0;
const int ledPin =  13;      // the number of the LED pin
const int buzzPin = 12;

// Variables will change :
int ledState = LOW;             // ledState used to set the LED

// Generally, you shuould use "unsigned long" for variables that hold time
// The value will quickly become too large for an int to store
unsigned long previousMillis = 0;        // will store last time LED was updated

// constants won't change :
const long interval = 500;           // interval at which to blink (milliseconds)

int active_alarm = 0;

void setup(){
  Serial.begin(9600);
  pinMode(13, OUTPUT);
  Serial.write("Power On");
}

void loop() {
  
  alarm();
  
  while(Serial.available() > 0) {
    char x = Serial.read();
    if (x == '!')
      index = 0;      // start
    else if (x == '.') 
      process(); // end
    else messageBuffer[index++] = x;
  } 
}   

void process() {
  index = 0;
  
  strncpy(cmd, messageBuffer, 2);
  cmd[2] = '\0';

  int cmdid = atoi(cmd);
  
  switch(cmdid) {
    case 0:
      active_alarm = 1;
      break;
    case 1:
      active_alarm = 0;
      break;
    default:      
      break;
  }
}

void alarm() {
  if (active_alarm > 0){
    unsigned long currentMillis = millis();
 
    if(currentMillis - previousMillis >= interval) {
      // save the last time you blinked the LED 
      previousMillis = currentMillis;   
  
      // if the LED is off turn it on and vice-versa:
      if (ledState == LOW) {
        ledState = HIGH;
      }
      else {
        ledState = LOW;
      }
  
      // set the LED with the ledState of the variable:
      digitalWrite(ledPin, ledState);
      digitalWrite(buzzPin, ledState);
    } 
  } else {
    digitalWrite(ledPin, LOW);
  }
}

