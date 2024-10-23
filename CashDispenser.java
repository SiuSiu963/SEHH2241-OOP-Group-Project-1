// CashDispenser.java
// Represents the cash dispenser of the ATM

public class CashDispenser 
{

   private final static int INITIAL_COUNT = 500;
   private int count100; 
   private int count500; 
   private int count1000; 

 
   public CashDispenser()
   {
      count100 = INITIAL_COUNT; 
      count500 = INITIAL_COUNT; 
      count1000 = INITIAL_COUNT; 
   } 


   public void dispenseCash(int amount)
   {
      if (amount % 100 != 0) {
         throw new IllegalArgumentException("Amount must be a multiple of HKD 100.");
      }

      int bills1000 = amount / 1000; 
      amount %= 1000;

      int bills500 = amount / 500;
      amount %= 500;

      int bills100 = amount / 100; 

      count1000 -= bills1000;
      count500 -= bills500;
      count100 -= bills100;
   } 

   public boolean isSufficientCashAvailable(int amount)
   {
      if (amount % 100 != 0) {
         return false;
      }

      int bills1000 = amount / 1000;
      amount %= 1000;

      int bills500= amount / 500;
      amount %= 500;

      int bills100 = amount / 100;


      if (count1000 >= bills1000 &&
          count500 >= bills500&&
          count100 >= bills100) {
         return true; 
      } else {
         return false; 
      }
   } 
}


/**************************************************************************
 * (C) Copyright 1992-2007 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/