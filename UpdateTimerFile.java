import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.BatchUpdateException;
import java.util.TimerTask;

public class UpdateTimerFile extends Thread {
    Summoner summ;
    public UpdateTimerFile(Summoner summ) {
        this.summ = summ;
    }

    public void run() {
        try {
            int h = 00;
            int m = 00;
            int s = 00;

            while (!summ.isActive()) {
                FileWriter fw = new FileWriter(new File("timeSinceLastGame.txt"));
                fw.write("Time since last game: " + h + ":" + m + ":" + s);

                s++;
                if (s > 60) {
                    s = 00;
                    m += 1;
                }
                if (m > 60) {
                    m = 00;
                    h += 1;
                }

                fw.close();
                Thread.sleep(1000);
                //System.out.println(s);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
