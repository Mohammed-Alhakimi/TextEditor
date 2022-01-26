package search;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchThread extends SwingWorker<List<SearchItem>,Void> {
    String whatToSearchFor;
    String textToSearchIn;
    List<SearchItem> searchItems;

    public SearchThread(String whatToSearchFor, String textToSearchIn) {
        this.whatToSearchFor = whatToSearchFor;
        this.textToSearchIn = textToSearchIn;
    }

    @Override
    protected List<SearchItem> doInBackground() throws Exception {
        List<SearchItem> foundItems = new ArrayList<>();
        Pattern searchPattern= Pattern.compile(whatToSearchFor);
        Matcher matcher= searchPattern.matcher(textToSearchIn);
        while(matcher.find()){
           foundItems.add(new SearchItem(matcher.start(),matcher.end()));
        }

        return foundItems;
    }

}
