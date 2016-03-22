import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * Created by josh on 17/03/16.
 */
public class DataGenerator {

    private static int mNumberOfSamples;
    private static int mLength;

    public static void main(String[] args)
    {
        assert(args.length == 2);

        DataGenerator generator = new DataGenerator();

        mNumberOfSamples = Integer.valueOf(args[1]);
        mLength = Integer.valueOf(args[0]);


        try {

            PrintStream inStream = new PrintStream("TestString1.in", "UTF-8");
            PrintStream expStream = new PrintStream("TestString1.exp", "UTF-8");
            PrintStream searchStream = new PrintStream("TestString1.search.exp", "UTF-8");


            String[] samples = generator.generateRandomStrings();
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


//            Print the samples
            for(String s : samples)
            {
                System.out.println(s);
            }

//            Print the number of instances of each string
            for(int i : sampleInstances)
            {
                System.out.println(i);
            }

            String[] outputToInFile = new String[(mNumberOfSamples * 2) + 4];
            String[] outputToSearch = new String[mNumberOfSamples];
            String[] outputToExpectedOut = new String[(mNumberOfSamples * 2) * 2];

            for(int i = 0; i < mNumberOfSamples; i++)
            {
//                Add the current sample to the output to the in file
                outputToInFile[i] = "A " + samples[i];
//                If there is more than one instance of an input,
//                remove one instance of it
//                if(sampleInstances[i] > 1)
//                    output[mNumberOfSamples + i] = "RO " + samples[i];
//                output[mNumberOfSamples + i] = "RO " + samples[i];

                outputToExpectedOut[i] = samples[i] + " | " + "1";
            }

//            Print the whole list
            outputToInFile[(mNumberOfSamples * 2) + 1] = "P";
//            Search for a String that exists
//            outputToInFile[(mNumberOfSamples * 2) + 2] = "S " + samples[2];
//            outputToSearch[0] = samples[2] + " " + sampleInstances[2];



            System.out.println("In File \n");
//            Print the output array that will go into the input file
            for(String s : outputToInFile)
            {
                if(s != null) {
                    System.out.println(s);
                    inStream.println(s);
                }
            }

            System.out.println("Expected Output File \n");
//            To output to Expected Out file
            for(String s : outputToExpectedOut)
            {
                if(s != null) {
                    System.out.println(s);
                    expStream.println(s);
                }
            }

            System.out.println("Search file \n");
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

    private String[] generateRandomStrings() throws FileNotFoundException, UnsupportedEncodingException {
        String[] samples = new String[mNumberOfSamples];

        PrintStream stream = new PrintStream("TestString1.in", "UTF-8");
        PrintStream expStream = new PrintStream("TestString1.exp", "UTF-8");
        Random r = new Random();
        char nextChar = (char)(r.nextInt(26) + 'a');


        for(int i = 0; i < mNumberOfSamples; i++)
        {
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < mLength; j++)
            {
                stringBuilder.append(nextChar);
//                stream.print(nextChar);
//                stringBuilder.append(nextChar);
//                samples[i].concat(stringBuilder.toString());
                nextChar = (char)(r.nextInt(26) + 'a');
            }
            samples[i] = stringBuilder.toString();
//            if(i % 5 == 0 && i != mNumberOfSamples)
//            {
//                samples[i + 1] = stringBuilder.toString();
//                i++;
//            }

//            stream.println();
        }
//        stream.println("P");
        return samples;
    }
}
