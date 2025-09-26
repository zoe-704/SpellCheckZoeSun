import java.util.ArrayList;
import java.util.Arrays;

/**
 * Spell Check
 * A puzzle written by Zach Blick
 * for Adventures in Algorithms
 * At Menlo School in Atherton, CA
 *
 * Completed by: Zoe Sun
 * */

public class SpellCheck {

    // takes 3s 117ms
    public static int binarySearch (String word, String[] dictionary) {
        int low = 0, high = dictionary.length-1;
        while (low <= high) {
            int mid = (low + high)/2;
            int wordDif = word.compareTo(dictionary[mid]);
            if (wordDif == 0) {
                return mid;
            } else if (wordDif < 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }
    /**
     * checkWords finds all words in text that are not present in dictionary
     *
     * @param text The list of all words in the text.
     * @param dictionary The list of all accepted words.
     * @return String[] of all misspelled words in the order they appear in text. No duplicates.
     */
    public String[] checkWords(String[] text, String[] dictionary) {
        ArrayList<String>misspelled = new ArrayList<>();
        for (String word : text) {
            int search = binarySearch(word, dictionary);
            if (search == -1 && !misspelled.contains(word)) {
                misspelled.add(word);
            }
        }
        return misspelled.toArray(new String[0]);
    }
}
