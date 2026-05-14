import java.io.File;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class Transcript {
    private final Line[] allLines;
    private int numOfLines;
    private final int nearDef = 2;

    public Transcript() {
        numOfLines = 100;
        allLines = new Line[numOfLines];
        for (int i = 0; i < numOfLines; i++) {
            allLines[i] = new Line(i);
        }
    }

    public Transcript(File file) {
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = "";
            br.readLine();
            while ((line = br.readLine()) != null) {
                numOfLines++;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        allLines = new Line[numOfLines];

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            for (int i = 0; i < numOfLines; i++) {
                allLines[i] = new Line(br.readLine());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Transcript(Line[] lines) {
        allLines = lines;
        numOfLines = lines.length;
    }

    public int getNumOfLines() {
        return numOfLines;
    }

    public String getText(int i) {
        return allLines[i].getText();
    }

    public boolean[] isInLine(String str) {
        boolean[] returnArr = new boolean[numOfLines];
        for (int i = 0; i < numOfLines; i++) {
            returnArr[i] = allLines[i].hasText(str);
        }
        return returnArr;
    }

    public boolean[] isNearLine(String str) {
        boolean[] returnArr = new boolean[numOfLines];
        for (int i = 0; i < numOfLines; i++) {
            if (allLines[i].hasText(str)) {
                for (int j = -nearDef; j <= nearDef; j++){
                    if (!((i + j) >= 0) && !((i + j) < allLines.length)) {
                        returnArr[i + j] = true;
                    }
                }
            }
        }
        return returnArr;
    }

    public boolean[] isNameInLine(String name) {
        boolean[] returnArr = new boolean[numOfLines];
        for (int i = 0; i < numOfLines; i++) {
            returnArr[i] = allLines[i].hasName(name);
        }
        return returnArr;
    }

    public boolean[] isNameNearLine(String name) {
        boolean[] returnArr = new boolean[numOfLines];
        for (int i = 0; i < numOfLines; i++) {
            if (allLines[i].hasName(name)) {
                for (int j = -nearDef; j <= nearDef; j++){
                    if (((i + j) >= 0) && ((i + j) < allLines.length)) {
                        returnArr[i + j] = true;
                    }
                }
            }
        }
        return returnArr;
    }

    public Transcript getEpisodeLines(int season, int episode) {
        int startRange = numOfLines;
        int endRange = 0;

        for (int i = 0; i < numOfLines; i++) {
            if (allLines[i].isEpisode(season, episode)) {
                if (i < startRange) {
                    startRange = i;
                }
                else if (i > endRange) {
                    endRange = i;
                }
            }
        }

        Line[] episodeLines = new Line[endRange - startRange + 1];
        int j = 0;
        for (int i = startRange; i <= endRange; i++) {
            episodeLines[j] = allLines[i];
            j++;
        }

        Transcript episodeTranscript = new Transcript(episodeLines);

        return episodeTranscript;
    }

    public Transcript getSeasonLines(int season) {
        int startRange = numOfLines;
        int endRange = 0;

        for (int i = 0; i < numOfLines; i++) {
            if (allLines[i].isSeason(season)) {
                if (i < startRange) {
                    startRange = i;
                }
                else if (i > endRange) {
                    endRange = i;
                }
            }
        }

        Line[] episodeLines = new Line[endRange - startRange + 1];
        int j = 0;
        for (int i = startRange; i <= endRange; i++) {
            episodeLines[j] = allLines[i];
            j++;
        }

        Transcript episodeTranscript = new Transcript(episodeLines);

        return episodeTranscript;
    }
}
