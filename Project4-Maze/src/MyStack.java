import java.util.StringTokenizer;

/**
 * 
 * Stack (linked-list implementation) Abstract Data Type (ADT)
 * LIFO data structure (Last In First Out)
 * Reference section 1.3, page 149
 */
public class MyStack<T extends Object> {
	
    private Node first;   // top of stack (most recently added node)
	private int N;        // number of items
	
	private class Node    // nested class to define nodes for linked list
	{  
		T item;
		Node next;
	}
	
	// no-arg constructor
	public MyStack() {
		first = null;
		N = 0;
	}
	
	public int size() { return N; }
	public boolean empty() { return first == null; }
	
	// Add item to top of stack
	public void push(T item) {
		Node oldFirst = first;   // cache previous first pointer
		first = new Node();
		first.item = item;
		first.next = oldFirst;
		N++;
	}
	
	// Remove item from top of stack
	public T pop() {
		T item = first.item;
		first = first.next;
		N--;
		return item;
	}

	// assume this is a "peek" - look at the item at the top of the stack, 
	// but leave it in place.
	public T top() { return first.item; }
	
	
	// Test program used from book (page 147) for testing stack
    //   Input: "to be or not to - be - - that - - - is"
	//   Expected output: to be not that or be (2 left on stack)
	public static void main(String[] args) {
		MyStack<String> s = new MyStack<String>();
		
		String testData = "to be or not to - be - - that - - - is";
		StringTokenizer tokens = new StringTokenizer(testData);
		
		while (tokens.hasMoreTokens())
		{
			String item = tokens.nextToken();
			if (!item.equals("-"))
				s.push((item));
			else if (!s.empty()) 
				System.out.print(s.pop() + " ");
		}
		System.out.println("(" + s.size() + " left on stack)");
	}
	
	
	
}
