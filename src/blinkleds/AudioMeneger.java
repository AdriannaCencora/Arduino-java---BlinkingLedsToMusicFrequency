/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blinkleds;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

/**
 *
 * @author adrianna
 */
public class AudioMeneger {
    private File soundFile;
    private static AudioInputStream audioStream;
    private static AudioFormat audioFormat;
    private Clip clip;
    private DataLine.Info info;
    
    private byte[] audioBytes;
    private int numberOfSamples;
     
    public void openFile(String filename) {
        try {
            soundFile = new File(filename);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void prepareAudioFile() {
        try {
            audioStream = AudioSystem.getAudioInputStream(soundFile);
            audioFormat = audioStream.getFormat();
            info = new DataLine.Info(Clip.class, audioFormat);
          
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
        }
    
    public void playAudio() {
        prepareAudioFile();
        try {
            clip = (Clip) AudioSystem.getLine(info);
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                }
            });
            clip.open(audioStream);
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }

    }

    public void readWavFile() {
        prepareAudioFile();
        try {
            int bytesPerFrame = audioStream.getFormat().getFrameSize();
            if (bytesPerFrame == AudioSystem.NOT_SPECIFIED) {
                bytesPerFrame = 1;
            }
            int numBytes = 1024 * bytesPerFrame;
            audioBytes = new byte[numBytes];
            int totalFramesRead = 0;
            
            numberOfSamples = audioBytes.length / bytesPerFrame;
            
            try {
                int numBytesRead = 0;
                int numFramesRead = 0;
                while ((numBytesRead = audioStream.read(audioBytes)) != -1) {
                    
                    numFramesRead = numBytesRead / bytesPerFrame;
                    totalFramesRead += numFramesRead;
                  
                }
                audioStream.close();
                
            } catch (Exception ex) {
               ex.printStackTrace(System.out);
            }

        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }

    public double[] retriveWavFile() {
        readWavFile();    
        return SignalProcessing.calculateFFT(audioBytes, numberOfSamples);
    }
}
