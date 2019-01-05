package greenglassworld;

import java.util.Scanner;

public class GreenGlassWorld {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        boolean consecutiveLetters = false;

        System.out.println("Please enter a single word to determine if it is"
                + " in the Green Glass World: \n");

        String word = input.nextLine();

        System.out.println("");

        greenGlassCalc(word, consecutiveLetters);
    }

    public static void greenGlassCalc(String word,
            boolean consecutiveLetters) {

        for (int i = 0; i < word.length() - 1; i++) {
            
            char first = word.charAt(i); 
            char next = word.charAt(i + 1); 


            if (first == next || first == Character.toLowerCase(next) || 
                    first == Character.toUpperCase(next)) {

                consecutiveLetters = true;
            }
        
        }

        if (!word.matches("[a-zA-Z]*")) {
            System.out.println("Please input only letters with no spaces");
        } 
        else if (consecutiveLetters) {
            System.out.println(consecutiveLetters);
        } 
        else {
            System.out.println(consecutiveLetters);
        }

    }
}