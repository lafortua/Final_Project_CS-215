import java.io.*;
import java.util.ArrayList;

public class Names {
    public static void main(String[] args) {
        String parentFile = "C:\\Users\\Augustus\\Desktop\\Code_Things\\word_analysis_cheers\\input\\";
        File transcripts = new File(parentFile + "linesWithImdb.txt");

        Transcript tr = new Transcript(transcripts);

        writeKeywords(parentFile, 500);

        writeNameIndex(parentFile, tr);

        ArrayList<String> words = readCleanKeywords(parentFile);

        writeWordIndex(parentFile, tr, words);

    }

    private static ArrayList<String> readKeywords(String parentFile) {
        File keywordList = new File(parentFile + "Cheers_Keywords.txt");

        ArrayList<String> allKeywords = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(keywordList))) {
            String word = "";
            while ((word = br.readLine()) != null) {
                word = word.substring(0, word.indexOf(","));
                //System.out.println(word);
                allKeywords.add(word);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return allKeywords;
    }

    private static ArrayList<String> readCleanKeywords(String parentFile) {
        File cleanKeywordList = new File(parentFile + "Clean_Keywords.txt");

        ArrayList<String> cleanKeywords = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(cleanKeywordList))) {
            String word = "";
            while ((word = br.readLine()) != null) {
                cleanKeywords.add(word);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        cleanKeywords.remove(0);

        return cleanKeywords;
    }

    private static void writeToFile(File file, ArrayList<boolean[]> lists, ArrayList<String> labels) {
        if (lists == null || lists.isEmpty()) return;

        try (FileWriter fw = new FileWriter(file)) {
            // 1. Write the Header Row
            StringBuilder header = new StringBuilder();
            for (int i = 0; i < labels.size(); i++) {
                header.append(labels.get(i));
                if (i < labels.size() - 1) {
                    header.append(";");
                }
            }
            fw.write(header.toString() + "\n");

            // 2. Transpose and Write the Data Rows
            int numRowsInFile = lists.get(0).length;
            int numColumns = lists.size();

            for (int i = 0; i < numRowsInFile; i++) {
                StringBuilder line = new StringBuilder();

                for (int j = 0; j < numColumns; j++) {
                    // Convert boolean to 1 or 0
                    boolean val = lists.get(j)[i];
                    line.append(val ? "1" : "0");

                    if (j < numColumns - 1) {
                        line.append(";");
                    }
                }
                fw.write(line.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeNameIndex(String parentFile, Transcript tr) {
        File nameOutputFile = new File(parentFile + "Cheers_Name_Index.txt");

        boolean[] samArr = tr.isNameNearLine("Sam");
        boolean[] carlaArr = tr.isNameNearLine("Carla");
        boolean[] cliffArr = tr.isNameNearLine("Cliff");
        boolean[] normArr = tr.isNameNearLine("Norm");
        boolean[] frasierArr = tr.isNameNearLine("Frasier");
        boolean[] woodyArr = tr.isNameNearLine("Woody");
        boolean[] rebeccaArr = tr.isNameNearLine("Rebecca");
        boolean[] dianeArr = tr.isNameNearLine("Diane");
        boolean[] lilithArr = tr.isNameNearLine("Lilith");
        boolean[] coachArr = tr.isNameNearLine("Coach");
        boolean[] nickArr = tr.isNameNearLine("Nick");
        boolean[] eddieArr = tr.isNameNearLine("Eddie");
        boolean[] kellyArr = tr.isNameNearLine("Kelly");
        boolean[] garyArr = tr.isNameNearLine("Gary");

        ArrayList<boolean[]> output = new ArrayList<>();
        ArrayList<String> outputLabels = new ArrayList<>();

        outputLabels.add("Sam");
        outputLabels.add("Carla");
        outputLabels.add("Cliff");
        outputLabels.add("Norm");
        outputLabels.add("Frasier");
        outputLabels.add("Woody");
        outputLabels.add("Rebecca");
        outputLabels.add("Diane");
        outputLabels.add("Lilith");
        outputLabels.add("Coach");
        outputLabels.add("Nick");
        outputLabels.add("Eddie");
        outputLabels.add("Kelly");
        outputLabels.add("Gary");

        output.add(samArr);
        output.add(carlaArr);
        output.add(cliffArr);
        output.add(normArr);
        output.add(frasierArr);
        output.add(woodyArr);
        output.add(rebeccaArr);
        output.add(dianeArr);
        output.add(lilithArr);
        output.add(coachArr);
        output.add(nickArr);
        output.add(eddieArr);
        output.add(kellyArr);
        output.add(garyArr);

        writeToFile(nameOutputFile,output,outputLabels);
    }

    private static void writeWordIndex(String parentFile, Transcript tr, ArrayList<String> words){
        File wordOutputFile = new File(parentFile + "Cheers_Keyword_Index.txt");

        ArrayList<boolean[]> output = new ArrayList<>();
        ArrayList<String> outputLabels = new ArrayList<>();

        for (String word: words){
            boolean[] wordArr = tr.isInLine(word);

            outputLabels.add(word);
            output.add(wordArr);
        }

        writeToFile(wordOutputFile, output, outputLabels);
    }

    private static void writeKeywords(String parentFile, int numOfWords) {
        File keywordsOutputFile = new File(parentFile + "Clean_Keywords.txt");

        String[] names = {"Sam", "Malone", "Sammy", "Mayday", "Carla", "Tortelli", "LeBec", "Cliff", "Clavin", "Cliffie", "Norm", "Peterson", "Normie", "Norman", "Frasier", "Crane", "Fras", "Woody", "Boyd", "Rebecca", "Howe", "Diane", "Chambers", "Lilith", "Sternin", "Coach", "Ernie", "Pantusso", "Nick", "Tortelli", "Eddie", "LeBec", "Kelly", "Gaines", "Gary", "Walter", "Robin", "Vera", "Cliffy", "Wood", "car", "bruins", "anthony"};

        ArrayList<String> keywords = readKeywords(parentFile);

        boolean nameRemoved;
        for (String name: names) {
            nameRemoved = false;
            while (!nameRemoved) {
                for (int i = 0; i < keywords.size(); i++) {
                    if (keywords.get(i).equalsIgnoreCase(name)) {
                        keywords.remove(i);
                        nameRemoved = true;
                    }
                }
                nameRemoved = true;
            }
        }

        try (FileWriter fw = new FileWriter(keywordsOutputFile)) {
            // 1. Write the Header Row
            StringBuilder header = new StringBuilder();
            header.append(numOfWords);
            header.append("_Keywords");
            fw.write(header.toString() + "\n");
            int k = numOfWords;

            while ((keywords.size() > 0) && (k > 0)) {
                fw.write(keywords.get(0) + "\n");
                k--;
                keywords.remove(0);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}