import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class IsSummonerActive {
    // skicka query for att fa summonerID
    // https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/<name>?api_key=<apiKey>

    // req url for activeGame
    // https://euw1.api.riotgames.com/lol/spectator/v4/active-games/by-summoner/<summonerID>

    private String summonerName;
    public IsSummonerActive(String summonerName) {
        this.summonerName = summonerName;
    }

    public void checkStatus() {

    }

    // tar fram spectator info om aktiv, annars hittar inte data
    public void isActive() throws Exception{
        // om in game, dvs aktiv 200ok
        // om ej in game, dvs inaktiv 404 data not found
        String apiKey = "RGAPI-e1d5a027-3570-49e4-adcb-69f2aee0b8a4";
        req1();
        String summonerID = getSummonerID();
        URL urlSpectator = new URL("https://euw1.api.riotgames.com/lol/spectator/v4/active-games/by-summoner/" + summonerID + "?api_key=" + apiKey);

        BufferedReader readr = new BufferedReader(new InputStreamReader(urlSpectator.openStream()));

        BufferedWriter writr = new BufferedWriter(new FileWriter("spectatorInfo.txt"));

        String line;
        while ((line = readr.readLine()) != null) {
            writr.write(line);
        }

        readr.close();
        writr.close();
    }

    // tar fram summonerInfo
    public void req1() throws Exception{
        String summonerName = "MAD%20Viper";
        String apiKey = "RGAPI-e1d5a027-3570-49e4-adcb-69f2aee0b8a4";
        String hostname = "https://euw1.api.riotgames.com/";
        int httpsPort = 443;
        String ipAddr = "104.160.144.188";

        URL urlSummoner = new URL("https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/"
                + summonerName + "?api_key=" + apiKey);

        BufferedReader readr = new BufferedReader(new InputStreamReader(urlSummoner.openStream()));

        BufferedWriter writr = new BufferedWriter(new FileWriter("summonerInfo.txt"));

        String line;
        while ((line = readr.readLine()) != null) {
            writr.write(line);
        }

        readr.close();
        writr.close();
    }

    public String getSummonerID() throws Exception {

        Scanner scan = new Scanner(new File("summonerInfo.txt"));
        String[] splitHelper;

        String summonerID = scan.next();
        splitHelper= summonerID.split("\"");
        summonerID = splitHelper[3];
        System.out.println(summonerID);
        scan.close();

        return summonerID;
    }
}
