package A;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SearchByName implements Runnable{
    private String name;

    private MyReadWriteLock locker;

    private final String fileName = "input.txt";
    private File file = new File(fileName);

    SearchByName(String name, MyReadWriteLock locker) {
        this.name = name;
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

                if (name.equals(str[0])) {
                    System.out.println("Searching by name thread found record: " + name + ": " + str[1]);
                    locker.unlockRead();
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Searching by name thread cant find: " + name);
        locker.unlockRead();
    }
}
