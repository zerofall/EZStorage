package com.zerofall.ezstorage.block;

import com.zerofall.ezstorage.EZStorage;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class EZBlock extends Block {

	protected EZBlock(String name, Material materialIn) {
		super(materialIn);
		this.setUnlocalizedName(name);
		this.setCreativeTab(EZStorage.instance.creativeTab);
		this.setHardness(2.0f);
	}

}
