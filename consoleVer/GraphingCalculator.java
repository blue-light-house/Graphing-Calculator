import java.lang.System;
import java.util.*;

public class GraphingCalculator {
	
	public GraphingCalculator() {
	}
	
	private String[] recognizer(String expression) {
		String[] splitExpression = expression.trim().split("\\s+");
		return splitExpression;
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
		}
    }
}