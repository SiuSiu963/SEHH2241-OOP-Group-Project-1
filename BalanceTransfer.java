//Transfer.java

import java.security.Key;

public class BalanceTransfer extends Transaction
{
    private int amount;
    private Keypad keypad;
    private int toAccountNumber;

    private final static int CANCELED = 3;



    public BalanceTransfer( int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad atmKeypad)
    {
        super ( userAccountNumber, atmScreen, atmBankDatabase);

        keypad = atmKeypad;

    }

    public void execute() {
        double availableBalance;
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();
     
        screen.displayMessageLine("\nSelect account type to send money from: ");
        screen.displayMessageLine("1 - Savings Account");
        screen.displayMessageLine("2 - Cheque Account");
        screen.displayMessage("\nChoose an account: ");

        int accountType = keypad.getInput() - 1; // Convert to 0-based index
    
        if (accountType != 0 && accountType != 1) {
            screen.displayMessageLine("\nInvalid account type. Transaction canceled.");
            return;
        }
        int[] returnedData = displayMenuOfTargetAccount();
        
        boolean amountCheckResult = false;
        
        if (returnedData[0] != CANCELED)
        {
            do {
                screen.displayMessage("\nEnter amount to be transfer: ");
                double amount = keypad.getInput();
                amountCheckResult = transferAmountVaildator( amount );

                availableBalance = bankDatabase.getAvailableBalance(getAccountNumber(), accountType);
                if (amount <=  availableBalance)
                {
                    switch (returnedData[0]) {
                        case 1:
                            bankDatabase.debit(getAccountNumber(), accountType, amount);
                            bankDatabase.credit(returnedData[1], 1, amount);
                            break;
                        case CANCELED:
                            return;

                        default:
                            break;
                    }
                }
            } while (!amountCheckResult);
        } 
        else {
            return;
        }

    }

    private boolean transferAmountVaildator(double input_amount) {
        Screen screen = getScreen();
        if (input_amount <= 0) {
            screen.displayMessageLine("Inputed Amount invalid!");
            return false;
        } else {
            return true;
        }
    };


    private int[] displayMenuOfTargetAccount() {
        int userChoice = 0;
        int targetAccount = 0;


        Screen screen = getScreen();

 
        while (userChoice == 0) {
           screen.displayMessageLine("Transfer Menu:");
           screen.displayMessageLine("1 - Transfer to other account no."); 
           screen.displayMessageLine("2 - Transfer to oversea bank account");
           screen.displayMessageLine("3 - Cancel Transaction");
           screen.displayMessage("\nEnter a choice: ");
           int input = keypad.getInput();
           userChoice = input;

           switch ( input ) {
            case 1: 
                while (userChoice == 1 && targetAccount == 0) {
                    screen.displayMessage("Enter the recipient account no. :");
                    int inputAccountno = keypad.getInput();
                    if ( inputAccountno != getAccountNumber() ) {
                        targetAccount = inputAccountno;

                    } else {
                        screen.displayMessageLine("ERROR: You can't transfer to yourself!");
                        targetAccount = 0;
                    }
                    
                }
                break;
            case 2: //TODO
            case CANCELED:
                break;
                
            default:
            screen.displayMessageLine( 
                "\nInvalid selection. Try again." );
           }
        }

        int dataForTransfer[] = {userChoice, targetAccount};
        return dataForTransfer;
    }
}