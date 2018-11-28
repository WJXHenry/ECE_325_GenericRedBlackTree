import java.util.Vector;
/**
 * Lab 4: Generics <br />
 * The {@code GenericStack} class
 */
public class GenericStack2<T> {

	Vector<T> stack;
	
	// Constructor
	public GenericStack2(int capacity) {
		this.stack = new Vector<T>(capacity);
	}
	
    /**
     * Query the top element
     * @return          {@code T} the top element
     */
    public T peek() {
        // TODO: Lab 4 Part 1-1 -- GenericStack, finish the peek method
        if (!this.isEmpty()) {
        	return this.stack.lastElement();
        } else {
        	return null;
        }
    }

    /**
     * Add a new element as top element
     * @param value     {@code T} the new element
     */
    public void push(T value) {
        // TODO: Lab 4 Part 1-2 -- GenericStack, finish the push method
        if (this.size() < this.stack.capacity()) {
        	this.stack.add(value);
        }
    }

    /**
     * Remove the top element
     * @return          {@code T} the removed element
     */
    public T pop() {
        // TODO: Lab 4 Part 1-3 -- GenericStack, finish the pop method
        if (!this.isEmpty()) {
        	return this.stack.remove(this.size() - 1);
        } else {
        	return null;
        }
    }

    /**
     * Query the size of the stack
     * @return          {@code int} size of the element
     */
    public int size() {
        // TODO: Lab 4 Part 1-4 -- GenericStack, finish the size method
        return this.stack.size();
    }

    /**
     * Check if the stack is empty of not
     * @return          {@code boolean} {@code true} for empty; {@code false} for not
     */
    public boolean isEmpty() {
        // TODO: Lab 4 Part 1-5 -- GenericStack, finish the isEmpty method
        return this.stack.isEmpty();
    }

    /**
     * Calculate a postfix expression
     * @param exp       {@code String} the postfix expression
     * @return          {@code Double} the value of the expression
     */
    public static Double calcPostfixExpression(String exp) {
        // TODO: Lab 4 Part 1-6 -- GenericStack, calculate postfix expression
    	// Constants
    	String ADD = "+";
    	String SUB = "-";
    	String MUL = "*";
    	String DIV = "/";
    	String POW = "^";
    	
        GenericStack2<String> stack = new GenericStack2<String>(exp.length());
        
        char[] exp_chars = exp.replaceAll(" ", "").toCharArray();
        
        for (char c : exp_chars) {
        	if (String.valueOf(c).matches("[+-/\\*\\^]")) {
        		Double b = Double.valueOf(stack.pop());
        		Double a = Double.valueOf(stack.pop());
        		String op = String.valueOf(c);
        		if (op.equals(ADD)) {
            		stack.push(String.valueOf(a + b));
            	} else if (op.equals(SUB)) {
            		stack.push(String.valueOf(a - b));
            	} else if (op.equals(MUL)) {
            		stack.push(String.valueOf(a * b));
            	} else if (op.equals(DIV)) {
            		stack.push(String.valueOf(a / b));
            	} else if (op.equals(POW)) {
            		stack.push(String.valueOf(Math.pow(a, b)));
            	}
        	} else {
        		stack.push(String.valueOf(c));
        	}
        }
        return Double.valueOf(stack.pop());
    }

    /**
     * Main entry
     * @param args      {@code String[]} Command line arguments
     */
    public static void main(String[] args) {
    	GenericStack2<String> stack = new GenericStack2<String>(10);
    	System.out.println(stack.pop());
    	stack.push("Hello");
    	System.out.println(stack.pop());
    	System.out.println(stack.pop());
    	stack.push("1");
    	stack.push("2");
    	stack.push("3");
    	stack.push("4");
    	stack.push("5");
    	stack.push("6");
    	stack.push("7");
    	stack.push("8");
    	stack.push("9");
    	stack.push("10");
    	stack.push("11");
    	stack.push("12");
    	System.out.println(stack.peek());
        String[] expressions = {
                "4 1 +",                    // 1: = 5
                "2 6 -",                    // 2: = -4
                "3 3 *",                    // 3: = 9
                "1 4 /",                    // 4: = 0.25
                "2 3 ^",                    // 5: = 8
                "2 1 + 4 * 8 - 3 ^ 6 -",    // 6: 58
        }; // String[] expressions = { ... };
        for (String s: expressions)
            System.out.println(s + " = " + calcPostfixExpression(s));
    }

}
