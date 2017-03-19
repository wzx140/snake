package main;

import core.GamePanel;

import javax.swing.*;

/**
 * Created by wzx on 17-3-18.
 */
public class Begin {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("贪吃蛇");
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setContentPane(new GamePanel(20,20).mainPanel());
                frame.pack();
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
