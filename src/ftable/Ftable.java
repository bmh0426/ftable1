package ftable;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * This program counts how many times a letter is in the string and prints
 * out different types of information about it.
 * @author Brian Holland
 */
public class Ftable {

    /**
     * @param args the command line arguments
     */
    
    private static int startCharSkip = 0;
    private static int countEvery = 1;
    private static boolean inFile = false;
    private static boolean outFile = false;
    private static FileReader rFile = null;
    private static FileWriter wFile = null;
    private static boolean verbosity = false;
    
    public static void main(String[] args) 
    {
        boolean first = false;
        /*Check which args are given to the program.*/
        for (int argNum = 0; argNum < args.length; argNum++)
        {
            String arg = args[argNum];
            /*if -v is given*/
            if (arg.equals("-v"))
            {
                System.out.println("-p is for the period.");
                System.out.println("-s is for skipping letter characters.");
                verbosity = true;
            }
            /*if -s is given*/
            else if (arg.equals("-s"))
            {
                startCharSkip = Integer.parseInt(args[++argNum]);
                if (verbosity)
                {
                    System.out.println("Skipping the first " 
                            + startCharSkip + " letters!");
                }
            }
            /*if -p is given*/
            else if (arg.equals("-p"))
            {
                countEvery = Integer.parseInt(args[++argNum]);
                if (verbosity)
                {
                    System.out.println("Only counting every " 
                            + countEvery + " letter!");
                }
            }
            /*if an input or output file is given*/
            else if (arg.contains(".in") || arg.contains(".txt") || 
                    arg.contains(".out"))
            {
                /*Check to see if this is the first file given or the second*/
                /*If first then it is the read file and it second the write file*/
                if (!first)
                {
                    inFile = true;
                    first = true;
                    try
                    {
                        rFile = new FileReader(arg);
                        if (verbosity)
                        {
                            System.out.println("Using " + rFile 
                                    + " as input file!");
                        }
                    }
                    catch (FileNotFoundException ex)
                    {
                        System.out.println("Could not open read file.");
                    }
                }
                else
                {
                    outFile = true;
                    try
                    {
                        wFile = new FileWriter(arg);
                        if (verbosity)
                        {
                            System.out.println("Using " + wFile 
                                    + " as output file!");
                        }
                    }
                    catch (IOException ex)
                    {
                        System.out.println("Could not open the write file.");
                    }
                }    
            }
            else
            {
                System.out.println("Not found");
            }
        }
        fTable();
        System.exit(1);
    }

    private static void fTable()
    {
        String plain = "";
        int total = 0;
        Scanner scanner = new Scanner(System.in);
        int number = 0;
        int numChar = 0;
        float per = 0;
        char cha = ' ';
        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);
        format.setMaximumIntegerDigits(3);
        format.setMinimumIntegerDigits(3);
        String temp = "";
        
        Map<Character, Integer> numCha = new HashMap<>();
        
        /*This will add 'A-Z' to the hashmap.*/
        for (int num = 0; num < 26; num++)
        {
            numCha.put((char)('A' + num), 0);
        }
        if (verbosity)
        {
            System.out.println("The hash map was made! " + numCha);
        }
        /*Check to see if an input file was given.*/
        if (inFile)
        {
            if (verbosity)
            {
                System.out.println("In ifFile code!");
            }
            /*Check to see if a start offset was given.*/
            if (startCharSkip != 0)
            {
                if(verbosity)
                {
                    System.out.println("In startCharSkip code!");
                }
                /*This will move the read of the file a number of times
                 * equal to the offset given.
                 */
                for (int num = 0; num <= startCharSkip && numChar != -1; num++)
                {  
                    if (verbosity)
                    {
                        System.out.println("Skipping " + num + " character.");
                    }
                    try
                    {
                        numChar = rFile.read();
                        if (verbosity)
                        {
                            System.out.println("Read in " 
                                    + (char)numChar + " character!");
                        }
                    }
                    catch (IOException ex)
                    {
                        System.out.println("Could not read from file.");
                    }
                    cha = (char)numChar;
                    /*This check to see if last read is a letter.
                     * if not then keep reading character until a letter is 
                     * found.
                     */
                    while (!Character.isLetter(cha))
                    {
                        if (verbosity)
                        {
                            System.out.println(cha + " is not a letter!");
                        }
                        try
                        {
                            if (verbosity)
                            {
                                System.out.println(cha 
                                    + " is not a letter, so reading "
                                        + "next character!");
                            }
                            numChar = rFile.read();
                        }
                        catch (IOException ex)
                        {
                            System.out.println("Could not read file.");
                        }
                        cha = (char)numChar;
                    }
                }
            }
            else
            {
                if (verbosity)
                {
                    System.out.println("Skipped startCharSkip code!" 
                            + " Reading in first character.");
                }
                try
                {
                    numChar = rFile.read();
                }
                catch (IOException ex)
                {
                    System.out.println("Could not read file.");
                }
            }
            /*This loops through the rest of the input file looking for 
             * letters.*/
            do
            {
                if (verbosity)
                {
                    System.out.println("In the loop to read in each character"
                            + " and if it is a letter added the count to "
                            + "the hash map!");
                }
                cha = (char)numChar;
                /*If letter add the count to the hashmap.*/
                if (Character.isLetter(cha))
                {
                    System.out.println("The character is a letter.");
                    numCha.put(cha, numCha.get(cha) + 1);
                }
                /*Check if a period was given*/
                if (countEvery > 1)
                {
                    if(verbosity)
                    {
                        System.out.println("In the period code.");
                    }
                    /*This loop will check if a letter has been read and move
                     * the read as many times as the period was given. It will
                     * only count the letters ignoring all non-letters.
                     */
                    for (int num = 0; numChar != -1 && num < countEvery - 1; 
                            num++)
                    {
                        if(verbosity)
                        {
                            System.out.println("Skipping every " + countEvery
                                    + " letters.");
                        }
                        try
                        {
                            while (numChar != -1 && 
                                    !Character.isLetter((char)(numChar = 
                                    rFile.read())))
                            {
                                System.out.println("In while loop to skip "
                                        + "non letters");
                                System.out.println((char)numChar 
                                        + " is not a letter");
                            }
                            System.out.println((char)numChar + " is a letter");
                        }                        
                        catch (IOException ex)
                        {
                            System.out.println("Could not read file.");
                        }
                    }
                }
                /*Check if at end of file.*/
                if (numChar != -1)
                {
                    System.out.println("Not at the end of file.");
                    try
                    {
                        /*This will keep moving the read until a letter is 
                         * found.*/
                        while (numChar != -1 && 
                                !Character.isLetter((char)(numChar = 
                                rFile.read())))
                        {
                            System.out.println("In the loop to "
                                    + "skip non-letters." + (char)numChar 
                                    + " is not a letter.");
                        }
                    }
                    catch (IOException ex)
                    {
                        System.out.println("Could not read file.");
                    }
                }
            } while (numChar != -1);
            try
            {
                rFile.close();
            }
            catch (IOException ex)
            {
                System.out.println("Could not close file.");
            }
        }
        else
        {
            System.out.println("Enter a string!");
            plain = scanner.nextLine();
            plain = plain.toUpperCase();
            
            if(verbosity)
            {
                
            }
            /*This will move the start offset if one was given.*/
            for (int num1 = startCharSkip; num1 < plain.length();)
            {
                /*If the character is not a letter then num1 is increased
                 * until a letter is found.
                 */
                while (!Character.isLetter(plain.charAt(num1)))
                {
                    num1++;
                }        
                /* Increase the count of the letter.*/
                if (Character.isLetter(plain.charAt(num1)))
                {
                    numCha.put(plain.charAt(num1), 
                            numCha.get(plain.charAt(num1)) + 1);                    
                }       
                /* This loop moves the correct period given.*/
                for (int num2 = 0; countEvery > 1 && num2 < countEvery; num2++)
                {
                    num1++;
                    if (num1 < plain.length() && 
                            !Character.isLetter(plain.charAt(num1)))
                    {
                        num2--;
                    }
                }
            }
        }
        /* This adds up all the letters found.*/
        for (int num = 0; num < 26; num++)
        {
            total = total + numCha.get((char)('A' + num));
        }   
        /* Checks if an out file was given.*/
        if (outFile)
        {   
            try
            {
                wFile.write("Total chars: " + total);
                wFile.write('\n');
            }
            catch (IOException ex)
            {
                System.out.println("Could not write to the file.");
            }
            /*Loop to print all the info for the letters.*/
            for (int num = 0; num < 26; num++){
                number = numCha.get((char)('A' + num));
                per = ((float)number * 100.0f) / total;
                temp = format.format(per);
                /* This helps format the output of the percent*/
                if (temp.charAt(0) == '0')
                {   
                    if (temp.charAt(1) == '0')
                    {
                        temp = temp.replaceFirst("0", " ");
                        temp = temp.replaceFirst("0", " ");
                    }
                    else
                    {
                        temp = temp.replaceFirst("0", " ");
                    }
                    
                }                
                try
                {
                    wFile.write((char)('A' + num) +
                            String.format("%" + 9 + "s", number)
                            + "(" + temp + ") ");
                }
                catch (IOException ex)
                {
                    System.out.println("Could not write to the file.");
                }
                /* This prints the histogram*/
                for (float num1 = 0f; num1 < per; num1++)
                {
                    try
                    {
                        wFile.write('*');
                    }
                    catch (IOException ex)
                    {
                        System.out.println("Could not write to the file.");
                    }
                }
                try
                {
                    wFile.write('\n');
                }
                catch (IOException ex)
                {
                    System.out.println("Could not write to the file.");
                }
            }
            try
            {
                wFile.close();
            }
            catch (IOException ex)
            {
                System.out.println("Could not close the file.");
            }
        }
        else
        {
            System.out.println("Total chars: " + total);
            /*Loop to print all the info for the letters.*/
            for (int num = 0; num < 26; num++){
                number = numCha.get((char)('A' + num));
                per = ((float)number * 100.0f) / total;
                temp = format.format(per);
                /* This helps format the output of the percent*/
                if (temp.charAt(0) == '0')
                {   
                    if (temp.charAt(1) == '0')
                    {
                        temp = temp.replaceFirst("0", " ");
                        temp = temp.replaceFirst("0", " ");
                    }
                    else
                    {
                        temp = temp.replaceFirst("0", " ");
                    }
                    
                }
                System.out.print((char)('A' + num) + 
                        String.format("%" + 9 + "s", number)
                        + "(" + temp + ") ");
                /* This prints the histogram*/
                for (float num1 = 0f; num1 < per; num1++)
                {
                    System.out.print('*');
                }
                System.out.print('\n');
            }
        }
    }
}
