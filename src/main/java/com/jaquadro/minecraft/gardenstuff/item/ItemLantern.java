package com.jaquadro.minecraft.gardenstuff.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.jaquadro.minecraft.gardenapi.api.component.ILanternSource;
import com.jaquadro.minecraft.gardenapi.internal.Api;
import com.jaquadro.minecraft.gardenstuff.block.tile.TileEntityLantern;
import com.jaquadro.minecraft.gardenstuff.core.ModBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemLantern extends ItemBlock {

    public ItemLantern(Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public int getMetadata(int meta) {
        return meta;
    }

    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ, int metadata) {
        if (!super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata & 15)) {
            return false;
        } else {
            TileEntityLantern tile = (TileEntityLantern) world.getTileEntity(x, y, z);
            if (tile != null) {
                tile.setHasGlass(ModBlocks.lantern.isGlass(stack));
                tile.setLightSourceMeta(ModBlocks.lantern.getLightSourceMeta(stack));
                tile.setLightSource(ModBlocks.lantern.getLightSource(stack));
            }

            if (world.getBlock(x, y + 1, z) == ModBlocks.heavyChain) {
                world.notifyBlockOfNeighborChange(x, y + 1, z, world.getBlock(x, y, z));
            }

            return true;
        }
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
        String contents;
        if (ModBlocks.lantern.isGlass(itemStack)) {
            contents = Blocks.stained_glass.getUnlocalizedName() + "."
                + ItemDye.field_150923_a[BlockColored.func_150032_b(itemStack.getItemDamage())];
            list.add(StatCollector.translateToLocal(contents + ".name"));
        }

        contents = StatCollector.translateToLocal(ModBlocks.makeName("lanternSource")) + ": "
            + EnumChatFormatting.YELLOW;
        String source = ModBlocks.lantern.getLightSource(itemStack);
        ILanternSource lanternSource = source != null ? Api.instance.registries()
            .lanternSources()
            .getLanternSource(source) : null;
        if (lanternSource != null) {
            contents = contents
                + StatCollector.translateToLocal(lanternSource.getLanguageKey(itemStack.getItemDamage()));
        } else {
            contents = contents + StatCollector.translateToLocal(ModBlocks.makeName("lanternSource.none"));
        }

        list.add(contents);
    }

    public ItemStack makeItemStack(int count, int meta, boolean hasGlass) {
        return this.makeItemStack(count, meta, hasGlass, (String) null);
    }

    public ItemStack makeItemStack(int count, int meta, boolean hasGlass, String source) {
        return this.makeItemStack(count, meta, hasGlass, source, 0);
    }

    public ItemStack makeItemStack(int count, int meta, boolean hasGlass, String source, int sourceMeta) {
        ItemStack stack = new ItemStack(this, count, meta);
        NBTTagCompound tag = new NBTTagCompound();
        if (hasGlass) {
            tag.setBoolean("glass", true);
        }

        if (source != null) {
            tag.setString("src", source);
        }

        if (sourceMeta != 0) {
            tag.setShort("srcMeta", (short) sourceMeta);
        }

        if (!tag.hasNoTags()) {
            stack.setTagCompound(tag);
        }

        return stack;
    }
}
