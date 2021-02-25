package A;

import java.lang.*;


public class TrueBees {

    public static int sema = 0;
    public static final int threads = 10;
    public static boolean filled = false;

    static Bank bank = new Bank(threads);

    public static void main(String[] args) {
        Process process = new Process();
        Bear bear = new Bear();

            bear.start();
            process.Action();

    }

}
