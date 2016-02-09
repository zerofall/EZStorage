package com.zerofall.ezstorage.block;

import com.zerofall.ezstorage.configuration.EZConfiguration;

import net.minecraft.block.material.Material;

public class BlockStorage extends StorageMultiblock {
	
	public BlockStorage() {
		super("storage_box", Material.wood);
	}
	
	public BlockStorage(String name, Material material) {
		super(name, material);
	}
	
	public int getCapacity() {
		return EZConfiguration.basicCapacity;
	}
}