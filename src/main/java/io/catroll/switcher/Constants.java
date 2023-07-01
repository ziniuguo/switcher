package io.catroll.switcher;

public class Constants {
    public static final Object WELCOME_MESSAGE =
            "Welcome! " +
                    "\r\n" +
                    "\r\nPlease read and follow the steps and instructions carefully as if you were reading your exam paper questions! " +
                    "\r\nIt is really easy to use and if you get stuck somewhere, please do double check the instructions and do not" +
                    "\r\nspam me in my DM or comments. " +
                    "\r\n" +
                    "\r\nQ: Am I changing the version of my account?" +
                    "\r\nA: No. Only local game files are modified so that you can launch the game from accounts of different version." +
                    "\r\nQ: Will I get banned for using it?" +
                    "\r\nA: No. The two defaultargs.dll files are official." +
                    "\r\n" +
                    "\r\nWhy is this app necessary?" +
                    "\r\nSome or you (including me) have purchased both Steam and Uplay version of this game. However, the game files" +
                    "\r\nare almost the same except for one defaultargs.dll preventing you from launching the game of the other version." +
                    "\r\nThis app takes the two defaultargs.dll files from the official Steam and Uplay games, allowing you to switch " +
                    "\r\nbetween two versions seamlessly." +
                    "\r\n" +
                    "\r\nWhat's new?" +
                    "\r\nThe older version keeps those two defaultargs.dll files as hardcoded static resources which may become invalid " +
                    "\r\nafter game updates. Now, you only need to reset to fetch the newer files after upgrading the game, instead of " +
                    "\r\nwaiting for developers to fix the switcher."

            ;
    public static final String APP_TITLE = "R6 Steam/Uplay version switcher";
    public static final String SET_STEAM_BUTTON = "1. Set Steam version";
    public static final String SWITCH_STEAM_BUTTON = "Switch to Steam version";
    public static final String STEAM_DAT_LITERAL = "steam.dat";
    public static final String SET_UPLAY_BUTTON = "2. Set Uplay version";
    public static final String SWITCH_UPLAY_BUTTON = "Switch to Uplay version";
    public static final String UPLAY_DAT_LITERAL = "uplay.dat";
    public static final String RESET_BUTTON = "Reset";
    public static final String KILL_BUTTON = "Kill R6 and Uplay process";
    public static final String SET_STEAM_INFO_MESSAGE = "In the next step, please select your steam game installation folder. " +
            "\r\nBefore proceeding, please make sure that the current game installed is steam version. " +
            "\r\nYou can do this by verifying game file integrity in steam.";
    public static final String SET_UPLAY_INFO_MESSAGE = "Please uninstall Uplay version if you haven't done so, and then reinstall the game (Click \"Download\", not \"Locate installed game\"!) to the same folder where Steam version is installed. \r\n" +
            "Please note, you are supposed to see \"Discovering files\" when doing so, or you might have selected the wrong destination folder. \r\n" +
            "After doing these, click yes.";
    public static final String UNKNOWN_GAME_DIR_WARNING = "Unknown game directory. \r\nPlease complete step 1. set Steam version first!";
    public static final String DLL_FILE_NAME = "defaultargs.dll";
    public static final String NO_DLL_FILE_FOUND_WARNING = "Cannot locate defaultargs.dll";
    public static final String GAME_INTEGRITY_WARNING = "make sure you've selected the correct folder or verify game file integrity.";
    public static final String UNKNOWN_ERROR_MESSAGE = "An error occurred. Please try again";
    public static final String ERROR_LITERAL = "Error";
    public static final String APP_HOME_DIR = System.getProperty("user.home")
            + "/AppData/Local/SwitcherByCatRoll/";
    public static final String GAME_DIR_LITERAL = "gameDir";
    public static final String VISIT_MY_GITHUB_LITERAL = "Visit my GitHub";
    public static final String VISIT_MY_WEBSITE_LITERAL = "Visit my website";
}
