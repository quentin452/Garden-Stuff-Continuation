package com.jaquadro.minecraft.gardencontainers.block;

import com.InfinityRaider.AgriCraft.api.v1.ISoilContainer;
import com.jaquadro.minecraft.gardencontainers.GardenContainers;
import com.jaquadro.minecraft.gardencontainers.block.tile.TileEntityLargePot;
import com.jaquadro.minecraft.gardencontainers.config.PatternConfig;
import com.jaquadro.minecraft.gardencontainers.core.ClientProxy;
import com.jaquadro.minecraft.gardencore.api.block.IChainAttachable;
import com.jaquadro.minecraft.gardencore.api.block.IGardenBlock;
import com.jaquadro.minecraft.gardencore.api.plant.PlantItem;
import com.jaquadro.minecraft.gardencore.api.plant.PlantSize;
import com.jaquadro.minecraft.gardencore.api.plant.PlantType;
import com.jaquadro.minecraft.gardencore.block.BlockGardenContainer;
import com.jaquadro.minecraft.gardencore.block.support.BasicSlotProfile;
import com.jaquadro.minecraft.gardencore.block.support.ContainerConnectionProfile;
import com.jaquadro.minecraft.gardencore.block.support.Slot14ProfileBounded;
import com.jaquadro.minecraft.gardencore.block.support.SlotShare8Profile;
import com.jaquadro.minecraft.gardencore.block.tile.TileEntityGarden;
import com.jaquadro.minecraft.gardencore.core.ModBlocks;
import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;
import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

@Interface(modid = "AgriCraft", iface = "com.InfinityRaider.AgriCraft.api.v1.ISoilContainer")
public abstract class BlockLargePot extends BlockGardenContainer implements IChainAttachable, ISoilContainer {

    @SideOnly(Side.CLIENT)
    private IIcon[] iconOverlayArray;
    private static final Vec3[] chainAttachPoints = new Vec3[] { Vec3.createVectorHelper(0.03625D, 1.0D, 0.03125D),
        Vec3.createVectorHelper(0.03125D, 1.0D, 0.96375D), Vec3.createVectorHelper(0.96875D, 1.0D, 0.03625D),
        Vec3.createVectorHelper(0.96375D, 1.0D, 0.96875D) };

    public BlockLargePot(String blockName) {
        super(blockName, Material.clay);
        this.setCreativeTab(ModCreativeTabs.tabGardenCore);
        this.setHardness(0.5F);
        this.setResistance(5.0F);
        this.setStepSound(Block.soundTypeStone);
        this.connectionProfile = new ContainerConnectionProfile();
        this.slotShareProfile = new SlotShare8Profile(6, 7, 8, 9, 10, 11, 12, 13);
        PlantType[] commonType = new PlantType[] { PlantType.GROUND, PlantType.AQUATIC_COVER,
            PlantType.AQUATIC_SURFACE };
        PlantSize[] commonSize = new PlantSize[] { PlantSize.LARGE, PlantSize.MEDIUM, PlantSize.SMALL };
        PlantSize[] allSize = new PlantSize[] { PlantSize.FULL, PlantSize.LARGE, PlantSize.MEDIUM, PlantSize.SMALL };
        this.slotProfile = new BlockLargePot.LocalSlotProfile(
            this,
            new BasicSlotProfile.Slot[] { new BasicSlotProfile.Slot(0, commonType, allSize),
                new BasicSlotProfile.Slot(1, new PlantType[] { PlantType.GROUND_COVER }, allSize),
                new BasicSlotProfile.Slot(2, commonType, commonSize),
                new BasicSlotProfile.Slot(3, commonType, commonSize),
                new BasicSlotProfile.Slot(4, commonType, commonSize),
                new BasicSlotProfile.Slot(5, commonType, commonSize),
                new BasicSlotProfile.Slot(6, commonType, commonSize),
                new BasicSlotProfile.Slot(7, commonType, commonSize),
                new BasicSlotProfile.Slot(8, commonType, commonSize),
                new BasicSlotProfile.Slot(9, commonType, commonSize),
                new BasicSlotProfile.Slot(10, commonType, commonSize),
                new BasicSlotProfile.Slot(11, commonType, commonSize),
                new BasicSlotProfile.Slot(12, commonType, commonSize),
                new BasicSlotProfile.Slot(13, commonType, commonSize) });
    }

    public abstract String[] getSubTypes();

    public int getDefaultSlot() {
        return 0;
    }

    protected int getSlot(World world, int x, int y, int z, EntityPlayer player, float hitX, float hitY, float hitZ) {
        return 0;
    }

    protected int getEmptySlotForPlant(World world, int x, int y, int z, EntityPlayer player, PlantItem plant) {
        TileEntityGarden garden = this.getTileEntity(world, x, y, z);
        if (plant.getPlantTypeClass() == PlantType.GROUND_COVER) {
            return garden.getStackInSlot(1) == null ? 1 : -1;
        } else if (plant.getPlantSizeClass() == PlantSize.FULL) {
            return garden.getStackInSlot(0) == null ? 0 : -1;
        } else if (garden.getStackInSlot(0) == null) {
            return 0;
        } else {
            int[] var8;
            int var9;
            int var10;
            int slot;
            if (plant.getPlantSizeClass() == PlantSize.SMALL) {
                var8 = new int[] { 3, 4, 2, 5 };
                var9 = var8.length;

                for (var10 = 0; var10 < var9; ++var10) {
                    slot = var8[var10];
                    if (garden.getStackInSlot(slot) == null) {
                        return slot;
                    }
                }
            }

            var8 = new int[] { 13, 9, 7, 11, 6, 10, 8, 12 };
            var9 = var8.length;

            for (var10 = 0; var10 < var9; ++var10) {
                slot = var8[var10];
                if (garden.isSlotValid(slot) && garden.getStackInSlot(slot) == null) {
                    return slot;
                }
            }

            return -1;
        }
    }

    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list,
        Entity colliding) {
        float dim = 0.0625F;
        TileEntityLargePot te = this.getTileEntity(world, x, y, z);
        if (te != null && te.getSubstrate() != null
            && this.isSubstrateSolid(
                te.getSubstrate()
                    .getItem())) {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F - dim, 1.0F);
        } else {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, dim, 1.0F);
        }

        super.addCollisionBoxesToList(world, x, y, z, mask, list, colliding);
        if (!te.isAttachedNeighbor(x - 1, y, z)) {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, dim, 1.0F, 1.0F);
            super.addCollisionBoxesToList(world, x, y, z, mask, list, colliding);
        }

        if (!te.isAttachedNeighbor(x, y, z - 1)) {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, dim);
            super.addCollisionBoxesToList(world, x, y, z, mask, list, colliding);
        }

        if (!te.isAttachedNeighbor(x + 1, y, z)) {
            this.setBlockBounds(1.0F - dim, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            super.addCollisionBoxesToList(world, x, y, z, mask, list, colliding);
        }

        if (!te.isAttachedNeighbor(x, y, z + 1)) {
            this.setBlockBounds(0.0F, 0.0F, 1.0F - dim, 1.0F, 1.0F, 1.0F);
            super.addCollisionBoxesToList(world, x, y, z, mask, list, colliding);
        }

        this.setBlockBoundsForItemRender();
    }

    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public int getRenderType() {
        return ClientProxy.largePotRenderID;
    }

    public int getRenderBlockPass() {
        return 1;
    }

    public boolean canRenderInPass(int pass) {
        ClientProxy.renderPass = pass;
        return true;
    }

    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return side != ForgeDirection.UP;
    }

    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
        int nx = x;
        int nz = z;
        switch (side) {
            case 0:
                ++y;
                break;
            case 1:
                --y;
                break;
            case 2:
                ++z;
                break;
            case 3:
                --z;
                break;
            case 4:
                ++x;
                break;
            case 5:
                --x;
        }

        if (side >= 2 && side < 6) {
            TileEntityGarden te = this.getTileEntity(blockAccess, x, y, z);
            if (te != null) {
                return !te.isAttachedNeighbor(nx, y, nz);
            }
        }

        return side != 1;
    }

    public ArrayList getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        TileEntityLargePot te = this.getTileEntity(world, x, y, z);
        ArrayList items = new ArrayList();
        int count = this.quantityDropped(metadata, fortune, world.rand);

        for (int i = 0; i < count; ++i) {
            Item item = this.getItemDropped(metadata, world.rand, fortune);
            int packedMeta = metadata | (te != null ? te.getCarving() << 8 : 0);
            if (item != null) {
                items.add(new ItemStack(item, 1, packedMeta));
            }
        }

        return items;
    }

    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
        return willHarvest ? true : super.removedByPlayer(world, player, x, y, z, willHarvest);
    }

    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
        super.harvestBlock(world, player, x, y, z, meta);
        world.setBlockToAir(x, y, z);
    }

    private boolean isSubstrateSolid(Item item) {
        Block block = Block.getBlockFromItem(item);
        return block != Blocks.water;
    }

    public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction,
        IPlantable plantable) {
        TileEntityGarden gardenTile = this.getTileEntity(world, x, y, z);
        EnumPlantType plantType = plantable.getPlantType(world, x, y, z);
        return plantType == EnumPlantType.Crop ? this.substrateSupportsCrops(gardenTile.getSubstrate()) : false;
    }

    protected boolean substrateSupportsCrops(ItemStack substrate) {
        if (substrate != null && substrate.getItem() != null) {
            if (Block.getBlockFromItem(substrate.getItem()) == ModBlocks.gardenFarmland) {
                return true;
            } else {
                return Block.getBlockFromItem(substrate.getItem()) == Blocks.farmland;
            }
        } else {
            return false;
        }
    }

    protected boolean applySubstrateToGarden(World world, int x, int y, int z, EntityPlayer player, int slot,
        ItemStack itemStack) {
        if (this.getGardenSubstrate(world, x, y, z, slot) != null) {
            return false;
        } else if (itemStack.getItem() == Items.water_bucket) {
            TileEntityGarden garden = this.getTileEntity(world, x, y, z);
            garden.setSubstrate(new ItemStack(Blocks.water));
            garden.markDirty();
            if (player != null && !player.capabilities.isCreativeMode) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.bucket));
            }

            world.markBlockForUpdate(x, y, z);
            return true;
        } else {
            return super.applySubstrateToGarden(world, x, y, z, player, slot, itemStack);
        }
    }

    protected boolean applyItemToGarden(World world, int x, int y, int z, EntityPlayer player, ItemStack itemStack,
        float hitX, float hitY, float hitZ, boolean hitValid) {
        ItemStack item = itemStack == null ? player.inventory.getCurrentItem() : itemStack;
        if (item == null) {
            return false;
        } else {
            TileEntityGarden garden = this.getTileEntity(world, x, y, z);
            if (garden.getSubstrate() != null) {
                if (item.getItem() == Items.bucket) {
                    if (Block.getBlockFromItem(
                        garden.getSubstrate()
                            .getItem())
                        == Blocks.water) {
                        player.inventory
                            .setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.water_bucket));
                        garden.setSubstrate((ItemStack) null);
                        garden.markDirty();
                        world.markBlockForUpdate(x, y, z);
                    }

                    return true;
                }

                if (item.getItem() == Items.water_bucket) {
                    this.applyWaterToSubstrate(world, x, y, z, garden, player);
                    return true;
                }

                if (item.getItem() instanceof ItemHoe) {
                    this.applyHoeToSubstrate(world, x, y, z, garden, player);
                    return true;
                }
            }

            return super.applyItemToGarden(world, x, y, z, player, itemStack, hitX, hitY, hitZ, hitValid);
        }
    }

    protected void applyWaterToSubstrate(World world, int x, int y, int z, TileEntityGarden tile, EntityPlayer player) {
        if (Block.getBlockFromItem(
            tile.getSubstrate()
                .getItem())
            == Blocks.dirt) {
            tile.setSubstrate(
                new ItemStack(Blocks.farmland, 1, 7),
                new ItemStack(
                    Blocks.dirt,
                    1,
                    tile.getSubstrate()
                        .getItemDamage()));
            tile.markDirty();
            world.markBlockForUpdate(x, y, z);
        }

    }

    protected boolean applyHoeToSubstrate(World world, int x, int y, int z, TileEntityGarden tile,
        EntityPlayer player) {
        Block substrate = Block.getBlockFromItem(
            tile.getSubstrate()
                .getItem());
        if (substrate != Blocks.dirt && substrate != Blocks.grass) {
            if (substrate != ModBlocks.gardenSoil) {
                return false;
            }

            tile.setSubstrate(new ItemStack(ModBlocks.gardenFarmland), new ItemStack(ModBlocks.gardenSoil));
        } else {
            tile.setSubstrate(
                new ItemStack(Blocks.farmland, 1, 7),
                new ItemStack(
                    Blocks.dirt,
                    1,
                    tile.getSubstrate()
                        .getItemDamage()));
        }

        tile.markDirty();
        world.markBlockForUpdate(x, y, z);
        world.playSoundEffect(
            (double) ((float) x + 0.5F),
            (double) ((float) y + 0.5F),
            (double) ((float) z + 0.5F),
            Blocks.farmland.stepSound.getStepResourcePath(),
            (Blocks.farmland.stepSound.getVolume() + 1.0F) / 2.0F,
            Blocks.farmland.stepSound.getPitch() * 0.8F);
        return true;
    }

    public boolean applyHoe(World world, int x, int y, int z) {
        TileEntityGarden te = this.getTileEntity(world, x, y, z);
        return te != null && te.isEmpty() ? this.applyHoeToSubstrate(world, x, y, z, te, (EntityPlayer) null) : false;
    }

    public TileEntityLargePot getTileEntity(IBlockAccess world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        return te != null && te instanceof TileEntityLargePot ? (TileEntityLargePot) te : null;
    }

    public TileEntityLargePot createNewTileEntity(World world, int data) {
        return new TileEntityLargePot();
    }

    public Vec3[] getChainAttachPoints(int side) {
        return side == 1 ? chainAttachPoints : null;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getOverlayIcon(int data) {
        return this.iconOverlayArray[data] != null ? this.iconOverlayArray[data] : null;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.iconOverlayArray = new IIcon[256];

        for (int i = 1; i < this.iconOverlayArray.length; ++i) {
            PatternConfig pattern = GardenContainers.config.getPattern(i);
            if (pattern != null && pattern.getOverlay() != null) {
                this.iconOverlayArray[i] = iconRegister.registerIcon("GardenContainers:" + pattern.getOverlay());
            }
        }

    }

    public Block getSoil(World world, int x, int y, int z) {
        ItemStack substrate = this.getGardenSubstrate(world, x, y, z, this.getDefaultSlot());
        return substrate != null && substrate.getItem() != null && substrate.getItem() instanceof ItemBlock
            ? ((ItemBlock) substrate.getItem()).field_150939_a
            : null;
    }

    public int getSoilMeta(World world, int x, int y, int z) {
        ItemStack substrate = this.getGardenSubstrate(world, x, y, z, this.getDefaultSlot());
        return substrate != null && substrate.getItem() != null && substrate.getItem() instanceof ItemBlock
            ? substrate.getItemDamage()
            : -1;
    }

    private class LocalSlotProfile extends Slot14ProfileBounded {

        public LocalSlotProfile(IGardenBlock garden, BasicSlotProfile.Slot[] slots) {
            super(garden, slots);
        }

        public float getPlantOffsetY(IBlockAccess blockAccess, int x, int y, int z, int slot) {
            return -0.0625F;
        }

        public Object openPlantGUI(InventoryPlayer playerInventory, TileEntity gardenTile, boolean client) {
            return gardenTile instanceof TileEntityGarden && ((TileEntityGarden) gardenTile).getSubstrate() == null
                ? null
                : super.openPlantGUI(playerInventory, gardenTile, client);
        }
    }
}
