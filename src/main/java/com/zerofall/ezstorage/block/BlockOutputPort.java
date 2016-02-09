package com.zerofall.ezstorage.block;

import com.zerofall.ezstorage.tileentity.TileEntityOutputPort;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockOutputPort extends EZBlockContainer {
	
	public BlockOutputPort() {
		super("output_port", Material.iron);
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityOutputPort();
	}
	
	@Override
	public int getRenderType() {
		return 3;
	}
}
