package com.zerofall.ezstorage.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;

public abstract class EZTileEntity extends TileEntity {
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		writeDataToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		readDataFromNBT(compound);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		writeDataToNBT(nbtTag);
		return new S35PacketUpdateTileEntity(this.pos, 1, nbtTag);
	}
	
	public abstract void writeDataToNBT(NBTTagCompound paramNBTTagCompound);
	
	public abstract void readDataFromNBT(NBTTagCompound paramNBTTagCompound);
}
