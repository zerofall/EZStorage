package com.zerofall.ezstorage.jei;

import java.util.List;
import java.util.Map;

import com.zerofall.ezstorage.EZStorage;
import com.zerofall.ezstorage.container.ContainerStorageCoreCrafting;
import com.zerofall.ezstorage.network.RecipeMessage;

import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.gui.ingredients.IGuiIngredient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class RecipeTransferHandler implements IRecipeTransferHandler {

	@Override
	public Class<? extends Container> getContainerClass() {
		return ContainerStorageCoreCrafting.class;
	}

	@Override
	public String getRecipeCategoryUid() {
		return "minecraft.crafting";
	}

	@Override
	public IRecipeTransferError transferRecipe(Container container, IRecipeLayout recipeLayout, EntityPlayer player, boolean maxTransfer,
			boolean doTransfer) {
		if (doTransfer) {
			Map<Integer, ? extends IGuiIngredient<ItemStack>> inputs = recipeLayout.getItemStacks().getGuiIngredients();
			NBTTagCompound recipe = new NBTTagCompound();
			for (Slot slot : (List<Slot>) container.inventorySlots) {
				if (slot.inventory instanceof InventoryCrafting) {
					IGuiIngredient<ItemStack> ingredient = inputs.get(slot.getSlotIndex()+1);
					if (ingredient != null) {
						List<ItemStack> possibleItems = ingredient.getAllIngredients();
						NBTTagList tags = new NBTTagList();
						for (ItemStack is : possibleItems) {
							NBTTagCompound tag = new NBTTagCompound();
							is.writeToNBT(tag);
							tags.appendTag(tag);
						}
						recipe.setTag("#" + slot.getSlotIndex(), tags);
					}
				}
			}
			EZStorage.networkWrapper.sendToServer(new RecipeMessage(recipe));
		}
		return null;
	}
}
