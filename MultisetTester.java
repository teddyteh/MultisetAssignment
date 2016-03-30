import java.io.*;
import java.util.*;


/**
 * Framework to test the multiset implementations.
 * 
 * @author jkcchan
 */
public class MultisetTester
{
	/** Name of class, used in error messages. */
	protected static final String progName = "MultisetTester";
	
	/** Standard outstream. */
	protected static final PrintStream outStream = System.out;

	/**
	 * Print help/usage message.
	 */
	public static void usage(String progName) {
		System.err.println(progName + ": <implementation> [fileName to output search results to]");
		System.err.println("<implementation> = <linkedlist | sortedlinkedlist | bst| hash | baltree>");
		System.exit(1);
	} // end of usage


	/**
	 * Process the operation commands coming from inReader, and updates the multiset according to the operations.
	 * 
	 * @param inReader Input reader where the operation commands are coming from.
	 * @param searchOutWriter Where to output the results of search.
	 * @param multiset The multiset which the operations are executed on.
	 * 
	 * @throws IOException If there is an exception to do with I/O.
	 */
	public static void processOperations(BufferedReader inReader, PrintWriter searchOutWriter, Multiset<String> multiset) 
		throws IOException
	{
		String line;
		int lineNum = 1;
		boolean bQuit = false;
		long totalTimeSoFar = 0;

		PrintStream timing = new PrintStream("timing.out", "UTF-8");
		timing.println(multiset);

		// continue reading in commands until we either receive the quit signal or there are no more input commands
		while (!bQuit && (line = inReader.readLine()) != null) {
			String[] tokens = line.split(" ");

			// check if there is at least an operation command
			if (tokens.length < 1) {
				System.err.println(lineNum + ": not enough tokens.");
				lineNum++;
				continue;
			}

			String command = tokens[0];
			// determine which operation to execute
			switch (command.toUpperCase()) {
				// add
				case "A":
					if (tokens.length == 2) {
						long startTime = System.nanoTime();
						multiset.add(tokens[1]);
						totalTimeSoFar = totalTimeSoFar + (System.nanoTime() - startTime);
						timing.println("Add: " + (System.nanoTime() - startTime));
					}
					else {
						System.err.println(lineNum + ": not enough tokens.");
					}
					break;
				// search
				case "S":
					if (tokens.length == 2) {
						long startTime = System.nanoTime();
						int foundNumber = multiset.search(tokens[1]);
						searchOutWriter.println(tokens[1] + " " + foundNumber);
						totalTimeSoFar = totalTimeSoFar + (System.nanoTime() - startTime);
						timing.println("Search: " + (System.nanoTime() - startTime));
					}
					else {
						// we print -1 to indicate error for automated testing
						searchOutWriter.println(-1);
						System.err.println(lineNum + ": not enough tokens.");
					}
					break;
				// remove one instance
				case "RO":
					if (tokens.length == 2) {
						long startTime = System.nanoTime();
						multiset.removeOne(tokens[1]);
						totalTimeSoFar = totalTimeSoFar + (System.nanoTime() - startTime);
						timing.println("Remove One: " + (System.nanoTime() - startTime));
					}
					else {
						System.err.println(lineNum + ": not enough tokens.");
					}
					break;
				// remove all instances
				case "RA":
					if (tokens.length == 2) {
						long startTime = System.nanoTime();
						multiset.removeAll(tokens[1]);
						totalTimeSoFar = totalTimeSoFar + (System.nanoTime() - startTime);
						timing.println("Remove All: " + (System.nanoTime() - startTime));
					}
					else {
						System.err.println(lineNum + ": not enough tokens.");
					}
					break;		
				// print
				case "P":
					timing.println("Total time so far: " + totalTimeSoFar);
					multiset.print(outStream);
					break;
				// quit
				case "Q":
					bQuit = true;
					break;
				default:
					System.err.println(lineNum + ": Unknown command.");
			}

			lineNum++;
		}

	} // end of processOperations() 


	/**
	 * Main method.  Determines which implementation to test.
	 */
	public static void main(String[] args) {

		// check number of command line arguments
		if (args.length > 2 || args.length < 1) {
			System.err.println("Incorrect number of arguments.");
			usage(progName);
		}

		String implementationType = args[0];
		
		String searchOutFilename = null;
		if (args.length == 2) {
			searchOutFilename = args[1];
		}
		
		
		// determine which implementation to test
		Multiset<String> multiset = null;
		switch(implementationType) {
			case "linkedlist":
				multiset = new LinkedListMultiset<String>();
				break;
			case "sortedlinkedlist":
				multiset = new SortedLinkedListMultiset<String>();
				break;
			case "bst":
				multiset = new BstMultiset<String>();
				break;
			case "hash":
				multiset = new HashMultiset<String>();
				break;
			case "baltree":
				multiset = new BalTreeMultiset<String>();
				break;
			default:
				System.err.println("Unknown implmementation type.");
				usage(progName);
		}


		// construct in and output streams/writers/readers, then process each operation.
		try {
			BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter searchOutWriter = new PrintWriter(System.out, true);
			
			if (searchOutFilename != null) {
				searchOutWriter = new PrintWriter(new FileWriter(searchOutFilename), true);
			}
			// process the operations
			processOperations(inReader, searchOutWriter, multiset);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	} // end of main()

} // end of class MultisetTester
