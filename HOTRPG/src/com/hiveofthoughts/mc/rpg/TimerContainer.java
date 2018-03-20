package com.hiveofthoughts.mc.rpg;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael George on 3/19/2018.
 */
public class TimerContainer {

    private static TimerContainer m_instance;

    public static TimerContainer getInstance(){
        if(m_instance == null)
            m_instance = new TimerContainer();
        return m_instance;
    }

    private List<Integer> m_timerList;

    private TimerContainer(){
        m_timerList = new ArrayList<>();
    }

    public int addTimer(int a_time){
        m_timerList.add(a_time);
        return a_time;
    }
    public boolean removeTimer(int a_time){
        Bukkit.getScheduler().cancelTask(a_time);
        return m_timerList.remove((Object)a_time);
    }

    public void deleteAllTimers(){
        for(int t_i : m_timerList){
            removeTimer(t_i);
        }
    }
}
