package project.src.engine;

import java.util.*;

public class Doc {
    private String title;
    private String body;

    public Doc(String content) {
        String[] splitContent = content.split("\n");
        this.title = splitContent[0];
        this.body = splitContent[1];
    }

    public List<Word> getTitle() {
        List<Word> wordList = new ArrayList<>();
        String[] titleSplit =  this.title.split(" ");
        for (String title : titleSplit) {
            Word w = Word.createWord(title);
            wordList.add(w);
        }
        return wordList;
    }

    public List<Word> getBody() {
        List<Word> wordBodyList = new ArrayList<>();
        String[] bodySplit =  this.body.split(" ");
        for (String title : bodySplit) {
            Word word = Word.createWord(title);
            wordBodyList.add(word);
        }
        return wordBodyList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doc doc = (Doc) o;
        return Objects.equals(this.title, doc.title) && Objects.equals(this.body, doc.body);
    }

    @Override
    public String toString() {
        return  this.title + " " + this.body;
    }
}
