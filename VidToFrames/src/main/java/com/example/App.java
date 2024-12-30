package com.example;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

public class App {
    public static void main(String[] args) throws Exception {
        getFramesOfVideo("src\\main\\resources\\testVideo.mp4");
    }

    public static void getFramesOfVideo(String path) throws Exception {
        File myObj = new File(path);
        FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(myObj.getAbsoluteFile());
        frameGrabber.start(); 
        Frame f;
        int length = frameGrabber.getLengthInVideoFrames();
        for (int i = 0; i < length; i++) {
            try {
                Java2DFrameConverter c = new Java2DFrameConverter();
                f = frameGrabber.grabImage();
                BufferedImage bi = c.convert(f);
                ImageIO.write(bi, "png", new File("src\\main\\frames\\frame" + i + ".png"));
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        frameGrabber.stop();
        frameGrabber.close();
    }
}