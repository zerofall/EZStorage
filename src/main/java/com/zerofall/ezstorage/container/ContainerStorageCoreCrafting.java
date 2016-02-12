package com.zerofall.ezstorage.container;

import com.zerofall.ezstorage.util.EZStorageUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;

public class ContainerStorageCoreCrafting extends ContainerStorageCore {
	
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
    public IInventory craftResult = new InventoryCraftResult();
    private World worldObj;

	public ContainerStorageCoreCrafting(EntityPlayer player, World world,
			int x, int y, int z) {
		super(player, world, x, y, z);
		this.worldObj = world;
		this.addSlotToContainer(new SlotCrafting(player, this.craftMatrix, this.craftResult, 0, 116, 117));
        int i;
        int j;

        for (i = 0; i < 3; ++i)
        {
            for (j = 0; j < 3; ++j)
            {
                this.addSlotToContainer(new Slot(this.craftMatrix, j + i * 3, 44 + j * 18, 99 + i * 18));
            }
        }
        this.onCraftMatrixChanged(this.craftMatrix);
	}
	
	public void onCraftMatrixChanged(IInventory inventoryIn) {
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj));
    }
	
	//Shift clicking
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        Slot slotObject = (Slot) inventorySlots.get(index);
        if (slotObject != null && slotObject.getHasStack()) {
        	if (slotObject instanceof SlotCrafting) {
        		ItemStack[] recipe = new ItemStack[9];
        		for (int i=0; i<9; i++) {
        			recipe[i] = this.craftMatrix.getStackInSlot(i);
        		}
        		
        		ItemStack itemstack1 = slotObject.getStack();
        		ItemStack itemstack = null;
        		ItemStack original = itemstack1.copy();
        		int crafted = 0;
        		int maxStackSize = itemstack1.getMaxStackSize();
        		int crafting = itemstack1.stackSize;
                for (int i=0; i < itemstack1.getMaxStackSize(); i++) {
                	if (slotObject.getHasStack() && slotObject.getStack().isItemEqual(itemstack1)) {
                		if (crafting >= maxStackSize) {
                			return null;
                		}
                		itemstack1 = slotObject.getStack();
                		itemstack = itemstack1.copy();
                		if (crafted + itemstack1.stackSize > itemstack1.getMaxStackSize()) {
                			return null;
                		}
	                	boolean merged = this.mergeItemStack(itemstack1, this.rowCount()*9, this.rowCount()*9+36, true);
	                	if (!merged) {
	                		return null;
	                	} else {
	                		
	                		//It merged! grab another
	                		crafted += itemstack.stackSize;
	                		slotObject.onSlotChange(itemstack1, itemstack);
	                		slotObject.onPickupFromSlot(playerIn, itemstack1);
	                		
	                		if (original.isItemEqual(slotObject.getStack())) {
	                			continue;
	                		}
	                		
	                		tryToPopulateCraftingGrid(recipe);
	                	}
                	} else {
                		break;
                	}
                }
                
                if (itemstack1.stackSize == itemstack.stackSize) {
                    return null;
                }

                
                return itemstack;
        	} else {
        		ItemStack stackInSlot = slotObject.getStack();
            	slotObject.putStack(this.tileEntity.inventory.input(stackInSlot));
        	}
        }
        return null;
	}
	
	@Override
	public ItemStack slotClick(int slotId, int clickedButton, int mode, EntityPlayer playerIn) {
		
		if (mode == 0 && clickedButton == 0) {		
    		if (slotId >= 0 && inventorySlots.size() > slotId) {
				Slot slotObject = inventorySlots.get(slotId);
		        if (slotObject != null) {
		        	if (slotObject instanceof SlotCrafting) {
		        		ItemStack[] recipe = new ItemStack[9];
		        		for (int i=0; i<9; i++) {
		        			recipe[i] = this.craftMatrix.getStackInSlot(i);
		        		}
		        		ItemStack result = super.slotClick(slotId, clickedButton, mode, playerIn);
		        		if (result != null) {
		        			tryToPopulateCraftingGrid(recipe);
		        		}
		        		return result;
		        	}
		        }
    		}
    		
		}
		return super.slotClick(slotId, clickedButton, mode, playerIn);
	}
	
	private void tryToPopulateCraftingGrid(ItemStack[] recipe) {
		for (int j = 0; j < recipe.length; j++) {
			if (recipe[j] != null) {
				if (recipe[j].stackSize > 1) {
					continue;
				} else {
					recipe[j].stackSize = 1;
				}
				Slot slot = getSlotFromInventory(this.craftMatrix, j);
				if (slot != null) {
					ItemStack retreived = tileEntity.inventory.getItems(new ItemStack[]{recipe[j]});
					if (retreived != null) {
						slot.putStack(retreived);
					}
				}
			}
		}
	}
	
	@Override
	protected int playerInventoryY() {
		return 162;
	}
	
	@Override
	protected int rowCount() {
		return 4;
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		clearGrid(playerIn);
		super.onContainerClosed(playerIn);
	}
	
	public void clearGrid(EntityPlayer playerIn){
		for (int i=0; i<9; i++) {
			ItemStack stack = this.craftMatrix.getStackInSlot(i);
			if (stack != null) {
				ItemStack result = this.tileEntity.input(stack);
				if (result != null) {
					playerIn.dropPlayerItemWithRandomChoice(result, false);
				}
			}
		}
	}
}
