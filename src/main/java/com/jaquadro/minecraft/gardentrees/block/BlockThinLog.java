package com.jaquadro.minecraft.gardentrees.block;

import com.jaquadro.minecraft.gardenapi.api.connect.IChainSingleAttachable;
import com.jaquadro.minecraft.gardencore.api.WoodRegistry;
import com.jaquadro.minecraft.gardencore.util.UniqueMetaIdentifier;
import com.jaquadro.minecraft.gardentrees.block.tile.TileEntityWoodProxy;
import com.jaquadro.minecraft.gardentrees.core.ClientProxy;
import com.jaquadro.minecraft.gardentrees.core.ModBlocks;
import com.jaquadro.minecraft.gardentrees.core.ModCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

public class BlockThinLog extends BlockContainer implements IChainSingleAttachable {

    public static final String[] subNames = new String[] { "oak", "spruce", "birch", "jungle", "acacia", "big_oak" };
    private int orientation;
    private final Vec3[] attachPoints = new Vec3[] { Vec3.createVectorHelper(0.5D, (double) this.getMargin(), 0.5D),
        Vec3.createVectorHelper(0.5D, (double) (1.0F - this.getMargin()), 0.5D),
        Vec3.createVectorHelper(0.5D, 0.5D, (double) this.getMargin()),
        Vec3.createVectorHelper(0.5D, 0.5D, (double) (1.0F - this.getMargin())),
        Vec3.createVectorHelper((double) this.getMargin(), 0.5D, 0.5D),
        Vec3.createVectorHelper((double) (1.0F - this.getMargin()), 0.5D, 0.5D) };

    public BlockThinLog(String blockName) {
        super(Material.wood);
        this.setCreativeTab(ModCreativeTabs.tabGardenTrees);
        this.setHardness(1.5F);
        this.setResistance(5.0F);
        this.setLightOpacity(0);
        this.setStepSound(Block.soundTypeWood);
        this.setBlockName(blockName);
        this.setBlockBoundsForItemRender();
    }

    public float getMargin() {
        return 0.25F;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public void setBlockBoundsForItemRender() {
        float margin = this.getMargin();
        this.setBlockBounds(margin, 0.0F, margin, 1.0F - margin, 1.0F, 1.0F - margin);
    }

    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list,
        Entity colliding) {
        int connectFlags = this.calcConnectionFlags(world, x, y, z);
        float margin = this.getMargin();
        float ys = (connectFlags & 1) != 0 ? 0.0F : margin;
        float ye = (connectFlags & 2) != 0 ? 1.0F : 1.0F - margin;
        float zs = (connectFlags & 4) != 0 ? 0.0F : margin;
        float ze = (connectFlags & 8) != 0 ? 1.0F : 1.0F - margin;
        float xs = (connectFlags & 16) != 0 ? 0.0F : margin;
        float xe = (connectFlags & 32) != 0 ? 1.0F : 1.0F - margin;
        this.setBlockBounds(xs, ys, zs, xe, ye, ze);
        super.addCollisionBoxesToList(world, x, y, z, mask, list, colliding);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        int connectFlags = this.calcConnectionFlags(world, x, y, z);
        float margin = this.getMargin();
        float ys = (connectFlags & 1) != 0 ? 0.0F : margin;
        float ye = (connectFlags & 2) != 0 ? 1.0F : 1.0F - margin;
        float zs = (connectFlags & 4) != 0 ? 0.0F : margin;
        float ze = (connectFlags & 8) != 0 ? 1.0F : 1.0F - margin;
        float xs = (connectFlags & 16) != 0 ? 0.0F : margin;
        float xe = (connectFlags & 32) != 0 ? 1.0F : 1.0F - margin;
        this.setBlockBounds(xs, ys, zs, xe, ye, ze);
    }

    public int quantityDropped(Random random) {
        return 1;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
        return true;
    }

    public int getRenderType() {
        return ClientProxy.thinLogRenderID;
    }

    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
        return true;
    }

    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        byte range = 4;
        int height = range + 1;
        if (world.checkChunksExist(x - height, y - height, z - height, x + height, y + height, z + height)) {
            for (int dx = -range; dx <= range; ++dx) {
                for (int dy = -range; dy <= range; ++dy) {
                    for (int dz = -range; dz <= range; ++dz) {
                        Block leaf = world.getBlock(x + dx, y + dy, z + dz);
                        if (leaf.isLeaves(world, x + dx, y + dy, z + dz)) {
                            leaf.beginLeavesDecay(world, x + dx, y + dy, z + dz);
                        }
                    }
                }
            }
        }

        super.breakBlock(world, x, y, z, block, meta);
    }

    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
        return willHarvest ? true : super.removedByPlayer(world, player, x, y, z, willHarvest);
    }

    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
        super.harvestBlock(world, player, x, y, z, meta);
        world.setBlockToAir(x, y, z);
    }

    public int damageDropped(int meta) {
        return meta;
    }

    public ArrayList getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        TileEntityWoodProxy tile = this.getTileEntity(world, x, y, z);
        ArrayList ret = new ArrayList();
        int count = this.quantityDropped(metadata, fortune, world.rand);

        for (int i = 0; i < count; ++i) {
            Item item = this.getItemDropped(metadata, world.rand, fortune);
            if (item != null) {
                int damage = this.damageDropped(metadata);
                if (tile != null && tile.getProtoBlock() != null) {
                    damage = TileEntityWoodProxy.composeMetadata(tile.getProtoBlock(), tile.getProtoMeta());
                }

                ItemStack stack = new ItemStack(item, 1, damage);
                ret.add(stack);
            }
        }

        return ret;
    }

    public int calcConnectionFlags(IBlockAccess world, int x, int y, int z) {
        int flagsY = this.calcConnectYFlags(world, x, y, z);
        int flagsZNeg = this.calcConnectYFlags(world, x, y, z - 1);
        int flagsZPos = this.calcConnectYFlags(world, x, y, z + 1);
        int flagsXNeg = this.calcConnectYFlags(world, x - 1, y, z);
        int flagsXPos = this.calcConnectYFlags(world, x + 1, y, z);
        int connectFlagsY = flagsY & 3;
        int connectFlagsZNeg = flagsZNeg & 3;
        int connectFlagsZPos = flagsZPos & 3;
        int connectFlagsXNeg = flagsXNeg & 3;
        int connectFlagsXPos = flagsXPos & 3;
        Block blockZNeg = world.getBlock(x, y, z - 1);
        Block blockZPos = world.getBlock(x, y, z + 1);
        Block blockXNeg = world.getBlock(x - 1, y, z);
        Block blockXPos = world.getBlock(x + 1, y, z);
        boolean hardZNeg = this.isNeighborHardConnection(world, x, y, z - 1, blockZNeg, ForgeDirection.NORTH)
            || blockZNeg instanceof BlockTorch;
        boolean hardZPos = this.isNeighborHardConnection(world, x, y, z + 1, blockZPos, ForgeDirection.SOUTH)
            || blockZPos instanceof BlockTorch;
        boolean hardXNeg = this.isNeighborHardConnection(world, x - 1, y, z, blockXNeg, ForgeDirection.WEST)
            || blockXNeg instanceof BlockTorch;
        boolean hardXPos = this.isNeighborHardConnection(world, x + 1, y, z, blockXPos, ForgeDirection.EAST)
            || blockXPos instanceof BlockTorch;
        boolean hardConnection = (flagsY & 4) != 0;
        boolean hardConnectionZNeg = hardConnection && (flagsZNeg & 4) != 0;
        boolean hardConnectionZPos = hardConnection && (flagsZPos & 4) != 0;
        boolean hardConnectionXNeg = hardConnection && (flagsXNeg & 4) != 0;
        boolean hardConnectionXPos = hardConnection && (flagsXPos & 4) != 0;
        boolean connectZNeg = connectFlagsY == 0 && hardZNeg
            || blockZNeg == this && !hardConnectionZNeg && (connectFlagsY != 3 || connectFlagsZNeg != 3);
        boolean connectZPos = connectFlagsY == 0 && hardZPos
            || blockZPos == this && !hardConnectionZPos && (connectFlagsY != 3 || connectFlagsZPos != 3);
        boolean connectXNeg = connectFlagsY == 0 && hardXNeg
            || blockXNeg == this && !hardConnectionXNeg && (connectFlagsY != 3 || connectFlagsXNeg != 3);
        boolean connectXPos = connectFlagsY == 0 && hardXPos
            || blockXPos == this && !hardConnectionXPos && (connectFlagsY != 3 || connectFlagsXPos != 3);
        boolean connectSide = connectZNeg | connectZPos | connectXNeg | connectXPos;
        if (!connectSide && (connectFlagsY & 1) == 0) {
            if (hardZNeg) {
                connectZNeg = true;
            }

            if (hardZPos) {
                connectZPos = true;
            }

            if (hardXNeg) {
                connectXNeg = true;
            }

            if (hardXPos) {
                connectXPos = true;
            }
        }

        if (!(connectZNeg | connectZPos | connectXNeg | connectXPos)) {
            connectFlagsY = 3;
        }

        if (connectFlagsY == 2 && hardZNeg) {
            connectZNeg = true;
        }

        if (connectFlagsY == 2 && hardZPos) {
            connectZPos = true;
        }

        if (connectFlagsY == 2 && hardXNeg) {
            connectXNeg = true;
        }

        if (connectFlagsY == 2 && hardXPos) {
            connectXPos = true;
        }

        return connectFlagsY | (connectZNeg ? 4 : 0)
            | (connectZPos ? 8 : 0)
            | (connectXNeg ? 16 : 0)
            | (connectXPos ? 32 : 0);
    }

    private int calcConnectYFlags(IBlockAccess world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        if (block != this) {
            return 0;
        } else {
            Block blockYNeg = world.getBlock(x, y - 1, z);
            boolean hardYNeg = this.isNeighborHardConnectionY(world, x, y - 1, z, blockYNeg, ForgeDirection.DOWN);
            boolean connectYNeg = hardYNeg || blockYNeg == this;
            Block blockYPos = world.getBlock(x, y + 1, z);
            boolean hardYPos = this.isNeighborHardConnectionY(world, x, y + 1, z, blockYPos, ForgeDirection.UP);
            boolean connectYPos = hardYPos || blockYPos == this || blockYPos instanceof BlockTorch;
            return (connectYNeg ? 1 : 0) | (connectYPos ? 2 : 0) | (hardYNeg ? 4 : 0) | (hardYPos ? 8 : 0);
        }
    }

    private boolean isNeighborHardConnection(IBlockAccess world, int x, int y, int z, Block block,
        ForgeDirection side) {
        if (block.getMaterial()
            .isOpaque() && block.renderAsNormalBlock()) {
            return true;
        } else {
            return block.isSideSolid(world, x, y, z, side.getOpposite());
        }
    }

    private boolean isNeighborHardConnectionY(IBlockAccess world, int x, int y, int z, Block block,
        ForgeDirection side) {
        if (this.isNeighborHardConnection(world, x, y, z, block, side)) {
            return true;
        } else {
            return block instanceof BlockLeavesBase || block == ModBlocks.thinLogFence;
        }
    }

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List blockList) {
        for (int i = 0; i < 6; ++i) {
            blockList.add(new ItemStack(item, 1, i));
        }

        Iterator var7 = WoodRegistry.instance()
            .registeredTypes()
            .iterator();

        while (var7.hasNext()) {
            Entry entry = (Entry) var7.next();
            if (entry.getValue() != Blocks.log && entry.getValue() != Blocks.log2) {
                int id = TileEntityWoodProxy
                    .composeMetadata((Block) entry.getValue(), ((UniqueMetaIdentifier) entry.getKey()).meta);
                blockList.add(new ItemStack(item, 1, id));
            }
        }

    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        int ometa = 0;
        if (this.orientation == 1) {
            ometa |= 8;
        } else if (this.orientation == 2) {
            ometa |= 4;
        } else if (this.orientation == 3) {
            ometa |= 12;
        }

        int protoMeta = TileEntityWoodProxy.getMetaFromComposedMetadata(meta);
        Block protoBlock = TileEntityWoodProxy.getBlockFromComposedMetadata(meta);
        if (protoBlock == null) {
            protoBlock = this.getIconSource(meta);
        }

        return protoBlock.getIcon(side, protoMeta | ometa);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
        TileEntityWoodProxy te = this.getTileEntity(blockAccess, x, y, z);
        if (te != null && te.getProtoBlock() != null) {
            int ometa = 0;
            if (this.orientation == 1) {
                ometa |= 8;
            } else if (this.orientation == 2) {
                ometa |= 4;
            } else if (this.orientation == 3) {
                ometa |= 12;
            }

            int protoMeta = te.getProtoMeta();
            Block protoBlock = te.getProtoBlock();
            if (protoBlock == null) {
                protoBlock = Blocks.log;
            }

            return protoBlock.getIcon(side, protoMeta | ometa);
        } else {
            return super.getIcon(blockAccess, x, y, z, side);
        }
    }

    private Block getIconSource(int meta) {
        switch (meta / 4) {
            case 0:
                return Blocks.log;
            case 1:
                return Blocks.log2;
            default:
                return Blocks.log;
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer) {
        TileEntityWoodProxy te = this.getTileEntity(worldObj, target.blockX, target.blockY, target.blockZ);
        BlockThinLog block = this.getBlock(worldObj, target.blockX, target.blockY, target.blockZ);
        if (te != null && block != null) {
            int protoMeta = te.getProtoMeta();
            Block protoBlock = te.getProtoBlock();
            if (protoBlock == null) {
                protoBlock = Blocks.log;
                protoMeta = worldObj.getBlockMetadata(target.blockX, target.blockY, target.blockZ);
            }

            float f = 0.1F;
            double xPos = (double) target.blockX
                + worldObj.rand.nextDouble()
                    * (block.getBlockBoundsMaxX() - block.getBlockBoundsMinX() - (double) (f * 2.0F))
                + (double) f
                + block.getBlockBoundsMinX();
            double yPos = (double) target.blockY
                + worldObj.rand.nextDouble()
                    * (block.getBlockBoundsMaxY() - block.getBlockBoundsMinY() - (double) (f * 2.0F))
                + (double) f
                + block.getBlockBoundsMinY();
            double zPos = (double) target.blockZ
                + worldObj.rand.nextDouble()
                    * (block.getBlockBoundsMaxZ() - block.getBlockBoundsMinZ() - (double) (f * 2.0F))
                + (double) f
                + block.getBlockBoundsMinZ();
            if (target.sideHit == 0) {
                yPos = (double) target.blockY + block.getBlockBoundsMinY() - (double) f;
            }

            if (target.sideHit == 1) {
                yPos = (double) target.blockY + block.getBlockBoundsMaxY() + (double) f;
            }

            if (target.sideHit == 2) {
                zPos = (double) target.blockZ + block.getBlockBoundsMinZ() - (double) f;
            }

            if (target.sideHit == 3) {
                zPos = (double) target.blockZ + block.getBlockBoundsMaxZ() + (double) f;
            }

            if (target.sideHit == 4) {
                xPos = (double) target.blockX + block.getBlockBoundsMinX() - (double) f;
            }

            if (target.sideHit == 5) {
                xPos = (double) target.blockX + block.getBlockBoundsMaxX() + (double) f;
            }

            EntityDiggingFX fx = new EntityDiggingFX(
                worldObj,
                xPos,
                yPos,
                zPos,
                0.0D,
                0.0D,
                0.0D,
                block,
                worldObj.getBlockMetadata(target.blockX, target.blockY, target.blockZ));
            fx.applyColourMultiplier(target.blockX, target.blockY, target.blockZ);
            fx.multiplyVelocity(0.2F)
                .multipleParticleScaleBy(0.6F);
            fx.setParticleIcon(
                block.getIcon(worldObj.rand.nextInt(6), TileEntityWoodProxy.composeMetadata(protoBlock, protoMeta)));
            effectRenderer.addEffect(fx);
            return true;
        } else {
            return false;
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer) {
        TileEntityWoodProxy te = this.getTileEntity(world, x, y, z);
        BlockThinLog block = this.getBlock(world, x, y, z);
        if (te != null && block != null) {
            int protoMeta = te.getProtoMeta();
            Block protoBlock = te.getProtoBlock();
            if (protoBlock == null) {
                protoBlock = Blocks.log;
                protoMeta = world.getBlockMetadata(x, y, z);
            }

            try {
                byte count = 4;

                for (int ix = 0; ix < count; ++ix) {
                    for (int iy = 0; iy < count; ++iy) {
                        for (int iz = 0; iz < count; ++iz) {
                            double xOff = (double) x + ((double) ix + 0.5D) / (double) count;
                            double yOff = (double) y + ((double) iy + 0.5D) / (double) count;
                            double zOff = (double) z + ((double) iz + 0.5D) / (double) count;
                            EntityDiggingFX fx = new EntityDiggingFX(
                                world,
                                xOff,
                                yOff,
                                zOff,
                                xOff - (double) x - 0.5D,
                                yOff - (double) y - 0.5D,
                                zOff - (double) z - 0.5D,
                                this,
                                meta);
                            fx.setParticleIcon(
                                block.getIcon(
                                    world.rand.nextInt(6),
                                    TileEntityWoodProxy.composeMetadata(protoBlock, protoMeta)));
                            effectRenderer.addEffect(fx.applyColourMultiplier(x, y, z));
                        }
                    }
                }
            } catch (Exception var22) {}

            return true;
        } else {
            return false;
        }
    }

    private TileEntityWoodProxy getTileEntity(IBlockAccess blockAccess, int x, int y, int z) {
        TileEntity te = blockAccess.getTileEntity(x, y, z);
        return te != null && te instanceof TileEntityWoodProxy ? (TileEntityWoodProxy) te : null;
    }

    private BlockThinLog getBlock(IBlockAccess blockAccess, int x, int y, int z) {
        Block block = blockAccess.getBlock(x, y, z);
        return block != null && block instanceof BlockThinLog ? (BlockThinLog) block : null;
    }

    public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityWoodProxy();
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

    public void registerBlockIcons(IIconRegister register) {}
}
