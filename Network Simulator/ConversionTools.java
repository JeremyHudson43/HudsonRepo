package Models;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.math.BigInteger;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConversionTools {

    // creates layer images
    public BufferedImage createImageFromPanel(JPanel panel) {

        int width = panel.getWidth();
        int height = panel.getHeight();

        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = buffImg.createGraphics();
        panel.print(graphics);
        return buffImg;
    }

    static String hexToBin(String string) {
        return new BigInteger(string, 16).toString(2);
    }

    public String asciiToHex(String asciiValue) {
        char[] chars = asciiValue.toCharArray();
        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            hex.append(Integer.toHexString((int) chars[i]));
        }
        return hex.toString();
    }

    // convert string value to binary equivalent
    public String stringToBinary(String input) {

        StringBuilder result = new StringBuilder();
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            result.append(
                    String.format("%8s", Integer.toBinaryString(aChar))
                            .replaceAll(" ", "0")
            );
        }
        return formatBinary(result.toString());

    }

    // break binary into more readable chunks
    public String formatBinary(String binary) {

        int chunkSize = 16;
        String separator = "\n";

        List<String> result = new ArrayList<>();
        int index = 0;
        while (index < binary.length()) {
            result.add(binary.substring(index, Math.min(index + chunkSize, binary.length())));
            index += chunkSize;
        }

        return result.stream().collect(Collectors.joining(separator));

    }

    // pad message with 0s 
    public String padMessage(String message) {

        int temp = 0;
        int padding = 0;

        String paddedMessage = message;
        temp = message.length() / 4;

        // if message is not a multiple of 0, add padding
        if ((message.length() % 4) != 0) {
            padding = message.length() - (temp * 4);
            for (int i = 0; i < (4 - padding); i++) {
                paddedMessage = paddedMessage + "0";
            }
        }
        return paddedMessage;
    }

    public String onesComplemenet(String binaryString) {
        char[] charResult = new char[binaryString.length()];
        char[] charArray = binaryString.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            boolean b = charArray[i] == '1';
            charResult[i] = !b ? '1' : '0';
        }
        String result = String.valueOf(charResult);

        return result;
    }
}
