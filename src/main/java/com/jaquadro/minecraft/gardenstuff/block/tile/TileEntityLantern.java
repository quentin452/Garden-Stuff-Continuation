package com.jaquadro.minecraft.gardenstuff.block.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityLantern extends TileEntity {

    private boolean hasGlass;
    private String source;
    private int sourceMeta = 0;

    public void setHasGlass(boolean hasGlass) {
        this.hasGlass = hasGlass;
    }

    public boolean hasGlass() {
        return this.hasGlass;
    }

    public void setLightSource(String source) {
        this.source = source;
    }

    public String getLightSource() {
        return this.source;
    }

    public void setLightSourceMeta(int meta) {
        this.sourceMeta = meta;
    }

    public int getLightSourceMeta() {
        return this.sourceMeta;
    }

    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.hasGlass = false;
        this.source = null;
        if (tag.hasKey("Glas")) {
            this.hasGlass = tag.getBoolean("Glas");
        }

        if (tag.hasKey("Src", 1)) {
            TileEntityLantern.LightSource[] values = TileEntityLantern.LightSource.values();
            int index = tag.getByte("Src");
            TileEntityLantern.LightSource legacySource = TileEntityLantern.LightSource.NONE;
            if (index >= 0 && index < values.length) {
                legacySource = values[index];
            }

            switch (legacySource) {
                case TORCH:
                    this.source = "torch";
                    break;
                case REDSTONE_TORCH:
                    this.source = "redstoneTorch";
                    break;
                case GLOWSTONE:
                    this.source = "glowstone";
                    break;
                case FIREFLY:
                    this.source = "firefly";
            }
        } else if (tag.hasKey("Src", 8)) {
            this.source = tag.getString("Src");
        }

        if (tag.hasKey("SrcMeta")) {
            this.sourceMeta = tag.getShort("SrcMeta");
        }

    }

    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        if (this.hasGlass) {
            tag.setBoolean("Glas", true);
        }

        if (this.source != null) {
            tag.setString("Src", this.source);
        }

        if (this.sourceMeta != 0) {
            tag.setShort("SrcMeta", (short) this.sourceMeta);
        }

    }

    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 5, tag);
    }

    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.func_148857_g());
        this.getWorldObj()
            .func_147479_m(this.xCoord, this.yCoord, this.zCoord);
    }

    public static enum LightSource {
        NONE,
        TORCH,
        REDSTONE_TORCH,
        GLOWSTONE,
        CANDLE,
        FIREFLY;
    }
}
