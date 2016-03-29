package com.zerofall.ezstorage.tileentity;

import java.util.List;

import com.zerofall.ezstorage.util.EZStorageUtils;
import com.zerofall.ezstorage.util.ItemGroup;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityOutputPort extends TileEntity implements ITickable {

	public TileEntityStorageCore core;

	@Override
	public void update() {
		if (core != null && !worldObj.isRemote) {
			boolean updateCore = false;
			BlockPos location = getPos().offset(EnumFacing.UP);
			TileEntity tileentity = worldObj.getTileEntity(location);
            if (tileentity instanceof IInventory) {
				IInventory inventory = (IInventory)tileentity;
				
				if (inventory != null) {
					List<ItemGroup> inventoryList = core.inventory.inventory;
					if (inventoryList != null && inventoryList.size() > 0) {
						ItemGroup group = inventoryList.get(0);
						if (group != null) {
							ItemStack stack = group.itemStack;
							stack.stackSize = (int) Math.min((long)stack.getMaxStackSize(), group.count);
							int stackSize = stack.stackSize;
							ItemStack leftOver = TileEntityHopper.putStackInInventoryAllSlots(inventory, stack, EnumFacing.DOWN);
							if (leftOver != null) {
								int remaining = stackSize - leftOver.stackSize;
								if (remaining > 0) {
									group.count -= remaining;
									updateCore = true;
								}
							} else {
								group.count -= stackSize;
								updateCore = true;
							}
							if (group.count <= 0) {
								core.inventory.inventory.remove(0);
							}
						}
					}
				}
            }
            if (updateCore) {
            	EZStorageUtils.notifyBlockUpdate(core);
				core.markDirty();
            }
		}
	}
}
