package Network;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NetworkUtils {

    // IP addresses
    public String sourceIP = "A1B2C3D";
    public String destIP = "D3C2B1A";

    // TCP Segment fields
    public String sourcePort = "01BB";
    public String destPort = "D49D";
    public String seqNumBlue = "DB4CC2BA";
    public String ackNumBlue = "DB4CC2BB";
    public String reserved = "000";
    public String windowSize = "0160";
    public String urgentPointer = "0000";
    public String tcpProtocol = "00000006";
    public String zeroesHeader = "00000000";
    public String controlHeader = "FIN";
    public String hLenHeader = "0101";

    // creates layer images
    public BufferedImage createImageFromPanel(JPanel panel) {

        int width = panel.getWidth();
        int height = panel.getHeight();

        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        panel.print(g);
        return buffImg;
    }

    public char toHex(int nybble) {
        if (nybble < 0 || nybble > 15) {
            throw new IllegalArgumentException();
        }
        return "0123456789ABCDEF".charAt(nybble);
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

}
