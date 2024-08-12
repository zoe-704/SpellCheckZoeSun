import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpellCheckTest {

    private final SpellCheck studentSolution = new SpellCheck();
    private String[] allWords, textWords, badWords;

    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    public void testSentence() {
        setTestData("small", "sentence");
        assertArrayEquals(badWords, studentSolution.checkWords(textWords, allWords), "Incorrect words returned.");
    }

    @Test
    @Timeout(value = 400, unit = TimeUnit.MILLISECONDS)
    public void testPoem() {
        setTestData("large", "jabberwocky");
        assertArrayEquals(badWords, studentSolution.checkWords(textWords, allWords), "Incorrect words returned.");
    }

    @Test
    @Timeout(value = 600, unit = TimeUnit.MILLISECONDS)
    public void testAlice() {
        setTestData("large", "alice");
        assertArrayEquals(badWords, studentSolution.checkWords(textWords, allWords), "Incorrect words returned.");
    }

    @Test
    @Timeout(value = 900, unit = TimeUnit.MILLISECONDS)
    public void testBard() {
        setTestData("large", "shakespeare");
        assertArrayEquals(badWords, studentSolution.checkWords(textWords, allWords), "Incorrect words returned.");
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    public void testRome() throws IOException {
        setTestData("large", "rome");
        assertArrayEquals(badWords, studentSolution.checkWords(textWords, allWords), "Incorrect words returned.");
    }

    private void setTestData(String dictionary, String text) {
        // Open files
        try {
            BufferedReader dictReader = new BufferedReader(new FileReader("dictionaries/" + dictionary + ".txt"));
            BufferedReader textReader = new BufferedReader(new FileReader("test_files/" + text + "_cleaned.txt"));
            BufferedReader answerReader = new BufferedReader(new FileReader("test_files/" + text + "_misspelled.txt"));

            allWords = loadWords(dictReader);
            textWords = loadText(textReader);
            badWords = loadWords(answerReader);

        } catch (IOException e) {
            System.out.println("Error opening test file " + text + ".txt");
            e.printStackTrace();
        }
    }

    private String[] loadWords(BufferedReader br) {
        String line;
        try {
            line = br.readLine();

            // Update instance variables with test data
            int n = Integer.parseInt(line);
            String[] words = new String[n];

            for (int i = 0; i < n; i++) {
                line = br.readLine();
                words[i] = line;
            }
            return words;
        } catch (IOException e) {
            System.out.println("Error opening test file");
            e.printStackTrace();
        }
        return null;
    }

    private String[] loadText(BufferedReader br) {
        ArrayList<String> wordList = new ArrayList<>();
        String[] words;
        String line;
        try {
            while ((line = br.readLine()) != null) {
                // Split the line into words by spaces
                if (!line.isEmpty()) {
                    words = line.split("[\\s]+"); // Splitting at spaces
                    wordList.addAll(Arrays.asList(words));
                }
            }

            // Copy over to array and set to lowercase
            int n = wordList.size();
            words = new String[n];
            for (int i = 0; i < n; i++) {
                words[i] = wordList.get(i);
            }

            return words;

        } catch (IOException e) {
            System.out.println("Error opening text file");
            e.printStackTrace();

        }
        return null;
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    /**
     * Cleans the given textfile. Removes em-dashes, possessive apostrophes, and leading and
     * trailing apostrophes. Splits characters by spaces or punctuation. Removes numeric words.
     * Sets everything to lowercase.
     * @param br
     * @param w
     */
    public static void cleanText(String text) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("test_files/" + text + ".txt"));
        BufferedWriter w = new BufferedWriter(new FileWriter("test_files/" + text + "_cleaned.txt"));
        ArrayList<String> wordList = new ArrayList<>();
        String[] words;
        String line;
        try {
            while ((line = br.readLine()) != null) {
                // Split the line into words by spaces
                if (line.length() > 0) {
                    words = line.split("[\\s\\p{Punct}—&&[^']]+"); // Splitting at spaces, ignoring punctuation
                    int n = words.length;
                    for(String word : words) {
                        word = word.toLowerCase();
                        word = word.replaceAll("^'+|'+$", "");
                        // Remove "'s" from the string
                        word = word.replaceAll("'s\\b", "");
                        if(!word.equals("") && !isNumeric(word)) {
                            wordList.add(word);
                            w.write(word);
                            w.write(" ");
                        }
                    }
                }
                w.write("\n");
            }
            w.close();

        } catch (IOException e) {
            System.out.println("Error opening text file");
            e.printStackTrace();

        }
    }
}
