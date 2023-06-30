package io.catroll.switcher;

import javax.swing.*;
import java.io.IOException;


public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    SwingApp swingApp = new SwingApp();

                    swingApp.setVisible(true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
