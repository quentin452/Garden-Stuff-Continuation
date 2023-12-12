package com.jaquadro.minecraft.gardencore.block.support;

import com.jaquadro.minecraft.gardencore.block.tile.TileEntityGarden;
import net.minecraft.world.IBlockAccess;

public class ContainerConnectionProfile extends BasicConnectionProfile {

    public boolean isAttachedNeighbor(IBlockAccess blockAccess, int x, int y, int z, int nx, int ny, int nz) {
        if (!super.isAttachedNeighbor(blockAccess, x, y, z, nx, ny, nz)) {
            return false;
        } else {
            TileEntityGarden sEntity = (TileEntityGarden) blockAccess.getTileEntity(x, y, z);
            TileEntityGarden nEntity = (TileEntityGarden) blockAccess.getTileEntity(nx, ny, nz);
            if ((sEntity.getSubstrate() == null || nEntity.getSubstrate() == null)
                && sEntity.getSubstrate() != nEntity.getSubstrate()) {
                return false;
            } else {
                return sEntity.getSubstrate() == null || sEntity.getSubstrate()
                    .getItem()
                    == nEntity.getSubstrate()
                        .getItem()
                    && sEntity.getSubstrate()
                        .getItemDamage()
                        == nEntity.getSubstrate()
                            .getItemDamage();
            }
        }
    }
}
