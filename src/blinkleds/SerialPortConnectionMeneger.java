/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blinkleds;


import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

/**
 *
 * @author adrianna
 */
public class SerialPortConnectionMeneger {
    
    public static final int DATA_RATE = 9600;
    public static final int TIMEOUT = 2000;
    
    private SerialPort serialPort;
    private OutputStream output;
    private String portName;

    SerialPortConnectionMeneger (String portName) {
        this.serialPort = null;
        this.output = null;
        this.portName = portName;
    }
    
    public void initSerial() {
        
    CommPortIdentifier serialPortId = null;
    Enumeration portList = CommPortIdentifier.getPortIdentifiers();

    while (portList.hasMoreElements()) {
        serialPortId = (CommPortIdentifier) portList.nextElement();
        
        if (serialPortId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
            System.out.println("Found " + serialPortId.getName());
                if (serialPortId.getName().equals(portName)) {
                    openPort(serialPortId);
                    initOutputStream();
                }
        }
      }
    }
    
    private void openPort(CommPortIdentifier serialPortId) {
    try {
        serialPort = (SerialPort) serialPortId.open(this.getClass().getName(), TIMEOUT);
        serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8,
                                    SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

    } catch (PortInUseException | UnsupportedCommOperationException e) {
        e.printStackTrace();
    }
}
    private void initOutputStream() {

    try {
        output = serialPort.getOutputStream();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    
    public void sendBytes(int setLed) {
    try { 
        output.write(setLed);
        
    } catch (IOException e) {
        e.printStackTrace();
    }
}
        
    public void closeSerial()  {
        serialPort.close();
    }
}
