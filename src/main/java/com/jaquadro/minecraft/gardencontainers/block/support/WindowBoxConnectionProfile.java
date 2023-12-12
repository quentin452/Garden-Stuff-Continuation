package com.jaquadro.minecraft.gardencontainers.block.support;

import com.jaquadro.minecraft.gardencontainers.block.tile.TileEntityWindowBox;
import com.jaquadro.minecraft.gardencore.block.support.BasicConnectionProfile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

public class WindowBoxConnectionProfile extends BasicConnectionProfile {

    public boolean isAttachedNeighbor(IBlockAccess blockAccess, int x, int y, int z, int nx, int ny, int nz) {
        if (!super.isAttachedNeighbor(blockAccess, x, y, z, nx, ny, nz)) {
            return false;
        } else {
            TileEntityWindowBox ste = (TileEntityWindowBox) blockAccess.getTileEntity(x, y, z);
            TileEntityWindowBox nte = (TileEntityWindowBox) blockAccess.getTileEntity(nx, ny, nz);
            if (ste != null && nte != null) {
                if (ste.isUpper() != nte.isUpper()) {
                    return false;
                } else {
                    int dir = ste.getDirection();
                    int ndir = nte.getDirection();
                    int rdir = dir % 2 == 0 ? dir + 1 : dir - 1;
                    int xzDir = this.neighborToDirection(x, z, nx, nz);
                    if (dir != xzDir && rdir != xzDir) {
                        TileEntity te = null;
                        if (dir == 2) {
                            te = blockAccess.getTileEntity(nx, ny, nz - 1);
                        } else if (dir == 3) {
                            te = blockAccess.getTileEntity(nx, ny, nz + 1);
                        } else if (dir == 4) {
                            te = blockAccess.getTileEntity(nx - 1, ny, nz);
                        } else if (dir == 5) {
                            te = blockAccess.getTileEntity(nx + 1, ny, nz);
                        }

                        if (te instanceof TileEntityWindowBox) {
                            TileEntityWindowBox dte = (TileEntityWindowBox) te;
                            int ddir = dte.getDirection();
                            if ((dir == 2 || dir == 3) && (ddir == 4 || ddir == 5)) {
                                return ddir == this.neighborToDirection(x, z, nx, nz);
                            }

                            if ((dir == 4 || dir == 5) && (ddir == 2 || ddir == 3)) {
                                return ddir == this.neighborToDirection(x, z, nx, nz);
                            }
                        }
                    } else {
                        switch (dir) {
                            case 2:
                            case 3:
                                return ndir == 4 || ndir == 5;
                            case 4:
                            case 5:
                                return ndir == 2 || ndir == 3;
                        }
                    }

                    switch (dir) {
                        case 2:
                        case 3:
                            if (nx - x != 0 && ndir == dir || nx - x < 0 && ndir == 4 || nx - x > 0 && ndir == 5) {
                                break;
                            }

                            return false;
                        case 4:
                        case 5:
                            if ((nz - z == 0 || ndir != dir) && (nz - z >= 0 || ndir != 2)
                                && (nz - z <= 0 || ndir != 3)) {
                                return false;
                            }
                            break;
                        default:
                            return false;
                    }

                    return true;
                }
            } else {
                return false;
            }
        }
    }

    private int neighborToDirection(int x, int z, int nx, int nz) {
        if (nz < z) {
            return 2;
        } else if (nz > z) {
            return 3;
        } else if (nx < x) {
            return 4;
        } else {
            return nx > x ? 5 : -1;
        }
    }
}
