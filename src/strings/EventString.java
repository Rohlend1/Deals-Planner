package strings;

public class EventString extends AbstractString{

    private String date;

    public EventString(String text){
        super(text);

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
