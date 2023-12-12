package com.jaquadro.minecraft.gardencore.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import com.jaquadro.minecraft.gardencore.GardenCore;
import com.jaquadro.minecraft.gardencore.api.IPlantProxy;
import com.jaquadro.minecraft.gardencore.api.event.UseTrowelEvent;
import com.jaquadro.minecraft.gardencore.block.BlockGarden;
import com.jaquadro.minecraft.gardencore.block.tile.TileEntityGarden;
import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;
import com.jaquadro.minecraft.gardencore.core.handlers.GuiHandler;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTrowel extends Item {

    protected ToolMaterial toolMaterial;

    public ItemTrowel(String name, ToolMaterial toolMaterial) {
        this.setUnlocalizedName(name);
        this.setCreativeTab(ModCreativeTabs.tabGardenCore);
        this.setTextureName("GardenCore:garden_trowel");
        this.setMaxStackSize(1);
        this.setMaxDamage(toolMaterial.getMaxUses());
        this.toolMaterial = toolMaterial;
    }

    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        if (!player.canPlayerEdit(x, y, z, side, itemStack)) {
            return false;
        } else {
            UseTrowelEvent event = new UseTrowelEvent(player, itemStack, world, x, y, z);
            if (MinecraftForge.EVENT_BUS.post(event)) {
                return false;
            } else if (event.getResult() == Result.ALLOW) {
                itemStack.damageItem(1, player);
                return true;
            } else if (side == 0) {
                return false;
            } else {
                Block block = world.getBlock(x, y, z);
                if (block instanceof BlockGarden) {
                    player.openGui(GardenCore.instance, GuiHandler.gardenLayoutGuiID, world, x, y, z);
                    return true;
                } else {
                    if (block instanceof IPlantProxy) {
                        IPlantProxy proxy = (IPlantProxy) block;
                        TileEntityGarden te = proxy.getGardenEntity(world, x, y, z);
                        if (te != null) {
                            player.openGui(
                                GardenCore.instance,
                                GuiHandler.gardenLayoutGuiID,
                                world,
                                te.xCoord,
                                te.yCoord,
                                te.zCoord);
                            return true;
                        }
                    }

                    return false;
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }
}
