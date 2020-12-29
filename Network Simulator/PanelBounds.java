package Models;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class PanelBounds {

    // values for how large button circles are
    int nodeHeight = 30;
    int nodeWidth = 30;

    // locations of buttons 
    Point transportSendPoint = new Point(85, 290);

    Point transportReceivePoint = new Point(1151, 290);

    Point linkPoint = new Point(626, 560);

    // set size and location of buttons with above values 
    Rectangle transportSendCircleBounds = new Rectangle(transportSendPoint, new Dimension(nodeHeight, nodeWidth));

    Rectangle transportRecCircleBounds = new Rectangle(transportReceivePoint, new Dimension(nodeHeight, nodeWidth));

    Rectangle linkPointBounds = new Rectangle(linkPoint, new Dimension(nodeHeight, nodeWidth));

}
