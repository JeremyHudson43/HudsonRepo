package Models;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Simulator extends JPanel {

    BufferedImage background;
    InputAndInit network;

    // create new button objects
    final JButton pause = new JButton("Pause");
    final JButton resume = new JButton("Resume");
    final JButton exit = new JButton("Exit");

    static JFrame frame = new JFrame();

    // constructor
    public Simulator() {
        try {
            background = ImageIO.read(this.getClass().getClassLoader().getResource("Network_Layers.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(Simulator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initiateSimulator() {

        // adds buttons to simulator GUI
        this.add(pause);
        this.add(resume);
        this.add(exit);
        network = new InputAndInit(this, pause, resume, exit);

        // initiates simulator and moves ball
        network.timer = new Timer(40, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                network.moveBall();
                repaint();
            }
        });
        network.timer.start();

    }

    @Override

    // adds the Network_Layers.jpg background 
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
        network.drawBlueBubble(graphics);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1300, 698);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                Simulator simulator = new Simulator();
                simulator.initiateSimulator();

                frame.add(simulator);

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
