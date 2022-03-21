import java.util.StringTokenizer;


/**
 * 
 * Queue (linked-list implementation) Abstract Data Type (ADT)
 * FIFO data structure (First In First Out)
 * Reference section 1.3, page 151
 */
public class MyQueue<T extends Object> {
	
    private Node first;   // link to least recently added node
    private Node last;    // link to most recenly added node
	private int N;        // number of items on the queue
	
	private class Node    // nested class to define nodes for linked list
	{  
		T item;
		Node next;
	}
	
	// no-arg constructor
	public MyQueue() {
		first = null;
		last = null;
		N = 0;
	}
	
	public boolean empty() { return first == null; }
	public int size() { return N; }
	
	// Add item to the end of the list
	public void push(T item)
	{
		Node oldLast = last;       // cache the last node (to update the linked pointer below)
		last = new Node();         // construct a new node to hold the new item
		last.item = item;
		last.next = null;
		if (empty()) 
			first = last;          // only 1 item in queue, both first and last point to it
		else
			oldLast.next = last;   // the previous last node now point to the new item added at the end
		N++;                       // increment total count by 1
	}
	
	// Remove the item from the beginning of the list
	public T pop()
	{
	    if (empty())  return null;
	    	
		T item = first.item;
		first = first.next;   // move first to point ot the next node
		if (empty())
			last = null;
		N--;                  // decrement total count by 1
		
		return item;
	}
	
	// Assume this is a "peek" - look at the next item, but don't pop
	public T front() {
		if (first != null)
			return first.item;
		else
			return null;
	}
	
	// Test program used from book (page 150) for testing queue
	//   Input: "to be or not to - be - - that - - - is"
	//   Expected output: to be or not to be (2 left on queue)
	public static void main(String[] args) {
		MyQueue<String> s = new MyQueue<String>();
		
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
		System.out.println("(" + s.size() + " left on queue)");
	}
	

}
