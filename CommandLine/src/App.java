import java.io.Console;
import java.io.PrintWriter;
import java.awt.image.BufferedImage;

public class App {
    private static PrintWriter writer;
    private static String[] characters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P" };
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    private static String[] colors = {ANSI_BLACK_BACKGROUND, ANSI_RED_BACKGROUND, ANSI_YELLOW_BACKGROUND, ANSI_GREEN_BACKGROUND, ANSI_CYAN_BACKGROUND, ANSI_BLACK_BACKGROUND, ANSI_PURPLE_BACKGROUND, ANSI_WHITE_BACKGROUND};

    public static void main(String[] args) throws Exception {
        Console console = System.console();
        writer = console.writer();

        int maxFrames = Images.getMaxFrames();

        String[][] imagesLines = new String[maxFrames][];
        for (int i = 0; i < maxFrames; i++) {
            imagesLines[i] = writeImage(Images.getFrame(i));
        }

        int frameNum = 0;
        while (true) {
            writer.print("\033[H"); //clears screen
            writeLines(imagesLines[frameNum]);
            frameNum++;
            frameNum %= maxFrames;
            Thread.sleep(20);
        }
    }

    public static void writeLines(String[] lines) {
        for (String line : lines) {
            writer.write(line + "\n");
        }
        writer.flush();
    }

    public static String[] writeImage(BufferedImage img) {
        int width = img.getWidth(), height = img.getHeight();
        int heightFactor = (int) ((height / 720.0) * 20);
        int widthFactor = (int) ((width / 1280.0) * 10);
        int[] preData = img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth());
        int[][] data = convertDataTo2D(preData, width, height);
        String[] linesToWrite = new String[height / heightFactor + 1];

        for (int i = 0; i < linesToWrite.length; i++) {
            linesToWrite[i] = "";
        }

        int lineNum = 0;
        for (int y = 0; y < height; y += heightFactor) {
            for (int x = 0; x < width; x += widthFactor) {
                int argb = data[x][y];
                int a = (argb >> 24) & 0xFF;
                int r = (argb >> 16) & 0xFF;
                int g = (argb >> 8) & 0xFF;
                int b = (argb) & 0xFF;
                String add = "";

                double[] hsl = rgbToHsl(r, g, b);
                add += colors[(int) (hsl[0] * colors.length)];
                add += " ";

                linesToWrite[lineNum] += add;
            }
            linesToWrite[lineNum] += ANSI_BLACK_BACKGROUND;
            lineNum++;
        }

        return linesToWrite;
    }

    private static double[] rgbToHsl(double r, double g, double b) {
        r /= 255d; g /= 255d; b /= 255d;

        double max = Math.max(Math.max(r, g), b), min = Math.min(Math.min(r, g), b);
        double h, s, l = (max + min) / 2;

        if (max == min) {
            h = s = 0; // achromatic
        } else {
            double d = max - min;
            s = l > 0.5 ? d / (2 - max - min) : d / (max + min);

            if (max == r) h = (g - b) / d + (g < b ? 6 : 0);
            else if (max == g) h = (b - r) / d + 2;
            else h = (r - g) / d + 4; // if (max == b)

            h /= 6;
        }

        return new double[] { h, s, l };
    }

    public static int[][] convertDataTo2D(int[] data, int width, int height) {
        int[][] newData = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                newData[x][y] = data[y * width + x];
            }
        }
        return newData;
    }
}
