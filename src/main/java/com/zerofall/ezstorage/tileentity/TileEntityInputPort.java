package com.zerofall.ezstorage.tileentity;

import com.zerofall.ezstorage.util.BlockRef;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class TileEntityInputPort extends TileEntity implements IInventory, ITickable, ISidedInventory {

	private ItemStack[] inv = new ItemStack[1];
	public TileEntityStorageCore core;
	
//	@Override
//	public String getName() {
//		return "input_port";
//	}

	@Override
	public boolean hasCustomName() {
		return false;
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString("input_port");
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return inv[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack stack = getStackInSlot(index);
	      if(stack != null) {
	         if(stack.stackSize <= count) {
	            setInventorySlotContents(index, null);
	         } else {
	            stack = stack.splitStack(count);
	            if(stack.stackSize == 0) {
	               setInventorySlotContents(index, null);
	            }
	         }
	      }
	      return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		inv[index] = stack;
        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
        	stack.stackSize = getInventoryStackLimit();
        }  
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		for (int i = 0; i < inv.length; ++i) {
			inv[i] = null;
		}
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		int[] slots = new int[1];
		slots[0] = 0;
		return slots;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn,
			EnumFacing direction) {
		return true;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack,
			EnumFacing direction) {
		return false;
	}
	

	@Override
	public void update() {
		
		if (this.core != null) {
			ItemStack stack = this.inv[0];
			if (stack != null && stack.stackSize > 0) {
				if (this.core.isPartOfMultiblock(new BlockRef(this))) { 
					this.inv[0] = this.core.input(stack);
				} else {
					this.core = null;
				}
			}
		}
	}

	@Override
	public String getName() {
		return "input_port";
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return null;
	}
}
