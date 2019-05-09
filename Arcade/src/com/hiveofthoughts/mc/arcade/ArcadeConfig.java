package com.hiveofthoughts.mc.arcade;

import org.bukkit.ChatColor;

public class ArcadeConfig {

    public static String Prefix = ChatColor.WHITE + "[" + ChatColor.DARK_BLUE + ChatColor.BOLD + "ARCADE" + ChatColor.RESET + "] ";

    public static String DefaultString = "NIL";

    public static String ChatLine = "=====================================================";

    public static String DefaultScoreboard = "Game Type: &gt\n \nTeam: &team\n  \nKit: &kit\n   \nPlayers: &pc\n     \nTotal Players To Start: &minp";

    public static String DefaultWorldName = "world";

    public static String LobbyWorldName = "world";

    public static int DefaultMinPlayers = 2;
    public static int DefaultMaxPlayers = 10;

    public static int TimeBeforeStartSeconds = 10;

    public static String MessageGameStateRequired = Prefix + "Game state required.";
    public static String MessageGameStateSet = Prefix + "Game state set.";
    public static String MessageGameStartingSoon = Prefix + "Game starting in " + TimeBeforeStartSeconds + " seconds.";


}
