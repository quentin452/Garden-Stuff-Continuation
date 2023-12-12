package com.jaquadro.minecraft.gardencore.block.support;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

import com.jaquadro.minecraft.gardencore.api.block.garden.IConnectionProfile;
import com.jaquadro.minecraft.gardencore.block.tile.TileEntityGarden;

public class BasicConnectionProfile implements IConnectionProfile {

    public boolean isAttachedNeighbor(IBlockAccess blockAccess, int x, int y, int z, int side) {
        switch (side) {
            case 0:
                return this.isAttachedNeighbor(blockAccess, x, y, z, x, y - 1, z);
            case 1:
                return this.isAttachedNeighbor(blockAccess, x, y, z, x, y + 1, z);
            case 2:
                return this.isAttachedNeighbor(blockAccess, x, y, z, x, y, z - 1);
            case 3:
                return this.isAttachedNeighbor(blockAccess, x, y, z, x, y, z + 1);
            case 4:
                return this.isAttachedNeighbor(blockAccess, x, y, z, x - 1, y, z);
            case 5:
                return this.isAttachedNeighbor(blockAccess, x, y, z, x + 1, y, z);
            default:
                return false;
        }
    }

    public boolean isAttachedNeighbor(IBlockAccess blockAccess, int x, int y, int z, int nx, int ny, int nz) {
        if (y == ny && Math.abs(nx - x) <= 1 && Math.abs(nz - z) <= 1) {
            Block sBlock = blockAccess.getBlock(x, y, z);
            Block nBlock = blockAccess.getBlock(nx, ny, nz);
            if (sBlock != nBlock) {
                return false;
            } else {
                int sData = blockAccess.getBlockMetadata(x, y, z);
                int nData = blockAccess.getBlockMetadata(nx, ny, nz);
                if (sData != nData) {
                    return false;
                } else {
                    TileEntity sEntity = blockAccess.getTileEntity(x, y, z);
                    TileEntity nEntity = blockAccess.getTileEntity(nx, ny, nz);
                    if (sEntity != null && nEntity != null && sEntity.getClass() == nEntity.getClass()) {
                        return sEntity instanceof TileEntityGarden;
                    } else {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
    }
}
