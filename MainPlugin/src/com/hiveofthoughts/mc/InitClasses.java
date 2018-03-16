package com.hiveofthoughts.mc;

import com.hiveofthoughts.mc.listeners.global.player.*;
import com.hiveofthoughts.mc.listeners.global.chat.*;
import com.hiveofthoughts.mc.listeners.rpg.BlockListener;
import com.hiveofthoughts.mc.listeners.rpg.EntityListener;
import com.hiveofthoughts.mc.listeners.rpg.PlayerListener;
import com.hiveofthoughts.mc.listeners.rpg.WorldListener;
import com.hiveofthoughts.mc.server.ServerType;
import org.bukkit.event.Listener;

import java.util.HashMap;

/**
 * Created by Michael George on 3/14/2018.
 */
public class InitClasses{
    public static final Class<Listener>[] EventListeners = new Class[]{
            DisableNonOpCommands.class,
            PreventBlockPlaceAndBreak.class,
            PlayerJoinQuitListener.class,
            PlayerQuitSaveConfigListener.class,
    };

    public static final HashMap<ServerType, Class<Listener>[] > ServerExclusive = new HashMap<ServerType, Class<Listener>[] >(){
        {
            // For default servers (AKA The Hub), disable server specific classes.
            put(ServerType.DEFAULT, new Class[]{  });
            put(ServerType.RPG, new Class[]{ PreventBlockPlaceAndBreak.class });
        }
    };

    public static final HashMap<ServerType, Class<Listener>[] > ServerInclusive = new HashMap<ServerType, Class<Listener>[] >(){
        {
            // For default servers (AKA The Hub), disable server specific classes.
            put(ServerType.DEFAULT, new Class[]{  });
            put(ServerType.RPG, new Class[]{PlayerListener.class, WorldListener.class, EntityListener.class, BlockListener.class});
        }
    };
}
