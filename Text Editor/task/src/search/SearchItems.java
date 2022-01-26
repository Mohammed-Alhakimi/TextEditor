package search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SearchItems implements Iterable{

    private List<SearchItem> searchItems = new ArrayList<>();

    public void setSearchItems(List<SearchItem> searchItems) {
        this.searchItems = searchItems;
    }

    public List<SearchItem> getSearchItems() {
        return searchItems;
    }

    @Override
    public Iterator<SearchItem> iterator() {
        return searchItems.iterator();
    }
}
