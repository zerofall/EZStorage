package com.zerofall.ezstorage.jei;

import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;

@JEIPlugin
public class EZStoragePlugin implements IModPlugin {

	@Override
	public void register(IModRegistry registry) {
		RecipeTransferHandler helper = new RecipeTransferHandler();
		registry.getRecipeTransferRegistry().addRecipeTransferHandler(helper);
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
		// TODO Auto-generated method stub
		
	}


}
