/*
*/
import javax.swing.*;
import java.awt.*;

public class PhysicalActivityCoach extends JFrame
{
   // Class Constants

   // GUI Components
   JLabel titleLabel                             = new JLabel ("");
   JLabel exerciseTimeLabel                      = new JLabel ("Exercise Time:");
   JLabel exerciseTimeRemainingLabel             = new JLabel ("Exercise Time Remaining:");
   JLabel nextExercisePeriodLabel                = new JLabel ("Next Exercise Period:");

   JProgressBar exerciseTimeProgressBar          = new JProgressBar ();
   JProgressBar exerciseTimeRemainingProgressBar = new JProgressBar ();
   JProgressBar nextExercisePeriodProgressBar    = new JProgressBar ();

   JButton nowButton                             = new JButton ("Now");
   JButton doneButton                            = new JButton ("Done");
   JButton aboutButton                           = new JButton ("About");
   JButton exitButton                            = new JButton ("Exit");

   // Class data:

   public PhysicalActivityCoach ()
   {
      JPanel gridPanel   = new JPanel (new GridLayout (4, 1)); // R,C
      JPanel buttonPanel = new JPanel (new FlowLayout (FlowLayout.CENTER) );
      JPanel flow01Panel = new JPanel (new FlowLayout (FlowLayout.CENTER) );
      JPanel flow02Panel = new JPanel (new FlowLayout (FlowLayout.CENTER) );
      JPanel flow03Panel = new JPanel (new FlowLayout (FlowLayout.CENTER) );
      JPanel flow04Panel = new JPanel (new FlowLayout (FlowLayout.CENTER) );

      flow01Panel.add (titleLabel);
      flow01Panel.add (new JLabel(""));

      flow02Panel.add (exerciseTimeLabel);
      flow02Panel.add (exerciseTimeProgressBar);

      flow03Panel.add (exerciseTimeRemainingLabel);
      flow03Panel.add (exerciseTimeRemainingProgressBar);

      flow04Panel.add (nextExercisePeriodLabel);
      flow04Panel.add (nextExercisePeriodProgressBar);

      gridPanel.add (flow01Panel);
      gridPanel.add (flow02Panel);
      gridPanel.add (flow03Panel);
      gridPanel.add (flow04Panel);


      buttonPanel.add (nowButton);
      buttonPanel.add (doneButton);
      buttonPanel.add (aboutButton);
      buttonPanel.add (exitButton);

      add (gridPanel,   BorderLayout.CENTER);
      add (buttonPanel, BorderLayout.SOUTH);


      aboutButton.addActionListener (event -> aboutApp() );
      exitButton.addActionListener  (event -> exitApp() );


   }

   private void aboutApp()
   {
      JOptionPane.showMessageDialog (
         PhysicalActivityCoach.this,
         "Physical Activity Coach v0.0001" + "\n" +
         "by Moose");
   }

   private void exitApp()
   {
      System.exit (0); // All OK
   }

   public static void main (String[] args)
   {
      PhysicalActivityCoach myApp = new PhysicalActivityCoach ();

      myApp.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
      myApp.setSize (600, 250);
      myApp.setVisible (true);
   }
}