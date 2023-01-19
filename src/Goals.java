

import java.text.AttributedString;
import java.util.*;


public class Goals {

        private static HashMap<Integer, AttributedString> goalsMap = new HashMap<>();

        public static void addGoal(AttributedString str){
            if(!goalsMap.containsValue(str))goalsMap.put(goalsMap.size(),str);
        }

        public static void setGoalsMap(HashMap<Integer, AttributedString> map){
            goalsMap = map;
        }

        public static HashMap<Integer, AttributedString> getGoals(){
                return goalsMap;
            }
}

