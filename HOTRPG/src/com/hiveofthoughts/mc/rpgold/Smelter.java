package com.hiveofthoughts.mc.rpgold;

import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class Smelter implements Furnace{
	
	public FurnaceInventory inv;
	
	public Smelter(FurnaceInventory i)
	{
		inv = i;
	}

	@Override
	public Block getBlock() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Chunk getChunk() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MaterialData getData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte getLightLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getLocation(Location arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte getRawData() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Material getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTypeId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public World getWorld() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setData(MaterialData arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRawData(byte arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isPlaced() {
		return false;
	}

	@Override
	public void setType(Material arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean setTypeId(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(boolean arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(boolean arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<MetadataValue> getMetadata(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasMetadata(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeMetadata(String arg0, Plugin arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMetadata(String arg0, MetadataValue arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public short getBurnTime() {
		// TODO Auto-generated method stub
		return 20;
	}

	@Override
	public short getCookTime() {
		// TODO Auto-generated method stub
		return 15;
	}

	@Override
	public FurnaceInventory getInventory() {
		// TODO Auto-generated method stub
		return inv;
	}

	@Override
	public FurnaceInventory getSnapshotInventory() {
		return null;
	}

	@Override
	public void setBurnTime(short arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCookTime(short arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCustomName() {
		return null;
	}

	@Override
	public void setCustomName(String s) {

	}

	@Override
	public boolean isLocked() {
		return false;
	}

	@Override
	public String getLock() {
		return null;
	}

	@Override
	public void setLock(String s) {

	}
}
