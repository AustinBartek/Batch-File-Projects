import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Images {
    private static BufferedImage[] frames;
    private static int maxFrames = 131;

    static {
        frames = new BufferedImage[maxFrames];
        for (int i = 0; i < maxFrames; i++) {
            frames[i] = getResource("frame" + i);
        }
    }

    public static BufferedImage getFrame(int frameNum) {
        return frames[frameNum];
    }

    public static int getMaxFrames() {
        return maxFrames;
    }

    private static BufferedImage getResource(String name) {
        try {
            return ImageIO.read(Images.class.getClassLoader().getResource("resources/" + name + ".png"));
        } catch (IOException e) {
            System.out.println("null image");
            return null;
        }
    }
}