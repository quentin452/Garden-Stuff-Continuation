package com.jaquadro.minecraft.gardencore.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.ColorizerGrass;

import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemUsedSoilKit extends Item {

    @SideOnly(Side.CLIENT)
    private IIcon icon;
    @SideOnly(Side.CLIENT)
    private IIcon iconOverlay;

    public ItemUsedSoilKit(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setTextureName("soil_test_kit");
        this.setCreativeTab(ModCreativeTabs.tabGardenCore);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        return this.icon;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int damage, int pass) {
        return pass == 0 ? this.iconOverlay : super.getIconFromDamageForRenderPass(damage, pass);
    }

    public static int PackTempHumidity(float temp, float humidity) {
        return (int) (humidity * 127.0F) << 7 | (int) (temp * 127.0F);
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromDamage(int damage) {
        int temperature = damage & 127;
        int humidity = damage >> 7 & 127;
        return ColorizerGrass
            .getGrassColor((double) ((float) temperature / 255.0F), (double) ((float) humidity / 255.0F));
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int pass) {
        return pass > 0 ? 16777215 : this.getColorFromDamage(itemStack.getItemDamage());
    }

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
        float temperature = (float) (itemStack.getItemDamage() & 127) / 127.0F;
        float humidity = (float) (itemStack.getItemDamage() >> 7 & 127) / 127.0F;
        EnumChatFormatting tempColor = EnumChatFormatting.BLUE;
        if ((double) temperature >= 0.2D) {
            tempColor = EnumChatFormatting.DARK_GREEN;
        }

        if (temperature >= 1.0F) {
            tempColor = EnumChatFormatting.DARK_RED;
        }

        EnumChatFormatting humidColor = EnumChatFormatting.DARK_RED;
        if ((double) humidity >= 0.3D) {
            humidColor = EnumChatFormatting.GOLD;
        }

        if ((double) humidity >= 0.6D) {
            humidColor = EnumChatFormatting.DARK_GREEN;
        }

        String temperatureStr = StatCollector.translateToLocal("gardencore.soilkit.temperature") + ": "
            + tempColor
            + String.format("%.2f", temperature);
        String humidityStr = StatCollector.translateToLocal("gardencore.soilkit.rainfall") + ": "
            + humidColor
            + String.format("%.2f", humidity);
        list.add(temperatureStr);
        list.add(humidityStr);
    }

    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        list.add(new ItemStack(item, 1, PackTempHumidity(0.0F, 0.0F)));
        list.add(new ItemStack(item, 1, PackTempHumidity(0.5F, 0.0F)));
        list.add(new ItemStack(item, 1, PackTempHumidity(1.0F, 0.0F)));
        list.add(new ItemStack(item, 1, PackTempHumidity(0.5F, 0.5F)));
        list.add(new ItemStack(item, 1, PackTempHumidity(1.0F, 0.5F)));
        list.add(new ItemStack(item, 1, PackTempHumidity(1.0F, 1.0F)));
    }

    public void registerIcons(IIconRegister iconRegister) {
        this.icon = iconRegister.registerIcon("GardenCore:soil_test_kit_used");
        this.iconOverlay = iconRegister.registerIcon("GardenCore:soil_test_kit_overlay");
    }
}
