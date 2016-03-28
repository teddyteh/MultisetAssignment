import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by josh on 17/03/16.
 */
public class DataGenerator {

    private static int mNumberOfSamples;
    private static int mLength;
    private static String mSamplingType;
    private static int mNumberOfSamplesToDelete;
    
    public static void main(String[] args)
    {
        assert(args.length == 4);

        DataGenerator generator = new DataGenerator();
        
        long startTime = System.nanoTime();

        mLength = Integer.valueOf(args[0]);
        mNumberOfSamples = Integer.valueOf(args[1]);
        mSamplingType = args[2];
        mNumberOfSamplesToDelete = Integer.valueOf(args[3]);
		
		String[] outputToInFile = new String[(mNumberOfSamples * 2) + 4];
        String[] outputToSearch = new String[mNumberOfSamples];
        String[] outputToExpectedOut = new String[(mNumberOfSamples * 2) * 2];

        try {
            PrintStream inStream = new PrintStream("TestString1.in", "UTF-8");
            PrintStream expStream = new PrintStream("TestString1.exp", "UTF-8");
            PrintStream searchStream = new PrintStream("TestString1.search.exp", "UTF-8");

            String[] samples = null;
            
            // Generate mNumberOfSamples of samples
            switch (mSamplingType) {
	            case "with":
	            	samples = generator.generateStringsWithReplacement();
	            	
	            	break;
	            case "without":
	            	if (mLength == 1 && mNumberOfSamples > 26)
	            	{
	            		System.err.println("Number of samples cannot be more than 26 when the sample size is 1 character long.");
		            	System.exit(0);
	            	}
	            	samples = generator.generateStringsWithOutReplacement();
	            	
	            	break;
	            default:
	            	System.err.println(mSamplingType + " is an unknown sampling type. Now exiting..");
	            	System.exit(0);
	            	
	            	break;
            }
            
            
            
            // Count the number of instances for each sample 
            int[] sampleInstances = new int[mNumberOfSamples];
            for(int i = 0; i < mNumberOfSamples; i++)
            {
                int numberOfInstances = 1;
                for(int j = 0; j < mNumberOfSamples; j++)
                {
                    if(samples[i].equals(samples[j]) && i != j)
                    {
                        numberOfInstances++;
                    }
                }
                sampleInstances[i] = numberOfInstances;
            }

            
            
            // Generate array of strings to be used for expected output file
            for(int i = 0; i < mNumberOfSamples; i++)
            {
                //  Add the current sample to the output to the in file
                outputToInFile[i] = "A " + samples[i];
                
                // Only print one instance of each string (if there is more than one)
                outputToExpectedOut[i] = samples[i] + " | " + sampleInstances[i];
            }
            // Add a "P" line at the end
            outputToInFile[(mNumberOfSamples * 2) + 1] = "P";

            
            
            // Search for a String that exists
            // outputToInFile[(mNumberOfSamples * 2) + 2] = "S " + samples[2];
            // outputToSearch[0] = samples[2] + " " + sampleInstances[2];
            
            
            
            // Remove mNumberOfSamplesToDelete number of samples
            for (int samplesDeleted = 0; samplesDeleted < mNumberOfSamplesToDelete; samplesDeleted++)
            {
            	int sampleToRemove = (int )(Math.random() * mNumberOfSamples + 0);
            	int randomLine = (int )(Math.random() * mNumberOfSamples + 0);
            	
            	
            	String[] tempArray = new String[(mNumberOfSamples * 3) + 4];
                
                // Shift samples down by one then insert "RO sample"
                System.arraycopy(outputToInFile, randomLine+1, tempArray, 0, mNumberOfSamples);
                for(int i = randomLine+2, j = 0; i < outputToInFile.length; i++, j++) {
                        outputToInFile[i] = tempArray[j];
                }
                // Print RO line
                outputToInFile[randomLine+1] = "RO " + samples[sampleToRemove];
                
                // Reset count for deleted sample
    			for (int j = 0; j < randomLine; j++)
        		{
        			if (samples[j] != null && samples[j].equals(samples[sampleToRemove]))
        			{
        				sampleInstances[j] = sampleInstances[j] - 1;
        				outputToExpectedOut[j] = samples[j] + " | " + sampleInstances[j];
        				
        				if (sampleInstances[j] == 0) 
        				{
        					outputToExpectedOut[j] = null;
        				}
        			}
        		}
    			
    			// Delete sample from our model
    			samples[sampleToRemove] = null;
            }
            
            
            
            
            System.out.println("In File");
            for(String s : outputToInFile)
            {
                if(s != null) {
                    System.out.println(s);
                    inStream.println(s);
                }
            }

            System.out.println("\nExpected Output File");
            for (int i = 0; i < mNumberOfSamples; i++)
            {
            	if (outputToExpectedOut[i] != null)
            	{
            		if (!alreadyInOutput(outputToExpectedOut, samples[i], sampleInstances[i], i))
            		{
                		System.out.println(outputToExpectedOut[i]);
                        expStream.println(outputToExpectedOut[i]);            			
            		}
            	}
            }

            System.out.println(" \nSearch file");
            for(String s : outputToSearch)
            {
                if(s != null)
                {
                    System.out.println(s);

                }
            }
            
            long endTime = System.nanoTime();
            System.out.println("time taken = " + ((double)(endTime - startTime)) / Math.pow(10, 9) + " sec");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }
    
    
    // Helper functions
    
    private static boolean alreadyInOutput(String[] array, String string, int count, int pos) {
		for (int i = 0; i < pos; i++)
        {
        	if (array[i] != null && array[i].equals(string + " | " + count))
			{
        		return true;
			}
        }
    	
    	return false;
    }
    
    private String[] generateStringsWithReplacement() {
        String[] samples = new String[mNumberOfSamples];

        for(int i = 0; i < mNumberOfSamples; i++)
        {
        	String sample = generateRandomString();

        	samples[i] = sample;
        }

        return samples;
    }
    
    private String[] generateStringsWithOutReplacement() {
        String[] samples = new String[mNumberOfSamples];

        for(int i = 0; i < mNumberOfSamples; i++)
        {
        	String sample = generateRandomString();

        	while (Arrays.asList(samples).contains(sample))
        	{
        		sample = generateRandomString();
        	}
        	samples[i] = sample;
        }

        return samples;
    }
        
    private String generateRandomString() {
    	Random r = new Random();
        char nextChar = (char)(r.nextInt(26) + 'a');
        
    	StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < mLength; j++)
        {
            stringBuilder.append(nextChar);
            nextChar = (char)(r.nextInt(26) + 'a');
        }

        return stringBuilder.toString();
    }
}
