package com.hiveofthoughts.mc.arcade;

import org.bukkit.ChatColor;

public class ArcadeConfig {

    public static String Prefix = ChatColor.WHITE + "[" + ChatColor.DARK_BLUE + ChatColor.BOLD + "ARCADE" + ChatColor.RESET + "] ";

    public static String DefaultString = "NIL";

    public static String DefaultWorldName = "world";

    public static int DefaultMinPlayers = 2;
    public static int DefaultMaxPlayers = 10;

    public static int TimeBeforeStartSeconds = 10;

    public static String MessageGameStateRequired = Prefix + "Game state required.";
    public static String MessageGameStartingSoon = Prefix + "Game starting in " + TimeBeforeStartSeconds + " seconds.";


}
