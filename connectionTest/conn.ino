#include <ESP8266WiFi.h>
#include <SPI.h>
 
const char* ssid     = "HUAWEI";
const char* password = "shani111";
IPAddress server(192, 168, 43, 58);
int port = 12345;
WiFiClient client;

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

void loop() {
 
 //Trying to read from server
 
    if (client.available()) {
    char c = client.read();
    Serial.println(c);
  }else if(!client.connected()){
    //Serial.println("disconnecting");
    client.stop();
  }

}
