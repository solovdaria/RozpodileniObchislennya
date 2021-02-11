package code.A;

import code.MyRunnable;

import javax.swing.*;

import java.awt.*;

public class Test {
    JFrame frame;
    JButton button;
    JSlider slider;

    Thread thread1;
    Thread thread2;

    JSpinner spinner1;
    JSpinner spinner2;

    JLabel label1;
    JLabel label2;

    boolean started = false;

    public Test() {
        frame = new JFrame("Lab 1 A");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        button = new JButton("Start");

        button.setSize(100,100);
        button.setBackground(Color.cyan);

        button.addActionListener(e -> {
            onButtonClick();
        });

        slider = new JSlider();

        slider.setMajorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        spinner1 = new JSpinner();
        spinner2 = new JSpinner();

        spinner1.setValue(1);
        spinner1.addChangeListener(e -> {
            int val = (Integer) spinner1.getValue();
            if (val < 1) {
                spinner1.setValue(1);
            } else if (val > 10) {
                spinner1.setValue(10);
            }

            thread1.setPriority((Integer) spinner1.getValue());
        });

        spinner2.setValue(1);
        spinner2.addChangeListener(e -> {
            int val = (Integer) spinner2.getValue();
            if (val < 1) {
                spinner2.setValue(1);
            } else if (val > 10) {
                spinner2.setValue(10);
            }

            thread2.setPriority((Integer) spinner2.getValue());
        });

        label1 = new JLabel();
        label2 = new JLabel();
        label1.setHorizontalAlignment(JLabel.RIGHT);
        label2.setHorizontalAlignment(JLabel.RIGHT);

        JPanel panel = new JPanel();
        panel.add(label1);
        panel.add(label2);
        panel.add(spinner1);
        panel.add(spinner2);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(slider, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
        frame.setSize(400, 400);
        frame.setVisible(true);

        thread1 = new Thread(new MyRunnable(slider,10,label1));
        thread2 = new Thread(new MyRunnable(slider,90,label2));
        thread1.setDaemon(true);
        thread2.setDaemon(true);
        thread1.setPriority(1);
        thread2.setPriority(1);
    }

    private void onButtonClick() {
        if (!started) {
            thread1.start();
            thread2.start();
            started = true;
        }
    }
}
