import javax.swing.*;
import java.awt.font.TextAttribute;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.HashMap;



public class ButtonCreater extends Panel{
    private static JButton jButtonSave;
    private static JButton jButtonLoad;
    private static JButton jButtonAccept;
    private static JButton jButtonStrikeThrough;
    private static JButton jButtonAddEvent;
    private static JButton jButtonLeftArrow;
    private static JButton jButtonRightArrow;
    private static JButton jButtonClear;


    static JButton[] createButtons(int width, int height){ // Создаем сами кнопки
        JButton[] res = new JButton[8];
        jButtonSave = new JButton("Сохранить");
        jButtonLoad = new JButton("Загрузить");
        jButtonAccept = new JButton("Подтвердить");
        jButtonStrikeThrough = new JButton("Вычеркнуть");
        jButtonAddEvent = new JButton("Добавить событие");
        jButtonLeftArrow = new JButton("←");
        jButtonRightArrow = new JButton("→");
        jButtonClear = new JButton("Очистить");
        jButtonClear.setBounds(width/2 - 45,height/2 - 35,100,25);
        jButtonLeftArrow.setBounds(width/2 - 94,height/2 - 150,45,25);
        jButtonRightArrow.setBounds(width/2 + 45,height/2 - 150,45,25);
        jButtonSave.setBounds(width/2 - 100,height/2 - 70,100,25);
        jButtonLoad.setBounds(width/2 + 10,height/2 - 70,100,25);
        jButtonAccept.setBounds(width/2 - 100,height/2 - 105,100,25);
        jButtonStrikeThrough.setBounds(width/2 + 10,height/2 - 105,100,25);
        jButtonAddEvent.setBounds(width/2 + 10,height/2 - 105,100,25);
        createImpl();

        res[0] = jButtonAddEvent;
        res[1] = jButtonLoad;
        res[2] = jButtonSave;
        res[3] = jButtonStrikeThrough;
        res[4] = jButtonAccept;
        res[5] = jButtonRightArrow;
        res[6] = jButtonLeftArrow;
        res[7] = jButtonClear;
        return res;
    }

    public static void setButtonVisible(){
        if(id == 0){
            jButtonStrikeThrough.setVisible(true);
            jButtonAddEvent.setVisible(false);
        }
        else if(id == 1){
            jButtonStrikeThrough.setVisible(false);
            jButtonAddEvent.setVisible(true);
        }
        else{
            System.out.println("Ошибка идентификатора");
        }

    }

    private static void createImpl(){                               // Создаем реализацию кнопок
        jButtonClear.addActionListener(e -> {
            if(id == 0){
                Goals.getGoals().clear();
            }
            else if (id == 1){
                Time.getEvent().clear();
            }
        });
        jButtonLeftArrow.addActionListener(e -> {
            if(id != 0){
                id -= 1;
                setButtonVisible();
            }
        });
        jButtonRightArrow.addActionListener(e -> {
            if(id != 1){
                id += 1;
                setButtonVisible();
            }
        });
        jButtonLoad.addActionListener(e -> {
            try(ObjectInputStream goalsStream = new ObjectInputStream(new FileInputStream("goals_save.bin"));
                ObjectInputStream eventStream = new ObjectInputStream(new FileInputStream("events_save.bin"))){
                reset();
                setButtonVisible();
                if(id == 0){
                    Goals.getGoals().clear();
                    int size = goalsStream.readInt();
                    HashMap<Integer, AttributedString> goals= new HashMap<>();
                    for(int i = 0; i < size; i++){
                        AttributedString atrs = new AttributedString((String)goalsStream.readObject());
                        atrs.addAttribute(TextAttribute.FONT, plainFont);
                        goals.put(i, atrs);
                    }
                    Goals.setGoalsMap(goals);
                }
                else if(id == 1){
                    int size = eventStream.readInt();
                    ArrayList<String> events = new ArrayList<>(size);
                    for(int i = 0; i < size; i++){
                        events.add((String) eventStream.readObject());
                    }
                    Time.setEvent(events);
                }
            }
            catch(Exception q){
                System.out.println("Ошибка чтения");
                q.printStackTrace();
            }
        });
        jButtonSave.addActionListener(e -> {

            try(ObjectOutputStream goalsSave = new ObjectOutputStream(new FileOutputStream("goals_save.bin"));
                ObjectOutputStream eventsSave = new ObjectOutputStream(new FileOutputStream("events_save.bin")))
            {
                if(id == 0){
                    goalsSave.writeInt(saveGoals.size());
                    for(String str : saveGoals){
                        goalsSave.writeObject(str);
                    }
                }
                else if(id == 1){
                    eventsSave.writeInt(Time.getEvent().size());
                    for(String events : Time.getEvent()){
                        eventsSave.writeObject(events);
                    }

                }
            }
            catch (Exception x){
                System.out.println("Ошибка записи");
            }
        });

        jButtonAccept.addActionListener(e -> {
            String str = jTextField.getText();
            if(id == 0) {
                saveGoals.add(str);
                AttributedString atrs = new AttributedString(str);
                atrs.addAttribute(TextAttribute.FONT, plainFont);
                Goals.addGoal(atrs);
            }
        });
        jButtonStrikeThrough.addActionListener(e -> {
            int index = Integer.parseInt(jTextField.getText())-1;
            if(!(index < 0 || index >= saveGoals.size())) {
                AttributedString str = Goals.getGoals().get(index);
                str.addAttribute(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON, 0, StringConverter.atrsToString(str).length());
            }
        });

        jButtonAddEvent.addActionListener(e -> {
            String str = jTextField.getText();
            Time.addToCalendar(str.split(" "));
        });
    }
}
