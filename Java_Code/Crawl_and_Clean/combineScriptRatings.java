import java.io.*;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class combineScriptRatings {
    public static void main(String[] args){
        try {
            String parentFile = "C:\\Users\\Augustus\\Desktop\\Code_Things\\webcrawler\\output\\";
            File imdb = new File(parentFile + "imdbList.txt");
            File lines = new File(parentFile + "cleanTranscripts.txt");

            ArrayList<String> linesList = new ArrayList<>();
            try(BufferedReader br = new BufferedReader(new FileReader(lines))) {
                String line = "";
                while ((line = br.readLine()) != null) {
                    linesList.add(line);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            ArrayList<String> imdbList = new ArrayList<>();
            try(BufferedReader br = new BufferedReader(new FileReader(imdb))) {
                String rating = "";
                while ((rating = br.readLine()) != null) {
                    imdbList.add(rating);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            FileWriter fw = new FileWriter(parentFile + "linesWithImdb.txt");

            linesList.remove(0);
            fw.write("Text;Season;Episode;EpisodeTitle;AirDate;Rating;Votes\n");

            while (linesList.size() > 0) {
                String line = linesList.get(0);
                String episodeNumber = getEpisodeNumber(line);
                System.out.println(episodeNumber);
                String rating = "";
                while (rating.isEmpty()) {
                    for (String rat : imdbList) {
                        if (rat.indexOf(episodeNumber + ";") == 0) {
                            rating = rat.substring(episodeNumber.length() + 1);
                        }
                    }
                }
                //System.out.println(linesList.size());

                fw.write(line + ";" + rating + "\n");
                linesList.remove(0);
            }

            fw.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getEpisodeNumber(String line) {
        int startIndex = line.indexOf(";") + 1;
        int middleIndex = line.substring(startIndex).indexOf(";") + 1;
        int endIndex = line.substring(startIndex + middleIndex).indexOf(";");
        String number = line.substring(startIndex, startIndex + middleIndex + endIndex);
        return number;
    }
}