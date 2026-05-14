import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class IMDbScraper {
    public static void main(String[] args) {
        // Update this path to wherever you saved the HTML file on your computer
        String parentFile = "C:\\Users\\Augustus\\Desktop\\Code_Things\\webcrawler\\output\\";
        File imdbList = new File(parentFile + "imdbList.txt");

        try {
            // Parse the local file. The second argument is the character encoding.
            FileWriter fw = new FileWriter(imdbList);
            fw.write("Season;Episode;AirDate;Rating;Votes" + "\n");
            for (int i = 1; i < 12; i++){
                String inputPath = "C:\\Users\\Augustus\\Desktop\\Code_Things\\webcrawler\\input\\s" + i + "\\Cheers (TV Series 1982–1993) - Episode list - IMDb.htm";
                File input = new File(inputPath);

                Document doc = Jsoup.parse(input, "UTF-8");

                // From here, your existing code stays exactly the same!
                Elements episodeCards = doc.select("article.episode-item-wrapper");


                for (Element card : episodeCards) {
                    // 1. Episode Title
                    Element titleEl = card.selectFirst("div.ipc-title__text");
                    String title = titleEl.text();
                    String number = title.substring(title.indexOf("S") + 1, title.indexOf(".E"));
                    number += ";" + title.substring(title.indexOf(".E") + 2, title.indexOf(" ∙ "));
                    // 2. Air Date
                    // Air dates usually have this specific test ID on modern IMDb episode lists
                    Element airDateEl = card.selectFirst("h4[data-testid=slate-list-card-title] + span");
                    String airDateText = (airDateEl != null) ? airDateEl.text() : "N/A";
                    String airDate = airDateText.substring(airDateText.indexOf(", ") + 2);
                    // 3 & 4. Rating and Votes
                    // The star rating span usually contains text like "8.2/10 (1.7K)"

                    Element ratingEl = card.selectFirst("span.ipc-rating-star--rating");
                    String rating = ratingEl.text();

                    Element votesEl = card.selectFirst("span.ipc-rating-star--voteCount");
                    String votesText = votesEl.text();
                    String votes = votesText.substring(1, votesEl.text().indexOf(")"));
                    double votesNum = 0;
                    if (votes.indexOf("K") > 0) {
                        votes = votes.substring(0, votes.indexOf("K"));
                        votesNum = Double.parseDouble(votes);
                        votesNum = votesNum * 1000;
                    } else {
                        votesNum = Double.parseDouble(votes);
                    }
                    int votesInt = (int) votesNum;

                    String episodeInfo = number + ";" + airDate + ";" + rating + ";" + votesInt;
                    fw.write(episodeInfo + "\n");
                }

            }
            fw.close();

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}