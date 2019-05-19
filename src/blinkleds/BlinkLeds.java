package blinkleds;

public class BlinkLeds {
    
    private static double frequencies[];
    
    public static void main(String[] args) {
        AudioMeneger audioMeneger = new AudioMeneger();
        audioMeneger.openFile("bach.wav");
        frequencies = audioMeneger.retriveWavFile();
        audioMeneger.playAudio();
        
        
        //PROPER SERIAL NAME NEEDS TO BE PROVIDED BEFORE TESTING!
        SerialPortConnectionMeneger serialConnector = new SerialPortConnectionMeneger("dev/ttysO");       
        serialConnector.initSerial();
        int data;
        for (int i = 0; i < frequencies.length; i++) {

           data = (int) (frequencies[i] * 1000);
                   System.out.println(data);

           serialConnector.sendBytes(data);
    }
    
        serialConnector.closeSerial();
    }
}
