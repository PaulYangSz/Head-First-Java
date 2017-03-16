package chap15;

public class RyanAndMonicaJob implements Runnable
{
    private BankAccount account = new BankAccount();
    
    public static void main(String[] args) {
        RyanAndMonicaJob theJob = new RyanAndMonicaJob();
        WithdrawJob wJob = theJob.new WithdrawJob();
        IncrementsJob iJob = theJob.new IncrementsJob();
        Thread one = new Thread(wJob);
        Thread two = new Thread(iJob);
        one.setName("Ryan");
        two.setName("Monica");
        one.start();
        two.start();
    }
    
    public class WithdrawJob implements Runnable 
    {
    	public void run() {
    		for(int i = 0; i < 10000; i++)
    		{
        		account.withdraw(1);
    		}
            System.out.println(Thread.currentThread().getName() + " withdraw " + account.getBalance());
    	}
    }
    
    public class IncrementsJob implements Runnable {
    	public void run() {
    		for(int i = 0; i < 10000; i++)
    		{
    			account.increments(1);
    		}
            System.out.println(Thread.currentThread().getName() + " incre " + account.getBalance());
    	}
    }
    public void run()
    {
        for (int x = 0; x < 10; x++) {
            makeWithdrawal(10);
            if (account.getBalance() < 0)
            {
                System.out.println("Overdrawn!");
            }
        }
    }
//  to demonstrate the "overdrawn" error remove the "synchronized" modifier
    private synchronized void makeWithdrawal(int amount)
    {
        if (account.getBalance() >= amount)
        {
            System.out.println(Thread.currentThread().getName() + " is about to withdrawal");
            try {
                System.out.println(Thread.currentThread().getName() + " is going to sleep");
                Thread.sleep(500);
            } catch (InterruptedException ex) { ex.printStackTrace(); }
            System.out.println(Thread.currentThread().getName() + " woke up");
            account.withdraw(amount);
            System.out.println(Thread.currentThread().getName() + " completes the withdrawal");
        }
        else
        {
            System.out.println("Sorry, not enough for " + Thread.currentThread().getName());
        }
    }

}


class BankAccount {
    private int balance = 100;
    
    public int getBalance () {
        return balance;
    }
    
    public synchronized void withdraw(int amount) {
        balance = balance - amount;
    }
    
    public void increments(int amount) {
        balance = balance + amount;
    }
}