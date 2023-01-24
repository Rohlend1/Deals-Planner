
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;



public class Time{
    private static final DateTimeFormatter parserFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE, d MMM!HH:mm:ss");
    private static final DateTimeFormatter dfe = DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy");
    private static ArrayList<String> eventArr = new ArrayList<>();

    private static String getTime(){
        return dtf.format(LocalDateTime.now());
    }

    public static void addToCalendar(String[] str){
        if(str.length != 2) return;

        String date = str[0];
        String event = str[1];
        LocalDate dateTime = LocalDate.parse(date,parserFormatter);

        if(!eventArr.contains(event) || !(dfe.format(dateTime).equals(eventArr.get(eventArr.indexOf(event)+1).split(" - ")[0].strip()))) {
            eventArr.add(event);
            eventArr.add((dfe.format(dateTime) + " - " + event));
        }

    }
    public static String now(){
        return getTime();
    }


    public static ArrayList<String> getEvent(){
        return eventArr;
    }

    public static void setEvent(ArrayList<String> arr){
        eventArr = arr;
    }
}
