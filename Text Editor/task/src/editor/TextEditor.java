package editor;

import search.SearchItem;
import search.SearchItems;
import search.SearchThread;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextEditor extends JFrame {
    private static final Color grayCustom;
    static {
        grayCustom = new Color(210, 203, 207);
    }
    private static int indexOfSearchItem;
    JTextArea textArea;
    JPanel panelUp;
    JPanel panelDown;
    JTextField filePathTextArea;
    JButton buttonSave;
    JButton buttonLoad;
    JButton buttonSearch;
    JButton buttonRight;
    JButton buttonLeft;
    JScrollPane pane;
    JFileChooser chooser;
    ImageIcon saveImage;
    ImageIcon loadImage;
    ImageIcon searchImage;
    ImageIcon rightImage;
    ImageIcon leftImage;
    JCheckBox regexCheckBox;
    SearchItems foundItems;
    ListIterator<SearchItem> iterator;


    public TextEditor() {

        super("TextEditor");
        this.foundItems= new SearchItems();

        /*__________________________ Icons __________________________ */
        this.saveImage=new ImageIcon("../icons/save-image.png");
        this.loadImage= new ImageIcon("../icons/load-image.png");
        this.searchImage = new ImageIcon("../icons/magnifying-glass.png");
        this.rightImage = new ImageIcon("../icons/right-arrow.png");
        this.leftImage = new ImageIcon("../icons/left-arrow.png");

        Icon iconSave = resizeIcon(saveImage, 30, 30);
        Icon iconLoad = resizeIcon(loadImage, 30, 30);
        Icon iconSearch = resizeIcon(searchImage, 30, 30);
        Icon iconLeft = resizeIcon(leftImage, 30, 30);
        Icon iconRight = resizeIcon(rightImage, 30, 30);


        /*__________________________ JButtons __________________________ */
        this.buttonSave = new JButton(iconSave);
        this.buttonSave.setBackground(grayCustom);
        this.buttonLoad = new JButton(iconLoad);
        this.buttonLoad.setBackground(grayCustom);
        this.buttonLeft = new JButton(iconLeft);
        this.buttonLeft.setBackground(grayCustom);
        this.buttonRight=new JButton(iconRight);
        this.buttonRight.setBackground(grayCustom);
        this.buttonSearch=new JButton(iconSearch);
        this.buttonSearch.setBackground(grayCustom);

        this.buttonSave.setName("SaveButton");
        this.buttonLoad.setName("LoadButton");
        this.buttonSearch.setName("SearchField");
        this.buttonSave.setFocusable(false);
        this.buttonLoad.setFocusable(false);
        chooser= new JFileChooser();
        this.chooser.setName("FileChooser");
        this.add(chooser);

        /*__________________________ Action listener load button __________________________ */
        this.buttonLoad.addActionListener(new LoadListener());

        /*__________________________ Action listener save button __________________________ */
        this.buttonSave.addActionListener(new SaveListener());
        /*__________________________ Action listener search button __________________________ */
        this.buttonSearch.addActionListener(new SearchListener());
        /*__________________________ Action listener next button __________________________ */
        this.buttonRight.addActionListener(new RightListener());
        /*__________________________ Action listener prev button __________________________ */
        this.buttonLeft.addActionListener(new LeftListener());
        this.buttonLoad.setName("OpenButton");
        this.buttonSearch.setName("StartSearchButton");
        this.buttonRight.setName("NextMatchButton");
        this.buttonLeft.setName("PreviousMatchButton");

        /*__________________________ JTextArea __________________________ */
        this.textArea = new JTextArea();
        this.textArea.setBounds(10, 10, 600, 500);
        this.textArea.setName("TextArea");
        this.textArea.setLineWrap(true);
        this.textArea.setMargin(new Insets(10, 10, 10, 10));
        /*__________________________ JTextAreaFilePath __________________________ */
        this.filePathTextArea = new JTextField();
        this.filePathTextArea.setPreferredSize(new Dimension(200, 30));
        this.filePathTextArea.setMargin(new Insets(0, 5, 0, 0));
        this.filePathTextArea.setName("SearchField");
        /*___________________________JCheckBox____________________________*/
        this.regexCheckBox= new JCheckBox("Use Regex");
        this.regexCheckBox.setBackground(grayCustom);
        this.regexCheckBox.setName("UseRegExCheckbox");
        /*__________________________ JPanel Up __________________________ */
        this.panelUp = new JPanel();
        this.panelUp = new JPanel();
        this.panelUp.setBackground(grayCustom);
        this.panelUp.setPreferredSize(new Dimension(100, 50));
        this.panelUp.setLayout(new FlowLayout());
        this.panelUp.add(buttonLoad);
        this.panelUp.add(buttonSave);
        this.panelUp.add(filePathTextArea);
        this.panelUp.add(buttonSearch);
        this.panelUp.add(buttonLeft);
        this.panelUp.add(buttonRight);
        this.panelUp.add(regexCheckBox);



        /*__________________________ JScrollPane __________________________ */
        this.pane = new JScrollPane(textArea);
        this.pane.setPreferredSize(new Dimension(100, 110));
        this.pane.setName("ScrollPane");
        this.pane.setBounds(100, 100, 100, 100);
        /*__________________________ JPanel Down __________________________ */
        this.panelDown = new JPanel();
        this.panelDown.setBackground(Color.red);
        this.panelDown.setLayout(new BorderLayout());
        this.panelDown.setPreferredSize(new Dimension(100, 510));
        /*__________________________ JMenuBar __________________________ */
        CustomMenu customMenu = new CustomMenu();
        setJMenuBar(customMenu);
        customMenu.getSaveItem().addActionListener(new SaveListener());
        customMenu.getExitItem().addActionListener(e->this.dispose());
        customMenu.getStartSearch().addActionListener(new SearchListener());
        customMenu.getNextMatch().addActionListener(new RightListener());
        customMenu.getPrevSearch().addActionListener(new LeftListener());
        customMenu.getUseRegex().addActionListener(e->{
            boolean selected = regexCheckBox.isSelected();
            regexCheckBox.setSelected(!selected);
        });

        customMenu.getOpenItem().addActionListener(new LoadListener());
        /*__________________________ JFrame __________________________ */

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        setLayout(new BorderLayout());
        add(panelUp, BorderLayout.NORTH);
        getContentPane().add(pane, BorderLayout.CENTER);
        setVisible(true);
        System.out.println(getWidth());

    }
    private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
    private class  SaveListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            chooser.showSaveDialog(null);
            File selectedFile = chooser.getSelectedFile();
            String filePath = selectedFile.getPath();
            try (
                    BufferedWriter br
                            = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)))) {
                String text = textArea.getText();
                br.write(text);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private class LoadListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            chooser.showOpenDialog(null);
            File selectedFile = chooser.getSelectedFile();
            filePathTextArea.setText(selectedFile.getPath());
            String filePath = filePathTextArea.getText();
            Path path = Paths.get(filePath);
            try {
                byte[] bytes = Files.readAllBytes(path);
                textArea.setText(new String(bytes));
            } catch (IOException ioException) {
                textArea.setText("");
                System.out.println("File doesn't exist");
            }
        }
    }
    private class SearchListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            String search = filePathTextArea.getText();

            SearchThread thread = new SearchThread(search, textArea.getText());
            thread.execute();
            try {
                foundItems.setSearchItems(thread.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            try{
                textArea.setCaretPosition(foundItems.getSearchItems().get(0).getIndexEnd());
                System.out.println(textArea.getCaretPosition());
                textArea.select(foundItems.getSearchItems().get(0).getIndexStart(), foundItems.getSearchItems().get(0).getIndexEnd());
                textArea.grabFocus();
                iterator = foundItems.getSearchItems().listIterator();
                iterator.next();
            }catch(Exception e){
                System.out.println("Not found");
            }

        }
    }
    private class RightListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {


            if (iterator.hasNext()){
                SearchItem next = iterator.next();
                textArea.setCaretPosition(next.getIndexEnd());
                System.out.println(textArea.getCaretPosition());
                textArea.select(next.getIndexStart(),next.getIndexEnd());
                textArea.grabFocus();
            }else{
                SearchItem lastItem = foundItems.getSearchItems().get(foundItems.getSearchItems().size()-1);
                textArea.setCaretPosition(lastItem.getIndexEnd());
                System.out.println(textArea.getCaretPosition());
                textArea.select(lastItem.getIndexStart(),lastItem.getIndexEnd());
                textArea.grabFocus();
            }
        }
    }
    private class LeftListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(iterator.hasPrevious()){
                SearchItem prev = iterator.previous();
                textArea.setCaretPosition(prev.getIndexEnd());
                System.out.println(textArea.getCaretPosition());
                textArea.select(prev.getIndexStart(),prev.getIndexEnd());
                textArea.grabFocus();
            }else{
                textArea.setCaretPosition(foundItems.getSearchItems().get(0).getIndexEnd());
                System.out.println(textArea.getCaretPosition());
                textArea.select(foundItems.getSearchItems().get(0).getIndexStart(), foundItems.getSearchItems().get(0).getIndexEnd());
                textArea.grabFocus();
            }

        }
    }
}
