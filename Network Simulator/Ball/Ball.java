package Models;

import java.awt.*;

public class Ball {

    public int x, y, area;
    private Color ballColor;

    // constructor for ball object
    public Ball(int locationX, int locationY, Color ballColor, int ballArea) {
        x = locationX;
        y = locationY;
        this.ballColor = ballColor;
        area = ballArea;
    }

    public void moveDown() {
        int speedY = 3;
        y += speedY;
    }

    public void moveUp() {
        int speedY = 3;
        y -= speedY;

    }

    public void moveLeftToRight() {
        int speedX = 3;
        x += speedX;
    }

    // fill ball with color passed in 
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(ballColor);
        g2d.fillOval(x, y, area, area);
    }

}
