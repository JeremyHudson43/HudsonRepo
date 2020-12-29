package Models;

import static Models.Simulator.frame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class InputAndInit extends Simulator
        implements MouseListener {

    JPanel panel;
    
    // set ball initial coordinates
    int blueBallX = 15;
    int blueBallY = 30;
    
    // create buttons
    JButton pause = new JButton("Pause");
    JButton resume = new JButton("Resume");
    JButton exit = new JButton("Exit");
    
    // create ball object
    public Ball ball;
    
    // declare relevant class objects
    NetworkVariables networkVar;
    Timer timer;
    CircleBounds circleBounds;
    ConversionTools convTools;
    CreatePopup createPopup;
    ProcessTransportLayer processTransportLayer;
    MoveBall moveBall;

    // constructor 
    public InputAndInit(JPanel panel, JButton pauseButton, JButton resumeButton, JButton exitButton) {
        try {
            circleBounds = new CircleBounds();
            
            this.convTools = new ConversionTools();
            this.networkVar = new NetworkVariables();
            this.createPopup = new CreatePopup();
            this.processTransportLayer = new ProcessTransportLayer();
            
            getUserInput();
            
            this.ball = new Ball(this.blueBallX, this.blueBallY, Color.BLUE, 25);
            this.moveBall = new MoveBall();
            
        } catch (Exception ex) {
            Logger.getLogger(Simulator.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.pause = pauseButton;
        this.resume = resumeButton;
        this.exit = exitButton;
        
        this.panel = panel;
      
        this.panel.addMouseListener(this);
        this.pause.addMouseListener(this);
        this.resume.addMouseListener(this);
        this.exit.addMouseListener(this);
    }

    public void drawBlueBubble(Graphics g) {
        this.ball.paint(g);
    }

    public void moveBall() {

        this.moveBall.moveBall(this.ball, this.networkVar);

    }

    // handles pause, resume and exit button clicks
    public void mouseClicked(MouseEvent click) {
        if (click.getSource() == this.pause) {
            this.networkVar.pausePressed = true;
        }

        if (click.getSource() == this.resume) {
            this.networkVar.pausePressed = false;
        }

        if (click.getSource() == this.exit) {
            System.exit(0);
        }
        Point mousePointClick = click.getPoint();
        System.out.println("X = " + click.getX() + " Y = " + click.getY());
        openPopupMessage(mousePointClick, click.getX(), click.getY());

    }
    
    private void openPopupMessage(Point mousePointClick, int x, int y) {
        createPopup.createPopup(frame, networkVar, convTools, ball, circleBounds, mousePointClick, processTransportLayer, x, y);

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    // initial user text entry 
    private void getUserInput() {
        JTextField textfieldBlue = new JTextField(30);
        JOptionPane.showMessageDialog(this, textfieldBlue, "Input Message ",
                -1, null);
        System.out.println("Input text : " + textfieldBlue.getText().toUpperCase());
        this.networkVar.messageBlue = textfieldBlue.getText().toUpperCase();

        if (this.networkVar.messageBlue.length() < 1) {
            JOptionPane.showMessageDialog(null, "Enter at least one character to start the simulation", "NOTICE", 1);
            System.exit(0);
        }

        if (this.networkVar.messageBlue.length() > 92) {
            JOptionPane.showMessageDialog(null, "Please enter fewer than 92 characters", "NOTICE", 1);
            System.exit(0);
        }
    }

}
