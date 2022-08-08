import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Scanner;

public class SpectateSummoner {
    private String summonerID;
    private String summonerName;
    private String region;
    private String observerKey;
    private String gameID;
    private String regionBat;
    private String REGIONBat;
    private int team;
    private static final String apiKey = "RGAPI-f0c8447f-2017-44e5-91cb-6ee4612b3b81";

    public SpectateSummoner(String summonerID, String region) {
        this.summonerID = summonerID;
        this.region = region;
    }

    public int getTeam() {
        return team;
    }

    public void startSpectate() throws IOException {
        setRegionBat();
        collectSpectatorInfo();
        setObserverKeyAndGameID();
        System.out.println("adfsg");
        executeBatScript();
    }
    public void endSpectate() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "endSpectate.bat");
        File pathToScript = new File("D:/Strimmer/Scripts");

        pb.directory(pathToScript);
        Process p = pb.start();
    }

    public void setFollow() throws IOException, AWTException, InterruptedException {
        setTeam(); //100 = {1,2,3,4,5} 200 = {q,w,e,r,t,y}
        int lane = getLane();
        System.out.println("setfollow");
        Robot r = new Robot();

        switch (lane) {
            case 1:
                if (team == 100) {
                    r.keyPress(KeyEvent.VK_1);
                    Thread.sleep(10);
                    r.keyRelease(KeyEvent.VK_1);
                    System.out.println("setfollwB1");
                    break;
                }
                r.keyPress(KeyEvent.VK_Q);
                Thread.sleep(10);
                r.keyRelease(KeyEvent.VK_Q);
                System.out.println("setfollwR1");
                break;

            case 2:
                if (team == 100) {
                    r.keyPress(KeyEvent.VK_2);
                    Thread.sleep(10);
                    r.keyRelease(KeyEvent.VK_2);
                    System.out.println("setfollwB2");
                    break;
                }
                r.keyPress(KeyEvent.VK_W);
                Thread.sleep(10);
                r.keyRelease(KeyEvent.VK_W);
                System.out.println("setfollwR2");
                break;
            case 3:
                if (team == 100) {
                    r.keyPress(KeyEvent.VK_3);
                    Thread.sleep(10);
                    r.keyRelease(KeyEvent.VK_3);
                    System.out.println("setfollwB3");
                    break;
                }
                r.keyPress(KeyEvent.VK_E);
                Thread.sleep(10);
                r.keyRelease(KeyEvent.VK_E);
                System.out.println("setfollwR3");
                break;
            case 4:
                if (team == 100) {
                    r.keyPress(KeyEvent.VK_4);
                    Thread.sleep(10);
                    r.keyRelease(KeyEvent.VK_4);
                    System.out.println("setfollwB4");
                    break;
                }
                r.keyPress(KeyEvent.VK_R);
                Thread.sleep(10);
                r.keyRelease(KeyEvent.VK_R);
                System.out.println("setfollwR4");
                break;
            case 5:
                if (team == 100) {
                    r.keyPress(KeyEvent.VK_5);
                    Thread.sleep(10);
                    r.keyRelease(KeyEvent.VK_5);
                    System.out.println("setfollwB5");
                    break;
                }
                r.keyPress(KeyEvent.VK_T);
                Thread.sleep(10);
                r.keyRelease(KeyEvent.VK_T);
                System.out.println("setfollwR5");
                break;
        }
        r.keyPress(KeyEvent.VK_Y);
        Thread.sleep(10);
        r.keyRelease(KeyEvent.VK_Y);
        System.out.println("lock");
    }

    public boolean hasGameEnded() throws AWTException, IOException, InterruptedException{

        Robot r = new Robot();
        double percentage = 100;
        // It saves screenshot to desired path
        String path = "endBanner/banner.jpg";

        while (percentage > 5) {
            // Used to get ScreenSize and capture image
            Rectangle capture =
                    new Rectangle(720, 360, 480, 170);
            BufferedImage Image = r.createScreenCapture(capture);
            ImageIO.write(Image, "jpg", new File(path));
            System.out.println("Screenshot saved");

            r.mouseMove(900, 800);

            BufferedImage img1 = ImageIO.read(new File("endBanner/victory.jpg"));
            BufferedImage img2 = ImageIO.read(new File("endBanner/banner.jpg"));
            int w1 = img1.getWidth();
            int w2 = img2.getWidth();
            int h1 = img1.getHeight();
            int h2 = img2.getHeight();
            if ((w1 != w2) || (h1 != h2)) {
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
                percentage = (avg / 255) * 100;
                System.out.println("Difference: " + percentage);
                Thread.sleep(10000);
            }
        }

        return true;
    }

    private int getLane() throws IOException, AWTException {
        // Capture nameplates
        captureNames();
        // compare nameplates
        int lane = compareNames();
        return lane;
    }

    private void captureNames() throws IOException, AWTException{
        Robot r = new Robot();
        int xCord;
        int yCord[] = {133, 236, 339, 440, 545};
        int width = 30;
        int height = 20;
        String path;
        char teamColour = 'R';

        if (team == 100) {
            xCord = 0;
            teamColour = 'B';
        } else xCord = 1890;

        for (int i = 1; i < 6; i++) {
            // It saves screenshot to desired path
            path ="Names/NamePlate"+ i + teamColour + ".jpg";

            // Used to get ScreenSize and capture image
            Rectangle capture =
                    new Rectangle(xCord, yCord[i-1], width, height);
            BufferedImage Image = r.createScreenCapture(capture);
            ImageIO.write(Image, "jpg", new File(path));
            System.out.println("Screenshot saved");
        }
    }

    private int compareNames() throws IOException{
        char teamColour = 'B';
        int summonerNumber = 0;
        double greatestMatch = 100;

        if (team == 200) {
            teamColour = 'R';
        }
        BufferedImage img1 = ImageIO.read(new File("Names/Baus" + teamColour + ".jpg"));

        for (int imgNr = 1; imgNr < 6; imgNr++) {
            BufferedImage img2 = ImageIO.read(new File("Names/NamePlate" + imgNr + teamColour +".jpg"));
            int w1 = img1.getWidth();
            int w2 = img2.getWidth();
            int h1 = img1.getHeight();
            int h2 = img2.getHeight();
            if ((w1 != w2) || (h1 != h2)) {
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

                if (greatestMatch > percentage) {
                    greatestMatch = percentage;
                    summonerNumber = imgNr;
                }
            }
        }
        return summonerNumber;
    }

    private void executeBatScript() throws IOException{
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "startSpectate.bat", observerKey, gameID, REGIONBat, regionBat);
        File pathToScript = new File("D:/Strimmer/Scripts");

        pb.directory(pathToScript);
        Process p = pb.start();
    }

    public void collectSpectatorInfo() throws IOException {
        URL urlSpectator = new URL("https://" + getRegionForAPI() + ".api.riotgames.com/lol/spectator/v4/active-games/by-summoner/" + summonerID + "?api_key=" + apiKey);

        BufferedReader readr = new BufferedReader(new InputStreamReader(urlSpectator.openStream()));
        BufferedWriter writr = new BufferedWriter(new FileWriter("SpectatorInfo.txt"));

        String line;
        while ((line = readr.readLine()) != null) {
            writr.write(line);
        }

        readr.close();
        writr.close();
    }

    private void setTeam() throws IOException{
        Scanner scan = new Scanner(new File("SpectatorInfo.txt"));
        String[] splitHelper;
        String helper = "hej";
        scan.useDelimiter("teamId");
        String line = scan.next();
        while (!helper.contains(summonerID)) {
            line = scan.next();

            splitHelper = line.split(":");
            splitHelper = splitHelper[1].split(",");
            team = Integer.parseInt(splitHelper[0]);

            splitHelper = line.split("summonerName");
            splitHelper = splitHelper[1].split("\"");
            summonerName = splitHelper[2];

            splitHelper = line.split("summonerId");
            splitHelper = splitHelper[1].split("\"");
            helper = splitHelper[2];
        }
    }
    private void setObserverKeyAndGameID() throws IOException {
        Scanner scan = new Scanner(new File("SpectatorInfo.txt"));
        String[] splitHelper;

        String line = scan.next();
        splitHelper = line.split("gameId");
        splitHelper = splitHelper[1].split(":");
        splitHelper = splitHelper[1].split(",");
        gameID = splitHelper[0];

        while (!line.contains("encryptionKey")) {
            line = scan.next();
        }

        splitHelper = line.split("encryptionKey");
        splitHelper = splitHelper[1].split(":");
        splitHelper = splitHelper[1].split("\"");
        observerKey = splitHelper[1];
    }

    private void setRegionBat() {
        switch (region) {
            case "euw":
                regionBat = "euw1";
                REGIONBat = "EUW1";
                break;
            case "kr":
                regionBat = "kr";
                REGIONBat = "KR";
                break;
            case "na":
                regionBat = "na1";
                REGIONBat = "NA1";
        }
    }

    private String getRegionForAPI() {
        switch (region) {
            case "euw":
                return "euw1";

            case "kr":
                return "kr";

            case "na" :
                return "na1";
        }
        return null;
    }
}
