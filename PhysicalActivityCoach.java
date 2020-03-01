/*
 Author: Mike O'Malley
 Source File: PhysicalActivityCoach.java
 Description: See "README.MD".

Ammendment History
Ver   Date        Author    Details
----- ----------- --------- ----------------------------------------------------
0.004 23-Feb-2020 Mike O    Add comment header for file.
                            Add TextArea and File Input.
                            Activate the Now and Done buttons.
                            Random Exercises displayed.
0.005 01-Mar-2020 Mike O    Add Title in title bar,
                            Add TABs,
                            Display a TextArea for input / editing of exercices,
                            Add SpinEdits for controlling the timers.

*/
import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.io.FileReader;
import java.security.SecureRandom;


public class PhysicalActivityCoach extends JFrame
{
   // Class Constants
   public static final String APPLICATION_VERSION = "v0.005";
   public static final String APPLICATION_NAME    = "Physical Activity Coach";


   // GUI Components
   JTabbedPane tabbedPane                        = new JTabbedPane ();

   JLabel titleLabel                             = new JLabel ("");
   JLabel exerciseTimeLabel                      = new JLabel ("Exercise Time:");
   JLabel exerciseTimeRemainingLabel             = new JLabel ("Exercise Time Remaining:");
   JLabel nextExercisePeriodLabel                = new JLabel ("Next Exercise Period:");
   JTextArea exercisesTextArea                   = new JTextArea ();

   JProgressBar exerciseTimerProgressBar              = new JProgressBar ();
   JProgressBar exercisingTimerProgressBar            = new JProgressBar ();
   JProgressBar exercisePeriodDelayTimerProgressBar   = new JProgressBar ();

   JSpinner secondsPerExerciseSpinner            = new JSpinner ();
   JSpinner secondsPerExercisePeriodSpinner      = new JSpinner ();
   JSpinner secondsBetweenExercisePeriodsSpinner = new JSpinner ();

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
   private SecureRandom generator = new SecureRandom ();


   public PhysicalActivityCoach ()
   {

      setTitle (APPLICATION_NAME + " - " + APPLICATION_VERSION);


      tabbedPane.addTab ("Exercises", null, createExercisesPanel (), "Exercises display");
      tabbedPane.addTab ("Settings",  null, createSettingsPanel (),  "Program Settings");

      setLayout (new BorderLayout());

      add (tabbedPane,              BorderLayout.CENTER);
      add (createButtonsPanel (),   BorderLayout.SOUTH);


      exerciseTimerProgressBar.setMinimum (0);
      exercisingTimerProgressBar.setMinimum (0);
      exercisePeriodDelayTimerProgressBar.setMinimum (0);

      secondsPerExerciseSpinnerChanged();
      secondsPerExercisePeriodSpinnerChanged();
      secondsBetweenExercisePeriodsSpinnerChanged();


      // Load Exercises from File:
      try
      {
         Scanner fileIn = new Scanner (new FileReader ("exercises_list.txt") );

         exercisesTextArea.setText ("");

         while (fileIn.hasNext() == true)
         {
            String lineStr = fileIn.nextLine().trim();

            if (lineStr.length() > 0)
               exercisesTextArea.append (lineStr + "\n");
         }
      }
      catch (Exception err)
      {
         err.printStackTrace();
      }

      exercisePeriodDelayTimer.start();
      secondsCountdownTimer.start();

      doExercises ();
   }

   private JPanel createExercisesPanel ()
   {
      JPanel thePanel    = new JPanel (new BorderLayout ());
      JPanel gridPanel   = new JPanel (new GridLayout (4, 1)); // R,C
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


      thePanel.add (gridPanel,   BorderLayout.CENTER);

      return thePanel;
   }

   private JPanel createSettingsPanel ()
   {
      JPanel thePanel     = new JPanel (new BorderLayout ());
      JPanel spinnerPanel = new JPanel (new FlowLayout (FlowLayout.CENTER));

                                                                          // val,      Min,     Max,    Step
      secondsPerExerciseSpinner.setModel            (new SpinnerNumberModel (15,      15,      60,       5) );
      secondsPerExercisePeriodSpinner.setModel      (new SpinnerNumberModel (60,      60,      5 * 60,  15) );
      secondsBetweenExercisePeriodsSpinner.setModel (new SpinnerNumberModel (60 * 60, 10 * 60, 60 * 60, 60) );


      spinnerPanel.add (new JLabel ("Sec / Exercise: ") );
      spinnerPanel.add (secondsPerExerciseSpinner);

      spinnerPanel.add (new JLabel ("    Sec / Exercise Period: ") );
      spinnerPanel.add (secondsPerExercisePeriodSpinner);

      spinnerPanel.add (new JLabel ("    Sec between Exercise Periods: ") );
      spinnerPanel.add (secondsBetweenExercisePeriodsSpinner);

      thePanel.add (exercisesTextArea, BorderLayout.CENTER);
      thePanel.add (spinnerPanel,      BorderLayout.SOUTH);

      secondsPerExerciseSpinner.addChangeListener
          (event -> secondsPerExerciseSpinnerChanged() );
      secondsPerExercisePeriodSpinner.addChangeListener
          (event -> secondsPerExercisePeriodSpinnerChanged() );
      secondsBetweenExercisePeriodsSpinner.addChangeListener
          (event -> secondsBetweenExercisePeriodsSpinnerChanged() );

      secondsPerExercisePeriodSpinner.setModel      (new SpinnerNumberModel (60,      60,      5 * 60,  15) );
      secondsBetweenExercisePeriodsSpinner.setModel (new SpinnerNumberModel (60 * 60, 10 * 60, 60 * 60, 60) );

      return thePanel;
   }

   private JPanel createButtonsPanel ()
   {
      JPanel buttonPanel = new JPanel (new FlowLayout (FlowLayout.CENTER) );

      buttonPanel.add (nowButton);
      buttonPanel.add (doneButton);
      buttonPanel.add (aboutButton);
      buttonPanel.add (exitButton);

      nowButton.addActionListener   (event -> startExercisePeriod () );
      doneButton.addActionListener  (event -> doExercises () );
      aboutButton.addActionListener (event -> aboutApp() );
      exitButton.addActionListener  (event -> exitApp() );

      return buttonPanel;
   }

   private void secondsPerExerciseSpinnerChanged()
   {
      exerciseTimer.setDelay ( (int) secondsPerExerciseSpinner.getValue() * 1000);

      exerciseTimerProgressBar.setMaximum (exerciseTimer.getDelay() / 1000);
      exerciseTimerProgressBar.setValue   (exerciseTimerProgressBar.getMaximum() );
   }

   private void secondsPerExercisePeriodSpinnerChanged()
   {
      //System.out.println ("secondsPerExercisePeriodSpinnerChanged()");
      //System.out.println (" -> secs: " + (int) secondsPerExercisePeriodSpinner.getValue() );
      exercisingTimer.setDelay ( (int) secondsPerExercisePeriodSpinner.getValue() * 1000);

      exercisingTimerProgressBar.setMaximum (exercisingTimer.getDelay() / 1000);
      exercisingTimerProgressBar.setValue   (exercisingTimerProgressBar.getMaximum() );
   }

   private void secondsBetweenExercisePeriodsSpinnerChanged()
   {
      exercisePeriodDelayTimer.setDelay ( (int) secondsBetweenExercisePeriodsSpinner.getValue() * 1000);

      exercisePeriodDelayTimerProgressBar.setMaximum (exercisePeriodDelayTimer.getDelay() / 1000);
      exercisePeriodDelayTimerProgressBar.setValue   (exercisePeriodDelayTimerProgressBar.getMaximum() );
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


   private String getRandomExercise ()
   {
      // Count exercises in TextArea
      // Select and return a random exercise

      String[] exercises = exercisesTextArea.getText().split ("\n");
      int randVal = generator.nextInt (exercises.length); // 0 <= x < array size

      return exercises[randVal];
   }


   private void startExercisePeriod ()
   {
      // A new exercise period is starting ...
      // We want to start the Exercises Timer

      exerciseCount = 0;

      System.out.println (" -> millisecs: " + exercisingTimer.getDelay() );
      exercisingTimer.start();
      exerciseTimer.start();
      doAnExercise ();

      exercisePeriodDelayTimer.stop();
      exercisePeriodDelayTimerProgressBar.setValue (exercisePeriodDelayTimerProgressBar.getMaximum() );

      nowButton.setEnabled  (false);
      doneButton.setEnabled (true);
   }

   private void doExercises ()
   {
      // When this timers fires, it's the END of the exercise period.

      exercisingTimer.stop();
      exerciseTimer.stop();

      exerciseTimerProgressBar.setValue   (exerciseTimerProgressBar.getMaximum() );
      exercisingTimerProgressBar.setValue (exercisingTimerProgressBar.getMaximum() );

      exercisePeriodDelayTimer.start();

      nowButton.setEnabled  (true);
      doneButton.setEnabled (false);

      titleLabel.setText ("Relax: it's not an exercise period." );
   }

   private void doAnExercise ()
   {
      // Select a random exercise
      // Display in in a label

      exerciseCount++;
      //titleLabel.setText ("Exercise #" + exerciseCount + " ...");
      titleLabel.setText (getRandomExercise () );

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
         val = exercisingTimerProgressBar.getValue ();
         val--;
         exercisingTimerProgressBar.setValue (val);
      }

      if (exerciseTimer.isRunning() == true)
      {
         val = exerciseTimerProgressBar.getValue ();
         val--;
         exerciseTimerProgressBar.setValue (val);
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