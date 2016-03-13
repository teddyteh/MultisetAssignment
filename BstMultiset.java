import java.io.PrintStream;

public class BstMultiset<T> extends Multiset<T>
{
	protected Node rootNode;

	public BstMultiset() {
		// Implement me!
		rootNode = null;
	} // end of BstMultiset()

	public void add(T item) {
		// Implement me!

//		Create a new Node instance with the value of new item, also give it an instance of 1
		Node newNode = new Node();
		newNode.setValue(item);
		newNode.setNumberOfInstances(1);


//		If there is no root, then make the new node the root
		if(rootNode == null)
		{
			rootNode = newNode;
			rootNode.setNumberOfInstances(1);
			return;
		}

//		Parent/Current/Cursor Node
		Node parentNode = rootNode;

		while(true)
		{
//			If the nodes have the same value, update the Number of Instances Counter on the selected Node
//			if(parentNode.getValue().toString().compareTo(newNode.getValue().toString()) == 0)
			if(compareValues(parentNode, newNode) == 0)
			{
				parentNode.setNumberOfInstances(parentNode.getNumberOfInstances() + 1);
				break;
			}
//			Test if the value of the new node is less than the value of the parentNode
			else if(parentNode.getValue().toString().compareTo(newNode.getValue().toString()) > 0)
			{
//				If there is no node on the left, insert the new node
				if(parentNode.leftNode == null)
				{
					parentNode.leftNode = newNode;
					break;
				}
//				Otherwise set the new Parent node to the left of the Parent node and continue searching
				else
					parentNode = parentNode.leftNode;
			}
//			Otherwise assume that the new node is greater than the value of the parentNode
			else
			{
				if(parentNode.rightNode == null)
				{
					parentNode.rightNode = newNode;
					break;
				}
				else
					parentNode = parentNode.rightNode;
			}
		}

		return;

	} // end of add()

	/**
	 * Compare 2 Nodes values and return the result, based on String values
	 * @param node1 - Node 1 to compare
	 * @param node2 - Node 2 to compare
     * @return - Integer value of the returned compare
     */
	private int compareValues(Node node1, Node node2)
	{
		return node1.getValue().toString().compareTo(node2.getValue().toString());
	}

	/**
	 * Compare a Node value to that of an items value (raw value) based on String values
	 * @param node - Node that contains a value to be compared
	 * @param item - Raw value to be compared
     * @return - Integer value of the returned compare
     */
	private int compareValues(Node node, T item)
	{
		return node.getValue().toString().compareTo(item.toString());
	}

	public int search(T item)
	{
		// Implement me!

//		Current Node cursor
		Node currentNode = rootNode;

//		Loop through all Nodes until match found, otherwise return 0
		while(currentNode != null)
		{
//			If the current Node value and the search item value are the same, return the number of instances
			if(compareValues(currentNode, item) == 0)
				return currentNode.getNumberOfInstances();

//			Otherwise check if the current Node value is greater than the item value, if so move cursor to the left node
			else if(compareValues(currentNode, item) > 0)
				currentNode = currentNode.leftNode;

//			Otherwise move cursor to right Node
			else
				currentNode = currentNode.rightNode;

		}

		// default return, please override when you implement this method
		return 0;
	} // end of add()


	public void removeOne(T item)
	{
		// Implement me!

//		Get the number of instances of the current item to remove. 0 if it does not exist.
		int numberOfInstance = search(item);

//		If the number of instances is 0, then the item does not exist, just return
		if(numberOfInstance == 0)
			return;

//		If the number of instances is 1, then we need to remove the whole Node anyway, so do a remove all fucntion
		else if(numberOfInstance == 1)
		{
			removeAll(item);
			return;
		}

//		Otherwise it does exist and does have more than one instance, find it and decrement the number of instances by 1
		else
		{
//			Current Node cursor
			Node currentNode = rootNode;

//			Loop through all Nodes until match found, otherwise return 0
			while(currentNode != null)
			{
//			If the current Node value and the search item value are the same, return the number of instances
				if(currentNode.getValue().toString().compareTo(item.toString()) == 0)
				{
					currentNode.setNumberOfInstances(currentNode.getNumberOfInstances() - 1);
					return;
				}

//			Otherwise check if the current Node value is greater than the item value, if so move cursor to the left node
				else if(currentNode.getValue().toString().compareTo(item.toString()) > 0)
					currentNode = currentNode.leftNode;

//			Otherwise move cursor to right Node
				else
					currentNode = currentNode.rightNode;

			}
		}

	} // end of removeOne()
	
	
	public void removeAll(T item)
	{
		// Implement me!
		int numberOfInstances = search(item);
		if(numberOfInstances == 0)
			return;

//		Takes advantage of method recursion to completely remove a Node from the BST
		rootNode = delete(rootNode, item);
	} // end of removeAll()

	/**
	 * A function using Method recursion to completely remove a Node from the BST and return the root Node.
	 * @param parentNode - The Node that is the current Parent Node under inspection
	 * @param item - Raw value that we are trying to find and remove from the BST
     * @return - Returns the currently selected Parent Node
     */
	private Node delete(Node parentNode, T item)
	{
//		If any Parent Node is null, then an error has occurred and throws an exception
		if (parentNode == null)
			throw new RuntimeException("Unable to delete item");

//		If the value of the Parent Node is less than the value of the raw Item value,
//		then we set the Parent Node to the left and recall the delete function
		else if (parentNode.getValue().toString().compareTo(item.toString()) > 0)
			parentNode.leftNode = delete (parentNode.leftNode, item);

//		If the value of the Parent Node is less than the value of the raw Item value,
//		then we set the Parent Node to the right and recall the delete function
		else if (parentNode.getValue().toString().compareTo(item.toString())  < 0)
			parentNode.rightNode = delete (parentNode.rightNode, item);

//		Otherwise they must be equal and we can delete the current Parent Node
		else
		{
			if (parentNode.leftNode == null) return parentNode.rightNode;
			else
			if (parentNode.rightNode == null) return parentNode.leftNode;
			else
			{
				parentNode.setNumberOfInstances(retrieveNumberOfInstances(parentNode.leftNode));
//				Retrieve value from the rightmost node in the left subtree
				parentNode.setValue(retrieveValue(parentNode.leftNode));
//				Delete the rightmost node in the left subtree
				parentNode.leftNode =  delete(parentNode.leftNode, parentNode.getValue()) ;
			}
		}
		return parentNode;
	}

	/**
	 * Method to retrieve the value of the right most Node in the tree
	 * @param parentNode
	 * @return
     */
	private T retrieveValue(Node parentNode)
	{
		while (parentNode.rightNode != null) parentNode = parentNode.rightNode;

		return parentNode.getValue();
	}

	/**
	 * Method to retrieve the number of instances of the right most Node in the tree
	 * @param parentNode
	 * @return
     */
	private int retrieveNumberOfInstances(Node parentNode)
	{
		while (parentNode.rightNode != null) parentNode = parentNode.rightNode;

		return parentNode.getNumberOfInstances();
	}

	public void print(PrintStream out)
	{
		// Implement me!
		print(rootNode, out);
	} // end of print()

	/**
	 * Overloaded method to loop through the BST and print the values of each Node.
	 * Takes advantage of method recursion.
	 * @param parent - The parent Node to inspect
	 * @param out - The output stream for printing results
     */
	private void print(Node parent, PrintStream out)
	{
		if(parent != null){
			out.println(parent.getValue() + " | " + parent.getNumberOfInstances());
			print(parent.leftNode, out);
			print(parent.rightNode, out);
		}
	}

	protected class Node
	{
		private T value;
		private int numberOfInstances = 0;

		private Node leftNode;
		private Node rightNode;

		public Node(){}

		public boolean isLeaf()
		{
			return (leftNode == null && rightNode == null);
		}

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

	}

} // end of class BstMultiset
