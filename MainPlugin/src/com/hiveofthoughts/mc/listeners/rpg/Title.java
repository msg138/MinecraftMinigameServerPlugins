package com.hiveofthoughts.mc.listeners.rpg;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Title {
	PacketPlayOutTitle packet;
	PacketPlayOutTitle packet1;
	PacketPlayOutTitle packet2;
	
	String title = "";
	String subtitle = "";
	
	public int fadeIn = 100;
	public int fadeOut = 100;
	public int stay = 100;
	
	public Title(String title,String sub)
	{
		this.title = title;
		this.subtitle = sub;
		packet1 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\""+title+"\"}"));
		packet2 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\""+sub+"\"}"));
	}
	
	public void send(Player p)
	{
		packet = new PacketPlayOutTitle(fadeIn,stay,fadeOut);
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet1);
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet2);
	}
}
