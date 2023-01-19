import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

public class StringConverter {

    public static String atrsToString(AttributedString atrs) {
        StringBuilder str = new StringBuilder();
        AttributedCharacterIterator iterator = atrs.getIterator();
        str.append(iterator.current());
        while (iterator.getIndex() < iterator.getEndIndex()) {
            str.append(iterator.next());
        }
        return str.substring(0, str.length() - 1);
    }

}
