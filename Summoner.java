import java.io.*;
import java.net.URL;
import java.util.Scanner;

public class Summoner {

    private boolean status = false;
    private String summonerName;
    private String summonerID = null;
    private Integer LP = null;
    private String rank = null;
    private String region;
    private static final String apiKey = "RGAPI-f0c8447f-2017-44e5-91cb-6ee4612b3b81";

    public Summoner(String summonerName, String region) {
        this.summonerName = summonerName;
        this.region = region;
    }

    public void updateSummoner() throws IOException {
        collectAccountIdentifiers();
        summonerID = extractID();

        collectRankingInformation();
        rank = extractRank();
        LP = extractLP();
    }

    public void updateRankSheet() throws IOException {
        FileWriter fw = new FileWriter(new File("currrentRank.txt"));
        fw.write(rank + " " + LP + "LP");
        fw.close();
    }

    public boolean isActive() throws IOException {
        updateStatus();
        return status;
    }
    public String getSummonerName() {
        return summonerName;
    }
    public String getSummonerID() {
        return summonerID;
    }
    public int getLP() {
        return LP;
    }
    public String getRank() {
        return rank;
    }
    public String getRegion() {
        return region;
    }

    private void collectAccountIdentifiers() throws IOException {
        URL url = new URL("https://" + getRegionForAPI() + ".api.riotgames.com/lol/summoner/v4/summoners/by-name/"
                + summonerName + "?api_key=" + apiKey);

        BufferedReader readr = new BufferedReader(new InputStreamReader(url.openStream()));

        BufferedWriter writr = new BufferedWriter(new FileWriter(summonerName + "Info.txt"));

        String line;
        while ((line = readr.readLine()) != null) {
            writr.write(line);
        }

        readr.close();
        writr.close();
    }
    private String extractID() throws FileNotFoundException {
        Scanner scan = new Scanner(new File(summonerName + "Info.txt"));
        String[] splitHelper;

        String line = scan.next();
        splitHelper= line.split("\"");
        line = splitHelper[3];
        System.out.println(line);
        scan.close();

        return line;
    }

    // Scrape opgg for rank
    /*
    private void collectRankingInformation() throws IOException {
        URL url = new URL("https://www.op.gg/summoners/" + region + "/" + summonerName);

        BufferedReader readr = new BufferedReader(new InputStreamReader(url.openStream()));

        BufferedWriter writr = new BufferedWriter(new FileWriter(summonerName + "OPGG.html"));

        String line;
        while ((line = readr.readLine()) != null) {
            writr.write(line);
        }

        readr.close();
        writr.close();
        System.out.println("Downloaded");
    }
    private String[] extractRankAndLP() throws FileNotFoundException{
        File html = new File(summonerName + "OPGG.html");
        Scanner scan = new Scanner(html);

        String rank = scan.next();
        while (!rank.contains("class=\"tier-rank\"")) {
            rank = scan.next();
        }
        //System.out.println(rank);

        String lp = scan.next();
        while (!lp.contains("class=\"lp\"")) {
            lp = scan.next();
        }
        //System.out.println(lp);
        return new String[] {rank, lp};
    }
    private String extractRank(String clutteredRank) {
        clutteredRank = clutteredRank.substring(clutteredRank.indexOf(">") + 1);
        clutteredRank = clutteredRank.substring(0, clutteredRank.indexOf("<"));
        return clutteredRank;
    }
    private int extractLP(String clutteredLP) {
        clutteredLP = clutteredLP.substring(clutteredLP.indexOf(">") + 1);
        clutteredLP = clutteredLP.substring(0, clutteredLP.indexOf("<"));
        return Integer.parseInt(clutteredLP);
    }
     */
    private void collectRankingInformation() throws IOException {
        URL url = new URL("https://" + getRegionForAPI() + ".api.riotgames.com/lol/league/v4/entries/by-summoner/" + summonerID + "?api_key=" + apiKey);

        BufferedReader readr = new BufferedReader(new InputStreamReader(url.openStream()));
        BufferedWriter writr = new BufferedWriter(new FileWriter(summonerName + "Rank.txt"));

        String line;
        while ((line = readr.readLine()) != null) {
            writr.write(line);
        }
        readr.close();
        writr.close();
    }
    private String extractRank() throws FileNotFoundException {
        Scanner scan = new Scanner(new File(summonerName + "Rank.txt"));
        String[] splitHelper;

        String line = scan.next();
        splitHelper = line.split("tier");
        splitHelper = splitHelper[1].split("\"");
        line = splitHelper[2];
        if (line.contains("CHALLENGER") || line.contains("GRANDMASTER") || line.contains("MASTER")) {
           return line;
        }
        line = line + " " + splitHelper[6];
        return line;
    }
    private int extractLP() throws FileNotFoundException{
        Scanner scan = new Scanner(new File(summonerName + "Rank.txt"));
        String[] splitHelper;

        String line = scan.nextLine();
        splitHelper = line.split("leaguePoints");
        splitHelper = splitHelper[1].split("\":");
        splitHelper = splitHelper[1].split(",");
        line = splitHelper[0];
        return Integer.parseInt(line);
    }

    private void updateStatus() {
        try {
            URL url = new URL("https://" + getRegionForAPI() + ".api.riotgames.com/lol/spectator/v4/active-games/by-summoner/" + summonerID + "?api_key=" + apiKey);
            BufferedReader readr = new BufferedReader(new InputStreamReader(url.openStream()));
            status = true;

        } catch (IOException ioe) {
            status = false;
            System.out.println(ioe.toString());
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
