/*
Jeremy Hudson
CSC 452-01 
November 13th, 2019
 */
package pushdownautomata;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class PushdownAutomata {

    public static int numberOfStates;
    public ArrayList<Character> alphabet = new ArrayList<Character>();
    public ArrayList<Character> symbolAlphabet = new ArrayList<Character>();
    public String stringAlphabet;
    public static String[] acceptStates;
    public static String startState;
    public static int transitionsBegin;
    public static int transitionsEnd;

    public void parseDataForPDA() throws Exception {

        //please change this path to your local computer's txt file 
        Path path = Paths.get("C:\\Users\\Jeremy\\Desktop", "PDA input.txt");

        //convert every line to a string list
        List<String> lines = Files.readAllLines(path, StandardCharsets.US_ASCII);

        int index = 0;

        //remove comments to read actual data
        while (lines.get(index).charAt(0) == '%') {
            lines.remove(index);
        }

        String[] arr = lines.toArray(new String[lines.size()]);

        //extract all data from text file 
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].contains("Accept states")) {
                acceptStates = (arr[i].substring(15).split(" "));
            } else if (arr[i].contains("Start state")) {
                startState = (arr[i].substring(13));
            } else if (arr[i].contains("Transitions begin")) {
                transitionsBegin = i + 1;
            } else if (arr[i].contains("Transitions end")) {
                transitionsEnd = i - 1;
            } else if (arr[i].contains("Alphabet:")) {
                stringAlphabet = (arr[i].substring(10)).replaceAll(" ", "");
            } else if (arr[i].contains("Number of states:")) {
                numberOfStates = (convertToInt(arr[0].replaceAll("[^0-9]+", " ")));
            }
        }

        //adds all characters extracted to alphabet ArrayList
        alphabet = new ArrayList<Character>();
        System.out.println("Alphabet is:");
        for (int i = 0; i < stringAlphabet.length(); i++) {
            alphabet.add(stringAlphabet.charAt(i));
            System.out.print(stringAlphabet.charAt(i) + " ");
        }

        System.out.println("");
        symbolAlphabet.add('e');
        symbolAlphabet.add('y');
        symbolAlphabet.add('#');

        //make array 20 units large multiplied by number of transitions
        String[][] Transition = new String[20 * alphabet.size()][20 * alphabet.size()];

        for (int i = transitionsBegin - 2; i < transitionsEnd - 1; i++) {

            String[] tempArray = (arr[transitionsBegin + i - 1].split(" "));

            for (int j = 0; j < 5; j++) {

                Transition[i][j] = tempArray[j];

            }
        }

        buildAndTraversePDA(startState, Transition, acceptStates, transitionsBegin, transitionsEnd);

    }
    //cycles through state/transition and returns appropriate state

    public ArrayDeque<Character> TransitionChainStack(String[][] Transition, String state, char input, ArrayDeque<Character> stack) {
        for (int i = 0; i < Transition.length; i++) {

            if (state.equals(Transition[i][0]) && input == Transition[i][1].charAt(0)) {

                //pop top of stack
                if (Transition[i][2].charAt(0) != 'e' && stack.peekFirst() == Transition[i][2].charAt(0)) {
                    stack.pop();
                }
                //push onto top of stack
                if (Transition[i][4].charAt(0) != 'e') {
                    stack.push(Transition[i][4].charAt(0));
                }

                return stack;
            }
        }

        return stack;
    }

    //cycles through state/transition and returns appropriate state
    public String TransitionChainState(String[][] Transition, String state, char input, char topOfStack) {
        for (int i = 0; i < Transition.length; i++) {

            if (state.equals(Transition[i][0]) && input == Transition[i][1].charAt(0)) {

                if (Transition[i][2].charAt(0) == topOfStack || Transition[i][2].charAt(0) == 'e') {

                    state = Transition[i][3];

                    System.out.println("From state " + Transition[i][0]
                            + " with input symbol " + input + ", " + Transition[i][2] + " was popped from the stack"
                            + " which takes us to state " + Transition[i][3] + " and pushes " + Transition[i][4] + " onto the stack \n");

                }
            }

        }

        return state;
    }

    //gets input and traverses PDA
    public void buildAndTraversePDA(String start, String[][] Transition, String[] finalStates, int startIndex, int endIndex) throws Exception {

        System.out.println("\nGive your input to the PDA with a single input symbol per line for correct results\n");

        Scanner scanner = new Scanner(System.in);
        char input = '!';

        String state = start;
        ArrayDeque<Character> stack = new ArrayDeque<Character>();

        while (input != '#') {

            input = scanner.next().charAt(0);

            if (!alphabet.contains(input) && !symbolAlphabet.contains(input)) {
                System.out.println("Bad alphabet");
            } else {

                if (!stack.isEmpty()) {
                    char topOfStack = stack.peekFirst();
                    stack = TransitionChainStack(Transition, state, input, stack);
                    state = TransitionChainState(Transition, state, input, topOfStack);

                } else if (stack.isEmpty()) {
                    stack = TransitionChainStack(Transition, state, input, stack);
                    state = TransitionChainState(Transition, state, input, 'e');

                }

                printStack(stack);

            }

        }

        //determine whether string is accepted by PDA
        boolean isAccepted = determineAcceptStrings(finalStates, state);

        if (isAccepted == true) {
            System.out.println(state + " is the final state from user input which is accepted by PDA");
        } else if (isAccepted == false) {
            System.out.println(state + " is the final state from user input which is rejected by PDA");
        }

    }

    public void printStack(ArrayDeque<Character> stack) {
        System.out.println("Stack contents from bottom to top are: \n");
        for (Character symbol : stack) 
        { 
            System.out.println(symbol); 
        } 
    }

    //loops through every finalState value to determine if it accepts the string
    public boolean determineAcceptStrings(String[] finalStates, String state) {
        boolean isAccepted = false;
        for (int i = 0; i < finalStates.length; i++) {
            if (state.equals(finalStates[i])) {
                isAccepted = true;
                return isAccepted;
            }

        }
        return false;
    }

    //convert to int for some formatting reasons
    public int convertToInt(String stringToConvert) {

        String finalString = stringToConvert.replaceAll("\\s+", "");
        int intValueOfString = Integer.valueOf(finalString);
        return intValueOfString;

    }

    public static void main(String args[]) throws Exception {
        PushdownAutomata automata = new PushdownAutomata();
        automata.parseDataForPDA();

    }
}
