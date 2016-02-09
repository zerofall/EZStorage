package com.zerofall.ezstorage.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class RecipeMessage implements IMessage {
	
	NBTTagCompound recipe;
	
	public RecipeMessage() {
		
	}
	
	public RecipeMessage(NBTTagCompound recipe) {
		this.recipe = recipe;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		recipe = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, this.recipe);
	}

}
