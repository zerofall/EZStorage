package com.zerofall.ezstorage.util;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class BlockRef {
	
	public BlockPos pos;
	public Block block;
	
	public BlockRef(Block block, int x, int y, int z) {
		this.block = block;
		this.pos = new BlockPos(x, y, z);
	}

	public BlockRef(TileEntity entity) {
		this.block = entity.getBlockType();
		this.pos = entity.getPos();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((block == null) ? 0 : block.hashCode());
		result = prime * result
				+ ((pos == null) ? 0 : pos.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlockRef other = (BlockRef) obj;
		if (block == null) {
			if (other.block != null)
				return false;
		} else if (!Block.isEqualTo(block, other.block))
			return false;
		if (pos == null) {
			if (other.pos != null)
				return false;
		} else if (!pos.equals(other.pos))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BlockRef [pos=" + pos + ", block=" + block + "]";
	}
}