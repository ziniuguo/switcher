package io.catroll.switcher;

import io.catroll.switcher.utils.WindowUtil;
import org.apache.commons.io.FileUtils;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;


public class Switcher extends JFrame {
    static File gameDir;
    static int currVersion;
    public Switcher() throws IOException {
        File gameDirFile = new File(
                Constants.APP_HOME_DIR, Constants.GAME_DIR_LITERAL
        );
        if (gameDirFile.exists()){
            gameDir = new File(FileUtils.readFileToString(gameDirFile, StandardCharsets.UTF_8));
        } else {
            gameDir = null;
        }
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("R6 Steam/Uplay Switcher");
        initUI();
    }

    private void initUI() {
        add(Box.createRigidArea(new Dimension(0, 20)));

        if (!new File(Constants.APP_HOME_DIR, Constants.UPLAY_DAT_LITERAL).exists()
                || !new File(Constants.APP_HOME_DIR, Constants.STEAM_DAT_LITERAL).exists()
                || !new File(Constants.APP_HOME_DIR, Constants.GAME_DIR_LITERAL).exists()) {
            // actually, only need to check Uplay. No need to check 3 files.
            setSize(350, 150);
            WindowUtil.centreWindow(this);
            JOptionPane.showMessageDialog(null,
                    Constants.WELCOME_MESSAGE);
            JButton setSteamButton = new JButton(Constants.SET_STEAM_BUTTON);
            setSteamButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(null,
                        Constants.SET_STEAM_INFO_MESSAGE);
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("."));
                chooser.setDialogTitle("Select steam version installation folder");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    saveDllFile(chooser.getSelectedFile(), true);
                }
            });
            JButton setUplayButton = new JButton(Constants.SET_UPLAY_BUTTON);
            setUplayButton.addActionListener(e -> {
                if (gameDir == null) {
                    JOptionPane.showMessageDialog(null,
                            Constants.UNKNOWN_GAME_DIR_WARNING,
                            "", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int res = JOptionPane.showConfirmDialog(null,
                        Constants.SET_UPLAY_INFO_MESSAGE,
                        "Before you proceed", JOptionPane.YES_NO_OPTION);
                if (res != 0) {return;}
                saveDllFile(gameDir, false);
            });

            setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
            setSteamButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            setUplayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(setSteamButton);
            add(Box.createRigidArea(new Dimension(0, 10)));
            add(setUplayButton);
        } else {
            setSize(350, 350);
            WindowUtil.centreWindow(this);
            // get current game version, and display it
            // compare two files
            File gameDll = new File(gameDir, Constants.DLL_FILE_NAME);
            File steamDll = new File(Constants.APP_HOME_DIR, Constants.STEAM_DAT_LITERAL);
            File uplayDll = new File(Constants.APP_HOME_DIR, Constants.UPLAY_DAT_LITERAL);
            JLabel versionLabel;
            try {
                if (FileUtils.contentEquals(gameDll, steamDll)) {
                    versionLabel = new JLabel("Current version detected: Steam");
                    currVersion = GameController.STEAM_VERSION;
                } else if (FileUtils.contentEquals(gameDll, uplayDll)) {
                    versionLabel = new JLabel("Current version detected: Uplay");
                    currVersion = GameController.UPLAY_VERSION;
                } else {
                    // hopefully won't happen
                    versionLabel = new JLabel("Current version detected: Unknown");
                    currVersion = GameController.UNKNOWN_VERSION;
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,
                        Constants.UNKNOWN_ERROR_MESSAGE +"\r\nlogs: \r\n" + e.getMessage(),
                        Constants.ERROR_LITERAL, JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                return;
            }

            JButton switchSteamButton = new JButton(Constants.SWITCH_STEAM_BUTTON);
            switchSteamButton.addActionListener(e -> {
                GameController.killProcess();
                GameController.switchSteamVersion();
                versionLabel.setText("Current version detected: Steam");
                currVersion = GameController.STEAM_VERSION;
            });

            JButton launchSteamButton = new JButton(Constants.SWITCH_STEAM_BUTTON + "\r\n and launch");
            launchSteamButton.addActionListener(e -> {
                GameController.killProcess();
                if (currVersion != GameController.STEAM_VERSION) {
                    GameController.switchSteamVersion();
                    versionLabel.setText("Current version detected: Steam");
                    currVersion = GameController.STEAM_VERSION;
                }
                GameController.launchSteam();
            });

            JButton switchUplayButton = new JButton(Constants.SWITCH_UPLAY_BUTTON);
            switchUplayButton.addActionListener(e -> {
                GameController.killProcess();
                GameController.switchUplayVersion();
                versionLabel.setText("Current version detected: Uplay");
                currVersion = GameController.UPLAY_VERSION;
            });

            JButton launchUplayButton = new JButton(Constants.SWITCH_UPLAY_BUTTON + "\r\n and launch");
            launchUplayButton.addActionListener(e -> {
                GameController.killProcess();
                if (currVersion != GameController.UPLAY_VERSION) {
                    GameController.switchUplayVersion();
                    versionLabel.setText("Current version detected: Uplay");
                    currVersion = GameController.UPLAY_VERSION;
                }
                GameController.launchUplay();
            });

            JButton resetButton = new JButton(Constants.RESET_BUTTON);
            resetButton.addActionListener(e -> {
                try {
                    FileUtils.delete(new File(Constants.APP_HOME_DIR, Constants.UPLAY_DAT_LITERAL));
                    FileUtils.delete(new File(Constants.APP_HOME_DIR, Constants.GAME_DIR_LITERAL));
                    FileUtils.delete(new File(Constants.APP_HOME_DIR, "steam.dat"));
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(null,
                            Constants.UNKNOWN_ERROR_MESSAGE +"\r\nlogs: \r\n" + ioException.getMessage(),
                            Constants.ERROR_LITERAL, JOptionPane.ERROR_MESSAGE);
                    ioException.printStackTrace();
                    return;
                }
                JOptionPane.showMessageDialog(null, "Reset done. Click next to quit.");
                System.exit(0);
            });

            JButton killButton = new JButton(Constants.KILL_BUTTON);
            killButton.addActionListener(e -> GameController.killProcess());

            setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
            versionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            switchSteamButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            launchSteamButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            switchUplayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            launchUplayButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            add(versionLabel);
            add(Box.createRigidArea(new Dimension(0, 10)));
            add(switchSteamButton);
            add(Box.createRigidArea(new Dimension(0, 10)));
            add(launchSteamButton);
            add(Box.createRigidArea(new Dimension(0, 10)));
            add(switchUplayButton);
            add(Box.createRigidArea(new Dimension(0, 10)));
            add(launchUplayButton);

            JPanel jPanel = new JPanel();
            jPanel.setLayout(new FlowLayout());
            jPanel.add(resetButton);
            jPanel.add(killButton);

            add(Box.createRigidArea(new Dimension(0, 25)));
            add(jPanel);

            JLabel desc2 = new JLabel("Made by CatRoll");
            desc2.setAlignmentX(Component.CENTER_ALIGNMENT);
            JLabel hyperlink = new JLabel(Constants.VISIT_MY_GITHUB_LITERAL);
            hyperlink.setForeground(Color.BLUE.darker());
            hyperlink.setCursor(new Cursor(Cursor.HAND_CURSOR));
            hyperlink.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://github.com/ziniuguo/switcher"));
                    } catch (IOException | URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            hyperlink.setAlignmentX(Component.CENTER_ALIGNMENT);
            JLabel hyperlink2 = new JLabel(Constants.VISIT_MY_WEBSITE_LITERAL);
            hyperlink2.setForeground(Color.BLUE.darker());
            hyperlink2.setCursor(new Cursor(Cursor.HAND_CURSOR));
            hyperlink2.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://www.catroll.io"));
                    } catch (IOException | URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            hyperlink2.setAlignmentX(Component.CENTER_ALIGNMENT);

            add(desc2);
            add(hyperlink);
            add(hyperlink2);
            add(Box.createRigidArea(new Dimension(0, 10)));
        }
    }

    private void saveDllFile(File gameDir, boolean isSteam) {
        File dllFile = new File(gameDir, Constants.DLL_FILE_NAME);
        if (!dllFile.exists()) {
            JOptionPane.showMessageDialog(null,
                    Constants.NO_DLL_FILE_FOUND_WARNING + "\r\n" +
                    Constants.GAME_INTEGRITY_WARNING, "Game file corrupted.", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            FileUtils.copyFile(dllFile,
                    new File(Constants.APP_HOME_DIR, isSteam ? Constants.STEAM_DAT_LITERAL : Constants.UPLAY_DAT_LITERAL));
            if (isSteam) {
                FileUtils.writeStringToFile(new File(Constants.APP_HOME_DIR, Constants.GAME_DIR_LITERAL),
                        String.valueOf(gameDir),
                        StandardCharsets.UTF_8,
                        false);
                this.gameDir = gameDir;
            }
        } catch (IOException ioException) {
            JOptionPane.showMessageDialog(null,
                    Constants.UNKNOWN_ERROR_MESSAGE +"\r\nlogs: \r\n" + ioException.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            ioException.printStackTrace();
            return;
        }
        JOptionPane.showMessageDialog(null,
                (isSteam ? "Steam" : "Uplay") + " version set. \n" +
                        (isSteam ? "" : "restart the app to enjoy seamless switching!"),
                "Success", JOptionPane.INFORMATION_MESSAGE);
        if (!isSteam) {
            System.exit(0);
        }
    }
}