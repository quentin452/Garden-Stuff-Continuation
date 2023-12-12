package com.jaquadro.minecraft.gardenstuff.block.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

public class TileEntityCandelabra extends TileEntity {

    private int direction;
    private int level;

    public boolean isDirectionInitialized() {
        return this.direction > 0;
    }

    public int getDirection() {
        return MathHelper.clamp_int(this.direction & 7, 2, 5);
    }

    public void setDirection(int direction) {
        this.direction = direction & 7;
    }

    public boolean isSconce() {
        return (this.direction & 8) != 0;
    }

    public void setSconce(boolean isSconce) {
        this.direction &= 7;
        if (isSconce) {
            this.direction |= 8;
        }

    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.direction = 0;
        if (tag.hasKey("Dir")) {
            this.direction = tag.getByte("Dir");
        }

        this.level = tag.getByte("Lev");
    }

    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setByte("Dir", (byte) this.direction);
        tag.setByte("Lev", (byte) this.level);
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
}
