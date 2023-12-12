package com.jaquadro.minecraft.gardencore.item;

import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;
import com.jaquadro.minecraft.gardencore.core.ModItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class ItemSoilKit extends Item {

    @SideOnly(Side.CLIENT)
    private IIcon icon;

    public ItemSoilKit(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setMaxStackSize(64);
        this.setTextureName("soil_test_kit");
        this.setCreativeTab(ModCreativeTabs.tabGardenCore);
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        if (player.inventory.getFirstEmptyStack() == -1 && player.inventory.getCurrentItem().stackSize > 1) {
            return false;
        } else {
            BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
            int temperature = (int) (Math.min(1.0F, Math.max(0.0F, biome.temperature)) * 255.0F) & 255;
            int rainfall = (int) (Math.min(1.0F, Math.max(0.0F, biome.rainfall)) * 255.0F) & 255;
            ItemStack usedKit = new ItemStack(ModItems.usedSoilTestKit, 1, rainfall << 8 | temperature);
            world.playSoundAtEntity(player, "step.grass", 1.0F, 1.0F);
            if (player.inventory.getCurrentItem().stackSize == 1) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, usedKit);
            } else {
                --stack.stackSize;
                player.inventory.setInventorySlotContents(player.inventory.getFirstEmptyStack(), usedKit);
            }

            return true;
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1) {
        return this.icon;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.icon = iconRegister.registerIcon("GardenCore:soil_test_kit");
    }
}
