# Mule Edge MQTT Demo

Reads QR Codes from an USB camera plugged-in a Raspberry Pi.

## Running the demo

Start Mule with: `bin/mule start -M-DmqttBrokerServerUri=tcp://SOME_HOST:1883 -M-DqrCodeReaderId=SOME_ID`

Start the webcam-to-file bridge with: `zbarcam --nodisplay | dump_sysin.py` (the Python script is packaged in the Mule application)
