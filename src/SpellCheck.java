import java.util.*;

/**
 * Spell Check
 * A puzzle written by Zach Blick
 * for Adventures in Algorithms
 * At Menlo School in Atherton, CA
 *
 * Completed by: Zoe Sun
 * */

// trie and node implementation
public class Node {
    boolean isWord;
    Node[] next;
    public Node() {
        isWord = false;
    }
    boolean isWord() {
        return isWord;
    }
    Node[] getNext() {
        return next;
    }
    void setWord() {
        // todo
    }
}
public class Trie {
    Node root;
    public Trie () {
        root =
    }
    void insert(String s) {

    }
    Node lookup() {

    }
}

public class SpellCheck {
    /**
     * checkWords finds all words in text that are not present in dictionary
     *
     * @param text The list of all words in the text.
     * @param dictionary The list of all accepted words.
     * @return String[] of all misspelled words in the order they appear in text. No duplicates.
     */

    public String[] checkWords(String[] text, String[] dictionary) {
        // make a trie;

        return ;
    }

    /* approach using a linked hashet and hashset
    public String[] checkWords(String[] text, String[] dictionary) {
        Set<String> dict = new HashSet<>(Arrays.asList(dictionary)); // 992 ms, 957 ms, 1 s 23 ms
        Set<String> misspelled = new LinkedHashSet<>(); // ordered
        for (String word : text) {
            //int search = binarySearch(word, dictionary);
            //if (search == -1) {
                if (!dict.contains(word)) {
                    misspelled.add(word);
                }
                //misspelled.add(word);
            //}
        }
        return misspelled.toArray(new String[0]);
    }
     */

    /* binary search approach
    // takes 3s 49 ms, 117ms, 160 ms
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
    */
}
