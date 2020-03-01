import javax.swing.Timer;
import java.util.Scanner;

public class TimerExplore
{
   static Timer timer1 = new Timer(1000, event -> displayDot() );
   static Timer timer2 = new Timer(1000, event -> halveDelayOfTimer1() );

   private static void halveDelayOfTimer1()
   {
      timer1.setDelay (timer1.getDelay() / 2);
   }

   private static void displayDot()
   {
      System.out.print (".");
   }

   public static void main (String[] args)
   {
      timer1.start();
      timer2.start();

      Scanner kb = new Scanner (System.in);
      kb.nextLine();

   }
}