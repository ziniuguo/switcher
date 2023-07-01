package io.catroll.switcher;

import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class GameController {
    private GameController() {}
    public static final int STEAM_VERSION = 0;
    public static final int UPLAY_VERSION = 1;
    public static final int UNKNOWN_VERSION = 2;

    public static void killProcess() {
        try {
            Runtime.getRuntime().exec("taskkill /F /IM upc.exe");
            Runtime.getRuntime().exec("taskkill /F /IM RainbowSix.exe");
            Runtime.getRuntime().exec("taskkill /F /IM RainbowSix_Vulkan.exe");
        } catch (IOException ioException) {
            JOptionPane.showMessageDialog(null,
                    Constants.UNKNOWN_ERROR_MESSAGE +"\r\nlogs: \r\n" + ioException.getMessage(),
                    Constants.ERROR_LITERAL, JOptionPane.ERROR_MESSAGE);
            ioException.printStackTrace();
        }
    }

    public static void switchSteamVersion() {
        killProcess();
        try {
            FileUtils.copyFile(new File(Constants.APP_HOME_DIR, Constants.STEAM_DAT_LITERAL),
                    new File(Switcher.gameDir, Constants.DLL_FILE_NAME));
        } catch (IOException ioException) {
            JOptionPane.showMessageDialog(null,
                    Constants.UNKNOWN_ERROR_MESSAGE +"\r\nlogs: \r\n" + ioException.getMessage(),
                    Constants.ERROR_LITERAL, JOptionPane.ERROR_MESSAGE);
            ioException.printStackTrace();
            return;
        }
        JOptionPane.showMessageDialog(null, "switched to Steam version!");
    }

    public static void switchUplayVersion() {
        killProcess();
        try {
            FileUtils.copyFile(new File(Constants.APP_HOME_DIR, Constants.UPLAY_DAT_LITERAL),
                    new File(Switcher.gameDir, Constants.DLL_FILE_NAME));
        } catch (IOException ioException) {
            JOptionPane.showMessageDialog(null,
                    Constants.UNKNOWN_ERROR_MESSAGE +"\r\nlogs: \r\n" + ioException.getMessage(),
                    Constants.ERROR_LITERAL, JOptionPane.ERROR_MESSAGE);
            ioException.printStackTrace();
            return;
        }
        JOptionPane.showMessageDialog(null, "switched to Uplay version!");
    }

    public static void launchSteam() {
        int res = JOptionPane.showConfirmDialog(null,
                "Do you want to use vulkan?",
                "Starting Steam Version", JOptionPane.YES_NO_OPTION);
        try {
            if (res == 0) {
                Runtime.getRuntime().exec("cmd /c start steam://rungameid/359550// -Vulkan");
            } else {
                Runtime.getRuntime().exec("cmd /c start steam://rungameid/359550");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    Constants.UNKNOWN_ERROR_MESSAGE +"\r\nlogs: \r\n" + e.getMessage(),
                    Constants.ERROR_LITERAL, JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void launchUplay() {
        int res = JOptionPane.showConfirmDialog(null,
                "Do you want to use vulkan?",
                "Starting Uplay Version", JOptionPane.YES_NO_OPTION);
        File exeFile;
        if (res == 0) {
            exeFile = new File(Switcher.gameDir, "RainbowSix_Vulkan.exe");
        } else {
            exeFile = new File(Switcher.gameDir, "RainbowSix.exe");
        }
        ProcessBuilder processBuilder = new ProcessBuilder(exeFile.getAbsolutePath());
        processBuilder.directory(exeFile.getParentFile());
        try {
            processBuilder.start();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    Constants.UNKNOWN_ERROR_MESSAGE +"\r\nlogs: \r\n" + e.getMessage(),
                    Constants.ERROR_LITERAL, JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
