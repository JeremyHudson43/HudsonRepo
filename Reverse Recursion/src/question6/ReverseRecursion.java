package reverserecursion;

public class ReverseRecursion {

    public static void main(String[] args) {

        //test case 1 
        System.out.print(reverse("Apple")
                + " is the given string reversed.\n");

        //test case 2 
        System.out.print(reverse("Cucumber")
                + " is the given string reversed.\n");

        //test case 3
        System.out.print(reverse("Pat")
                + " is the given string reversed.\n");
    }

    /* use this method as the driver for the recursive routine
      that will create two stringbuffers to modify the given string
     */
    public static String reverse(String str) {
        StringBuilder strBuild1 = new StringBuilder();
        StringBuilder strBuild2 = new StringBuilder(str);

        strBuild1 = recursiveRoutine(strBuild1, strBuild2);

        return strBuild1.toString();
    }

    /*  recursively add elements to SBone beginning at the element at
    the end of SBtwo and moving backwards until the whole word is spelled
     */
    private static StringBuilder recursiveRoutine(StringBuilder SBone, 
            StringBuilder SBtwo) {

        int length = SBtwo.length();

        if (SBtwo.length() == 0) {
            return SBone;
        } else {
            length--;
            return recursiveRoutine(SBone.append(SBtwo.charAt(length)),
                    SBtwo.deleteCharAt(length));
        }
    }
}
