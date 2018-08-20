package com.hiveofthoughts.mc;

import com.hiveofthoughts.mc.commands.CommandTemplate;
import com.hiveofthoughts.mc.listeners.global.player.*;
import com.hiveofthoughts.mc.listeners.global.chat.*;
import com.hiveofthoughts.mc.listeners.global.server.ServerSelectorAutoGiveListener;
import com.hiveofthoughts.mc.listeners.global.server.ServerSelectorInventoryControlListener;
import com.hiveofthoughts.mc.listeners.global.server.ServerSelectorListener;
import com.hiveofthoughts.mc.listeners.global.world.DisableRainListener;
import com.hiveofthoughts.mc.server.ServerType;
import org.bukkit.command.Command;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Michael George on 3/14/2018.
 */
public class InitClasses{
    public static final Class<Listener>[] EventListeners = new Class[]{
            DisableNonOpCommands.class,
            PlayerChatHandler.class,

            ClearInventoryOnJoin.class,
            MovePlayerToSpawnListener.class,

            PreventBlockPlaceAndBreak.class,

            PlayerJoinQuitListener.class,
            PlayerQuitSaveConfigListener.class,

            DisableRainListener.class,

            ServerSelectorListener.class,
            ServerSelectorAutoGiveListener.class,
            ServerSelectorInventoryControlListener.class,

    };

    public static final HashMap<ServerType, Class<Listener>[] > ServerExclusive = new HashMap<ServerType, Class<Listener>[] >(){
        {
            // For default servers (AKA The Hub), disable server specific classes.
            put(ServerType.DEFAULT, new Class[]{  });
            put(ServerType.RPG, new Class[]{
                    PreventBlockPlaceAndBreak.class,
                    ServerSelectorAutoGiveListener.class,
                    ServerSelectorListener.class,
                    ClearInventoryOnJoin.class,
                    MovePlayerToSpawnListener.class,
            });
        }
    };

    public static final HashMap<ServerType, Class<Listener>[] > ServerInclusive = new HashMap<ServerType, Class<Listener>[] >(){
        {
            // For default servers (AKA The Hub), disable server specific classes.
            put(ServerType.DEFAULT, new Class[]{  });
            put(ServerType.RPG, new Class[]{ });
        }
    };

    public static final ArrayList<CommandTemplate> AddCommands = new ArrayList<>();
}
