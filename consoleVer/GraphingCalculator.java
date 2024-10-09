import java.lang.System;
import java.util.*;

public class GraphingCalculator {
	
	public GraphingCalculator() {
	}
	
	private String[] recognizer(String expression) {
		String[] splitExpression = expression.trim().split("\\s+");
		return splitExpression;
	}
	
	private double evaluate (String[] expression) throws IllegalArgumentException {
		if(expression.length % 2 != 1) throw new IllegalArgumentException("Expression input is invalid!");
		double toReturn = Double.parseDouble(expression[0]);
		boolean evaluating = true;
		int index = 1;
		while (index < expression.length - 1) {
			if (expression[index].equals("+")) {
				toReturn += Double.parseDouble(expression[index+1]);
				index +=2;
			} else if (expression[index].equals("-")) {
				toReturn -= Double.parseDouble(expression[index+1]);
				index +=2;
			} else if (expression[index].equals("*")) {
				index +=2;
			} else if (expression[index].equals("/")) {
				index +=2;
			} else if (expression[index].equals("^")) {
				index +=2;
				System.out.println("Coming soon!");
			} else {
				throw new IllegalArgumentException("Invalid operation in expression!");
			}
		}
		
		return toReturn;
	}
	
    public static void main(String[] args) {
        System.out.println("Hello World!");
		GraphingCalculator self = new GraphingCalculator();
		if (args.length > 0) {
			String[] test = self.recognizer(args[0]);
			for (String s : test) {
				System.out.print(s);
			}
			System.out.println();
			System.out.println(self.evaluate(test));
		}
    }
}