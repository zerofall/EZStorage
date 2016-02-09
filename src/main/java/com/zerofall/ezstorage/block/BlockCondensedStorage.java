package com.zerofall.ezstorage.block;

import com.zerofall.ezstorage.configuration.EZConfiguration;

import net.minecraft.block.material.Material;

public class BlockCondensedStorage extends BlockStorage {

	public BlockCondensedStorage() {
		super("condensed_storage_box", Material.iron);
	}
	
	@Override
	public int getCapacity() {
		return EZConfiguration.condensedCapacity;
	}
}
