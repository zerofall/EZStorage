package com.zerofall.ezstorage.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityOutputPort extends TileEntity implements ITickable {

	public TileEntityStorageCore core;

	@Override
	public void update() {
		if (core != null && !worldObj.isRemote) {
			BlockPos location = getPos().offset(EnumFacing.UP);
			TileEntity tileentity = worldObj.getTileEntity(location);
            if (tileentity instanceof IInventory) {
				IInventory inventory = (IInventory)tileentity;
				if (inventory != null && !isInventoryFull(inventory, EnumFacing.DOWN)) {
					ItemStack stack = core.getRandomStack();
					TileEntityHopper.putStackInInventoryAllSlots(inventory, stack, EnumFacing.DOWN);
				}
            }
		}
	}
	
	private boolean isInventoryFull(IInventory inventoryIn, EnumFacing side) {
		if (inventoryIn instanceof ISidedInventory) {
			ISidedInventory isidedinventory = (ISidedInventory) inventoryIn;
			int[] aint = isidedinventory.getSlotsForFace(side);

			for (int k = 0; k < aint.length; ++k) {
				ItemStack itemstack1 = isidedinventory.getStackInSlot(aint[k]);

				if (itemstack1 == null) {
					return false;
				}
			}
		} else {
			int i = inventoryIn.getSizeInventory();

			for (int j = 0; j < i; ++j) {
				ItemStack itemstack = inventoryIn.getStackInSlot(j);

				if (itemstack == null) {
					return false;
				}
			}
		}

		return true;
	}

}
