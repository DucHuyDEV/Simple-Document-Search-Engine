package project.src.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Query {
    private final List<Word> keywords;
    public Query(String searchPhrase) {
        this.keywords = new ArrayList<>();
        Word word;
        String[] searchPhraseSplit = searchPhrase.split(" ");
        for (String keyword : searchPhraseSplit) {
            word = Word.createWord(keyword);
            if (word.isKeyword()) {
                this.keywords.add(word);
            }
        }
    }

    public List<Word> getKeywords() {
        return this.keywords;
    }

    public List<Match> matchAgainst(Doc d) {
        List<Match> matches = new ArrayList<>();
        String doc = d.toString();
        String[] docArray = doc.split(" ");
        Collections.reverse(keywords);
        for (Word word : keywords) {
            int freq = 0;
            int firstIndex;
            Word otherWord;
            for (String s : docArray) {
                otherWord = Word.createWord(s);
                if (word.getText().equalsIgnoreCase(otherWord.getText())) {
                    freq++;
                    firstIndex = doc.indexOf(word.getText());
                    Match match = new Match(d, word, freq, firstIndex);
                    matches.add(match);
                    break;
                }
            }
        }
        return matches;
    }
}
