package Models;

public class NetworkVariables {

    // variables that represent network values
    public boolean popUpClosed = false;
    public boolean pausePressed = false;
    public String hexBlue = "";
    public String checksumBlue = "";
    public String checksumTCPBlue = "";
    public String tcpTotalLengthBlue = "";
    public String TCPDataBlue = "";
    public String messageBlue = "";
    public String tcpSegmentBlue = "";

    // IP addresses
    public String sourceIP = "A1B2C3D";
    public String destIP = "D3C2B1A";

    // TCP Segment fields
    public String sourcePort = "01BB";
    public String destPort = "D49D";
    public String seqNumBlue = "DB4CC2BA";
    public String ackNumBlue = "DB4CC2BF";
    public String reserved = "000";
    public String windowSize = "0016";
    public String urgentPointer = "0000";
    public String tcpProtocol = "00000006";
    public String zeroesHeader = "00000000";
    public String controlHeader = "ACK = 1";
    public String hLenHeader = "0101";

}
