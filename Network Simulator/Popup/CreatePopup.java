package Popup;

import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class CreatePopup {

    public String bitsRed;
    public String bitsBlue;
    boolean passedFirstTransport = false;

    public void createPopup(JFrame frame, NetworkVariables nwVar, ConversionTools convTools, Ball ball, CircleBounds circleBounds,
            Point mousePointClick, ProcessTransportLayer processTransportLayer, int pointX, int pointY) {

        if (!nwVar.popUpClosed) {

            if (circleBounds.transportSendCircleBounds.contains(mousePointClick)) {
                nwVar.pausePressed = true;

                if ((ball.y > 300) || passedFirstTransport) {

                    // opens transport layer if ball has passed first transport layer
                    processTransportLayer.transport(convTools, "H1", nwVar, nwVar.messageBlue);
                    processTransportLayer.transportFrame(frame, convTools, "H1", nwVar, nwVar.messageBlue);

                } else {
                    JLabel lbl1 = new JLabel("Data packet of the  ball hasen't arrived yet");
                    JOptionPane.showMessageDialog(frame, lbl1, " Ball at H1 Transport Layer",
                            JOptionPane.PLAIN_MESSAGE, null);

                }
                nwVar.pausePressed = false;
            } else if (circleBounds.transportRecCircleBounds.contains(mousePointClick)) {
                nwVar.pausePressed = true;

                if ((ball.x > 1200) && (ball.y < 300)) {

                    passedFirstTransport = true;
                    // opens transport layer if ball has passed second transport layer
                    processTransportLayer.transport(convTools, "H2", nwVar, nwVar.messageBlue);
                    processTransportLayer.transportFrame(frame, convTools, "H2", nwVar, nwVar.messageBlue);

                } else {
                    JLabel lbl1 = new JLabel("Data packet of the ball hasen't arrived yet");
                    JOptionPane.showMessageDialog(frame, lbl1, "Ball at H2 Transport Layer",
                            JOptionPane.PLAIN_MESSAGE, null);

                }
                nwVar.pausePressed = false;
            } else if (circleBounds.linkPointBounds.contains(mousePointClick)) {

                if ((ball.y >= 626) || (ball.x >= 560)) {

                    nwVar.pausePressed = true;

                    // TCP pseudoheader, then header, then message 
                    String fullTCPHeader = nwVar.sourceIP + nwVar.destIP + nwVar.tcpProtocol + nwVar.tcpTotalLengthBlue + 
                            nwVar.sourcePort + nwVar.destPort + nwVar.seqNumBlue + nwVar.ackNumBlue + nwVar.reserved
                            + nwVar.windowSize + nwVar.urgentPointer + nwVar.hexBlue + nwVar.messageBlue;

                    // convert full header to binary 
                    String tcpToBinary = convTools.stringToBinary(fullTCPHeader);

                    // display popup of binary equivalent of TCP header and message
                    JOptionPane.showMessageDialog(frame, String.format(tcpToBinary, 175, 175));

                    nwVar.popUpClosed = true;
                } else {
                    JLabel lbl1 = new JLabel("Data packet of the ball hasen't arrived yet");
                    JOptionPane.showMessageDialog(frame, lbl1, "Ball at link layer",
                            JOptionPane.PLAIN_MESSAGE, null);
                }

            }
            // display initial message on first application layer
            else if (circleBounds.appSendPointBounds.contains(mousePointClick)) {
                JLabel lbl1 = new JLabel("Message: " + nwVar.messageBlue);
                JOptionPane.showMessageDialog(frame, lbl1, "Ball at H1 App Layer",
                        JOptionPane.PLAIN_MESSAGE, null);
            } 
            // display inital message on receiving application layer 
            else if (circleBounds.appRecPointBounds.contains(mousePointClick)) {
                if ((ball.x > 1200) && (ball.y < 300)) {
                    JLabel lbl1 = new JLabel("Message: " + nwVar.messageBlue);
                    JOptionPane.showMessageDialog(frame, lbl1, "Ball at H2 App Layer",
                            JOptionPane.PLAIN_MESSAGE, null);
                } else {
                    JLabel lbl1 = new JLabel("Data packet hasen't arrived yet");
                    JOptionPane.showMessageDialog(frame, lbl1, "Ball at H2 App Layer",
                            JOptionPane.PLAIN_MESSAGE, null);
                }
            } 
            else {
                nwVar.popUpClosed = false;
            }
            nwVar.popUpClosed = false;
        }
    }

}
