package com.jaquadro.minecraft.gardencore.core.handlers;

import com.jaquadro.minecraft.gardencore.block.BlockGarden;
import com.jaquadro.minecraft.gardencore.block.tile.TileEntityCompostBin;
import com.jaquadro.minecraft.gardencore.block.tile.TileEntityGarden;
import com.jaquadro.minecraft.gardencore.client.gui.GuiCompostBin;
import com.jaquadro.minecraft.gardencore.inventory.ContainerCompostBin;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    public static int gardenLayoutGuiID = 0;
    public static int compostBinGuiID = 1;

    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityGarden) {
            return this.openGardenGui(player.inventory, (TileEntityGarden) tileEntity, false);
        } else {
            return tileEntity instanceof TileEntityCompostBin
                ? new ContainerCompostBin(player.inventory, (TileEntityCompostBin) tileEntity)
                : null;
        }
    }

    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityGarden) {
            return this.openGardenGui(player.inventory, (TileEntityGarden) tileEntity, true);
        } else {
            return tileEntity instanceof TileEntityCompostBin
                ? new GuiCompostBin(player.inventory, (TileEntityCompostBin) tileEntity)
                : null;
        }
    }

    private Object openGardenGui(InventoryPlayer playerInventory, TileEntityGarden te, boolean client) {
        if (te == null) {
            return null;
        } else {
            BlockGarden block = te.getGardenBlock();
            return block == null ? null
                : block.getSlotProfile()
                    .openPlantGUI(playerInventory, te, client);
        }
    }
}
