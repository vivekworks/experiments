package com.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        new Main().write();
    }

    public void start(){
        File alarmFile = new File("F:\\Work\\Workspace\\AudioSystem\\src\\com\\audio\\chimes.wav");
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(alarmFile.getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            System.out.println(clip.getMicrosecondLength()/1000);
            Thread.sleep(clip.getMicrosecondLength()/1000);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void write(){
        try {
            String path = Paths.get("").toAbsolutePath() + "\\src\\file";
            File directory = new File(path);
            if(!directory.exists())
                directory.mkdir();
            File aFile = new File(path+"\\newfile.txt");
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(aFile,true)));
            writer.println("Kubernetes");
            writer.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
