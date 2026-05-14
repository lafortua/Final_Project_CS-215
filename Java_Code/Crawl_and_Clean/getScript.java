import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
public class getScript {
    public static void main(String[] args) {
        try {
            String url = "https://subslikescript.com/series/Cheers-83399";
            String parentFile = "C:\\Users\\Augustus\\Desktop\\Code_Things\\webcrawler\\output\\";
            File urlsList = new File(parentFile + "urlsList.txt");

            // Connect to the URL and get the HTML document

            ArrayList<String> urls = new ArrayList<>();
            try(BufferedReader br = new BufferedReader(new FileReader(urlsList))) {
                String line = "";
                while ((line = br.readLine()) != null) {
                    //System.out.println(line);
                    urls.add(line);
                }
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            FileWriter fw = new FileWriter(parentFile + "allTranscripts.txt");

            fw.write("Text;Season;Episode;EpisodeTitle\n");

            while (urls.size() > 0) {

                Document doc = Jsoup.connect(urls.get(0)).userAgent("Mozilla/5.0").get();

                Element scriptDiv = doc.selectFirst("div.full-script");

                if (scriptDiv != null) {
                    Element head = doc.selectFirst("h1");
                    String headText = head.text();
                    System.out.println("\n" + headText);

                    String season = headText.substring(headText.indexOf("Season") + 7, headText.indexOf(", Episode"));
                    String episode = headText.substring(headText.indexOf("Episode") + 8, headText.indexOf(" - "));
                    String episodeTitle = headText.substring(headText.indexOf(" - ") + 3);
                    episodeTitle = episodeTitle.substring(0, episodeTitle.indexOf(" - "));

                    scriptDiv.select("br").append("LINE_BREAK");
                    String rawText = scriptDiv.text();
                    String[] scriptLines = rawText.split("LINE_BREAK");

                    for (int i = 0; i < scriptLines.length; i++) {
                        scriptLines[i] = scriptLines[i].trim();
                    }


                    for (String line : scriptLines) {
                        if (!line.isEmpty()) {
                            if (line.indexOf("-") == 0) {
                                line = line.substring(2);
                            }

                            String lastChar = line.substring(line.length() - 1);
                            line = line.replace(";", ",");
                            line = line.replace("\"", "");

                            if ((lastChar.equals(".")) || (lastChar.equals("!")) || (lastChar.equals("?")) || (lastChar.equals("♪"))) {
                                fw.write(line + ";" + season + ";" + episode + ";" + episodeTitle + "\n");
                            }
                            else {
                                fw.write(line + " ");
                            }
                        }
                    }
                }
                urls.remove(0);
            }
            fw.close();

        } catch (Exception e) {
            System.err.println("Error fetching the web page: " + e.getMessage());
        }
    }
}

