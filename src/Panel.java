

import strings.EventString;
import strings.GoalString;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.TextAttribute;
import java.io.*;
import java.text.AttributedString;
import java.util.*;
import java.util.List;
import javax.swing.*;


public class Panel extends JPanel implements Serializable {

    private static final JFrame f = new JFrame();
    static ArrayList<String> saveGoals = new ArrayList<>(); // для сохранения целей и индексов, т.к. atributtedstring несериализуем

    public static final JTextField jTextField = new JTextField(10);
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;
    static final Font plainFont = new Font("Times New Roman", Font.PLAIN, 20);
    private static Component panel = anotherPanel();
    public static AttributedString atrsTime;
    public static AttributedString atrsDay;
    public static JButton mainTempButton;
    static int id = 0; // Идентификатор текущего окна(0 - запись целей, 1 - запись событий на число)

    private static final Font font = new Font("Times New Roman", Font.BOLD, 20);


    public Panel() {
        setLayout(null);
        setBackground(Color.getHSBColor(22,33,45));
        JButton[] buttons = ButtonCreater.createButtons(WIDTH, HEIGHT);
        jTextField.setBounds(WIDTH / 2 - 50, HEIGHT / 2 - 150, 100, 25);

        for (JButton button : buttons) {
            if(button.getText().equals("Подтвердить")) mainTempButton = button;
            add(button);
        }
        KeyListener acceptKey = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    for (JButton button : buttons) {
                        if(button.getText().equals("Подтвердить")){
                            button.doClick();
                        }

                    }
                }
            }
        };


        addKeyListener(acceptKey);
        add(jTextField);
        ButtonCreater.setButtonVisible();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        String[] arr = Time.now().split("!");
        atrsDay = new AttributedString(arr[0]);
        atrsTime = new AttributedString(arr[1]);
        setAttributesToStrings();

        g.drawString(Panel.atrsDay.getIterator(), WIDTH - 130,40);
        g.drawString(Panel.atrsTime.getIterator(), WIDTH - 135, 25);
        if(id == 0) {
            AttributedString atrs = new AttributedString("<<Запись цели>>");
            atrs.addAttribute(TextAttribute.FONT, font);
            g.drawString(atrs.getIterator(), WIDTH/2 - 83, 30);
            paintGoals(g, Goals.getGoals());
        }
        else if(id == 1) {
            AttributedString atrs = new AttributedString("<<Запись события>>");
            AttributedString hint = new AttributedString("Формат ввода: (дд.мм.гггг \"событие\")");
            atrs.addAttribute(TextAttribute.FONT, font);
            hint.addAttribute(TextAttribute.FONT, new Font("Times New Roman", Font.PLAIN, 12));
            g.drawString(atrs.getIterator(), WIDTH/2 - 86, 30);
            g.drawString(hint.getIterator(),WIDTH/2 - 82, 50);
            paintEvents(g, Time.getEvent());
        }
        repaint();
    }

    private static void paintEvents(Graphics g, ArrayList<EventString> events){
        int x = 24, y = 70;
        if(events.size() != 0){
            AttributedString atrs;
            for (EventString event : events) {
                if(events.indexOf(event) != 0 && events.indexOf(event) %2 != 0){
                    atrs = new AttributedString(String.format("%d. %s", (events.indexOf(event) + 1) / 2, event.getText()));
                }
                else atrs = new AttributedString(event.getText());
                if(event.isStrikeThrough()) {

                    atrs.addAttribute(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
                }
                atrs.addAttribute(TextAttribute.FONT, plainFont);
                g.drawString(atrs.getIterator(), x, y);
                y += 20;
            }
        }

    }

    public static void setAttributesToStrings() {
        atrsTime.addAttribute(TextAttribute.FONT, new Font("Times New Roman",Font.BOLD,24));
        atrsDay.addAttribute(TextAttribute.FONT, new Font("Times New Roman",Font.BOLD,16));
    }


    // Передаем элемент
    private static void paintGoals(Graphics g, List<GoalString> goals) {

        int x = 24, y = 70;
        if (goals.size() != 0) {
            for (GoalString goal : goals) {
                String str = goals.indexOf(goal) + 1 + ". " + goal.getText();
                int counts = (""+goals.indexOf(goal)).length()+2;
                AttributedString s = new AttributedString(str);
                s.addAttribute(TextAttribute.FONT, plainFont);
                if(goal.isStrikeThrough())
                {
                    s.addAttribute(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON, counts, counts + goal.getText().length());
                }
                g.drawString(s.getIterator(), x, y);
                y += 20;
            }
        }
    }

    static void reset() {
        f.getContentPane().removeAll();
        panel = anotherPanel();
        f.getContentPane().add(panel);
        f.revalidate();
    }

    private static Panel anotherPanel() {
        return new Panel();
    }

    public static void createFrame() {
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        f.setResizable(false);
        f.getContentPane().add(panel);
        f.getRootPane().setDefaultButton(mainTempButton);
        UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
        f.pack();
        f.setVisible(true);
    }
}



