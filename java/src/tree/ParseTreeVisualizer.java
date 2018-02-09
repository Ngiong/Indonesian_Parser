package tree;

import java.util.Stack;

@Deprecated
public class ParseTreeVisualizer {
  public static void print(ParseTree pt) {
    String brackets = pt.toString();
    Stack<Integer> indents = new Stack<>(); indents.push(0);
    for (int i = 0; i < brackets.length(); i++) {

      if (brackets.charAt(i) == '(') {
        System.out.println(brackets.charAt(i));
        indents.push(indents.peek() + 2);
        for (int j = 0; j < indents.peek(); j++) System.out.print(" ");
      } else if (brackets.charAt(i) == ')') {
        System.out.println(); indents.pop();
        for (int j = 0; j < indents.peek(); j++) System.out.print(" ");
        System.out.print(brackets.charAt(i));
      } else {
        System.out.print(brackets.charAt(i));
      }
    }
  }
}
