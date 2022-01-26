package editor;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class CustomMenu extends JMenuBar {

    JMenu fileMenu;
    JMenu searchMenu;
    JMenuItem openItem;
    JMenuItem saveItem;
    JMenuItem startSearch;
    JMenuItem prevSearch;
    JMenuItem nextMatch;

    public JMenuItem getStartSearch() {
        return startSearch;
    }

    public JMenuItem getPrevSearch() {
        return prevSearch;
    }

    public JMenuItem getNextMatch() {
        return nextMatch;
    }

    public JMenuItem getUseRegex() {
        return useRegex;
    }

    JMenuItem useRegex;

    public JMenu getFileMenu() {
        return fileMenu;
    }

    public JMenuItem getOpenItem() {
        return openItem;
    }

    public JMenuItem getSaveItem() {
        return saveItem;
    }

    public JMenuItem getExitItem() {
        return exitItem;
    }

    JMenuItem  exitItem;

    public JMenu getSearchMenu() {
        return searchMenu;
    }

    public CustomMenu() {
        this.fileMenu = new JMenu("File");
        this.searchMenu = new JMenu("Search");
        this.openItem = new JMenuItem("Open");
        this.saveItem = new JMenuItem("Save");
        this.exitItem = new JMenuItem("Exit");
        this.startSearch = new JMenuItem("Start Search");
        this.prevSearch = new JMenuItem("Previous Search");
        this.nextMatch = new JMenuItem("Next Match");
        this.useRegex = new JMenuItem("Use regular expressions");

        this.add(fileMenu);
        this.add(searchMenu);
        this.fileMenu.add(openItem);
        this.fileMenu.add(saveItem);
        this.fileMenu.addSeparator();
        this.fileMenu.add(exitItem);
        this.searchMenu.add(startSearch);
        this.searchMenu.add(prevSearch);
        this.searchMenu.add(nextMatch);
        this.searchMenu.add(useRegex);
        
        getFileMenu().setName("MenuFile");
        getOpenItem().setName("MenuOpen");
        getSaveItem().setName("MenuSave");
        getExitItem().setName("MenuExit");
        getSearchMenu().setName("MenuSearch");
        getStartSearch().setName("MenuStartSearch");
        getNextMatch().setName("MenuNextMatch");
        getPrevSearch().setName("MenuPreviousMatch");
        getUseRegex().setName("MenuUseRegExp");

        getFileMenu().setMnemonic(KeyEvent.VK_F);
        getOpenItem().setMnemonic(KeyEvent.VK_O);
        getSaveItem().setMnemonic(KeyEvent.VK_S);
        getExitItem().setMnemonic(KeyEvent.VK_F4);
    }
}
