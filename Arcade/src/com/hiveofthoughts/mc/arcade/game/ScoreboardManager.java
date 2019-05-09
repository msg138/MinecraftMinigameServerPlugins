package com.hiveofthoughts.mc.arcade.game;

import com.hiveofthoughts.mc.server.ServerInfo;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardManager {

    private static ScoreboardManager m_instance;

    private List<String > m_data;

    private ScoreboardManager (){
        m_data = new ArrayList<>();
    }

    public static ScoreboardManager getInstance(){
        if(m_instance == null)
            m_instance = new ScoreboardManager();
        return m_instance;
    }

    public void setData(String a_data) {
        String[] t_data = a_data.split("\n");
        setData(t_data);
    }
    public void setData(String[] a_data){
        m_data.clear();

        for(String t_d : a_data) {
            m_data.add(t_d);
        }
    }

    public void setScoreBoard(Player a_player) {
        Scoreboard t_board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective t_obj = t_board.registerNewObjective(ServerInfo.getInstance().getServerName(), "dummy");
        t_obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        t_obj.setDisplayName(ServerInfo.getInstance().getServerName().toUpperCase());
        for(int t_i = 0; t_i < m_data.size(); t_i ++) {
            String t_d = parse(m_data.get(t_i), a_player);
            Score t_score = t_obj.getScore(t_d);
            t_score.setScore(m_data.size() - t_i);
        }
        a_player.setScoreboard(t_board);
    }

    public String parse(String a_str, Player a_player){
        // Player count
        a_str = a_str.replaceAll("&pc", GameManager.getInstance().getCurrentGame().getPlayerCount() + "");
        // Player spectator count
        a_str = a_str.replaceAll("&psc", GameManager.getInstance().getCurrentGame().getPlayerStatusCount(PlayerStatus.SPECTATOR) + "");
        // Player playing count
        a_str = a_str.replaceAll("&ppc", GameManager.getInstance().getCurrentGame().getPlayerStatusCount(PlayerStatus.PLAYING) + "");
        // Game Type
        a_str = a_str.replaceAll("&gt", GameManager.getInstance().getCurrentGame().getName() + "");
        // Game Team
        a_str = a_str.replaceAll("&team", GameManager.getInstance().getCurrentGame().getPlayerInfo(a_player).getTeam().getTeamName() + "");
        // Game Kit
        a_str = a_str.replaceAll("&kit", GameManager.getInstance().getCurrentGame().getPlayerInfo(a_player).getKit().getKitName() + "");
        // Minimal players to start
        a_str = a_str.replace("&minp", GameManager.getInstance().getCurrentGame().getMinPlayers() +"");

        a_str = GameManager.getInstance().getCurrentGame().parse(a_str, a_player);

        return a_str;
    }

}
