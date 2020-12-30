package transport;

public class ChecksumAuth {

    // Psuedoheader
    public static String sourceIP = "152.13.17.98";
    public static String destIP = "192.168.1.107";
    public static String tcpProtocol = "0006";
    public static String tcpTotalLength = "0463";

    // header
    public static String sourcePort = "80";
    public static String destPort = "58284";

    public static String seqNum = "1724564533";
    public static String ackNum = "113326652";

    public static String reserved = "000";
    public static String windowSize = "716";
    public static String urgentPointer = "0000";
    public static String flagsHeader = "5010";

    public static void main(String[] args) {

        // convert all values to hex for calculation
        sourceIP = parseIPAddress(sourceIP);
        destIP = parseIPAddress(destIP);
        tcpTotalLength = toHex(tcpTotalLength);
        windowSize = toHex(windowSize);
        sourcePort = toHex(sourcePort);
        destPort = toHex(destPort);
        seqNum = toHex(seqNum);
        ackNum = toHex(ackNum);

        // pseudoheader
        System.out.println("Psuedoheader\n");
        System.out.println("Source IP: " + sourceIP);
        System.out.println("Dest IP: " + destIP);
        System.out.println("Reserved / TCP protocol: " + tcpProtocol);
        System.out.println("Padding / Length: " + tcpTotalLength);
        System.out.println("");

        // header
        System.out.println("Header\n");
        System.out.println("TCP Source Port; " + sourcePort);
        System.out.println("TCP Dest Port: " + destPort);
        System.out.println("Sequence Number: " + seqNum);
        System.out.println("Ack Num: " + ackNum);
        System.out.println("Offset / Res / Flags: " + flagsHeader);
        System.out.println("Windows: " + windowSize);
        System.out.println("Urgent Pointer: " + urgentPointer);
        System.out.println("");

        String tcpPseudoHeader = sourceIP + destIP + tcpProtocol + tcpTotalLength;

        String tcpHeader = sourcePort + destPort + seqNum + ackNum
                + flagsHeader + windowSize + urgentPointer;

        String data = "485454502f312e3120323030204f4b0d0a5365727665723a206e67696e782f312e31372e390d0a446174653a2053756e2c203034204f637420323032302031383a35373a343020474d540d0a436f6e74656e742d547970653a20746578742f68746d6c3b20636861727365743d5554462d380d0a436f6e74656e742d4c656e6774683a2031313437370d0a436f6e6e656374696f6e3a206b6565702d616c6976650d0a582d506f77657265642d42793a205048502f372e332e31380d0a566172793a204163636570742d456e636f64696e670d0a436f6e74656e742d456e636f64696e673a20677a69700d0a5374726963742d5472616e73706f72742d53656375726974793a206d61782d6167653d3330303b0d0a582d4672616d652d4f7074696f6e733a2044454e590d0a582d436f6e74656e742d547970652d4f7074696f6e733a206e6f736e6966660d0a5365742d436f6f6b69653a204e53435f71772d6f686a6f797866632e766f64682e6665762d7366656a736664753d66666666666666663962303530636533343535323564356634663538343535653434356134613432333636303b706174683d2f3b687474706f6e6c790d0a0d0a";
        String tcpFullHeader = (tcpPseudoHeader + tcpHeader + padMessageEnd(data));
        calcCheckSum(tcpFullHeader);

    }


    // convert acknowledgement number to hex 
    public static String toHex(String valueToConvert) {

        long value = Long.parseLong(valueToConvert);
        String valueStr = padMessageStart(Long.toHexString(value));

        return valueStr;

    }

    // pad message with 0s at beginning 
    public static String padMessageStart(String message) {

        int temp = 0;
        int padding = 0;

        String paddedMessage = message;
        temp = message.length() / 4;

        // if message is not a multiple of 0, add padding
        if ((message.length() % 4) != 0) {
            padding = message.length() - (temp * 4);
            for (int i = 0; i < (4 - padding); i++) {
                paddedMessage = "0" + paddedMessage;
            }
        }
        return paddedMessage;
    }

    // pad message with 0s at the end 
    public static String padMessageEnd(String message) {

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

    // convert IP address to hex
    private static String parseIPAddress(String fullData) {
        String[] ip = fullData.split("\\.");

        String convertedIP = "";

        for (int i = 0; i < 4; i++) {

            if (ipParserHelper(ip[i]).length() == 1) {
                convertedIP += "0" + ipParserHelper(ip[i]);
            } else {
                convertedIP += ipParserHelper(ip[i]);
            }
        }
        return convertedIP;

    }

    // helper method for parseIPAddress
    private static String ipParserHelper(String data) {
        return Integer.toHexString(Integer.parseInt(data));
    }

    public static void calcCheckSum(String tcpHeader) {

        //System.out.println(tcpHeader);
        int x, i, checksum = 0;
        int j = 0;
        String hex_value;
        for (i = 0; i < (tcpHeader.length() / 4); i++) {
            hex_value = tcpHeader.substring(j, j + 4);
            System.out.println(hex_value);
            j = j + 4;
            x = Integer.parseInt(hex_value, 16);
            checksum = checksum + x;

        }
        hex_value = Integer.toHexString(checksum);

        // Convert into hexadecimal string
        if (hex_value.length() > 4) {

            System.out.println("\nChecksum in hex before wraparound: " + hex_value);

            int substringVal = (hex_value.length() - 4);

            // If a carry is generated, then we wrap the carry
            int carry = Integer.parseInt(("" + hex_value.substring(0, substringVal)), 16);

            System.out.println("\nCarry Portion: " + Integer.toHexString(carry));

            // Get the value of the carry bit
            hex_value = hex_value.substring(substringVal, hex_value.length());
            System.out.println("\nPortion being added to: " + hex_value);

            // Remove it from the string
            checksum = Integer.parseInt(hex_value, 16);
            // Convert it into an int
            checksum += carry;

            System.out.println("\nChecksum after wrapping carry: " + checksum);

        }

        checksum = Integer.parseInt("FFFF", 16) - checksum;

        String finalChecksum = Integer.toHexString(checksum).toUpperCase();

        System.out.println("\nFinal Checksum after one's complement: " + finalChecksum);

    }

}
