// Deposit.java
public class Deposit extends Transaction
{
   private double amount; // amount to deposit
   private Keypad keypad; // reference to keypad
   private DepositSlot depositSlot; // reference to deposit slot
   private final static int CANCELED = 0; // constant for cancel option

   // Deposit constructor remains the same
   public Deposit(int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, Keypad atmKeypad, 
      DepositSlot atmDepositSlot)
   {
      // initialize superclass variables
      super(userAccountNumber, atmScreen, atmBankDatabase);

      // initialize references to keypad and deposit slot
      keypad = atmKeypad;
      depositSlot = atmDepositSlot;
   }

   // perform transaction
   public void execute()
   {
      BankDatabase bankDatabase = getBankDatabase();
      Screen screen = getScreen();

      // First select account type
      screen.displayMessageLine("\nSelect account type for deposit:");
      screen.displayMessageLine("1 - Savings Account");
      screen.displayMessageLine("2 - Cheque Account");
      screen.displayMessage("\nChoose an account: ");
      
      int accountType = keypad.getInput() - 1; // Convert to 0-based index
      
      if (accountType != 0 && accountType != 1) {
         screen.displayMessageLine("\nInvalid account type. Transaction canceled.");
         return;
      }

      amount = promptForDepositAmount(); // get deposit amount from user

      // check whether user entered a deposit amount or canceled
      if (amount != CANCELED)
      {
         // request deposit envelope containing specified amount
         screen.displayMessage("\nPlease insert a deposit envelope containing ");
         screen.displayDollarAmount(amount);
         screen.displayMessageLine(".");

         // receive deposit envelope
         boolean envelopeReceived = depositSlot.isEnvelopeReceived();

         // check whether deposit envelope was received
         if (envelopeReceived)
         {  
            screen.displayMessageLine("\nYour envelope has been " + 
               "received.\nNOTE: The money just deposited will not " + 
               "be available until we verify the amount of any " +
               "enclosed cash and your checks clear.");
            
            // credit account to reflect the deposit
            bankDatabase.credit(getAccountNumber(), accountType, amount); 
         }
         else // deposit envelope not received
         {
            screen.displayMessageLine("\nYou did not insert an " +
               "envelope, so the ATM has canceled your transaction.");
         }
      }
      else // user canceled instead of entering amount
      {
         screen.displayMessageLine("\nCanceling transaction...");
      }
   }

   // promptForDepositAmount method remains the same
   private double promptForDepositAmount()
   {
      Screen screen = getScreen();

      screen.displayMessage("\nPlease enter a deposit amount in " + 
         "CENTS (or 0 to cancel): ");
      int input = keypad.getInput();
      
      if (input == CANCELED) 
         return CANCELED;
      else
      {
         return (double) input / 100; // return dollar amount 
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