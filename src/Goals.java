

import strings.GoalString;
import java.util.*;


public class Goals {

        private static ArrayList<GoalString> goalsArr = new ArrayList<>();

        public static void addGoal(GoalString str){
            if(!goalsContains(str.getText())) goalsArr.add(str);
        }

        private static boolean goalsContains(String text){
            if(text == null) return false;
            for(GoalString goal : goalsArr){
                if(text.equals(goal.getText())) return true;
            }
            return false;
        }
        public static void setGoalsArr(ArrayList<GoalString> arr){
            goalsArr = arr;
        }

        public static ArrayList<GoalString> getGoals(){
                return goalsArr;
            }
}

