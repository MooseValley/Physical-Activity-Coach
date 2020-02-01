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

   JProgressBar exerciseTimerProgressBar              = new JProgressBar ();
   JProgressBar exercisingTimerProgressBar            = new JProgressBar ();
   JProgressBar exercisePeriodDelayTimerProgressBar   = new JProgressBar ();

   JButton nowButton                             = new JButton ("Now");
   JButton doneButton                            = new JButton ("Done");
   JButton aboutButton                           = new JButton ("About");
   JButton exitButton                            = new JButton ("Exit");


   Timer exercisePeriodDelayTimer                = new Timer (20_000,
                                                   event -> startExercisePeriod () );
   Timer exercisingTimer                         = new Timer (9_000,
                                                   event -> doExercises () );
   Timer exerciseTimer                           = new Timer (3_000,
                                                   event -> doAnExercise () );
   Timer secondsCountdownTimer                   = new Timer (1_000,
                                                   event -> refreshGUI () );



   // Class data:

   private int exerciseCount;


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
      flow02Panel.add (exerciseTimerProgressBar);

      flow03Panel.add (exerciseTimeRemainingLabel);
      flow03Panel.add (exercisingTimerProgressBar);

      flow04Panel.add (nextExercisePeriodLabel);
      flow04Panel.add (exercisePeriodDelayTimerProgressBar);

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


      exerciseTimerProgressBar.setMinimum (0);
      exerciseTimerProgressBar.setMaximum (exerciseTimer.getDelay() / 1000);
      exerciseTimerProgressBar.setValue   (exerciseTimerProgressBar.getMaximum() );

      exercisingTimerProgressBar.setMinimum (0);
      exercisingTimerProgressBar.setMaximum (exercisingTimer.getDelay() / 1000);
      exercisingTimerProgressBar.setValue   (exercisingTimerProgressBar.getMaximum() );

      exercisePeriodDelayTimerProgressBar.setMinimum (0);
      exercisePeriodDelayTimerProgressBar.setMaximum (exercisePeriodDelayTimer.getDelay() / 1000);
      exercisePeriodDelayTimerProgressBar.setValue   (exercisePeriodDelayTimerProgressBar.getMaximum() );

      exercisePeriodDelayTimer.start();
      secondsCountdownTimer.start();

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

   private void startExercisePeriod ()
   {
      // A new exercise period is starting ...
      // We want to start the Exercises Timer

      exerciseCount = 0;

      exercisingTimer.start();
      exerciseTimer.start();

      exercisePeriodDelayTimer.stop();
      exercisePeriodDelayTimerProgressBar.setValue (exercisePeriodDelayTimerProgressBar.getMaximum() );
   }

   private void doExercises ()
   {
      // When this timers fires, it's the END of the exercise period.

      exercisingTimer.stop();
      exerciseTimer.stop();

      exerciseTimerProgressBar.setValue   (exerciseTimerProgressBar.getMaximum() );
      exercisingTimerProgressBar.setValue (exercisingTimerProgressBar.getMaximum() );

      exercisePeriodDelayTimer.start();
   }

   private void doAnExercise ()
   {
      // Select a random exercise
      // Display in in a label

      exerciseCount++;
      titleLabel.setText ("Exercise #" + exerciseCount + " ...");

      exerciseTimerProgressBar.setValue   (exerciseTimerProgressBar.getMaximum() );
   }

   private void refreshGUI ()
   {
      // Update timer progress bars.
      int val = 0;

      if (exercisePeriodDelayTimer.isRunning() == true)
      {
         val = exercisePeriodDelayTimerProgressBar.getValue ();
         val--;
         exercisePeriodDelayTimerProgressBar.setValue (val);
      }

      if (exercisingTimer.isRunning() == true)
      {
         val = exerciseTimerProgressBar.getValue ();
         val--;
         exerciseTimerProgressBar.setValue (val);
      }

      if (exerciseTimer.isRunning() == true)
      {
         val = exercisingTimerProgressBar.getValue ();
         val--;
         exercisingTimerProgressBar.setValue (val);
      }
   }

   public static void main (String[] args)
   {
      PhysicalActivityCoach myApp = new PhysicalActivityCoach ();

      myApp.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
      myApp.setSize (600, 250);
      myApp.setVisible (true);
   }
}