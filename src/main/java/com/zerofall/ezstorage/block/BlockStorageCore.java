package com.zerofall.ezstorage.block;

import com.zerofall.ezstorage.EZStorage;
import com.zerofall.ezstorage.tileentity.TileEntityStorageCore;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockStorageCore extends EZBlockContainer {

	public BlockStorageCore() {
		super("storage_core", Material.wood);
		this.setResistance(6000.0f);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityStorageCore();
	}
	
	@Override
	public int getRenderType() {
		return 3;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntityStorageCore tileEntity = (TileEntityStorageCore)worldIn.getTileEntity(pos);
		if (tileEntity.inventory.getTotalCount() > 0) {
			super.breakBlock(worldIn, pos, state);
		}
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			TileEntityStorageCore tileEntity = (TileEntityStorageCore)worldIn.getTileEntity(pos);
			if (tileEntity.hasCraftBox) {
				playerIn.openGui(EZStorage.instance, 2, worldIn, pos.getX(), pos.getY(), pos.getZ());
			} else {
				playerIn.openGui(EZStorage.instance, 1, worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
			
		}
		return true;
	}
	
}
