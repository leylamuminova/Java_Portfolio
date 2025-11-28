/**
 * ISBN.java
 * Homework 1, Problem 1A and 1B
 * Program to validate ISBN input and compute the 10th (ISBN-10) or 13th (ISBN-13) check digit.
 */

import java.util.Scanner;

public class ISBN {

    /**
     * Compute the 10th digit of an ISBN-10 from the first 9 digits.
     *
     * @param isbn int, the first 9 digits of the ISBN
     * @return String, the 10th check digit (0–9 or "X")
     */

    public static String isbn10(int isbn) {
        int sum = 0;

        /**
         * Example of my own number: for first 9 digits 093847582 -> check digit is 7
         * Full ISBN-10 = 0938475827
         */

        // Loop through 9 times (one time for each digit!)
        for (int i = 1; i <= 9; i++) {
            // Taking the last digit of the number
            int digit = isbn % 10;

            // Removing that last digit from the number
            isbn = isbn / 10;

            // Figuring out the weight of each digit
            int weight = 10 - i;

            // Adding (digit * weight) to sum
            sum += digit * weight;
        }

        // The remainder when divided by 11 is the check digit that we need to add to the end
        int check = sum % 11;

        // If the check digit is 10, return capital "X"
        if (check == 10) {
            return "X";
        }

        // Or returing the 10th digit itself as a string (beacuse String isbn10)
        return Integer.toString(check);
    }

    /**
     * Compute the 13th digit of an ISBN-13 from the first 12 digits.
     *
     * @param isbn12 int, the first 12 digits of the ISBN
     * @return String, the 13th check digit (0–9)
     */

    public static String isbn13(long isbn12) {
        int sum = 0;

        /**
         * Example of my own number: for first 12 digits 978030640615  -> check digit is 7
         * Full ISBN-13 = 9780306406157
         */

        // Loop through 12 times (one time for each digit!)     
        for (int i = 1; i <= 12; i++) {

            // Taking the last digit of the number, as we go from right to left
            int digit = (int)(isbn12 % 10);

            // Removing that last digit from the number
            isbn12 = isbn12 / 10;

            // Checking if number is odd or even
            if (i % 2 == 0) {   
                sum += digit;
            } else {            
                sum += digit * 3;
            }
        }

        // By using formula, find check digit
        int x = 10 - (sum % 10);
        int result = x % 10; 
        
        //Returning string!
        return Integer.toString(result);
    
    }

    /** Main method for the ISBN program.
     * Prompts the user to enter an ISBN number from the command line.
     * If the input is 9 digits, computes the 10th digit (ISBN-10).
     * If the input is 12 digits, computes the 13th digit (ISBN-13).
     * If the input is invalid, prints an error message and reprompts user again.
     *
     * Using try-catch to handle invalid numeric input.
     */

    public static void main(String[] args) {

        // Intiated some variables
        boolean goodAnswer = false; // Used primitive type boolean instead of Object Boolean
        String prompt = "Enter your ISBN-number: ";
        String answer;

        // Scanner reads the input from command line
        Scanner input = new Scanner(System.in);
        
        while (!goodAnswer) {

            // Prompting the user
            System.out.print(prompt);
            answer = input.nextLine();
            
            // Using try-catch so exceptions don’t escape from main.
            // This way the program handles errors instead of crashing.

            // Note on "The main method should not throw any exceptions. All exceptions
            // thrown in the main method should also be caught there. This allows the
            // program to have some space to handle these exceptions."
            // In my code, the main method does not declare 'throws Exception', and
            // the try-catch here already handles NumberFormatException from parsing.
            // Since that is the only realistic exception in this context, I believe
            // this requirement is already met.

            try {
                // If the user's number is 9-digit long, we use method isbn10()
                if (answer.length() == 9) {
                    // int integerAnswer = Integer.parseInt(answer); // Removed for redundancy
                    String result = isbn10(Integer.parseInt(answer));
                    System.out.println(result);
                    goodAnswer = true; // Break the while loop when the parse works 

                // If user's number is 12-digit long, we use isbn13()
                } else if (answer.length() == 12) {
                    // long longAnswer = Long.parseLong(answer); // Removed because of redundancy
                    String result = isbn13(Long.parseLong(answer));
                    System.out.println(result);
                    goodAnswer = true; // Break the while loop when the parse works 

                } else {
                    // Wrong length it shows message, then loop continues to re-prompt
                    System.out.println("Invalid input");
                    // Comment from Muhammad Haroon: "re-prompt if length is invalid."
                    // Because this code is inside while(!goodAnswer), the program already
                    // goes back and asks again automatically after showing this message.

                }

            } catch (NumberFormatException e) {
                // Handles case when input is not a number
                System.out.println("Invalid input");
            }

        }

        input.close();
    }
}

