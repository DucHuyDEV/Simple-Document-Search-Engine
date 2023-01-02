package project.src.engine;

public class Match implements Comparable<Match>{
    private final int freq;
    private final int firstIndex;

    private final Word word;
    private final Doc doc;

    public Match(Doc d, Word w, int freq, int firstIndex) {
        this.freq = freq;
        this.firstIndex = firstIndex;
        this.word = w;
        this.doc = d;
    }
    public int getFreq() {
        return this.freq;
    }

    public int getFirstIndex() {
        return this.firstIndex;
    }

    @Override
    public int compareTo(Match o) {
        if (this.firstIndex > o.firstIndex) {
            return 1;
        } else if (this.firstIndex < o.firstIndex) {
            return -1;
        }
        return 0;
    }

    public Word getWord() {
        return this.word;
    }
}
