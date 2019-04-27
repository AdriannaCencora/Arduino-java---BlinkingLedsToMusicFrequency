package blinkleds;
import gnu.io.*;
import java.io.IOException;

import java.io.OutputStream;
import java.util.Enumeration;


public class BlinkLeds {

    private SerialPort serial;
    private OutputStream output;

    public static final int DATA_RATE = 9600;
    public static final int TIMEOUT = 600;

    public static final int LIGHT_ON = 0x01;
    public static final int LIGHT_OFF = 0x02;
    
    
    private void initSerial() {
    CommPortIdentifier serialPortId = null;
    Enumeration enumComm = CommPortIdentifier.getPortIdentifiers();

    while (enumComm.hasMoreElements() && serialPortId == null) {
        serialPortId = (CommPortIdentifier) enumComm.nextElement();
    }

    try {
        serial = (SerialPort) serialPortId.open(this.getClass().getName(), TIMEOUT);
        serial.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8,
                                    SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

    } catch (PortInUseException | UnsupportedCommOperationException e) {
        e.printStackTrace();
    }
}
    private void closeSerial()  {
        serial.close();
    }
    
    
private void initOutputStream() {

    try {
        output = serial.getOutputStream();
    } catch (IOException e) {

        e.printStackTrace();
    }

}

private void sendBytes(int isSet) {
    try { 
        output.write(isSet);
        
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    public static void main(String[] args) {
        BlinkLeds blinkleds = new BlinkLeds();
        blinkleds.initSerial();
        blinkleds.initOutputStream();
        
        for (int i = 0; i < 10; i++) {
            blinkleds.sendBytes(LIGHT_ON);
        
                try {
                    Thread.sleep(1000);
                }
                catch(InterruptedException ex){
                    Thread.currentThread().interrupt();
                }
                
            blinkleds.sendBytes(LIGHT_OFF);     
    }
    
        blinkleds.closeSerial();
    }
}
