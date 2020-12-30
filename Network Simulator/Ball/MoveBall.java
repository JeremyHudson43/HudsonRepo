package Ball;

public class MoveBall {

    public void moveBall(Ball colorBall, NetworkVariables nwData) {

        if (!nwData.pausePressed) {
            
            // move down the senders set of layers
            if (colorBall.x <= 15 && colorBall.y < 570) { 
                colorBall.moveDown();
            } 
            
            // move across the link to the receiver from left to right
            else if (colorBall.x <= 1200 && colorBall.y < 600) { 
                colorBall.moveLeftToRight();
            } 
            
            // move up the receiving set of layers
            else if (colorBall.x <= 1210 && colorBall.y > 55) {
                colorBall.moveUp();
            }

        }
    }
}
