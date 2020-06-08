package greenglassworld;

import java.util.Scanner;

public class GreenGlassWorld {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        boolean consecutiveLetters = false;

        System.out.println("Please enter a single word to determine if it is"
                + " in the Green Glass World: \n");

        // get user input
        String word = input.nextLine();

        System.out.println("");

        boolean result = greenGlassCalc(word, consecutiveLetters);

        //print final boolean value from method return result
        System.out.println(result);

    }

    public static boolean greenGlassCalc(String word,
            boolean consecutiveLetters) {

        //determines if the next letter is identical to the current letter 
        for (int i = 0; i < word.length() - 1; i++) {

            char first = word.charAt(i);
            char next = word.charAt(i + 1);

            //check for lower and uppercase
            if (first == next || first == Character.toLowerCase(next)
                    || first == Character.toUpperCase(next)) {

                consecutiveLetters = true;
            }

        }

        // check to see if user entered numbers
        if (!word.matches("[a-zA-Z]*")) {
            System.out.println("Please input only letters with no spaces");
        }
        return consecutiveLetters;
    }
}
