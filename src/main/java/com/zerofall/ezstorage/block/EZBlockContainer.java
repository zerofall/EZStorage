package com.zerofall.ezstorage.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.zerofall.ezstorage.EZStorage;

public class EZBlockContainer extends StorageMultiblock implements ITileEntityProvider {

	protected EZBlockContainer(String name, Material materialIn) {
		super(name, materialIn);
		this.setUnlocalizedName(name);
		this.setCreativeTab(EZStorage.instance.creativeTab);
		this.isBlockContainer = true;
	}

	public int getRenderType()
    {
        return -1;
    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }

    /**
     * Called on both Client and Server when World#addBlockEvent is called
     */
    public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam)
    {
        super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity == null ? false : tileentity.receiveClientEvent(eventID, eventParam);
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return null;
	}

}