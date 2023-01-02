package project.src.engine;

import java.util.ArrayList;
import java.util.List;

public class Result implements Comparable<Result>{

    private Doc d;
    private List<Match> matches;

    public Result(Doc d, List<Match> matches){
        this.d = d;
        this.matches = matches;
    }

    public List<Match> getMatches() {
        return this.matches;
    }

    public int getTotalFrequency() {
        int totalFrequency = 0;
        for (Match m : this.matches) {
            totalFrequency += m.getFreq();
        }

        return totalFrequency;
    }

    public double getAverageFirstIndex() {
        String[] docWords = d.toString().split(" ");
        int count = 0;
        for (Match m : this.matches) {
            count = 0;
            for (String docWord : docWords) {
                Word word = Word.createWord(docWord);
                if (m.getWord().getText().equalsIgnoreCase(word.getText())) {
                    count++;
                }
            }
        }
        return count;
    }

    public String htmlHighlight() {
        StringBuilder html = new StringBuilder();
        List<Word> titleWords = d.getTitle();
        List<Word> bodyWords = d.getBody();
        List<String> newTitleWords = new ArrayList<>();
        List<String> newBodyWords = new ArrayList<>();
        StringBuilder newTitle = new StringBuilder("<h3>");
        StringBuilder newBody = new StringBuilder("<p>");
        for (Word word : titleWords) {
            String newWord = word.getPrefix() + word.getText() + word.getSuffix();
            for (Match m : matches) {
                if (word.getText().equalsIgnoreCase(m.getWord().getText())) {
                    newWord = word.getPrefix() + "<u>" + word.getText() + "</u>" + word.getSuffix();
                    break;
                }
            }
            newTitleWords.add(newWord);
        }

        for (Word word : bodyWords) {
            String newWord = word.getPrefix() + word.getText() + word.getSuffix();
            for (Match m : matches) {
                if (word.getText().equalsIgnoreCase(m.getWord().getText())) {
                    newWord = word.getPrefix() + "<b>" + word.getText() + "</b>" + word.getSuffix();
                    break;
                }
            }
            newBodyWords.add(newWord);
        }

        for (int i = 0; i < newTitleWords.size(); i++) {
            if (i == newTitleWords.size() - 1) {
                newTitle.append(newTitleWords.get(i));
                break;
            }
            newTitle.append(newTitleWords.get(i)).append(" ");
        }
        newTitle.append("</h3>");


        for (int i = 0; i < newBodyWords.size(); i++) {
            if(i == newBodyWords.size() - 1) {
                newBody.append(newBodyWords.get(i));
                break;
            }
            newBody.append(newBodyWords.get(i)).append(" ");
        }

        newBody.append("</p>");
        html.append(newTitle).append(newBody);

        return html.toString();
    }

    @Override
    public int compareTo(Result o) {
        if (this.matches.size() > o.matches.size()) {
            if (this.getTotalFrequency() > o.getTotalFrequency()) {
                return 1;
            } else if (this.getTotalFrequency() == o.getTotalFrequency()) {
                if (this.getAverageFirstIndex() > o.getAverageFirstIndex() ||this.getAverageFirstIndex() == o.getAverageFirstIndex()) {
                    return 1;
                } else {
                    return 0;
                }
            } else {
                return Double.compare(this.getAverageFirstIndex(), o.getAverageFirstIndex());
            }
        } else if(this.matches.size() == o.matches.size()){
            if (this.getTotalFrequency() > o.getTotalFrequency()) {
                if (this.getAverageFirstIndex() == o.getAverageFirstIndex()
                || this.getAverageFirstIndex() > o.getAverageFirstIndex()) {
                    return 1;
                } else {
                    return 0;
                }
            } else if (this.getTotalFrequency() == o.getTotalFrequency()) {
                return Double.compare(this.getAverageFirstIndex(), o.getAverageFirstIndex());
            } else {
                if (this.getAverageFirstIndex() > o.getAverageFirstIndex()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        } else {
            if (this.getTotalFrequency() > o.getTotalFrequency()) {
                return Double.compare(this.getAverageFirstIndex(), o.getAverageFirstIndex());
            } else if (this.getTotalFrequency() == o.getTotalFrequency()) {
                if (this.getAverageFirstIndex() <= o.getAverageFirstIndex()) {
                    return -1;
                } else {
                    return 0;
                }
            } else if (this.getTotalFrequency() < o.getTotalFrequency()){
                return -1;
            }
        }
        return 0;
    }

    public Doc getDoc() {
        return this.d;
    }
}
