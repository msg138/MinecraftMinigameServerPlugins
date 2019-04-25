package com.hiveofthoughts.mc.server;

import org.bukkit.Bukkit;

/**
 * Created by Michael George on 3/22/2018.
 */
public abstract class SelfCancelTask implements Runnable {

    private int m_bukkitSchedulerId;

    public void setSchedulerId(int a_id){
        m_bukkitSchedulerId = a_id;
    }

    public void cancelTask(){
        Bukkit.getScheduler().cancelTask(m_bukkitSchedulerId);
    }


}
