public class SavingsAccount extends Account {
   private double interestRate; 

   public SavingsAccount(int accountNumber, int pin, double availableBalance, double totalBalance) {
      super(accountNumber, pin, availableBalance, totalBalance);
      this.interestRate = 0.005; // default interest rate
   }

   public double getInterestRate() {
      return interestRate;
   }

   // based on different period
   public void applyInterest(String period) {
      double interest = 0;

      switch (period.toLowerCase()) {
         case "year":
            interest = getAvailableBalance() * interestRate; // annually
            break;
         case "quarter":
            interest = getAvailableBalance() * (interestRate / 4); // quarterly
            break;
         case "month":
            interest = getAvailableBalance() * (interestRate / 12); // monthly
            break;
         default:
            System.out.println("Invalid period selected. No interest applied.");
            return; // 
      }

      credit(interest); // Use the parent class method to add interest to the total balance
   }
}