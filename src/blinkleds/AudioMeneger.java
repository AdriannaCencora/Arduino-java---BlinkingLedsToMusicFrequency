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
    private AudioInputStream audioStream;
    private AudioFormat audioFormat;
    private Clip clip;
    private DataLine.Info info;

    public void openFile(String filename) {
        try {
            soundFile = new File(filename);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void playAudioForTests() {
        try {
            audioStream = AudioSystem.getAudioInputStream(soundFile);
            audioFormat = audioStream.getFormat();
            info = new DataLine.Info(Clip.class, audioFormat);

            clip = (Clip) AudioSystem.getLine(info);
            System.out.println(audioFormat.toString());
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
}
