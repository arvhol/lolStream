import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SetSpec {
    public static void main (String[] args) {
        try {

            Thread.sleep(1200);

            Robot r = new Robot();
            // It saves screenshot to desired path
            String path = "endBanner/banner.jpg";

            // Used to get ScreenSize and capture image
            Rectangle capture =
                    new Rectangle(720, 360, 480, 170);
            BufferedImage Image = r.createScreenCapture(capture);
            ImageIO.write(Image, "jpg", new File(path));
            System.out.println("Screenshot saved");

            r.mouseMove(900,500);

            /*
            Thread.sleep(1200);
            SpectateSummoner s = new SpectateSummoner("ss", "wu");
            s.setFollow();

             */
            // Compare img

            BufferedImage img1 = ImageIO.read(new File("endBanner/victory.jpg"));
            BufferedImage img2 = ImageIO.read(new File("endBanner/banner.jpg"));
            int w1 = img1.getWidth();
            int w2 = img2.getWidth();
            int h1 = img1.getHeight();
            int h2 = img2.getHeight();
            if ((w1!=w2)||(h1!=h2)) {
                System.out.println("Both images should have same dimwnsions");
            } else {
                long diff = 0;
                for (int j = 0; j < h1; j++) {
                    for (int i = 0; i < w1; i++) {
                        //Getting the RGB values of a pixel
                        int pixel1 = img1.getRGB(i, j);
                        Color color1 = new Color(pixel1, true);
                        int r1 = color1.getRed();
                        int g1 = color1.getGreen();
                        int b1 = color1.getBlue();
                        int pixel2 = img2.getRGB(i, j);
                        Color color2 = new Color(pixel2, true);
                        int r2 = color2.getRed();
                        int g2 = color2.getGreen();
                        int b2 = color2.getBlue();
                        //sum of differences of RGB values of the two images
                        long data = Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
                        diff = diff + data;
                    }
                }
                double avg = diff / (w1 * h1 * 3);
                double percentage = (avg / 255) * 100;
                System.out.println("Difference: " + percentage);
            }



            // get screenshots
            /*

            Robot r = new Robot();
            // xB = 0
            int x = 1030;
            int widthR = 40;
            int widthB = 50;
            int y[] = {133, 236, 339, 440, 545};
            for (int i = 1; i < 6; i++) {

                // It saves screenshot to desired path
                String path = "Names/NamePlate" + i + ".jpg";

                // Used to get ScreenSize and capture image
                Rectangle capture =
                        new Rectangle(1880, y[i-1], widthR, 20);
                BufferedImage Image = r.createScreenCapture(capture);
                ImageIO.write(Image, "jpg", new File(path));
                System.out.println("Screenshot saved");

            }

            r.keyPress(KeyEvent.VK_O);
            Thread.sleep(10);
            r.keyRelease(KeyEvent.VK_O);

             */

        } catch (Exception e) {
            System.out.println(e);
        }



        /*
        try {

            SpectateSummoner spec = new SpectateSummoner("OFyx-FamHIkmUTNIOV2up3iTCVRcZ9ZI29H8dj72o_heGqtFLVYba87h1w", "euw");

            spec.setFollow();
            Thread.sleep(5000);

            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_U);
            Thread.sleep(10);
            r.keyRelease(KeyEvent.VK_U);
            Thread.sleep(10);
            r.keyPress(KeyEvent.VK_O);
            Thread.sleep(10);
            r.keyRelease(KeyEvent.VK_O);

            r.mouseMove(50, 200);
            // x;y = 50;210
            //rgb = 8, 199, 74
            Color c = r.getPixelColor(50, 210);
            System.out.println(c.toString());

        } catch (AWTException awt) {
            System.out.printf("a");
        } catch (InterruptedException e) {
            System.out.printf("h");
        } catch (IOException e) {
            System.out.println(e);
        }

         */

    }
}
