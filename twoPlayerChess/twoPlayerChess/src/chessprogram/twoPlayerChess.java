/*
Jeremy Hudson
1-9-2019
 */
package chessprogram;

import java.util.Scanner;

public class twoPlayerChess {

    public static void main(String[] args) {

        String[][] chessBoard = new String[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessBoard[i][j] = "00";
            }

        }

        //set up pawns
        for (int i = 0; i < 8; i++) {

            chessBoard[1][i] = "BP";

            chessBoard[6][i] = "WP";

        }

        //setup rooks
        chessBoard[0][0] = "BR";
        chessBoard[0][7] = "BR";

        chessBoard[7][0] = "WR";
        chessBoard[7][7] = "WR";

        //setup knights
        chessBoard[0][1] = "BN";
        chessBoard[0][6] = "BN";

        chessBoard[7][1] = "WN";
        chessBoard[7][6] = "WN";

        //setup bishops
        chessBoard[0][2] = "BB";
        chessBoard[0][5] = "BB";

        chessBoard[7][2] = "WB";
        chessBoard[7][5] = "WB";

        //setup king and queen
        chessBoard[0][3] = "BQ";
        chessBoard[0][4] = "BK";

        chessBoard[7][3] = "WQ";
        chessBoard[7][4] = "WK";

        Scanner input = new Scanner(System.in);

        String quit = "";

        //loops through white and blacks turns until user quits
        while (!quit.equals("z")) {

            printAnswer(chessBoard);

            getRankAndFile(chessBoard, 0);

            printAnswer(chessBoard);

            getRankAndFile(chessBoard, 1);

            System.out.println("Press z to quit, anything else to continue");

            quit = input.nextLine();

            if (quit.equals("z")) {
                System.out.println("Thanks for playing!");
            }

        }

    }

    //print board
    public static void printAnswer(String[][] chessBoard) {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(" " + chessBoard[i][j] + " ");
            }
            System.out.println("");
        }
    }

    /*this method and the four following it do a simple conversion for the 
     notation based upon whose turn it is
    */
    public static int rankConversionWhite(int rank) {

        int rankNumber = 0;

        switch (rank) {
            case 1:
                rankNumber = 7;
                break;
            case 2:
                rankNumber = 6;
                break;
            case 3:
                rankNumber = 5;
                break;
            case 4:
                rankNumber = 4;
                break;
            case 5:
                rankNumber = 3;
                break;
            case 6:
                rankNumber = 2;
                break;
            case 7:
                rankNumber = 1;
                break;
            case 8:
                rankNumber = 0;
                break;
            default:
                break;
        }

        return rankNumber;
    }

    // notation conversion
    public static int fileConversionWhite(String file) {

        int fileNumber = 0;

        if (null != file) {
            switch (file) {
                case "a":
                    fileNumber = 0;
                    break;
                case "b":
                    fileNumber = 1;
                    break;
                case "c":
                    fileNumber = 2;
                    break;
                case "d":
                    fileNumber = 3;
                    break;
                case "e":
                    fileNumber = 4;
                    break;
                case "f":
                    fileNumber = 5;
                    break;
                case "g":
                    fileNumber = 6;
                    break;
                case "h":
                    fileNumber = 7;
                    break;
                default:
                    break;
            }
        }

        return fileNumber;
    }

    public static int fileConversionBlack(String file) {

        int fileNumber = 0;

        if (null != file) {
            switch (file) {
                case "a":
                    fileNumber = 7;
                    break;
                case "b":
                    fileNumber = 6;
                    break;
                case "c":
                    fileNumber = 5;
                    break;
                case "d":
                    fileNumber = 4;
                    break;
                case "e":
                    fileNumber = 3;
                    break;
                case "f":
                    fileNumber = 2;
                    break;
                case "g":
                    fileNumber = 1;
                    break;
                case "h":
                    fileNumber = 0;
                    break;
                default:
                    break;
            }
        }

        return fileNumber;
    }

    public static int rankConversionBlack(int rank) {

        int rankNumber = 0;

        switch (rank) {
            case 1:
                rankNumber = 0;
                break;
            case 2:
                rankNumber = 1;
                break;
            case 3:
                rankNumber = 2;
                break;
            case 4:
                rankNumber = 3;
                break;
            case 5:
                rankNumber = 4;
                break;
            case 6:
                rankNumber = 5;
                break;
            case 7:
                rankNumber = 6;
                break;
            case 8:
                rankNumber = 7;
                break;
            default:
                break;
        }

        return rankNumber;
    }

    //move piece on a given rank and file from one square to another
    public static void getRankAndFile(String[][] chessBoard, int turn) {

        Scanner inputOne = new Scanner(System.in);
        Scanner inputTwo = new Scanner(System.in);

        System.out.println("\nMove piece on what square? "
                + "Input file then rank with no spaces, for example: e2\n");

        //get input as a single string
        String squareInputOne = inputOne.nextLine();

        //if string is longer than two or contains a space, get a new input
        while (squareInputOne.length() > 2 || squareInputOne.contains(" ")) {

            System.out.println("\nPlease enter a letter immediately followed"
                    + " by a number with no spaces to indicate the correct "
                    + "square\n");
            squareInputOne = inputOne.nextLine();

        }

        //split string into file and rank
        String[] charSetOne = squareInputOne.split("");

        if (turn == 0) {

            int file = fileConversionWhite(charSetOne[0]);

            /*while file is not a letter, squareInputOne is larger than two or 
            contains a space, reset file and try again
             */
            while (!Character.isLetter(charSetOne[0].charAt(0)) || 
                    Character.isLetter(charSetOne[1].charAt(0))
                    || squareInputOne.length() > 2
                    || squareInputOne.contains(" ")) {

              System.out.println("\nPlease enter a letter immediately followed"
                        + " by a number with no spaces to indicate the correct "
                        + "square\n");

                squareInputOne = inputOne.nextLine();
                charSetOne = squareInputOne.split("");
                file = fileConversionWhite(charSetOne[0]);
            }

                
            int rankValue = Integer.valueOf(charSetOne[1]);
            
                
            int rank = rankConversionWhite(rankValue);

             /*while rank is not an integer, squareInputOne is longer than 2 
             or contains a space, reset rank and try again      
            */
            while (!Character.isDigit(charSetOne[1].charAt(0)) || 
                    Character.isLetter(charSetOne[1].charAt(0))
                    || squareInputOne.length() > 2
                    || squareInputOne.contains(" ")) {

                System.out.println("Please enter a letter immediately followed"
                        + " by a number to indicate the correct square");

                squareInputOne = inputOne.nextLine();
                charSetOne = squareInputOne.split("");
                rankValue = Integer.valueOf(charSetOne[1]);
                rank = rankConversionWhite(rankValue);

            }

            //hold piece in memory
            String pieceBeingMoved = chessBoard[rank][file];

            //set current square to be empty
            chessBoard[rank][file] = "00";

            //check what piece it is
            System.out.println("\nMove piece to what sqaure\n");

            //get input as a single string
            String squareInputTwo = inputTwo.nextLine();

          while (squareInputTwo.length() > 2 || squareInputTwo.contains(" ")) {

              System.out.println("\nPlease enter a letter immediately followed"
                      + " by a number with no spaces to indicate the correct "
                      + "square\n");
               squareInputTwo = inputTwo.nextLine();

            }

            /* the following 5 lines split string into file and rank in the 
          same way as the first square input was split, then moves the piece to 
          the designated square
            */
            String[] charSetTwo = squareInputTwo.split("");

            file = fileConversionWhite(charSetTwo[0]);

            rankValue = Integer.valueOf(charSetTwo[1]);

            rank = rankConversionWhite(rankValue);

            chessBoard[rank][file] = pieceBeingMoved;

            System.out.println("");

        } else if (turn == 1) {

            int file = fileConversionBlack(charSetOne[0]);

            /*while file is not a letter, squareInputOne is larger than two or 
            contains a space, reset file and try again
             */
            while (!Character.isLetter(charSetOne[0].charAt(0))) {

                System.out.println("Please enter a letter immediately followed"
                        + " by a number to indicate the correct square");

                squareInputOne = inputOne.nextLine();
                charSetOne = squareInputOne.split("");
                file = fileConversionBlack(charSetOne[0]);
            }

            int rankValue = Integer.valueOf(charSetOne[1]);

            int rank = rankConversionBlack(rankValue);

             /*while rank is not an integer, squareInputTwo is longer than 2 
             or contains a space, reset rank and try again      
            */
            while (!Character.isDigit(charSetOne[1].charAt(0))) {

                System.out.println("Please enter a letter immediately followed"
                        + " by a number to indicate the correct square");

                squareInputOne = inputOne.nextLine();
                charSetOne = squareInputOne.split("");
                rankValue = Integer.valueOf(charSetOne[1]);
                rank = rankConversionBlack(rankValue);

            }

            //hold piece in memory
            String pieceBeingMoved = chessBoard[rank][file];

            //set current square to be empty
            chessBoard[rank][file] = "00";

            //check what piece it is
            System.out.println("\nMove piece to what sqaure\n");

            //get input as a single string
            String squareInputTwo = inputTwo.nextLine();

            /*splits string into file and rank in the same way as the
            first square input was split, then moves the piece to the 
            designated square
            */
            String[] charSetTwo = squareInputTwo.split("");

            file = fileConversionBlack(charSetTwo[0]);

            rankValue = Integer.valueOf(charSetTwo[1]);

            rank = rankConversionBlack(rankValue);

            chessBoard[rank][file] = pieceBeingMoved;

            System.out.println("");

        }

        flipBoard(chessBoard);

    }

    /*flip the board 180 degrees to get the correct perspective of current
    players turn
     */
    public static String[][] flipBoard(String[][] board) {

        for (int i = 0, k = 7; i < 4; i++, k--) {
            String[] temp = board[k];
            board[k] = board[i];
            board[i] = temp;
        }

        for (String[] square : board) {
            for (int i = 0; i < square.length / 2; i++) {
                String temp = square[i];
                square[i] = square[square.length - i - 1];
                square[square.length - i - 1] = temp;
            }
        }

        return board;
    }

}
