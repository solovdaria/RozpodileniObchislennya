package A;


public class Bee implements Runnable {
    public void run() {
        try {
            TrueBees.bank.Add();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
