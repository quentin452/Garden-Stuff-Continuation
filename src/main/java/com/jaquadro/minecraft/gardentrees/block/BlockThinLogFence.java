package com.jaquadro.minecraft.gardentrees.block;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemLead;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.jaquadro.minecraft.gardencore.api.WoodRegistry;
import com.jaquadro.minecraft.gardencore.util.UniqueMetaIdentifier;
import com.jaquadro.minecraft.gardentrees.block.tile.TileEntityWoodProxy;
import com.jaquadro.minecraft.gardentrees.core.ClientProxy;
import com.jaquadro.minecraft.gardentrees.core.ModCreativeTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockThinLogFence extends BlockContainer {

    public static final String[] subNames = new String[] { "oak", "spruce", "birch", "jungle", "acacia", "big_oak" };
    @SideOnly(Side.CLIENT)
    IIcon sideIcon;

    public BlockThinLogFence(String blockName) {
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

    public void setBlockBoundsForItemRender() {
        float margin = this.getMargin();
        this.setBlockBounds(margin, 0.0F, margin, 1.0F - margin, 1.0F, 1.0F - margin);
    }

    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list,
        Entity colliding) {
        boolean connectedZNeg = this.canConnectFenceTo(world, x, y, z - 1);
        boolean connectedZPos = this.canConnectFenceTo(world, x, y, z + 1);
        boolean connectedXNeg = this.canConnectFenceTo(world, x - 1, y, z);
        boolean connectedXPos = this.canConnectFenceTo(world, x + 1, y, z);
        float margin = this.getMargin();
        float xs = margin;
        float xe = 1.0F - margin;
        float zs = margin;
        float ze = 1.0F - margin;
        if (connectedZNeg) {
            zs = 0.0F;
        }

        if (connectedZPos) {
            ze = 1.0F;
        }

        if (connectedZNeg || connectedZPos) {
            this.setBlockBounds(margin, 0.0F, zs, xe, 1.5F, ze);
            super.addCollisionBoxesToList(world, x, y, z, mask, list, colliding);
        }

        zs = margin;
        ze = 1.0F - margin;
        if (connectedXNeg) {
            xs = 0.0F;
        }

        if (connectedXPos) {
            xe = 1.0F;
        }

        if (connectedXNeg || connectedXPos || !connectedZNeg && !connectedZPos) {
            this.setBlockBounds(xs, 0.0F, margin, xe, 1.5F, ze);
            super.addCollisionBoxesToList(world, x, y, z, mask, list, colliding);
        }

        if (connectedZNeg) {
            zs = 0.0F;
        }

        if (connectedZPos) {
            ze = 1.0F;
        }

        this.setBlockBounds(xs, 0.0F, zs, xe, 1.0F, ze);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        boolean connectedZNeg = this.canConnectFenceTo(world, x, y, z - 1);
        boolean connectedZPos = this.canConnectFenceTo(world, x, y, z + 1);
        boolean connectedXNeg = this.canConnectFenceTo(world, x - 1, y, z);
        boolean connectedXPos = this.canConnectFenceTo(world, x + 1, y, z);
        float margin = this.getMargin();
        float xs = margin;
        float xe = 1.0F - margin;
        float zs = margin;
        float ze = 1.0F - margin;
        if (connectedZNeg) {
            zs = 0.0F;
        }

        if (connectedZPos) {
            ze = 1.0F;
        }

        if (connectedXNeg) {
            xs = 0.0F;
        }

        if (connectedXPos) {
            xe = 1.0F;
        }

        this.setBlockBounds(xs, 0.0F, zs, xe, 1.0F, ze);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean getBlocksMovement(IBlockAccess world, int x, int y, int z) {
        return false;
    }

    public int getRenderType() {
        return ClientProxy.thinLogFenceRenderID;
    }

    public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
        return true;
    }

    public boolean canConnectFenceTo(IBlockAccess world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        if (block == this) {
            return true;
        } else {
            return block.getMaterial()
                .isOpaque() && block.renderAsNormalBlock() ? block.getMaterial() != Material.gourd : false;
        }
    }

    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return true;
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

    public IIcon getIcon(int side, int meta) {
        int protoMeta = TileEntityWoodProxy.getMetaFromComposedMetadata(meta);
        Block protoBlock = TileEntityWoodProxy.getBlockFromComposedMetadata(meta);
        if (protoBlock == null) {
            protoBlock = this.getIconSource(meta);
        }

        return protoBlock.getIcon(side, protoMeta);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
        TileEntityWoodProxy te = this.getTileEntity(blockAccess, x, y, z);
        if (te != null && te.getProtoBlock() != null) {
            int protoMeta = te.getProtoMeta();
            Block protoBlock = te.getProtoBlock();
            if (protoBlock == null) {
                protoBlock = Blocks.log;
            }

            return protoBlock.getIcon(side, protoMeta);
        } else {
            return super.getIcon(blockAccess, x, y, z, side);
        }
    }

    private TileEntityWoodProxy getTileEntity(IBlockAccess blockAccess, int x, int y, int z) {
        TileEntity te = blockAccess.getTileEntity(x, y, z);
        return te != null && te instanceof TileEntityWoodProxy ? (TileEntityWoodProxy) te : null;
    }

    private BlockThinLogFence getBlock(IBlockAccess blockAccess, int x, int y, int z) {
        Block block = blockAccess.getBlock(x, y, z);
        return block != null && block instanceof BlockThinLogFence ? (BlockThinLogFence) block : null;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getSideIcon() {
        return this.sideIcon;
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
        BlockThinLogFence block = this.getBlock(worldObj, target.blockX, target.blockY, target.blockZ);
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
        BlockThinLogFence block = this.getBlock(world, x, y, z);
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

    public void registerBlockIcons(IIconRegister iconRegister) {
        this.sideIcon = iconRegister.registerIcon("GardenTrees:thinlog_fence_side");
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float vx, float vy,
        float vz) {
        return world.isRemote ? true : ItemLead.func_150909_a(player, world, x, y, z);
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityWoodProxy();
    }
}
