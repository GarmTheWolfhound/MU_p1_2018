#include <ESP8266WiFi.h>
#include <SPI.h>

#define SHIFT_CLK_PIN 14
#define STORE_CLK_PIN 13
#define SERIAL_PIN 12
#define INTERRUPT_PIN 2
 
const char* ssid     = "AndroidAP";
const char* password = "exqc4047";
IPAddress server(192, 168, 43, 117);
int port = 12345;
int previousControlByte = -1;
WiFiClient client;
int imageArray[120][16];
int j, i;

void setup() {
  Serial.begin(115200);
  delay(100);
 
  // We start by connecting to a WiFi network
 
  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
  
  WiFi.begin(ssid, password);
  
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
 
  Serial.println("");
  Serial.println("WiFi connected");  
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());


  // Now we try to connect to the Server
  
  Serial.print("Attempting conenction to server... ");
  if(client.connect(server, port)){
        Serial.println("SUCSESS");
    }
    else{
        Serial.println("FAILED");
    }

}

void readFromServer() {
 
 //Trying to read from server
 
    if (client.available()) {
    int serverByte = client.read();
    if(serverByte != previousControlByte){
      Serial.println(serverByte);
      for(i = 0; i < 120; i++){
        Serial.print("{");
        for(j = 0; j < 16; j++){
          serverByte = client.read();
          imageArray[i][j] = serverByte;
          Serial.print(serverByte);
          Serial.print(", ");
        }
        Serial.println("}");
      }
    }
  }
  
  else if(!client.connected()){
    //Serial.println("disconnecting");
    client.stop();
  }
  

}

void loop(){
  
  readFromServer();
}

