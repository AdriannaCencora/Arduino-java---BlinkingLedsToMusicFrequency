package blinkleds;

public class BlinkLeds {
    public static final int LIGHT_ON = 0x01;
    public static final int LIGHT_OFF = 0x00;
    
    public static void main(String[] args) {
        
        //PROPER SERIAL NAME NEEDS TO BE PROVIDED BEFORE TESTING!
        SerialPortConnectionMeneger serialConnector = new SerialPortConnectionMeneger("dev/ttysO");
        serialConnector.initSerial();
        
        for (int i = 0; i < 10; i++) {
            serialConnector.sendBytes(LIGHT_ON);
        
                try {
                    Thread.sleep(1000);
                }
                catch(InterruptedException ex){
                    Thread.currentThread().interrupt();
                }
                
            serialConnector.sendBytes(LIGHT_OFF);     
    }
    
        serialConnector.closeSerial();
    }
}
