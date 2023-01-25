package strings;
/*
Класс родитель для строк событий и целей
 */

import java.io.Serializable;

public abstract class AbstractString implements Serializable {
    private boolean isStrikeThrough = false;
    private final String text;

    public AbstractString(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public boolean isStrikeThrough() {
        return isStrikeThrough;
    }

    public void setStrikeThrough(boolean strikeThrough) {
        isStrikeThrough = strikeThrough;
    }
}
