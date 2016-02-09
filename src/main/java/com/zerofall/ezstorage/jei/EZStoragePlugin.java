package com.zerofall.ezstorage.jei;

import com.zerofall.ezstorage.container.ContainerStorageCoreCrafting;

import mezz.jei.api.IItemRegistry;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.inventory.ContainerWorkbench;

@JEIPlugin
public class EZStoragePlugin implements IModPlugin {

	@Override
	public void register(IModRegistry registry) {
		RecipeTransferHandler helper = new RecipeTransferHandler();
		registry.getRecipeTransferRegistry().addRecipeTransferHandler(helper);
	}

	@Override
	public void onJeiHelpersAvailable(IJeiHelpers jeiHelpers) {
		
	}

	@Override
	public void onItemRegistryAvailable(IItemRegistry itemRegistry) {
		
	}

	@Override
	public void onRecipeRegistryAvailable(IRecipeRegistry recipeRegistry) {
		
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
		// TODO Auto-generated method stub
		
	}


}
