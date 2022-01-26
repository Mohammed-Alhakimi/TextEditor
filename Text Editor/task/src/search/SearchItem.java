package search;

import java.util.Iterator;

public class SearchItem implements Iterable{
    private int indexStart;
    private int indexEnd;
    private int id;
    private static int idCounter=0;

    public SearchItem(int indexStart, int indexEnd) {
        this.indexStart = indexStart;
        this.indexEnd = indexEnd;
        this.id=idCounter++;
    }

    public int getIndexStart() {
        return indexStart;
    }

    public int getIndexEnd() {
        return indexEnd;
    }

    @Override
    public Iterator iterator() {
        return null;
    }
}
