
import strings.EventString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;



public class Time{
    private static final DateTimeFormatter parserFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE, d MMM!HH:mm:ss");
    private static final DateTimeFormatter dfe = DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy");
    private static ArrayList<EventString> eventArr = new ArrayList<>();

    private static String getTime(){
        return dtf.format(LocalDateTime.now());
    }

    public static void addToCalendar(String[] str){
        if(str.length != 2) return;

        String date = str[0];
        String event = str[1];
        LocalDate dateTime = LocalDate.parse(date,parserFormatter);

        if(!checkEvent(event) || !checkDate(dfe.format(dateTime))) {
            eventArr.add(new EventString(event));
            EventString tempEvent = new EventString(dfe.format(dateTime) + " - " + event);
            eventArr.add(tempEvent);
            tempEvent.setDate(dfe.format(dateTime));
        }

    }
    public static String now(){
        return getTime();
    }

    private static boolean checkEvent(String str){
        if(str == null) return false;
        for(EventString event : eventArr){
            if(str.equals(event.getText())) return true;
        }
        return false;
    }

    private static boolean checkDate(String date){
        if(date == null) return false;
        for(EventString event : eventArr){
            if(date.equals(event.getDate())) return true;
        }
        return false;
    }
    public static ArrayList<EventString> getEvent(){
        return eventArr;
    }

    public static void setEvent(ArrayList<EventString> arr){
        eventArr = arr;
    }
}
