package project.src.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Word {
    public static Set<String> stopWords;
    private String text;
    private String prefix;
    private String suffix;

    private String keyword;

    public static Word createWord(String rawText) {
        Word word = new Word();
        word.prefix = "";
        word.text = "";
        word.suffix = "";
        word.keyword = rawText;
        String[] rawTextSplit = rawText.split("");
        int lengthRawText = rawTextSplit.length;
        ArrayList<String> textArray = new ArrayList<>();
        for (String character : rawTextSplit) {
            if (character.matches("^[A-Z|a-z]|[0-9]*")) {
                textArray.add(character);
            }
        }

        if (rawText.length() > 0) {
            if (rawText.matches("^\\w+\\d$+|^\\d+|^[?!@#$%^,_-]+|^[\\s\\u0085\\p{Zs}]+\\w+" +
                            "|[a-zA-Z]+,[a-zA-Z]+|,\\w+[?.@#$%^,_-]*|^[\\s\\u0085\\p{Zs}]+|\\(\\w+(--)+\\w+\\)|\\w+[^A-Za-z,.'\")(!?:-]+|[^A-Za-z]+\\w+|\\w+[^A-Za-z,.'\")(!?:-]+\\w+|(^n't)|(^'s)|(^'ve)|[^A-Za-z]")) {
                word.text = rawText;
                return word;
            }

            if (rawText.matches("[^A-Za-z]")) {

            }

            String prefixPart = "";
            String actualWord = "";
            String suffixPart = "";
            if (textArray.size() > 0) {
                prefixPart = rawText.substring(0, rawText.indexOf(textArray.get(0)));
                actualWord = rawText.substring(rawText.indexOf(textArray.get(0)),
                        rawText.lastIndexOf(textArray.get(textArray.size() - 1)) + 1);
                if (rawText.indexOf(textArray.get(textArray.size() - 1)) < lengthRawText) {
                    if (rawText.contains("'s") || rawText.contains("'ve")) {
                        suffixPart = rawText.substring(rawText.lastIndexOf("'"));
                        actualWord = rawText.substring(rawText.indexOf(textArray.get(0)),
                                rawText.lastIndexOf("'"));
                    } else {
                        int indexLastChar = rawText.lastIndexOf(textArray.get(textArray.size() - 1));
                        suffixPart = rawText.substring(indexLastChar + 1);
                    }
                }
            }


            word.text = actualWord;
            word.prefix = prefixPart;
            word.suffix = suffixPart;
            return word;
        }
        return word;
    }

    public boolean isKeyword() {
        for (String stopWord : stopWords) {
            if (text.isEmpty() ||
                    text.matches("^\\w+\\d$+|^\\d+|^[?.!@#$%^,_-]+|^[\\s\\u0085\\p{Zs}]+\\w+" +
                            "|[a-zA-Z]+,[a-zA-Z]+|,\\w+[?.@#$%^,_-]*|^[\\s\\u0085\\p{Zs}]+|\\(\\w+(--)+\\w+\\)||\\w+[^A-Za-z,.'\")(!?:-]+|[^A-Za-z]+\\w+|\\w+[^A-Za-z,.'\")(!?:-]+\\w+|(^n't)|(^'s)|(^'ve)|[^A-Za-z]") ||
                    text.toLowerCase().equalsIgnoreCase(stopWord)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        Word otherWord = (Word) obj;
        return getText().equalsIgnoreCase(otherWord.getText());
    }

    public String toString() {
        return prefix + text + suffix;
    }

    public static boolean loadStopWords(String fileName) {
        stopWords = new HashSet<>();
        File file = new File(fileName);
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                stopWords.add(scanner.next());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public String getText() {
        return this.text;
    }

}
