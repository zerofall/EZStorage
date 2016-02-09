package com.zerofall.ezstorage.events;

import com.zerofall.ezstorage.tileentity.TileEntityStorageCore;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class XEventHandler {

	@SubscribeEvent
	public void onBlockBreak(BreakEvent e) {
		if (!e.world.isRemote) {
			TileEntity tileentity = e.world.getTileEntity(e.pos);
			if (tileentity instanceof TileEntityStorageCore) {
				TileEntityStorageCore core = (TileEntityStorageCore)tileentity;
				if (core.inventory.getTotalCount() > 0) {
					e.setCanceled(true);
				}
			}
		}
	}
}
