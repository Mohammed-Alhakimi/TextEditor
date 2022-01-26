import java.util.*;

public class Main {

    public static void processIterator(String[] array) {
        ListIterator<String> iterator = Arrays.asList(array).listIterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
        while (iterator.hasPrevious()) {
            String word = iterator.previous();
            if (word.charAt(0) == 'J') {
                System.out.println(word.substring(1));
            }
        }
    }

    /* Do not change code below */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        processIterator(scanner.nextLine().split(" "));
    }
}