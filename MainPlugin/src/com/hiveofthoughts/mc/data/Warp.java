package com.hiveofthoughts.mc.data;

public class Warp {
	private String name;

	private double x;
	private double y;
	private double z;
	///Not used
	private int pitch;
	private int yaw;
	
	public Warp()
	{
		
	}
	public Warp(String n,double X,double Y, double Z) {
		name = n;
		x = X;
		y = Y;
		z = Z;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public int getPitch() {
		return pitch;
	}

	public void setPitch(int pitch) {
		this.pitch = pitch;
	}

	public int getYaw() {
		return yaw;
	}

	public void setYaw(int yaw) {
		this.yaw = yaw;
	}
}
