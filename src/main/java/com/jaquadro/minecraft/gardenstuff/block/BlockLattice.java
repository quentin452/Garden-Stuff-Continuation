package com.jaquadro.minecraft.gardenstuff.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.jaquadro.minecraft.gardenapi.api.GardenAPI;
import com.jaquadro.minecraft.gardenapi.api.connect.IAttachable;
import com.jaquadro.minecraft.gardenapi.api.connect.IChainSingleAttachable;
import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;
import com.jaquadro.minecraft.gardenstuff.block.tile.TileEntityLattice;
import com.jaquadro.minecraft.gardenstuff.core.ClientProxy;
import com.jaquadro.minecraft.gardentrees.block.BlockThinLog;

public abstract class BlockLattice extends BlockContainer implements IChainSingleAttachable {

    private static final float UN4 = -0.25F;
    private static final float U7 = 0.4375F;
    private static final float U8 = 0.5F;
    private static final float U9 = 0.5625F;
    private static final float U20 = 1.25F;
    private final Vec3[] attachPoints = new Vec3[] { Vec3.createVectorHelper(0.5D, 0.5D, 0.5D),
        Vec3.createVectorHelper(0.5D, 0.5D, 0.5D), Vec3.createVectorHelper(0.5D, 0.5D, 0.5D),
        Vec3.createVectorHelper(0.5D, 0.5D, 0.5D), Vec3.createVectorHelper(0.5D, 0.5D, 0.5D),
        Vec3.createVectorHelper(0.5D, 0.5D, 0.5D) };

    public BlockLattice(String blockName, Material material) {
        super(material);
        this.setBlockName(blockName);
        this.setHardness(2.5F);
        this.setResistance(5.0F);
        this.setBlockTextureName("GardenStuff:lattice_iron");
        this.setCreativeTab(ModCreativeTabs.tabGardenCore);
        this.setBlockBoundsForItemRender();
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public int getRenderType() {
        return ClientProxy.latticeRenderID;
    }

    public int damageDropped(int meta) {
        return meta;
    }

    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        int connectFlags = this.calcConnectionFlags(world, x, y, z);
        float margin = 0.375F;
        float ys = (connectFlags & 1) != 0 ? 0.0F : margin;
        float ye = (connectFlags & 2) != 0 ? 1.0F : 1.0F - margin;
        float zs = (connectFlags & 4) != 0 ? 0.0F : margin;
        float ze = (connectFlags & 8) != 0 ? 1.0F : 1.0F - margin;
        float xs = (connectFlags & 16) != 0 ? 0.0F : margin;
        float xe = (connectFlags & 32) != 0 ? 1.0F : 1.0F - margin;
        this.setBlockBounds(xs, ys, zs, xe, ye, ze);
    }

    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list,
        Entity colliding) {
        int connectFlags = this.calcConnectionFlags(world, x, y, z);
        boolean connectYNeg = (connectFlags & 1) != 0;
        boolean connectYPos = (connectFlags & 2) != 0;
        boolean connectZNeg = (connectFlags & 4) != 0;
        boolean connectZPos = (connectFlags & 8) != 0;
        boolean connectXNeg = (connectFlags & 16) != 0;
        boolean connectXPos = (connectFlags & 32) != 0;
        boolean extYNeg = (connectFlags & 64) != 0;
        boolean extYPos = (connectFlags & 128) != 0;
        boolean extZNeg = (connectFlags & 256) != 0;
        boolean extZPos = (connectFlags & 512) != 0;
        boolean extXNeg = (connectFlags & 1024) != 0;
        boolean extXPos = (connectFlags & 2048) != 0;
        float yMin = extYNeg ? -0.25F : (connectYNeg ? 0.0F : 0.4375F);
        float yMax = extYPos ? 1.25F : (connectYPos ? 1.0F : 0.5625F);
        IAttachable attachableYN = GardenAPI.instance()
            .registries()
            .attachable()
            .getAttachable(world.getBlock(x, y - 1, z), world.getBlockMetadata(x, y - 1, z));
        if (attachableYN != null && attachableYN.isAttachable(world, x, y - 1, z, 1)) {
            yMin = (float) attachableYN.getAttachDepth(world, x, y - 1, z, 1) - 1.0F;
        }

        IAttachable attachableYP = GardenAPI.instance()
            .registries()
            .attachable()
            .getAttachable(world.getBlock(x, y + 1, z), world.getBlockMetadata(x, y + 1, z));
        if (attachableYP != null && attachableYP.isAttachable(world, x, y + 1, z, 0)) {
            yMax = (float) attachableYP.getAttachDepth(world, x, y + 1, z, 0) + 1.0F;
        }

        float zMin = extZNeg ? -0.25F : (connectZNeg ? 0.0F : 0.4375F);
        float zMax = extZPos ? 1.25F : (connectZPos ? 1.0F : 0.5625F);
        float xMin = extXNeg ? -0.25F : (connectXNeg ? 0.0F : 0.4375F);
        float xMax = extXPos ? 1.25F : (connectXPos ? 1.0F : 0.5625F);
        this.setBlockBounds(0.4375F, yMin, 0.4375F, 0.5625F, yMax, 0.5625F);
        super.addCollisionBoxesToList(world, x, y, z, mask, list, colliding);
        this.setBlockBounds(xMin, 0.4375F, 0.4375F, xMax, 0.5625F, 0.5625F);
        super.addCollisionBoxesToList(world, x, y, z, mask, list, colliding);
        this.setBlockBounds(0.4375F, 0.4375F, zMin, 0.5625F, 0.5625F, zMax);
        super.addCollisionBoxesToList(world, x, y, z, mask, list, colliding);
        this.setBlockBoundsBasedOnState(world, x, y, z);
    }

    public ArrayList getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList ret = new ArrayList();
        int count = this.quantityDropped(metadata, fortune, world.rand);

        for (int i = 0; i < count; ++i) {
            Item item = this.getItemDropped(metadata, world.rand, fortune);
            if (item != null) {
                ret.add(new ItemStack(item, 1, this.damageDropped(metadata)));
            }
        }

        return ret;
    }

    public int calcConnectionFlags(IBlockAccess world, int x, int y, int z) {
        Block blockYNeg = world.getBlock(x, y - 1, z);
        Block blockYPos = world.getBlock(x, y + 1, z);
        Block blockZNeg = world.getBlock(x, y, z - 1);
        Block blockZPos = world.getBlock(x, y, z + 1);
        Block blockXNeg = world.getBlock(x - 1, y, z);
        Block blockXPos = world.getBlock(x + 1, y, z);
        boolean hardYNeg = this.isNeighborHardConnection(world, x, y - 1, z, blockYNeg, ForgeDirection.DOWN);
        boolean hardYPos = this.isNeighborHardConnection(world, x, y + 1, z, blockYPos, ForgeDirection.UP);
        boolean hardZNeg = this.isNeighborHardConnection(world, x, y, z - 1, blockZNeg, ForgeDirection.NORTH);
        boolean hardZPos = this.isNeighborHardConnection(world, x, y, z + 1, blockZPos, ForgeDirection.SOUTH);
        boolean hardXNeg = this.isNeighborHardConnection(world, x - 1, y, z, blockXNeg, ForgeDirection.WEST);
        boolean hardXPos = this.isNeighborHardConnection(world, x + 1, y, z, blockXPos, ForgeDirection.EAST);
        boolean extYNeg = this.isNeighborExtConnection(world, x, y - 1, z, blockYNeg, ForgeDirection.DOWN);
        boolean extYPos = this.isNeighborExtConnection(world, x, y + 1, z, blockYPos, ForgeDirection.UP);
        boolean extZNeg = this.isNeighborExtConnection(world, x, y, z - 1, blockZNeg, ForgeDirection.NORTH);
        boolean extZPos = this.isNeighborExtConnection(world, x, y, z + 1, blockZPos, ForgeDirection.SOUTH);
        boolean extXNeg = this.isNeighborExtConnection(world, x - 1, y, z, blockXNeg, ForgeDirection.WEST);
        boolean extXPos = this.isNeighborExtConnection(world, x + 1, y, z, blockXPos, ForgeDirection.EAST);
        return (hardYNeg ? 1 : 0) | (hardYPos ? 2 : 0)
            | (hardZNeg ? 4 : 0)
            | (hardZPos ? 8 : 0)
            | (hardXNeg ? 16 : 0)
            | (hardXPos ? 32 : 0)
            | (extYNeg ? 64 : 0)
            | (extYPos ? 128 : 0)
            | (extZNeg ? 256 : 0)
            | (extZPos ? 512 : 0)
            | (extXNeg ? 1024 : 0)
            | (extXPos ? 2048 : 0);
    }

    private boolean isNeighborHardConnection(IBlockAccess world, int x, int y, int z, Block block,
        ForgeDirection side) {
        if (block.getMaterial()
            .isOpaque() && block.renderAsNormalBlock()) {
            return true;
        } else if (block.isSideSolid(world, x, y, z, side.getOpposite())) {
            return true;
        } else if (block == this) {
            return true;
        } else {
            return (side == ForgeDirection.DOWN || side == ForgeDirection.UP)
                && (block instanceof BlockFence || block instanceof net.minecraft.block.BlockFence);
        }
    }

    private boolean isNeighborExtConnection(IBlockAccess world, int x, int y, int z, Block block, ForgeDirection side) {
        return block instanceof BlockThinLog;
    }

    protected TileEntityLattice getTileEntity(IBlockAccess blockAccess, int x, int y, int z) {
        TileEntity te = blockAccess.getTileEntity(x, y, z);
        return te != null && te instanceof TileEntityLattice ? (TileEntityLattice) te : null;
    }

    public Vec3 getChainAttachPoint(IBlockAccess blockAccess, int x, int y, int z, int side) {
        int connectFlags = this.calcConnectionFlags(blockAccess, x, y, z);
        switch (side) {
            case 0:
                return (connectFlags & 1) == 0 ? this.attachPoints[0] : null;
            case 1:
                return (connectFlags & 2) == 0 ? this.attachPoints[1] : null;
            case 2:
                return (connectFlags & 4) == 0 ? this.attachPoints[2] : null;
            case 3:
                return (connectFlags & 8) == 0 ? this.attachPoints[3] : null;
            case 4:
                return (connectFlags & 16) == 0 ? this.attachPoints[4] : null;
            case 5:
                return (connectFlags & 32) == 0 ? this.attachPoints[5] : null;
            default:
                return null;
        }
    }
}
