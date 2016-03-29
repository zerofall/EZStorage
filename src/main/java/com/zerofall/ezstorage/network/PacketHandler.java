package com.zerofall.ezstorage.network;

import com.zerofall.ezstorage.container.ContainerStorageCore;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketHandler implements IMessageHandler<MyMessage, IMessage> {
    
    @Override
    public IMessage onMessage(MyMessage message, MessageContext ctx) {
    	EntityPlayer player = ctx.getServerHandler().playerEntity;
    	Container container = player.openContainer;
    	if (container != null && container instanceof ContainerStorageCore) {
    		ContainerStorageCore storageContainer = (ContainerStorageCore)container;
    		storageContainer.customSlotClick(message.index, message.button, message.mode, player);
    	}
    	return null; // no response in this case
    }
    
    
}