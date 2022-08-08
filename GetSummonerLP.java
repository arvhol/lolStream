import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class GetSummonerLP {

    public void download() throws MalformedURLException, IOException {
            URL url = new URL("https://www.op.gg/summoners/kr/Baus");
            BufferedReader readr = new BufferedReader(new InputStreamReader(url.openStream()));

            BufferedWriter writr = new BufferedWriter(new FileWriter("BausOPGG.html"));

            String line;
            while ((line = readr.readLine()) != null) {
                writr.write(line);
            }

            readr.close();
            writr.close();
            System.out.println("Downloaded");
    }

    public void getLPFile() throws FileNotFoundException {
        File html = new File("summonerOPGG.html");
        Scanner scan = new Scanner(html);


        String rank = scan.next();
        while (!rank.contains("class=\"tier-rank\"")) {
            rank = scan.next();
        }
        System.out.println(rank);

        String lp = scan.next();
        while (!lp.contains("class=\"lp\"")) {
            lp = scan.next();
        }
        System.out.println(lp);
    }

}
