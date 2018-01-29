package MacCalc2;

import java.util.*;

public class MacCalc2 {
    // Пример для ввода: (35+20)+((25+15)+100)

    public static String calc(String a, String b, String operator){
        switch (operator) {
            case "+": return Double.valueOf(a) + Double.valueOf(b)+"";
            case "~": return Double.valueOf(a) - Double.valueOf(b)+"";
            case "*": return Double.valueOf(a) * Double.valueOf(b)+"";
            case "/": return Double.valueOf(a) / Double.valueOf(b)+"";
        }
        return null;
    }

    //difference with operator '-'..i replace this operator to ~

    public static String minustotild(String s){

        String result = ""+s.charAt(0);

        for (int i = 1; i < s.length(); i++){
            if ((s.charAt(i) == '-') && ("+-*(/~".indexOf(s.charAt(i-1)) == -1))   // if previous char is not a symbol( is digit)
                result += ""+'~';
            else result +=""+s.charAt(i);
        }
        return result;
    }

    public static String operate(String expression){

        String num[];   int index = -1;
        Character priorityOperator='/';  // default
        String operators;
        while (!((  operators = expression.replaceAll("[^*+/~]","")  ).isEmpty()))     // while have operator..
        {
            if ( (index = operators.indexOf('/')) == -1){        // choose priority operator
                priorityOperator = '*';
                if  ( (index = operators.indexOf('*')) == -1){
                    priorityOperator=operators.charAt(0);
                    index = operators.indexOf(priorityOperator);
                }
            }
            num = expression.split("[^0-9\\-.]"); // сплитим все числа..

            // заменяем строкое представление арифметики,на строковой результат с помощью калк().
            expression=expression.replaceFirst(num[index]+"\\"+priorityOperator+num[index+1], calc(num[index],num[index+1],""+priorityOperator));
        }
        return expression;
    }


    public static String operateBracket(StringBuilder s, int startIndex){
        // ''
        if (startIndex == -1) {        // если скобок нету то оперируем .
            return (operate(s.toString()));
        }
        else {
            int k = 1;
            for (int i=startIndex+1; i < s.length(); i++){
                if (s.charAt(i) == '(')
                    k++;
                else if ((s.charAt(i) == ')'))
                {
                    if (k == 1) {    // нашли конец первой скобки
                        String newBracket = s.substring(startIndex+1, i);
                        s=s.replace(startIndex,i+1,operateBracket(new StringBuilder(newBracket), newBracket.indexOf(""+'(')));
                    }
                    k--;
                }
            }
        }
        return operate(s.toString());
    }

    public static void main(String[] args){

        Scanner s = new Scanner( System.in );
        System.out.println("Введите данные для расчета(num/operator/brackets):");
        String b = s.next();
        do  {
            StringBuilder a = new StringBuilder(minustotild(b));
            System.out.println(" result = "+operateBracket(a,a.indexOf(""+'(')));
        }   while ( (b = s.next()) != "null");
    }
}