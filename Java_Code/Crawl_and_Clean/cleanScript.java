import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
public class cleanScript {
    public static void main(String[] args) {
        try {
            String parentFile = "C:\\Users\\Augustus\\Desktop\\Code_Things\\webcrawler\\output\\";
            File transcripts = new File(parentFile + "allTranscripts.txt");

            // Connect to the URL and get the HTML document
            ArrayList<String> lines = new ArrayList<>();
            try(BufferedReader br = new BufferedReader(new FileReader(transcripts))) {
                String line = "";
                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            FileWriter fw = new FileWriter(parentFile + "cleanTranscripts.txt");

            while (lines.size() > 0) {
                String text = lines.get(0);
                String lineInfo = text.substring(text.indexOf(";"));
                String line = text.substring(0, text.indexOf(";"));
                String error = "true";

                String firstLetter = "";

                while (!error.equals("false")) {
                    firstLetter = line.substring(0,1);
                    if (firstLetter.equals(" ")) {
                        line = line.substring(1);
                    } else if (firstLetter.equals("l")) {
                        line = "I" + line.substring(1);
                    } else if (firstLetter.equals(":")) {
                        line = line.substring(1);
                    }

                    error = checkForErrors(line);
                    line = fixError(line, error);
                }

                text = line + lineInfo;
                lines.remove(0);
                fw.write(text + "\n");
            }

            fw.close();

        } catch (Exception e) {
            System.err.println("Error fetching the web page: " + e.getMessage());
        }
    }

    public static String checkForErrors(String line) {
        //boolean hasError = false;
        if (line.indexOf(" l ") >= 0) {
            return " l ";
        }
        if (line.indexOf(" l'll ") >= 0) {
            return " l'll ";
        }
        if (line.indexOf(" l've ") >= 0) {
            return " l've ";
        }
        if (line.indexOf(" l'm ") >= 0) {
            return " l'm ";
        }
        if (line.indexOf(" l'd ") >= 0) {
            return " l'd ";
        }
        if (line.indexOf(" ls ") >= 0) {
            return " ls ";
        }
        if (line.indexOf(" ln ") >= 0) {
            return " ln ";
        }
        if (line.indexOf(" lt ") >= 0) {
            return " lt ";
        }
        if (line.indexOf("(") >= 0) {
            return "(";
        }
        if (line.indexOf("[") >= 0) {
            return "[";
        }
        if (line.indexOf(":") > 1) {
            if ((Character.isUpperCase(line.charAt(line.indexOf(":") - 1))) || (Character.isUpperCase(line.charAt(line.indexOf(":") - 2))))
            return ":";
        }
        return "false";
    }

    public static String fixError(String line, String errorText) {
        if (errorText.equals("false")) {
            return line;
        }

        int errorIndex = line.indexOf(errorText);
        String lp1 = line.substring(0, errorIndex);
        String lp2 = "";
        String lp3 = "";

        if (errorText.length() > 1) {
            lp2 = " I" + errorText.substring(2);
            lp3 = line.substring(errorIndex + errorText.length());
        } else if (errorText.equals("(")) {
            lp3 = line.substring(line.indexOf(")") + 1);
        } else if (errorText.equals("[")) {
            lp3 = line.substring(line.indexOf("]") + 1);
        } else if (errorText.equals(":")) {
            lp1 = line.substring(errorIndex + 1);
        }

        return lp1 + lp2 + lp3;
    }
}

