import java.util.*;

/**
 * Spell Check
 * A puzzle written by Zach Blick
 * for Adventures in Algorithms
 * At Menlo School in Atherton, CA
 * Completed by: Zoe Sun
 * */

// #1: TST implementation
// Create Node class that contains insert and lookup method
class Node {
    char data; // Letter assigned to each node
    boolean isEndOfString; // End of valid word
    Node left, eq, right; // TST only points to 3 child nodes

    // Node constructor
    public Node(char data) {
        this.data = data;
        this.isEndOfString = false;
        this.left = null;
        this.eq = null;
        this.right = null;
    }
    // Insert word into TST given root node
    public static Node insert (Node root, String word) {
        char c = word.charAt(0);
        // Create node for current letter
        if (root == null) root = new Node(c);
        // Compare current character with root’s character
        if (c < root.data) { // Left for smaller chars
            root.left = insert(root.left, word);
        } else if (c > root.data) { // Right for bigger chars
            root.right = insert(root.right, word);
        } else { // Letter matches
            if (word.length() > 1) { // Move down the tree for next character
                root.eq = insert(root.eq, word.substring(1));
            } else { // Mark the end of a word
                root.isEndOfString = true;
            }
        }
        return root;
    }
    // Lookup word in TST
    public static boolean lookup(Node root, String word) {
        if (root == null) return false; // Word not found

        char c = word.charAt(0);
        // Compare current character with root’s character
        if (c < root.data) { // Search left subtree
            return lookup(root.left, word);
        } else if (c > root.data) { // Search right subtree
            return lookup(root.right, word);
        } else {
            if (word.length() == 1) { // Return if last character is end of a word in TST
                return root.isEndOfString;
            }
            // Recursive call to continue looking up next character
            return lookup(root.eq, word.substring(1));
        }
    }
    // Convert TST to a list of all stored words
    public static List<String> toArray(Node root) {
        List<String> result = new ArrayList<>();
        compileWords(root, "", result);
        return result;
    }

    // Recursively compile the words in the TST
    private static void compileWords(Node node, String prefix, List<String> result) {
        if (node == null) return;

        // Traverse left subtree
        compileWords(node.left, prefix, result);

        // Process current node
        String newPrefix = prefix + node.data;
        if (node.isEndOfString) result.add(newPrefix);

        // Traverse middle subtree
        compileWords(node.eq, newPrefix, result);

        // Traverse right subtree
        compileWords(node.right, prefix, result);
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
        Node dictRoot = null; // Root node of TST
        // Insert every word in dictionary into TST
        for (String word : dictionary) {
            dictRoot = Node.insert(dictRoot, word);
        }
        // Initialize an ordered misspelled set and add words to it that are not in dictionary
        Set<String> misspelled = new LinkedHashSet<>();
        for (String word : text) {
            if (!Node.lookup(dictRoot, word)) {
                misspelled.add(word);
            }
        }
        // Return misspelled set as an array
        return misspelled.toArray(new String[0]);
    }
        /* SLOWER
        // Initialize a misspelled TST and add words to it that are not in dictionary
        Node misspelledRoot = null;
        Set<String> seen = new LinkedHashSet<>(); // Track inserted misspelled words

        for (String word : text) {
            if (!Node.lookup(dictRoot, word) && !seen.contains(word)) {
                misspelledRoot = Node.insert(misspelledRoot, word);
                seen.add(word);
            }
        }
        List<String> misspelled = Node.toArray(misspelledRoot);

        // Return misspelled set as an array
        return seen.toArray(new String[0]);

    }*/

}

/*
// #2: trie and node implementation
class Node {
    boolean isWord;
    Node[] next;
    public Node() {
        isWord = false;
        next = new Node[256];
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

    void insert(String s) {
        Node cur = root;
        for (char letter : s.toCharArray()) {
            int index = letter;
            if (cur.next[index] == null) cur.next[index] = new Node();
            cur = cur.next[index];
        }
        cur.setWord();
    }
    boolean lookup(String s) {
        Node cur = root;
        for (char letter : s.toCharArray()) {
            int index = letter;
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
     **/
    // 560 ms, 564 ms, 523 ms with trie
/*
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
*/
/*
// #3: approach using a linked hashset and hashset 483, 447, 436 ms
public class SpellCheck {

    public String[] checkWords(String[] text, String[] dictionary) {
        Set<String> dict = new HashSet<>(Arrays.asList(dictionary)); // 992 ms, 957 ms, 1 s 23 ms
        Set<String> misspelled = new LinkedHashSet<>(); // ordered
        for (String word : text) {
            if (!dict.contains(word)) {
                misspelled.add(word);
            }
        }
        return misspelled.toArray(new String[0]);
    }
}
*/