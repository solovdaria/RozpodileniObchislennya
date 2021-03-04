package A;

import java.io.*;

public class RemoveRecord implements Runnable{
    private String name;
    private String number;

    private MyReadWriteLock locker;

    private final String fileName = "input.txt";
    private File newFile = new File(fileName + ".tmp");
    private File oldFile = new File(fileName);

    RemoveRecord(String name, String number, MyReadWriteLock locker) {
        this.name = name;
        this.number = number;
        this.locker = locker;
    }

    public void run() {
        try {
            locker.writeLock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        boolean flag = false;

        try (PrintWriter printWriter = new PrintWriter(new FileWriter(newFile))) {

            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(oldFile))) {
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    String[] str = line.split(" - ");

                    if (!str[0].equals(name) && !str[1].equals(number)) {
                        printWriter.println(line);
                    } else {
                        flag = true;
                    }
                }
            }
        } catch (IOException e) {
            e.getMessage();
        }
        locker.unlockWrite();
        if (flag) {
            oldFile.delete();
            newFile.renameTo(oldFile);
            System.out.println("Deleting thread deleted record: " + name + " " + number);
        } else {
            newFile.delete();
            System.out.println("Deleting thread cant find record: " + name + " " + number);
        }

    }
}
