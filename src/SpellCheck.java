import java.util.*;

/**
 * Spell Check
 * A puzzle written by Zach Blick
 * for Adventures in Algorithms
 * At Menlo School in Atherton, CA
 * Completed by: Zoe Sun
 * */

// trie and node implementation
class Node {
    boolean isWord;
    Node[] next;
    public Node() {
        isWord = false;
        next = new Node[27]; // a-z + '
    }
    void setWord() {
        isWord = true;
    }

}
class Trie {
    Node root;
    public Trie () {
        root = new Node();
    }

    private int letterToIndex(char letter) {
        if (letter == '\'') return 26;
        if (letter >= 'a' && letter <= 'z') return letter - 'a';
        return -1; // not valid
    }

    void insert(String s) {
        Node cur = root;
        for (char letter : s.toCharArray()) {
            int index = letterToIndex(letter);
            if (cur.next[index] == null) cur.next[index] = new Node();
            cur = cur.next[index];
        }
        cur.setWord();
    }
    boolean lookup(String s) {
        Node cur = root;
        for (char letter : s.toCharArray()) {
            int index = letterToIndex(letter);
            if (cur.next[index] == null) return false;
            cur = cur.next[index];
        }
        return cur.isWord;
    }
}

public class SpellCheck {
    /**
     * checkWords finds all words in text that are not present in dictionary
     *
     * @param text       The list of all words in the text.
     * @param dictionary The list of all accepted words.
     * @return String[] of all misspelled words in the order they appear in text. No duplicates.
     */
    // 560 ms, 564 ms, 523 ms with trie
    public String[] checkWords(String[] text, String[] dictionary) {
        // Initialize new trie and insert words into it
        Trie trie = new Trie();
        for (String word : dictionary) {
            trie.insert(word);
        }
        // Initialize an ordered misspelled set and add words to it that are not in dictionary
        Set<String> misspelled = new LinkedHashSet<>();
        for (String word : text) {
            if (!trie.lookup(word)) {
                misspelled.add(word);
            }
        }
        // Return misspelled set as an array
        return misspelled.toArray(new String[0]);
    }
}
/*//approach using a linked hashet and hashset 483, 447, 436 ms
public class SpellCheck {

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
} */
