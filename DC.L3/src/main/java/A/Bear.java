package A;



public class Bear extends Thread {
    @Override
    public void run() {
        System.out.println("Run Bear");
        for(int i=0; i<10; i++) TrueBees.bank.Annihilate();
    }
}
