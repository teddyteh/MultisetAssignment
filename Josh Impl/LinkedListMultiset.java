import java.io.PrintStream;

public class LinkedListMultiset<T> extends Multiset<T>
{
	protected Node mHead;
	protected int mLength;
    
    public LinkedListMultiset() {
		this.mHead = null;
		this.mLength = 0;
	} // end of LinkedListMultiset()
	
	
	public void add(T item) {

		Node newNode = new Node();
		newNode.setValue(item);
		newNode.setNumberOfInstances(1);

		if(mHead == null)
		{
			mHead = newNode;
			return;
		}

		else
		{
			Node currNode = mHead;

			while(true)
			{
				if(currNode.getValue().toString().compareTo(item.toString()) == 0)
				{
					currNode.setNumberOfInstances(currNode.getNumberOfInstances() + 1);
					return;
				}

				else if(currNode.nextNode == null)
				{
					currNode.nextNode = newNode;
					return;
				}



				currNode = currNode.nextNode;
			}
		}

	} // end of add()
	
	
	public int search(T item) {

		Node currNode = mHead;

		while(currNode != null)
		{
			if(currNode.getValue().equals(item))
			{
				return currNode.getNumberOfInstances();
			}

			currNode = currNode.nextNode;
		}

		return 0;
	} // end of add()
	
	
	public void removeOne(T item) {

		Node currNode = mHead;

		while(currNode != null)
		{
			if(currNode.getValue().equals(item))
			{
				if(currNode.getNumberOfInstances() == 1)
				{
					removeAll(item);
					return;
				}

				else
				{
					currNode.setNumberOfInstances(currNode.getNumberOfInstances() - 1);
					return;
				}
			}

			currNode = currNode.nextNode;
		}

	} // end of removeOne()
	
	
	public void removeAll(T item) {

		Node currNode = mHead;
		Node prevNode = null;

		while(currNode != null)
		{
			if(currNode.getValue().equals(item))
			{
				if(currNode == mHead)
				{
					mHead = currNode.nextNode;
					return;
				}

				else
				{
					prevNode.setNextNode(currNode.nextNode);
					return;
				}
			}

			prevNode = currNode;
			currNode = currNode.nextNode;
		}

	} // end of removeAll()
	
	
	public void print(PrintStream out) {

		Node currNode = mHead;

		while(true)
		{
			if(currNode != null)
			{
				out.println(currNode.getValue() + " " + currNode.getNumberOfInstances());
			}
			else
				break;
			currNode = currNode.nextNode;
		}

	} // end of print()

	protected class Node
	{
		private T value;
		private int numberOfInstances = 0;

		private Node nextNode;

		public Node(){}

		public void setValue(T value)
		{
			this.value = value;
		}

		public void setNumberOfInstances(int numberOfInstances)
		{
			this.numberOfInstances = numberOfInstances;
		}

		public T getValue()
		{
			return value;
		}

		public int getNumberOfInstances()
		{
			return numberOfInstances;
		}

		private void setNextNode(Node node)
		{
			this.nextNode = node;
		}

	}
} // end of class LinkedListMultiset