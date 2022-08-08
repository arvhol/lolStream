import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.MalformedURLException;

public class Main {
    public static void main(String[] args) {
        String summonerName = "thebausffs";
        String region = "euw";
        Summoner Baus = new Summoner(summonerName, region);

        UpdateTimerFile updateTimer = new UpdateTimerFile(Baus);
        boolean startTimer = false;

        System.out.println("START");
        while (true) {
            try {
                System.out.println("1");
                Baus.updateSummoner();
                System.out.println("2");
                Baus.updateRankSheet();

                System.out.println(Baus.getSummonerName() + " is " + Baus.getRank() + " " + Baus.getLP() + "LP.");

                if (startTimer) {
                    updateTimer.start();
                }
                System.out.println("yep");
                
                while (!Baus.isActive()) {
                    Thread.sleep(60000);
                }

                SpectateSummoner spec = new SpectateSummoner(Baus.getSummonerID(), region);
                spec.endSpectate();

                System.out.println("2mins");
                Thread.sleep(120000);

                System.out.println(Baus.getSummonerName() + " is in game.");
                //Thread.sleep(120000);
                spec.startSpectate();
                Robot r = new Robot();

                Thread.sleep(10000);

                r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(10);
                r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

                Color healthbar = r.getPixelColor(50, 210);
                System.out.println(healthbar.toString());
                while (!healthbar.equals(new Color(8, 199, 74))) {
                    healthbar = r.getPixelColor(50, 210);
                }
                r.keyPress(KeyEvent.VK_U);
                Thread.sleep(10);
                r.keyRelease(KeyEvent.VK_U);
                Thread.sleep(1000);
                r.keyPress(KeyEvent.VK_O);
                Thread.sleep(10);
                r.keyRelease(KeyEvent.VK_O);
                Thread.sleep(10);
                r.keyPress(KeyEvent.VK_V);
                Thread.sleep(10);
                r.keyRelease(KeyEvent.VK_V);

                spec.setFollow();

                if (spec.getTeam() == 200) {
                    Thread.sleep(10);
                    r.keyPress(KeyEvent.VK_V);
                    Thread.sleep(10);
                    r.keyRelease(KeyEvent.VK_V);
                }


                while (Baus.isActive()) {
                    Thread.sleep(60000);
                }

                spec.hasGameEnded();

                spec.endSpectate();
                //startTimer = true;

                System.out.println("foobar");

            } catch (MalformedURLException mue) {
                System.out.println(mue);
            } catch (IOException ioe) {
                System.out.println(ioe);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
