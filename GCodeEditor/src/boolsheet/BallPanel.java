package boolsheet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JPanel;
import javax.swing.JComponent;

public class BallPanel
{
  private static final Color BALL_COLOR = Color.red;
  private JPanel mainPanel = new JPanel()
  {
    protected void paintComponent(Graphics g)
    {
      super.paintComponent(g);
      myPaint(g);
    }
  };
  private Point ballPoint = new Point(0, 0);
  private int diameter;
  private int delta;

  public BallPanel(Dimension preferredSize, int diameter, int delta)
  {
    mainPanel.setPreferredSize(preferredSize);
    this.diameter = diameter;
    this.delta = delta;
  }
  
  private void myPaint(Graphics g)
  {
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(
        RenderingHints.KEY_ANTIALIASING, 
        RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setColor(BALL_COLOR);
    g2.fillOval(ballPoint.x, ballPoint.y, diameter, diameter);
  }
  
  public void moveBall()
  {
    ballPoint = new Point(ballPoint.x + delta, ballPoint.y + delta);
    mainPanel.repaint();
  }
  
  public void resetBall()
  {
    ballPoint = new Point(0, 0);
    mainPanel.repaint();
  }
  
  public JComponent getComponent()
  {
    return mainPanel;
  }
}