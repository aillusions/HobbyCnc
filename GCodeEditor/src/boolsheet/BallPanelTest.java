package boolsheet;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.Timer;

public class BallPanelTest
{
  private static final int WIDTH = 400;
  private static final Dimension BALL_PANEL_SIZE = new Dimension(WIDTH, WIDTH);
  private static final int DIAMETER = 20;
  private static final int DELTA = 2;
  private static final int SLEEP_TIME = 20;
  private static final String THREAD_SLEEP = "Thread Sleep";
  private static final String SWING_TIMER = "Swing Timer";
  private static final String RESET = "Reset";
  private static final String[] BUTTON_STRINGS =
  {
    THREAD_SLEEP, SWING_TIMER, RESET
  };
  private JPanel mainPanel = new JPanel();
  private BallPanel ballPanel = new BallPanel(BALL_PANEL_SIZE, DIAMETER, DELTA);

  public BallPanelTest()
  {
    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    btnPanel.setBorder(BorderFactory.createEtchedBorder());
    ButtonListener btnListener = new ButtonListener();
    for (String btnString : BUTTON_STRINGS)
    {
      JButton btn = new JButton(btnString);
      btn.addActionListener(btnListener);
      btnPanel.add(btn);
    }
    
    int blGap = 5;
    mainPanel.setLayout(new BorderLayout(blGap, blGap));
    mainPanel.add(ballPanel.getComponent(), BorderLayout.CENTER);
    mainPanel.add(btnPanel, BorderLayout.SOUTH);
  }

  public JComponent getComponent()
  {
    return mainPanel;
  }
  
  private class ButtonListener implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      ballPanel.resetBall();
      String command = e.getActionCommand();
      if (command.equals(RESET))
      {
        ballPanel.resetBall();
      }
      else if (command.equals(THREAD_SLEEP))
      {
        for (int count = 0; count < WIDTH/DELTA - 10; count++)
        {
          ballPanel.moveBall();
          try
          {
            Thread.sleep(SLEEP_TIME);
          } catch (InterruptedException e1) {}
        }
      }
      else if (command.equals(SWING_TIMER))
      {
        new Timer(SLEEP_TIME, new TimerListener()).start();
      }
    }
  }
  
  private class TimerListener implements ActionListener
  {
    int count = 0;
    public void actionPerformed(ActionEvent e)
    {
      if (count < WIDTH/DELTA - 10)
      {
        ballPanel.moveBall();
        count++;
      }
      else
      {
        ((Timer)e.getSource()).stop();
      }
    }
  }

  private static void createAndShowUI()
  {
    JFrame frame = new JFrame("MoveBallMain");
    frame.getContentPane().add(new BallPanelTest().getComponent());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  public static void main(String[] args)
  {
    java.awt.EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        createAndShowUI();
      }
    });
  }
}

