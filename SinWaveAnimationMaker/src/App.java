import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;

public class App {
    public static void main(String[] args) throws Exception {
        double twoPI = Math.PI * 2;
        

        ImageIO.write(null, "png", new File("src\\frames"));
    }
}
