package com.zerofall.ezstorage.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.zerofall.ezstorage.EZStorage;
import com.zerofall.ezstorage.tileentity.TileEntityInputPort;

public class BlockInputPort extends EZBlockContainer {
	
	public BlockInputPort() {
		super("input_port", Material.iron);
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityInputPort();
	}
	
	@Override
	public int getRenderType() {
		return 3;
	}
}