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

    public static void main(String[] args)
    {
        assert(args.length == 3);

        DataGenerator generator = new DataGenerator();

        mLength = Integer.valueOf(args[0]);
        mNumberOfSamples = Integer.valueOf(args[1]);
        // Allow duplications or not
		String samplingType = args[2];

        try {

            PrintStream inStream = new PrintStream("TestString1.in", "UTF-8");
            PrintStream expStream = new PrintStream("TestString1.exp", "UTF-8");
            PrintStream searchStream = new PrintStream("TestString1.search.exp", "UTF-8");

            String[] samples = null;
            
            switch (samplingType) {
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
	            	System.err.println(samplingType + " is an unknown sampling type. Now exiting..");
	            	System.exit(0);
	            	
	            	break;
            }
            
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

            String[] outputToInFile = new String[50];
            String[] outputToSearch = new String[mNumberOfSamples];
            String[] outputToExpectedOut = new String[(mNumberOfSamples * 2) * 2];

            // Generate array to be used for expected output file	
            for(int i = 0; i < mNumberOfSamples; i++)
            {
                //  Add the current sample to the output to the in file
                outputToInFile[i] = "A " + samples[i];
                // Only print one instance of each string
                if(!alreadyInArray(outputToExpectedOut, samples[i], sampleInstances[i]))
                	outputToExpectedOut[i] = samples[i] + " | " + sampleInstances[i];
            }

            for(String s : outputToInFile)
            {
                if(s != null) {
                    System.out.println(s);
                }
            }
            
//            Print the whole list
            outputToInFile[(mNumberOfSamples * 2) + 1] = "P";
//            Search for a String that exists
//            outputToInFile[(mNumberOfSamples * 2) + 2] = "S " + samples[2];
//            outputToSearch[0] = samples[2] + " " + sampleInstances[2];
            
            // Remove a random element
            int sampleToRemove = (int )(Math.random() * mNumberOfSamples + 0);
            String[] tempArray = new String[(mNumberOfSamples * 3) + 4];
            
            // Shift elements down by one then insert "RO sample"
            System.arraycopy(outputToInFile, sampleToRemove+1, tempArray, 0, mNumberOfSamples);
            
            for(int i = sampleToRemove+2, j = 0; i < 20; i++, j++) {
                    outputToInFile[i] = tempArray[j];
            }
            outputToInFile[sampleToRemove+1] = "RO " + samples[sampleToRemove];
            
            System.out.println("\nIn File");
//            Print the output array that will go into the input file
            for(String s : outputToInFile)
            {
                if(s != null) {
                    System.out.println(s);
                    inStream.println(s);
                }
            }

            System.out.println("\nExpected Output File");
//            To output to Expected Out file
            for(String s : outputToExpectedOut)
            {
                if(s != null) {
                    System.out.println(s);
                    expStream.println(s);
                }
            }

            System.out.println(" \nSearch file");
//            Print the Search file
            for(String s : outputToSearch)
            {
                if(s != null)
                {
                    System.out.println(s);

                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    private static boolean alreadyInArray(String[] array, String string, int count) {
		if (array[0] == null)
			return false;

		for (int j = 0; j < array.length; j++)
        {
			if (array[j] == null)
				break;
			
        	if (array[j].equals(string + " | " + count))
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
