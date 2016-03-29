package com.zerofall.ezstorage.block;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zerofall.ezstorage.tileentity.TileEntityStorageCore;
import com.zerofall.ezstorage.util.BlockRef;
import com.zerofall.ezstorage.util.EZStorageUtils;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class StorageMultiblock extends EZBlock {
	
	protected StorageMultiblock(String name, Material material) {
		super(name, material);
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos,
			IBlockState state) {
		super.onBlockDestroyedByPlayer(worldIn, pos, state);
		attemptMultiblock(worldIn, pos);
	}
	
	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos,
			Explosion explosionIn) {
		super.onBlockDestroyedByExplosion(worldIn, pos, explosionIn);
		attemptMultiblock(worldIn, pos);
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockAdded(worldIn, pos, state);
		attemptMultiblock(worldIn, pos);
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		if (!worldIn.isRemote) {
			Set<TileEntityStorageCore> coreSet = new HashSet<TileEntityStorageCore>();
			BlockRef br = new BlockRef(this, pos.getX(),  pos.getY(),  pos.getZ());
			findMultipleCores(br, worldIn, null, coreSet);
			if (coreSet.size() > 1) {
				return false;
			}
		}
		return super.canPlaceBlockAt(worldIn, pos);
	}
	
	/**
	 * Attempt to form the multiblock structure by searching for the core, then telling the core to scan the multiblock
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 */
	public void attemptMultiblock(World world, BlockPos position) {
		if (!world.isRemote) {
			if (!(this instanceof BlockStorageCore)) {
				BlockRef br = new BlockRef(this, position.getX(),  position.getY(),  position.getZ());
				TileEntityStorageCore core = findCore(br, world, null);
				if (core != null) {
					core.scanMultiblock();
				}
			}
		}
	}
	
	
	/**
	 * Recursive function that searches for a StorageCore in a multiblock structure
	 * @param br
	 * @param world
	 * @param scanned
	 * @return
	 */
	public TileEntityStorageCore findCore(BlockRef br, World world, Set<BlockRef> scanned) {
		if (scanned == null) {
			scanned = new HashSet<BlockRef>();
		}
		List<BlockRef> neighbors = EZStorageUtils.getNeighbors(br.pos.getX(), br.pos.getY(), br.pos.getZ(), world);
		for (BlockRef blockRef : neighbors) {
			if (blockRef.block instanceof StorageMultiblock) {
				if (blockRef.block instanceof BlockStorageCore) {
					return (TileEntityStorageCore)world.getTileEntity(blockRef.pos);
				} else {
					if (scanned.add(blockRef) == true) {
						TileEntityStorageCore entity = findCore(blockRef, world, scanned);
						if (entity != null) {
							return entity;
						}
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Recursive function that searches for a StorageCore in a multiblock structure
	 * @param br
	 * @param world
	 * @param scanned
	 * @return
	 */
	public void findMultipleCores(BlockRef br, World world, Set<BlockRef> scanned, Set<TileEntityStorageCore> cores) {
		if (scanned == null) {
			scanned = new HashSet<BlockRef>();
		}
		List<BlockRef> neighbors = EZStorageUtils.getNeighbors(br.pos.getX(), br.pos.getY(), br.pos.getZ(), world);
		for (BlockRef blockRef : neighbors) {
			if (blockRef.block instanceof StorageMultiblock) {
				if (blockRef.block instanceof BlockStorageCore) {
					cores.add((TileEntityStorageCore)world.getTileEntity(blockRef.pos));
				} else {
					if (scanned.add(blockRef) == true) {
						findMultipleCores(blockRef, world, scanned, cores);
					}
				}
			}
		}
	}
}
