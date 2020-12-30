package Popup;

import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class PopupMessage {

    public String bitsRed;
    public String bitsBlue;
    boolean passedFirstTransport = false;

    public void openPopupMessage(JFrame frame, NetworkData nwData, NetworkUtils netUtils, Ball ball, PanelBounds panelPopup,
            Point mousePointClick, ProcessTransportLayer processTransportLayer, int pointX, int pointY) {

        if (!nwData.popUpClosed) {

            if (panelPopup.transportSendCircleBounds.contains(mousePointClick)) {
                nwData.pausePressed = true;

                if ((ball.y > 300) || passedFirstTransport) {

                    processTransportLayer.transport(netUtils, "H1", nwData, nwData.messageBlue);
                    processTransportLayer.transportFrame(frame, netUtils, "H1", nwData, nwData.messageBlue);

                } else {
                    JLabel lbl1 = new JLabel("Data packet of the blue ball hasen't arrived yet");
                    JOptionPane.showMessageDialog(frame, lbl1, "Blue Ball at H1 Transport Layer",
                            JOptionPane.PLAIN_MESSAGE, null);

                }
                nwData.pausePressed = false;
            } else if (panelPopup.transportRecCircleBounds.contains(mousePointClick)) {
                nwData.pausePressed = true;

                if ((ball.x > 1200) && (ball.y < 300)) {
                    
                    passedFirstTransport = true;

                    processTransportLayer.transport(netUtils, "H2", nwData, nwData.messageBlue);
                    processTransportLayer.transportFrame(frame, netUtils, "H2", nwData, nwData.messageBlue);

                } else {
                    JLabel lbl1 = new JLabel("Data packet of the blue ball hasen't arrived yet");
                    JOptionPane.showMessageDialog(frame, lbl1, "Blue Ball at H2 Transport Layer",
                            JOptionPane.PLAIN_MESSAGE, null);

                }
                nwData.pausePressed = false;
            } else if (panelPopup.linkPointBounds.contains(mousePointClick)) {

                if ((ball.y >= 626) || (ball.x >= 560)) {

                    nwData.pausePressed = true;

                    String pseudoHeader = netUtils.sourceIP + netUtils.destIP + netUtils.tcpProtocol + nwData.tcpTotalLengthBlue;

                    String tcpHeader = netUtils.sourcePort + netUtils.destPort + netUtils.seqNumBlue + netUtils.ackNumBlue + netUtils.reserved
                            + netUtils.windowSize + netUtils.urgentPointer + nwData.hexBlue;

                    String tcpToBinary = netUtils.stringToBinary(pseudoHeader + tcpHeader);
                    String messageToBinary = netUtils.stringToBinary(nwData.messageBlue);
                    String tcpSegment = (tcpToBinary + "\n" + messageToBinary);

                    System.out.println(tcpSegment);

                    JOptionPane.showMessageDialog(frame, String.format(tcpSegment, 175, 175));

                    nwData.popUpClosed = true;
                } else {
                    JLabel lbl1 = new JLabel("Data packet of the blue ball hasen't arrived yet");
                    JOptionPane.showMessageDialog(frame, lbl1, "Blue Ball at link layer",
                            JOptionPane.PLAIN_MESSAGE, null);
                }

            } else {
                nwData.popUpClosed = false;
            }
            nwData.popUpClosed = false;
        }
    }

}
