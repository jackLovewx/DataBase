package day01_Postfix;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Postfix {
    /*
        利用栈的特点计算后缀表达式的结果
        已知中缀表达式为：(2 + 1) * 3
     */
    //定义一个用来装载运算符的列表
    private static LinkedList<String> operators = new LinkedList<>();
    //定义一个用来展示后缀表达式的列表
    private static StringBuilder sb = new StringBuilder();
    //定义一个用来记录输出
    private static LinkedList<String> show = new LinkedList<>();

    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入一个中缀表达式每个字符用空格隔开,以#键结束！！");
        String profix;
        while (!(profix = scanner.next()).equals("#")) {
            list.add(profix);
        }
//      System.out.println(list);
        //将一个中缀表达式转换成一个后缀表达式。
        changehouprofix(list);
        //打印转换之后的后缀表达式。
        scanner.close();
    }

    //将一个中缀表达式转换成一个后缀表达式。
    private static void changehouprofix(LinkedList<String> list) {
        //list参数传递过来之后需要遍历这个list
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String next = it.next();
            //判断从list中遍历到的这个字符是不是运算符
            if (isoperator(next)) {
                if (operators == null) {
                        operators.push(next);
                } else {
                    //走到这一步代码说明装载运算符的列表不是空的所以要去判断之前的
                    //运算符和即将进入的运算符之间的优先级谁高谁低。
                    //如果即将进入的运算符的优先级比栈顶的优先级高并且即将进入的运算符不是”）"，
                    //将这个运算符压入栈顶。
                    if (compareGrade(next) >= compareGrade(list.peek()) && !list.peek().equals(")") &&!next.equals(")")) {
                        operators.push(next);
                    } else if (compareGrade(next) < compareGrade(list.peek()) && !list.peek().equals(")")&&!next.equals(")")) {
                        operators.push(next);
                        //如果下一个运算符是")"那么直接出栈。
                    }else if(next.equals(")")){
                         while(!operators.peek().equals("(")){
                            String outString = operators.pop();
                            sb.append(outString).append(" ");
                            show.push(outString);
                        }
                        //弹出"("
                        operators.pop();
                    }
                }
            } else {
                sb.append(next).append(" ");
                show.push(next);
            }
        }
        if (operators != null || operators.size() != 0) {
            Iterator<String> iterator=operators.iterator();
            while (iterator.hasNext()) {
                String operator=iterator.next();
                sb.append(operator).append(" ");
                show.push(operator);
                iterator.remove();
            }
        }
        System.out.println("后缀： "+sb);
        getResult();
    }
    //判断一个字符是不是运算符的方法
    private static boolean isoperator(String next) {
        if (next.equals("+") || next.equals("-") || next
                .equals("*") || next.equals("/") || next.equals("(") || next.equals(")")) {
            return true;
        }
        return false;
    }

    //判断一个运算符的优先级别
    private static int compareGrade(String s) {
        switch (s) {
            case "+":
                return 1;
            case "-":
                return 1;
            case "*":
                return 2;
            case "/":
                return 2;
            case "(":
                return 3;
            case ")":
                return 3;
            default:
                return 0;
        }
    }
    //计算后缀表达式的最终结果。
    private static void getResult(){
        LinkedList<String> list = new LinkedList<>();
        String[] str = sb.toString().split(" ");
        for(String s : str){
            if(isoperator(s)){
                if(list != null){
                    int num1 = Integer.valueOf(list.pop());
                    int num2 = Integer.valueOf(list.pop());
                    if(s.equals("/") && num1 == 0){
                        System.out.println("出除数不能为0");
                        return;
                    }
                    int newNum = jisuan(num1,num2,s);
                    list.push(""+newNum);
                }
            }else{
                list.push(s);
            }
        }
        if(list != null){
            System.out.println("result:"+list.pop());
        }
    }
    //计算方法
    private static int jisuan(int num1,int num2,String s){
        switch (s){
            case "+":return num1+num2;
            case "-":return num1-num2;
            case "*":return num1*num2;
            case "/":return num1/num2;
        }
        return 0;
    }
}
