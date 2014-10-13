import java.io.*;
import java.util.*;
/**
 *
 * @author Hung (Michael) Ngo
 */
public class AWPCounter
{
    //Array for letters
    public static int[] letterCounter = new int[52];

    //Array for words
    public static ArrayList<String> listOfWords = new ArrayList<String>();
    public static ArrayList<Integer> wordsFrequency = new ArrayList<Integer>();

    //Array for double-word phrases
    public static ArrayList<String> listOfDoubleWordPhrases = new ArrayList<String>();
    public static ArrayList<Integer> doubleWordPhrasesFrequency = new ArrayList<Integer>();

    public static void main(String[] args)
    {
        //Input file
        FileInputStream inputFile;
        String fileName = "Rise of the Lich King.txt";

        try
        {
            //Open an input stream
            inputFile = new FileInputStream(fileName);
            DataInputStream textData = new DataInputStream(inputFile);

            //Read a line of text
            String textLine = "";

            while(textData.available()!=0)
                textLine = textLine + textData.readLine();

            //Ignore characters other than alphabets
            String delim = " ().,<>$/\\`~?!#%’^&*-—+:1234567890=";
            StringTokenizer st = new StringTokenizer(textLine,delim);

            AWPCounter(st);

            printFrequencyOfAlphabets();
            printFrequencyOfWords();
            printFrequencyOfDoubleWordPhrases();

        }

        catch (IOException error)
        {
            System.err.println ("Invalid file.");
            System.exit(-1);
	}
    }

    public static void AWPCounter(StringTokenizer st)
    {
        char alphabet;
        String previousWord = "";
        String word = "";

        while(st.hasMoreTokens())
        {
            word = st.nextToken();

            //Count alphabets
            for(int i = 0; i < word.length(); i++)
            {
                alphabet = word.charAt(i);

                if(alphabet >= 65 && alphabet <= 90 )
                    letterCounter[alphabet - 65] = letterCounter[alphabet - 65] + 1;

                else if(alphabet >= 97 && alphabet <= 122)
                    letterCounter[alphabet - 71] = letterCounter[alphabet - 71] + 1;
            }

            //convert word to lower case
            word = word.toLowerCase();

            //Count word occurence
            if(listOfWords.contains(word))
            {
                for(int i = 0 ; i < listOfWords.size(); i++)
                {
                    if(listOfWords.get(i).equals(word))
                        wordsFrequency.set(i,wordsFrequency.get(i)+1);
		}
            }

            //add new word to arraylists
            else
            {
                listOfWords.add(word);
		wordsFrequency.add(1);
            }

            //Count double word phrases
            if(previousWord.equalsIgnoreCase(""))
            {
                previousWord = word;
            }

            else
            {
                String doubleWord = previousWord + " " + word;

                if (listOfDoubleWordPhrases.contains(doubleWord))
                {
                    for (int i = 0 ; i < listOfDoubleWordPhrases.size(); i++)
                    {
                        if (listOfDoubleWordPhrases.get(i).equals(doubleWord))
                            doubleWordPhrasesFrequency.set(i,doubleWordPhrasesFrequency.get(i)+1);
                    }
                }

                //add new word to arraylists
                else
                {
                    listOfDoubleWordPhrases.add(doubleWord);
                    doubleWordPhrasesFrequency.add(1);
                }

                previousWord = word;
            }
        }
    }

    public static void printFrequencyOfAlphabets()
    {
        System.out.println("Frequency of alphabets:");

        System.out.println("Upper Case:");
        for (int letter = 0; letter < 26; letter++)
            System.out.println((char)(letter + 65) + ": " +  letterCounter[letter]);

        System.out.println();

        System.out.println("Lower Case:");
        for (int letter = 0; letter < 26; letter++)
            System.out.println((char)(letter + 97) + ": " +  letterCounter[letter + 26]);
    }

    public static void printFrequencyOfWords()
    {
        int max;

        System.out.println("\nTop 50 most frequently used words:");

	for (int j = 0; j < 50; j++)
        {
            max = findWordMaxSquencePosition(wordsFrequency);
            System.out.print(listOfWords.get(max) + ": ");
            System.out.println(wordsFrequency.get(max));
            listOfWords.remove(max);
            wordsFrequency.remove(max);
        }
    }

    public static int findWordMaxSquencePosition(ArrayList<Integer> wordsFrequency)
    {
        int maxPosition = 0;

        for (int i = 0; i < wordsFrequency.size(); i++)
        {
            if (wordsFrequency.get(i) > wordsFrequency.get(maxPosition))
                maxPosition = i;
	}

        return maxPosition;
    }

    public static void printFrequencyOfDoubleWordPhrases()
    {
        int max;

        System.out.println("\nTop 25 most frequently used double-world phrases:");

	for (int j = 0; j < 25; j++)
        {
            max = findWordMaxSquencePosition(doubleWordPhrasesFrequency);
            System.out.print(listOfDoubleWordPhrases.get(max) + ": ");
            System.out.println(doubleWordPhrasesFrequency.get(max));
            listOfDoubleWordPhrases.remove(max);
            doubleWordPhrasesFrequency.remove(max);
        }
    }

    public static int findDoubleWordMaxSquencePosition(ArrayList<Integer> doubleWordsFrequency)
    {
        int maxPosition = 0;

        for (int i = 0; i < doubleWordPhrasesFrequency.size(); i++)
        {
            if (doubleWordPhrasesFrequency.get(i) > doubleWordPhrasesFrequency.get(maxPosition))
                maxPosition = i;
	}

        return maxPosition;
    }

}
