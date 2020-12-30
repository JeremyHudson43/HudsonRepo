package Transport;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ProcessTransportLayer {

    public void transportFrame(JFrame frame, ConversionTools netUtils, String hostname, NetworkVariables nwVar, String message) {
        String panelHead = "";
        JLabel lbl;

        panelHead = "Ball TCP Segment at ";

        // pass all variables into the drawTransport method and class
        DrawTransportLayer drawTransport = new DrawTransportLayer(nwVar.sourceIP, nwVar.destIP, nwVar.zeroesHeader,
                nwVar.tcpProtocol, nwVar.tcpTotalLengthBlue, nwVar.sourcePort, nwVar.destPort,
                nwVar.seqNumBlue, nwVar.ackNumBlue, nwVar.hLenHeader,
                nwVar.reserved, nwVar.controlHeader, nwVar.windowSize,
                nwVar.checksumTCPBlue, nwVar.urgentPointer, nwVar.TCPDataBlue);

        // set dimensions
        JPanel jpTransportLayer = drawTransport;
        jpTransportLayer.setSize(new Dimension(910, drawTransport.TotalMessageWidth));
        BufferedImage imgTransportLayer = netUtils.createImageFromPanel(jpTransportLayer);
        lbl = new JLabel(new ImageIcon(imgTransportLayer));

        JScrollPane jsp = new JScrollPane(lbl);
        jsp.setPreferredSize(new Dimension(800, 480));
        jsp.setVerticalScrollBarPolicy(22);
        jsp.setHorizontalScrollBarPolicy(31);
        JOptionPane.showMessageDialog(frame, jsp, panelHead + hostname + " Transport Layer",
                -1, null);
        nwVar.popUpClosed = true;
    }

    public void transport(ConversionTools netUtils, String hostname, NetworkVariables nwVar, String message) {

        //data padding for tcp
        String paddedMessage = netUtils.padMessage(message);

        paddedMessage = paddedMessage.toUpperCase();
        System.out.println("The message after padding is " + paddedMessage);

        String data = netUtils.asciiToHex(paddedMessage).toUpperCase();

        nwVar.hexBlue = data;
        nwVar.TCPDataBlue = paddedMessage;

        String tcpHeader = "";
        String tcpPseudoHeader = "";

        tcpPseudoHeader = nwVar.sourceIP + nwVar.destIP + nwVar.tcpProtocol + nwVar.tcpTotalLengthBlue + nwVar.zeroesHeader;

        tcpHeader = nwVar.sourcePort + nwVar.destPort + nwVar.seqNumBlue + nwVar.ackNumBlue + nwVar.reserved
                + nwVar.windowSize + nwVar.urgentPointer + nwVar.hexBlue;
        
        String tcpFullHeader = tcpHeader +  tcpPseudoHeader;

        calcLength(paddedMessage, nwVar);
        calcCheckSum(tcpFullHeader, nwVar, netUtils);

    }

    //calculate TCP length variable
    public void calcLength(String paddedMessage, NetworkVariables nwData) {
        int totalLength = 0;

        totalLength = 18 + paddedMessage.length();
        nwData.tcpTotalLengthBlue = Integer.toHexString(totalLength).toUpperCase();
        if (nwData.tcpTotalLengthBlue.length() != 4) {
            int len = 4 - nwData.tcpTotalLengthBlue.length();
            for (int i = 0; i < len; i++) {
                nwData.tcpTotalLengthBlue = "0" + nwData.tcpTotalLengthBlue;
            }
        }
    }

    public void calcCheckSum(String tcpHeader, NetworkVariables nwVar, ConversionTools netUtils) {

        String result;
        String hexString = "";

        int sum = 0;
        int checksum = 0;
        int decimal = 0;

        // convert hex values of TCP header to bianry
        String hexValue = netUtils.hexToBin(tcpHeader);

        // break binary up into chunks of 16 
        hexValue = netUtils.formatBinary(hexValue);

        //break the new values of 16 into an array
        String[] splitArray = hexValue.split("\n");

        for (int i = 0; i < splitArray.length; i++) {
            
            // treat element of array as binary           
            int number = Integer.parseInt(splitArray[i], 2);

            // add all binary numbers together
            sum += number;

        }

        // convert checksum back to hex 
        result = Integer.toBinaryString(sum);

        // take one's complement of result
        result = netUtils.onesComplemenet(result);

        decimal = Integer.parseInt(result, 2);
        hexString = Integer.toString(decimal, 16);

        // If a carry is generated, wrap it
        if (hexString.length() > 4) {
            // Get the value of carry bit
            int carry = Integer.parseInt(("" + hexString.charAt(0)), 16);
            // Remove it 
            hexString = hexString.substring(1, 5);
            // Convert back to hex
            checksum = Integer.parseInt(hexString, 16);
            // Add to checksum
            checksum += carry;

        }

        //convert to hex string
        nwVar.checksumTCPBlue = Integer.toHexString(checksum).toUpperCase();

        int len = 4 - nwVar.checksumTCPBlue.length();

        // pad with leading 0s
        for (int k = 0; k < len; k++) {
            nwVar.checksumTCPBlue = "0" + nwVar.checksumTCPBlue;
        }

        // construct TCP segment
        nwVar.tcpSegmentBlue = nwVar.sourcePort + nwVar.destPort + nwVar.seqNumBlue + nwVar.ackNumBlue
                + nwVar.reserved + nwVar.windowSize + nwVar.checksumTCPBlue + nwVar.urgentPointer
                + netUtils.asciiToHex(nwVar.TCPDataBlue).toUpperCase();
    }

}
