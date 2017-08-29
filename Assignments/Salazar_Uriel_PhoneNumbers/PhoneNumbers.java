/*******************************************************************************
* File:       PhoneNumbers.java
* Programmer: Uriel Salazar
* Date:       April 4, 2017
* Course:     CSC-18B
* Purpose:    Utilize String class "split" to extract integers from input
*******************************************************************************/

//package class
package phonenumbers;

//import packages
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//declare class
public class PhoneNumbers
{
    //declare main program method
    public static void main(String[] args)
    {
        //display menu
        System.out.println("******* Telephone Number Tokenizer *******");
        
        //declare variables
        String   userNumber = "";       //user-entered telephone number
        String[] areaToken = {};        //string for area code
        String[] numberTokens = {};     //string array for split substrings
        String   delimiters = "[()]|\\s\\d{3}\\-\\d{4}";
        
        //declare objects
        Scanner input = new Scanner(System.in);
        
        //create regular expression
        Pattern format = Pattern.compile("^[(]\\d{3}[)]\\s\\d{3}[-]\\d{4}");
        
        //create matcher
        Matcher m = format.matcher(userNumber);
        
        //begin while-loop
        while(!m.matches())
        {
            //prompt user for input
            System.out.print("\nPlease input your telephone number in the "
                    + "following format: (XXX) XXX-XXXX\n\n");
            System.out.print(">> ");
            userNumber = input.nextLine();
            
            //assign new user-input to matcher
            m = format.matcher(userNumber);

            //validate the user-entered string
            if(m.matches())
            {
                //grab second token of user-input (area code)
                areaToken = userNumber.replaceFirst("^\\(", "").split(
                        delimiters);
                
                //change delimiter characters
                delimiters = "-";
                
                //grab the rest of the phone number
                numberTokens = userNumber.replaceFirst("\\((\\d{3})\\)\\s", "")
                        .split(delimiters);
            }
            else
            {
                //output error message
                System.out.println("ERROR: Incorrect Format");
            }
        }
        //convert arrays into strings
        StringBuilder builder = new StringBuilder();
        for(String area : areaToken)
        {
            builder.append(area);
        }
        StringBuilder builder2 = new StringBuilder();
        for(String numbers : numberTokens)
        {
            builder2.append(numbers);
        }
        String area = builder.toString();
        String numbers = builder2.toString();
        
        //print output
        System.out.println("\nYour area code is: " + area + "\nYour phone "
                + "number is: " + numbers);
       
        //close object
        input.close();
    }
}

