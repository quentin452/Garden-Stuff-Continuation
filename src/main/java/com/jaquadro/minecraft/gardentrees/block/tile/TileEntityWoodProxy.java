package com.jaquadro.minecraft.gardentrees.block.tile;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityWoodProxy extends TileEntity {

    private Block protoBlock;
    private int protoMeta;

    public Block getProtoBlock() {
        return this.protoBlock;
    }

    public int getProtoMeta() {
        return this.protoMeta;
    }

    public void setProtoBlock(Block block, int meta) {
        this.protoBlock = block;
        this.protoMeta = meta;
    }

    public static int composeMetadata(Block block, int meta) {
        int id = GameData.blockRegistry.getId(block);
        return id & 4095 | (meta & 15) << 12;
    }

    public static Block getBlockFromComposedMetadata(int metadata) {
        if (metadata >= 16) {
            return (Block) GameData.blockRegistry.get(metadata & 4095);
        } else if (metadata < 4) {
            return Blocks.log;
        } else {
            return metadata < 8 ? Blocks.log2 : null;
        }
    }

    public static int getMetaFromComposedMetadata(int metadata) {
        return metadata >= 16 ? metadata >> 12 & 15 : metadata % 4;
    }

    public static void syncTileEntityWithData(World world, int x, int y, int z, int metadata) {
        if (metadata < 16) {
            world.removeTileEntity(x, y, z);
        } else {
            TileEntityWoodProxy te = (TileEntityWoodProxy) world.getTileEntity(x, y, z);
            if (te == null) {
                te = new TileEntityWoodProxy();
                world.setTileEntity(x, y, z, te);
            }

            Block block = getBlockFromComposedMetadata(metadata);
            int protoMeta = getMetaFromComposedMetadata(metadata);
            if (block != null) {
                te.setProtoBlock(block, protoMeta);
            }

            te.markDirty();
        }
    }

    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        if (this.protoBlock != null) {
            tag.setInteger("P", this.getUnifiedProtoData());
        }

    }

    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        if (tag.hasKey("P")) {
            this.unpackUnifiedProtoData(tag.getInteger("P"));
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

    private int getUnifiedProtoData() {
        return Block.getIdFromBlock(this.protoBlock) & '\uffff' | (this.protoMeta & '\uffff') << 16;
    }

    private void unpackUnifiedProtoData(int protoData) {
        this.protoBlock = Block.getBlockById(protoData & '\uffff');
        this.protoMeta = protoData >> 16 & '\uffff';
    }
}
