package Transport;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


import javax.swing.JPanel;

public class DrawTransportLayer extends JPanel {

    String strDrawMessage = "";

    // header values for transport packet
    String sourcePortHeader = "";
    String destPortHeader = "";
    String seqNumHeader = "";
    String ackNumHeader = "";
    String reservedHeader = "";
    String windowSizeHeader = "";
    String checkSumHeader = "";
    String urgentPointerHeader = "";
    String drawMessageHeader = "";
    String zeroesHeader = "";
    String controlHeader = "";
    String hLenHeader = "";

    // pseudo header values for transport packet
    String sourceIPHeader = "";
    String destIPHeader = "";
    String tcpProtocolHeader = "";
    String tcpLengthHeader = "";

    // set subdivision dimensions
    int sourceIPHeaderX = 100;
    int sourceIPHeaderY = 20;

    int destIPX = 100;
    int destIPY = 70;

    int tcpProtocolHeaderX = 100;
    int tcpProtocolHeaderY = 120;

    int tcpLengthX = 240;
    int tcpLengthY = 170;

    int sourcePortX = 100;
    int sourcePortY = 220;

    int destPortX = 240;
    int destPortY = 270;

    int seqNumHeaderX = 100;
    int seqNumHeaderY = 320;

    int ackNumHeaderX = 100;
    int ackNumHeaderY = 370;

    int reservedHeaderX = 100;
    int reservedHeaderY = 320;

    int windowSizeHeaderX = 340;
    int windowSizeHeaderY = 320;

    int checkSumX = 100;

    int urgentPointerHeaderX = 240;

    int sourceIPWidth = 580;

    int destIPWidth = 580;

    int tcpHeaderWidth = 440;
    int tcpLength = 440;

    int sourcePort = 440;
    int destPort = 440;

    int seqNumWidth = 580;

    int ackNumWidth = 580;

    int reservedWidth = 540;
    int windowSizeWidth = 340;

    int checksumWidth = 440;
    int urgentPointer = 440;

    int MessageHeight = 800;

    int MessageX = 100;
    int MessageY = 420;
    int MessageWidth = 580;

    int TotalMessageWidth = 1400;
    int MsgHeight = 50;

    ConversionTools netUtils = new ConversionTools();
    String paddedMessage;

    // constructor 
    public DrawTransportLayer(String pseudoSourceIP, String pseudoDestIP, String zeroesHeader, String psuedoTcpProtocol, String psuedoTCPLength,
            String headerSourcePort, String headerDestPort,
            String headerSeqNum,
            String headerAckNum,
            String hLenHeader,
            String headerReserved, String controlHeader, String headerWindowSize,
            String headerCheckSum, String headerUrgentPointer,
            String message) {
        this.sourceIPHeader = "Source IP: " + pseudoSourceIP;
        this.destIPHeader = "Dest IP: " + pseudoDestIP;
        this.zeroesHeader = "Zeroes: " + zeroesHeader;
        this.controlHeader = "Control: " + controlHeader;
        this.hLenHeader = "hLen: " + hLenHeader;
        this.tcpProtocolHeader = "Protocol:  " + psuedoTcpProtocol;
        this.tcpLengthHeader = "TCP Length: " + psuedoTCPLength;
        this.sourcePortHeader = "Source Port: " + headerSourcePort;
        this.destPortHeader = "Dest Port: " + headerDestPort;
        this.seqNumHeader = "Seq Num: " + headerSeqNum;
        this.ackNumHeader = "Ack Num: " + headerAckNum;
        this.reservedHeader = "Reserved: " + headerReserved;
        this.windowSizeHeader = "Window Size: " + headerWindowSize;
        this.checkSumHeader = "Checksum: " + headerCheckSum;
        this.urgentPointerHeader = "Urgent Pointer: " + headerUrgentPointer;
        this.drawMessageHeader = message;

        this.setOpaque(true);
        this.setBackground(Color.WHITE);

        paddedMessage = netUtils.padMessage(message);

    }

    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        // create rectangle objects for TCP graphics 
        graphics.setColor(Color.BLUE);
        graphics.fillRect(sourceIPHeaderX, sourceIPHeaderY, sourceIPWidth, MsgHeight);
        graphics.fillRect(destIPX, destIPY, destIPWidth, MsgHeight);

        graphics.fillRect(tcpProtocolHeaderX, tcpProtocolHeaderY, tcpHeaderWidth, MsgHeight);
        graphics.fillRect(tcpLengthX, tcpProtocolHeaderY, tcpLength, MsgHeight);

        graphics.setColor(Color.WHITE);
        graphics.drawRect(sourceIPHeaderX, sourceIPHeaderY, sourceIPWidth, MsgHeight);
        graphics.drawRect(destIPX, destIPY, destIPWidth, MsgHeight);
        graphics.drawRect(tcpProtocolHeaderX, tcpProtocolHeaderY, tcpHeaderWidth - 300, MsgHeight);
        graphics.drawRect(tcpProtocolHeaderX, tcpProtocolHeaderY, tcpHeaderWidth - 160, MsgHeight);
        graphics.setColor(Color.BLACK);

        graphics.drawRect(sourcePortX, tcpLengthY, sourcePort - 160, MsgHeight);
        graphics.drawRect(sourceIPHeaderX, tcpLengthY, destPort + 140, MsgHeight);
        graphics.drawRect(seqNumHeaderX, sourcePortY, seqNumWidth, MsgHeight);
        graphics.drawRect(ackNumHeaderX, destPortY, ackNumWidth, MsgHeight);
        graphics.drawRect(reservedHeaderX, reservedHeaderY, reservedWidth - 400, MsgHeight);
        graphics.drawRect(reservedHeaderX + 140, reservedHeaderY, reservedWidth - 400, MsgHeight);
        graphics.drawRect(reservedHeaderX + 280, reservedHeaderY, reservedWidth - 400, MsgHeight);
        graphics.drawRect(reservedHeaderX + 420, reservedHeaderY, reservedWidth - 380, MsgHeight);

        graphics.drawRect(sourcePortX, ackNumHeaderY, checksumWidth - 160, MsgHeight);
        graphics.drawRect(urgentPointerHeaderX - 140, ackNumHeaderY, urgentPointer + 140, MsgHeight);
        graphics.drawRect(MessageX, MessageY, MessageWidth, MsgHeight);
        graphics.setFont(new Font("Calibri", Font.PLAIN, 15));
        graphics.setColor(Color.WHITE);

        // TCP psuedoheader values
        graphics.drawString(sourceIPHeader, sourceIPHeaderX + 200, (MsgHeight + sourceIPHeaderY * 2 + 10) / 2);
        graphics.drawString(destIPHeader, destIPX + 200, (MsgHeight + destIPY * 2 + 10) / 2);
        graphics.drawString(tcpProtocolHeader, tcpProtocolHeaderX + 150, (MsgHeight + tcpProtocolHeaderY * 2 + 10) / 2);
        graphics.drawString(tcpLengthHeader, tcpLengthX + 220, (MsgHeight + tcpProtocolHeaderY * 2 + 10) / 2);
        graphics.drawString(zeroesHeader, tcpLengthX - 120, (MsgHeight + tcpProtocolHeaderY * 2 + 10) / 2);

        graphics.setColor(Color.BLACK);

        // TCP header values 
        graphics.drawString(sourcePortHeader, sourcePortX + 80, (MsgHeight + tcpLengthY * 2 + 10) / 2);
        graphics.drawString(destPortHeader, destPortX + 220, (MsgHeight + tcpLengthY * 2 + 10) / 2);
        graphics.drawString(seqNumHeader, seqNumHeaderX + 200, (MsgHeight + sourcePortY * 2 + 10) / 2);
        graphics.drawString(ackNumHeader, ackNumHeaderX + 200, (MsgHeight + destPortY * 2 + 10) / 2);
        graphics.drawString(reservedHeader, reservedHeaderX + 160, (MsgHeight + seqNumHeaderY * 2 + 10) / 2);
        graphics.drawString(windowSizeHeader, windowSizeHeaderX + 200, (MsgHeight + seqNumHeaderY * 2 + 10) / 2);
        graphics.drawString(hLenHeader, windowSizeHeaderX - 220, (MsgHeight + seqNumHeaderY * 2 + 10) / 2);
        graphics.drawString(controlHeader, windowSizeHeaderX + 60, (MsgHeight + seqNumHeaderY * 2 + 10) / 2);
        graphics.drawString(checkSumHeader, checkSumX + 80, (MsgHeight + ackNumHeaderY * 2 + 10) / 2);
        graphics.drawString(urgentPointerHeader, urgentPointerHeaderX + 200, (MsgHeight + ackNumHeaderY * 2 + 10) / 2);

        if (paddedMessage.length() > 85) {
            graphics.setFont(new Font("Calibri", Font.PLAIN, 11));
        } else if (paddedMessage.length() > 50) {
            graphics.setFont(new Font("Calibri", Font.PLAIN, 14));
        }
        graphics.drawString("Message: " + paddedMessage, MessageX + 10, (50 + (MessageY) * 2 + 10) / 2);

    }

}
