package io.catroll.switcher.utils;

import javax.swing.*;
import java.awt.*;

public class WindowUtil {
    private WindowUtil(){}
    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2 );
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2 );
        frame.setLocation(x,y);
    }

    public static void showDialog(Frame owner, boolean modal, String title, String... messages) {
        JDialog warning = new JDialog(owner, title, modal);
        warning.setLayout((new BoxLayout(warning.getContentPane(), BoxLayout.Y_AXIS)));
        WindowUtil.centreWindow(warning);
        for (String message : messages) {
            JLabel label = new JLabel(message);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            warning.add(label);
        }
        warning.setSize(500, 150);
        warning.setResizable(false);
        warning.setVisible(true);
    }
}
