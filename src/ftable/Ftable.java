
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
 *
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
    
    public static void main(String[] args) throws FileNotFoundException, IOException 
    {
        boolean first = false;
        for (int argNum = 0; argNum < args.length; argNum++)
        {
            String arg = args[argNum];
            
            if (arg.equals("-v"))
            {
                System.out.println(arg);
            }
            else if (arg.equals("-s"))
            {
                startCharSkip = Integer.parseInt(args[++argNum]);
            }
            else if (arg.equals("-p"))
            {
                countEvery = Integer.parseInt(args[++argNum]);
            }
            else if (arg.contains(".in") || arg.contains(".txt") || arg.contains(".out"))
            {
                if (!first)
                {
                    inFile = true;
                    first = true;
                    rFile = new FileReader(arg);
                }
                else
                {
                    outFile = true;
                    wFile = new FileWriter(arg);
                }    
            }
            else
            {
                System.out.println("Not found");
            }
        }
        fTable();
    }

    private static void fTable() throws IOException 
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
        
        for (int num = 0; num < 26; num++)
        {
            numCha.put((char)('A' + num), 0);
        }
        
        if (inFile)
        {
            if (startCharSkip != 0)
            {
                for (int num = 0; num <= startCharSkip && numChar != -1; num++)
                {                    
                    numChar = rFile.read();
                    cha = (char)numChar;
                    System.out.println("For 1 " + num + " " + numChar + " " + cha);
                }
                while (cha == ' ' && numChar != -1)
                {
                    System.out.println("While 1");
                    numChar = rFile.read();
                    cha = (char)numChar;
                }
            }
            else
            {
                numChar = rFile.read();
            }
            do
            {
                cha = (char)numChar;
                if (Character.isLetter(cha))
                {
                    numCha.put(cha, numCha.get(cha) + 1);
                }
                if (countEvery > 1)
                {
                    for (int num = 0; numChar != -1 && num < countEvery - 1; num++)
                    {
                        while ((char)(numChar = rFile.read()) == ' ')
                            System.out.println("While 2");
                    }
                }
                if (numChar != -1)
                {
                    while ((char)(numChar = rFile.read()) == ' ')
                        System.out.println("While 3");
                }
                System.out.println("While 4");
            } while (numChar != -1);
            rFile.close();
        }
        else
        {
            System.out.println("Enter a string!");
            plain = scanner.nextLine();
            plain = plain.toUpperCase();
            for (int num1 = startCharSkip; num1 < plain.length();)
            {
                while (plain.charAt(num1) == ' ')
                {
                    num1++;
                }            
                if (Character.isLetter(plain.charAt(num1)))
                {
                    numCha.put(plain.charAt(num1), numCha.get(plain.charAt(num1)) + 1);                    
                }       
                for (int num2 = 0; countEvery > 1 && num2 < countEvery; num2++)
                {
                    num1++;
                    if (num1 < plain.length() && plain.charAt(num1) == ' ')
                    {
                        num2--;
                    }
                }
            }
        }
        
        for (int num = 0; num < 26; num++)
        {
            total = total + numCha.get((char)('A' + num));
        }
        
        
        
        if (outFile)
        {            
            wFile.write("Total chars: " + total);
            wFile.write('\n');
            for (int num = 0; num < 26; num++){
                number = numCha.get((char)('A' + num));
                per = ((float)number * 100.0f) / total;
                temp = format.format(per);
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
                wFile.write((char)('A' + num) + 
                        String.format("%" + 9 + "s", number)
                        + "(" + temp + ") ");
                for (float num1 = 0f; num1 < per; num1++)
                {
                    wFile.write('*');
                }
                wFile.write('\n');
            }
            wFile.close();
        }
        else
        {
            System.out.println("Total chars: " + total);
            for (int num = 0; num < 26; num++){
                number = numCha.get((char)('A' + num));
                per = ((float)number * 100.0f) / total;
                temp = format.format(per);
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
                for (float num1 = 0f; num1 < per; num1++)
                {
                    System.out.print('*');
                }
                System.out.print('\n');
            }
        }
    }
}
