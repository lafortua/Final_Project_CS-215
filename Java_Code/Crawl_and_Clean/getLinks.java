import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class getLinks {
    public static void main(String[] args) {
        try {
            String url = "https://subslikescript.com/series/Cheers-83399";
            String parentFile = "C:\\Users\\Augustus\\Desktop\\Code_Things\\webcrawler\\output\\";
            File urlsList = new File(parentFile + "urlsList.txt");
            ArrayList<String> urls;

            // Connect to the URL and get the HTML document
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();

            // Print the title of the page
            System.out.println("Page Title: " + doc.title());

            Elements episodeLinks = doc.select("div.series_seasons div.season ul li a");

            FileWriter fw = new FileWriter(urlsList);
                for (Element link : episodeLinks) {
                    String episodeUrl = link.attr("abs:href");
                    fw.write(episodeUrl + "\n");
                }
            fw.close();

        } catch (Exception e) {
            System.err.println("Error fetching the web page: " + e.getMessage());
        }
    }
}