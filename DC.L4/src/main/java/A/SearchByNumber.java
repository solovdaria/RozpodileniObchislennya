package A;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SearchByNumber implements Runnable{
    private String number;

    private MyReadWriteLock locker;

    private final String fileName = "input.txt";
    private File file = new File(fileName);

    SearchByNumber(String number, MyReadWriteLock locker) {
        this.number = number;
        this.locker = locker;
    }

    public void run() {
        try {
            locker.readLock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] str = line.split(" - ");

                if (number.equals(str[1])) {
                    System.out.println("Searching by number thread found record: " + number + ": " + str[1]);
                    locker.unlockRead();
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Searching by number thread cant find record: " + number);
        locker.unlockRead();
    }
}
