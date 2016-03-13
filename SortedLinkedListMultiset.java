import java.io.PrintStream;

public class SortedLinkedListMultiset<T> extends Multiset<T>
{
	protected Node mHead;
	protected int mLength;
    
    public SortedLinkedListMultiset() {
    	this.mHead = null;
		this.mLength = 0;
	} // end of SortedLinkedListMultiset()
	
	
	public void add(T item) {
		Node newNode = new Node(item.toString());
		
		// List is empty
		if (mHead == null) {
			mHead = newNode;
			
			mLength++;
		}
		else {
			Node currentNode = mHead;
			boolean findRightPos = true;
			
			// Check if element already exists
			while (currentNode != null) {
				if (item.toString().equals(currentNode.getElement())) {
					currentNode.incrementCount();
					findRightPos = false;
					
					break;
				}
				
				currentNode = currentNode.getNext();
			}
			
			// Element does not exist, find the right position to insert element
			if (findRightPos) {
				currentNode = mHead;
				// New element is smaller than current node
				Node temp = currentNode;
				Node last = null;
				
				while (temp != null) {
					if (item.toString().compareTo(temp.getElement()) > 0)
					{
						last = temp;
					}
					
					temp = temp.getNext();
				}
				
				newNode.setNext(last.getNext());
				last.setNext(newNode);
				mLength++;		
			}
		}
	} // end of add()
	
	
	public int search(T item) {
		Node currentNode = mHead;
		
		while (currentNode != null) {
			if (item.toString().equals(currentNode.getElement())) {
				System.out.println(currentNode.getElement() + printDelim + currentNode.getCount());
				
				return currentNode.getCount();
			}
			
			currentNode = currentNode.getNext();
		}
		
		return 0;
	} // end of add()
	
	
	public void removeOne(T item) {
		Node currentNode = mHead;
		
		while (currentNode != null) {
			// Only one item in list
			if (currentNode.getCount() > 1 && currentNode.getElement().equals(item.toString())) {
				currentNode.decrementCount();
				
				break;
			}
			
			if (currentNode.getCount() == 1) {
				removeAll(item);
				
				break;
			}
			
			currentNode = currentNode.getNext();
    	}
	} // end of removeOne()
	
	
	public void removeAll(T item) {
		Node currentNode = mHead;
		
		while (currentNode != null) {
			// Only one item in list
			if (currentNode.getNext() == null && currentNode.getElement().equals(item.toString())) {
				mHead = null;
			}
			
			// Match is the head node
			if (currentNode.getNext() != null && mHead.getElement().equals(item.toString())) {
				mHead = currentNode.getNext();
			}
			
			// Match is last node
			if (currentNode.getNext() != null && currentNode.getNext().getNext() == null && currentNode.getNext().getElement().equals(item.toString())) {
				currentNode.setNext(null);
			}
			
			// Match is not head or last node
			if (currentNode.getNext() != null && 
				currentNode.getNext().getNext() != null && 
				currentNode.getNext().getElement().equals(item.toString()))
			{
				// Remove the node
				currentNode.mNext = currentNode.getNext().getNext();
			}
			
			currentNode = currentNode.getNext();
    	}
	} // end of removeAll()
	
	
	public void print(PrintStream out) {
		Node currentNode = mHead;
		
		while (currentNode != null) {
			out.println(currentNode.getElement() + printDelim + currentNode.getCount());
			
			currentNode = currentNode.getNext();
		}
	} // end of print()
	
	private class Node
    {
        /* Stored value of node */
        protected String mElement;
        /* Count of element */
        protected int mCount;
        /* Reference to next node */
        protected Node mNext;

        public Node(String value) {
            mElement = value;
            mCount = 1;
            mNext = null;
        }

        public String getElement() {
            return mElement;
        }
        
        public int getCount() {
        	return mCount;
        }
        
        public Node getNext() {
            return mNext;
        }

        public void incrementCount() {
        	this.mCount++;
        }
        
        public void decrementCount() {
        	this.mCount--;
        }
        
        public void setNext(Node next) {
            mNext = next;
        }
    }
} // end of class SortedLinkedListMultiset