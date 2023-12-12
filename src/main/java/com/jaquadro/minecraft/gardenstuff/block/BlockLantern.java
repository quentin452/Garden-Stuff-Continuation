package com.jaquadro.minecraft.gardenstuff.block;

import com.jaquadro.minecraft.gardenapi.api.component.ILanternSource;
import com.jaquadro.minecraft.gardenapi.api.component.IRedstoneSource;
import com.jaquadro.minecraft.gardenapi.internal.Api;
import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;
import com.jaquadro.minecraft.gardenstuff.block.tile.TileEntityLantern;
import com.jaquadro.minecraft.gardenstuff.core.ClientProxy;
import com.jaquadro.minecraft.gardenstuff.integration.ColoredLightsIntegration;
import com.jaquadro.minecraft.gardenstuff.item.ItemLantern;
import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.Optional.Method;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.api.crafting.IInfusionStabiliser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Interface(iface = "thaumcraft.api.crafting.IInfusionStabiliser", modid = "Thaumcraft", striprefs = true)
public class BlockLantern extends BlockContainer implements IInfusionStabiliser {

    @SideOnly(Side.CLIENT)
    private IIcon iconBottom;
    @SideOnly(Side.CLIENT)
    private IIcon iconSide;
    @SideOnly(Side.CLIENT)
    private IIcon iconTop;
    @SideOnly(Side.CLIENT)
    private IIcon iconTopCross;
    @SideOnly(Side.CLIENT)
    private IIcon iconGlass;
    @SideOnly(Side.CLIENT)
    private IIcon iconCandle;

    public BlockLantern(String blockName) {
        super(Material.iron);
        this.setBlockName(blockName);
        this.setTickRandomly(true);
        this.setHardness(2.5F);
        this.setResistance(5.0F);
        this.setLightLevel(1.0F);
        this.setBlockTextureName("GardenStuff:lantern");
        this.setCreativeTab(ModCreativeTabs.tabGardenCore);
        this.setBlockBoundsForItemRender();
    }

    public boolean isGlass(ItemStack item) {
        if (item.hasTagCompound()) {
            NBTTagCompound tag = item.getTagCompound();
            if (tag.hasKey("glass")) {
                return tag.getBoolean("glass");
            }
        }

        return false;
    }

    public String getLightSource(ItemStack item) {
        if (item.hasTagCompound()) {
            NBTTagCompound tag = item.getTagCompound();
            if (tag.hasKey("src", 8)) {
                return tag.getString("src");
            }
        }

        return null;
    }

    public int getLightSourceMeta(ItemStack item) {
        if (item.hasTagCompound()) {
            NBTTagCompound tag = item.getTagCompound();
            if (tag.hasKey("srcMeta")) {
                return tag.getShort("srcMeta");
            }
        }

        return 0;
    }

    public String getItemIconName() {
        return "GardenStuff:lantern";
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public int getRenderType() {
        return ClientProxy.lanternRenderID;
    }

    public int getRenderBlockPass() {
        return 1;
    }

    public boolean canRenderInPass(int pass) {
        ClientProxy.lanternRenderer.renderPass = pass;
        return true;
    }

    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        this.setBlockBoundsForItemRender();
    }

    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.125F, 0.0F, 0.125F, 0.875F, 0.875F, 0.875F);
    }

    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list,
        Entity colliding) {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        super.addCollisionBoxesToList(world, x, y, z, mask, list, colliding);
    }

    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return true;
    }

    public int damageDropped(int meta) {
        return meta;
    }

    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
        return willHarvest ? true : super.removedByPlayer(world, player, x, y, z, willHarvest);
    }

    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
        super.harvestBlock(world, player, x, y, z, meta);
        world.setBlockToAir(x, y, z);
    }

    public ArrayList getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        TileEntityLantern tile = this.getTileEntity(world, x, y, z);
        ArrayList ret = new ArrayList();
        int count = this.quantityDropped(metadata, fortune, world.rand);

        for (int i = 0; i < count; ++i) {
            Item item = this.getItemDropped(metadata, world.rand, fortune);
            if (item != null) {
                boolean glass = false;
                String source = null;
                int sourceMeta = 0;
                if (tile != null) {
                    glass = tile.hasGlass();
                    source = tile.getLightSource();
                    sourceMeta = tile.getLightSourceMeta();
                }

                ItemStack stack = ((ItemLantern) Item.getItemFromBlock(this))
                    .makeItemStack(1, metadata, glass, source, sourceMeta);
                ret.add(stack);
            }
        }

        return ret;
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
        float hitY, float hitZ) {
        TileEntityLantern tile = this.getTileEntity(world, x, y, z);
        if (tile == null) {
            return false;
        } else {
            ItemStack item = player.inventory.getCurrentItem();
            if (item == null && player.isSneaking() && tile.getLightSource() != null) {
                ILanternSource lanternSource = Api.instance.registries()
                    .lanternSources()
                    .getLanternSource(tile.getLightSource());
                if (lanternSource != null) {
                    this.dropBlockAsItem(world, x, y, z, lanternSource.getRemovedItem(tile.getLightSourceMeta()));
                }

                tile.setLightSource((String) null);
                world.markBlockForUpdate(x, y, z);
                world.notifyBlocksOfNeighborChange(x, y, z, this);
                world.notifyBlocksOfNeighborChange(x, y - 1, z, this);
                tile.markDirty();
                return true;
            } else {
                if (tile.getLightSource() == null && item != null) {
                    Iterator var12 = Api.instance.registries.lanternSources.getAllLanternSources()
                        .iterator();

                    while (var12.hasNext()) {
                        ILanternSource lanternSource = (ILanternSource) var12.next();
                        if (lanternSource.isValidSourceItem(item)) {
                            tile.setLightSource(lanternSource.getSourceID());
                            tile.setLightSourceMeta(lanternSource.getSourceMeta(item));
                            if (player != null && !player.capabilities.isCreativeMode && --item.stackSize <= 0) {
                                player.inventory
                                    .setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                            }

                            world.markBlockForUpdate(x, y, z);
                            world.notifyBlocksOfNeighborChange(x, y, z, this);
                            world.notifyBlocksOfNeighborChange(x, y - 1, z, this);
                            tile.markDirty();
                            return true;
                        }
                    }
                }

                return false;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        TileEntityLantern tile = this.getTileEntity(world, x, y, z);
        if (tile != null) {
            ILanternSource lanternSource = Api.instance.registries.lanternSources
                .getLanternSource(tile.getLightSource());
            if (lanternSource != null) {
                lanternSource.renderParticle(world, x, y, z, rand, tile.getLightSourceMeta());
            }

        }
    }

    public boolean canHarvestBlock(EntityPlayer player, int meta) {
        return true;
    }

    public void breakBlock(World world, int x, int y, int z, Block block, int data) {
        if (this.getRedstoneSource(world, x, y, z) != null) {
            world.notifyBlocksOfNeighborChange(x, y, z, this);
            world.notifyBlocksOfNeighborChange(x, y - 1, z, this);
        }

        super.breakBlock(world, x, y, z, block, data);
    }

    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        list.add(new ItemStack(item, 1, 0));
        NBTTagCompound glassTag = new NBTTagCompound();
        glassTag.setBoolean("glass", true);

        for (int i = 0; i < 16; ++i) {
            ItemStack entry = new ItemStack(item, 1, i);
            entry.setTagCompound(glassTag);
            list.add(entry);
        }

    }

    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        TileEntityLantern tile = this.getTileEntity(world, x, y, z);
        if (tile == null) {
            return 0;
        } else if (tile.hasGlass() && ColoredLightsIntegration.isInitialized()) {
            return ColoredLightsIntegration.getPackedColor(world.getBlockMetadata(x, y, z));
        } else {
            ILanternSource lanternSource = Api.instance.registries.lanternSources
                .getLanternSource(tile.getLightSource());
            return lanternSource != null ? lanternSource.getLightLevel(tile.getLightSourceMeta()) : 0;
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (side == 0) {
            return this.iconBottom;
        } else {
            return side == 1 ? this.iconTop : this.iconSide;
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconCandle() {
        return this.iconCandle;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconTopCross() {
        return this.iconTopCross;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconGlass(int meta) {
        return this.iconGlass;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconStainedGlass(int meta) {
        return Blocks.stained_glass_pane.getIcon(0, meta);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.iconBottom = register.registerIcon(this.getTextureName() + "_bottom");
        this.iconSide = register.registerIcon(this.getTextureName());
        this.iconGlass = register.registerIcon(this.getTextureName() + "_glass");
        this.iconTop = register.registerIcon(this.getTextureName() + "_top");
        this.iconTopCross = register.registerIcon(this.getTextureName() + "_top_cross");
        this.iconCandle = register.registerIcon("GardenStuff:candle");
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityLantern();
    }

    public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int side) {
        IRedstoneSource source = this.getRedstoneSource(world, x, y, z);
        return side == 1 && source != null ? source.strongPowerValue(
            this.getTileEntity(world, x, y, z)
                .getLightSourceMeta())
            : 0;
    }

    public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
        IRedstoneSource source = this.getRedstoneSource(world, x, y, z);
        return source != null ? source.weakPowerValue(
            this.getTileEntity(world, x, y, z)
                .getLightSourceMeta())
            : 0;
    }

    public boolean canProvidePower() {
        return true;
    }

    private IRedstoneSource getRedstoneSource(IBlockAccess world, int x, int y, int z) {
        TileEntityLantern tile = this.getTileEntity(world, x, y, z);
        if (tile != null) {
            ILanternSource source = Api.instance.registries.lanternSources.getLanternSource(tile.getLightSource());
            if (source instanceof IRedstoneSource) {
                return (IRedstoneSource) source;
            }
        }

        return null;
    }

    public TileEntityLantern getTileEntity(IBlockAccess world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        return te != null && te instanceof TileEntityLantern ? (TileEntityLantern) te : null;
    }

    @Method(modid = "Thaumcraft")
    public boolean canStabaliseInfusion(World world, int x, int y, int z) {
        TileEntityLantern tile = this.getTileEntity(world, x, y, z);
        if (tile == null) {
            return false;
        } else {
            ILanternSource lanternSource = Api.instance.registries()
                .lanternSources()
                .getLanternSource(tile.getLightSource());
            return lanternSource == null ? false
                : lanternSource.getSourceID()
                    .equals("thaumcraftCandle");
        }
    }
}
