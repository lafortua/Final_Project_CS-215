import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.time.*;

public class Line {
    private final String text;
    private final String lcText;
    private final int episode;
    private final int season;
    private final String episodeTitle;
    private final String airDate;
    private final double rating;
    private final int votes;

    final String[] samNames = {"Sam", "Malone", "Sammy", "Mayday"};
    final String[] carlaNames = {"Carla", "Tortelli", "LeBec"};
    final String[] cliffNames = {"Cliff", "Clavin", "Cliffie", "Cliffy"};
    final String[] normNames = {"Norm", "Peterson", "Normie", "Norman"};
    final String[] frasierNames = {"Frasier", "Crane", "Fras"};
    final String[] woodyNames = {"Woody", "Boyd", "Wood"};
    final String[] rebeccaNames = {"Rebecca", "Howe"};
    final String[] dianeNames = {"Diane", "Chambers"};
    final String[] lilithNames = {"Lilith", "Sternin"};
    final String[] coachNames = {"Coach", "Ernie", "Pantusso"};
    final String[] nickNames = {"Nick", "Tortelli"};
    final String[] eddieNames = {"Eddie", "LeBec"};
    final String[] kellyNames = {"Kelly", "Gaines"};

    public Line(int i) {
        text = "This is line #" + i + ".";
        lcText = text.toLowerCase();
        episode = 0;
        season = 0;
        episodeTitle = "Test Episode";
        airDate = "January 1, 1970";
        rating = 2.3;
        votes = 23;
    }

    public Line(String str){
        text = str.substring(0,str.indexOf(";"));
        lcText = text.toLowerCase();
        str = str.substring(str.indexOf(";") + 1);
        episode = Integer.parseInt(str.substring(0,str.indexOf(";")));
        str = str.substring(str.indexOf(";") + 1);
        season = Integer.parseInt(str.substring(0,str.indexOf(";")));
        str = str.substring(str.indexOf(";") + 1);
        episodeTitle = str.substring(0,str.indexOf(";"));
        str = str.substring(str.indexOf(";") + 1);
        airDate = str.substring(0,str.indexOf(";"));
        str = str.substring(str.indexOf(";") + 1);
        rating = Double.parseDouble(str.substring(0,str.indexOf(";")));
        str = str.substring(str.indexOf(";") + 1);
        votes = Integer.parseInt(str);
    }

    public boolean hasText(String str){
        str = str.toLowerCase();
        return (lcText.indexOf(str) >= 0);
    }

    public String getText(){
        return text;
    }

    public int getEpisode(){
        return episode;
    }

    public int getSeason(){
        return season;
    }

    public String getEpisodeTitle(){
        return episodeTitle;
    }

    public String getAirDate(){
        return airDate;
    }

    public double getRating(){
        return rating;
    }

    public int getVotes(){
        return votes;
    }

    public boolean isEpisode(int s, int e) {
        return (s == season) && (e == episode);
    }

    public boolean isSeason(int s) {
        return (s == season);
    }

    public boolean hasName(String name) {
        if (name.equals("Sam")) {
            for (String characterName: samNames) {
                if (text.contains(characterName)){
                    return true;
                }
            }
        }
        else if (name.equals("Carla")) {
            for (String characterName: carlaNames) {
                if ((text.contains(characterName)) && !((text.contains("Mr.")) || (text.contains("Nick")) || ((text.contains("Eddie"))))) {
                    return true;
                }
            }
        }
        else if (name.equals("Cliff")) {
            for (String characterName: cliffNames) {
                if (text.contains(characterName)){
                    return true;
                }
            }
        }
        else if (name.equals("Norm")) {
            for (String characterName: normNames) {
                if (text.contains(characterName)){
                    return true;
                }
            }
        }
        else if (name.equals("Frasier")) {
            for (String characterName: frasierNames) {
                if (text.contains(characterName)){
                    return true;
                }
            }
        }
        else if (name.equals("Woody")) {
            for (String characterName: woodyNames) {
                if (text.contains(characterName)){
                    return true;
                }
            }
        }
        else if (name.equals("Rebecca")) {
            for (String characterName: rebeccaNames) {
                if (text.contains(characterName)){
                    return true;
                }
            }
        }
        else if (name.equals("Diane")) {
            for (String characterName: dianeNames) {
                if (text.contains(characterName)){
                    return true;
                }
            }
        }
        else if (name.equals("Lilith")) {
            for (String characterName: lilithNames) {
                if (text.contains(characterName)){
                    return true;
                }
            }
        }
        else if (name.equals("Coach")) {
            for (String characterName: coachNames) {
                if (text.contains(characterName)){
                    return true;
                }
            }
        }
        else if (name.equals("Nick")) {
            for (String characterName: nickNames) {
                if ((text.contains(characterName)) && !((text.contains("Carla")) || (text.contains("Mrs.")) || (text.contains("Miss")))){
                    return true;
                }
            }
        }
        else if (name.equals("Eddie")) {
            for (String characterName: eddieNames) {
                if ((text.contains(characterName)) && !((text.contains("Carla")) || (text.contains("Mrs.")) || (text.contains("Miss")))){
                    return true;
                }
            }
        }
        else if (name.equals("Kelly")) {
            for (String characterName: kellyNames) {
                if ((text.contains(characterName)) && !((text.contains("Mr.")) || (text.contains("Walter")))){
                    return true;
                }
            }
        }
        else if (name.equals("Gary") && (text.contains("Gary"))) {
            return true;
        }

        return false;
    }
}
