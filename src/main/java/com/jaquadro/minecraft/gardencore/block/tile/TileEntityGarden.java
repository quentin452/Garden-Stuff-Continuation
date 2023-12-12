package com.jaquadro.minecraft.gardencore.block.tile;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.jaquadro.minecraft.gardencore.api.IPlantProxy;
import com.jaquadro.minecraft.gardencore.api.block.garden.ISlotMapping;
import com.jaquadro.minecraft.gardencore.api.plant.PlantItem;
import com.jaquadro.minecraft.gardencore.block.BlockGarden;

public class TileEntityGarden extends TileEntity implements IInventory {

    private static final int DEFAULT_BIOME_DATA = 65407;
    public static final int SLOT_INVALID = -1;
    private ItemStack[] containerStacks = new ItemStack[this.containerSlotCount()];
    private PlantItem[] containerPlants;
    private String customName;
    private ItemStack substrate;
    private ItemStack substrateSource;
    private boolean hasBiomeOverride;
    private int biomeData = 65407;

    protected int containerSlotCount() {
        return 0;
    }

    public ItemStack getPlantInSlot(int slot) {
        return this.getStackInSlot(slot);
    }

    public void clearPlantedContents() {
        BlockGarden block = this.getGardenBlock();
        if (block != null) {
            this.clearReachableContents(0, this.containerSlotCount());
        }

    }

    public void clearReachableContents() {
        this.clearReachableContents(0, this.containerStacks.length);
    }

    public boolean isEmpty() {
        for (int i = 0; i < this.containerStacks.length; ++i) {
            if (this.getPlantInSlot(i) != null) {
                return false;
            }
        }

        return true;
    }

    private void clearReachableContents(int start, int length) {
        for (int i = start; i < length; ++i) {
            this.setInventorySlotContents(i, (ItemStack) null, false);
        }

        BlockGarden.validateBlockState(this);
        if (!this.worldObj.isRemote) {
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        }

    }

    public List getReachableContents() {
        List contents = new ArrayList();

        for (int i = 0; i < this.containerStacks.length; ++i) {
            ItemStack item = this.getStackInSlot(i);
            if (item != null) {
                contents.add(item);
            }
        }

        return contents;
    }

    public boolean isSharedSlot(int slot) {
        BlockGarden block = this.getGardenBlock();
        if (block == null) {
            return false;
        } else {
            ISlotMapping[] nmap = block.getSlotShareProfile()
                .getNeighborsForSlot(slot);
            return nmap != null && nmap.length > 0;
        }
    }

    public boolean isSlotValid(int slot) {
        if (!this.isSharedSlot(slot)) {
            return true;
        } else {
            BlockGarden block = this.getGardenBlock();
            ISlotMapping[] var3 = block.getSlotShareProfile()
                .getNeighborsForSlot(slot);
            int var4 = var3.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                ISlotMapping mapping = var3[var5];
                if (!block.getConnectionProfile()
                    .isAttachedNeighbor(
                        this.worldObj,
                        this.xCoord,
                        this.yCoord,
                        this.zCoord,
                        this.xCoord + mapping.getMappedX(),
                        this.yCoord,
                        this.zCoord + mapping.getMappedZ())) {
                    return false;
                }
            }

            return true;
        }
    }

    public boolean isAttachedNeighbor(int nx, int ny, int nz) {
        BlockGarden garden = this.getGardenBlock();
        return garden == null ? false
            : garden.getConnectionProfile()
                .isAttachedNeighbor(this.worldObj, this.xCoord, this.yCoord, this.zCoord, nx, ny, nz);
    }

    public BlockGarden getGardenBlock() {
        Block block = this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord);
        return block instanceof BlockGarden ? (BlockGarden) block : null;
    }

    public ItemStack getSubstrate() {
        return this.substrate;
    }

    public ItemStack getSubstrateSource() {
        return this.substrateSource != null ? this.substrateSource : this.substrate;
    }

    public void setSubstrate(ItemStack substrate) {
        this.substrate = substrate != null ? substrate.copy() : null;
        this.substrateSource = null;
    }

    public void setSubstrate(ItemStack substrate, ItemStack substrateSource) {
        this.substrate = substrate != null ? substrate.copy() : null;
        this.substrateSource = this.getSubstrateSource() != null ? substrateSource.copy() : null;
    }

    public boolean hasBiomeDataOverride() {
        return this.hasBiomeOverride;
    }

    public int getBiomeData() {
        return this.biomeData;
    }

    public float getBiomeTemperature() {
        return (float) (this.biomeData & 127) / 127.0F;
    }

    public float getBiomeHumidity() {
        return (float) (this.biomeData >> 7 & 127) / 127.0F;
    }

    public void setBiomeData(int data) {
        this.biomeData = data;
        this.hasBiomeOverride = true;
    }

    private ItemStack getStackLocal(int slot) {
        if (this.containerPlants == null && this.hasWorldObj()) {
            this.initializeContainerPlants();
        }

        return this.containerPlants != null ? this.containerPlants[slot].getPlantSourceItem()
            : this.containerStacks[slot];
    }

    private void setStackLocal(int slot, ItemStack itemStack) {
        if (this.containerPlants == null && this.hasWorldObj()) {
            this.initializeContainerPlants();
        }

        if (this.containerPlants != null && this.hasWorldObj()) {
            this.containerPlants[slot] = PlantItem.getForItem(this.worldObj, itemStack);
        }

        this.containerStacks[slot] = itemStack;
    }

    private void initializeContainerPlants() {
        this.containerPlants = new PlantItem[this.containerStacks.length];

        for (int i = 0; i < this.containerStacks.length; ++i) {
            if (this.containerStacks[i] != null) {
                this.containerPlants[i] = PlantItem.getForItem(this.worldObj, this.containerStacks[i]);
            }
        }

    }

    public void setWorldObj(World world) {
        super.setWorldObj(world);
        if (this.containerPlants == null && world != null) {
            this.initializeContainerPlants();
        } else if (world == null) {
            this.containerPlants = null;
        }

    }

    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        NBTTagList itemList = tag.getTagList("Items", 10);
        this.containerStacks = new ItemStack[this.getSizeInventory()];
        this.containerPlants = null;

        for (int i = 0; i < itemList.tagCount(); ++i) {
            NBTTagCompound item = itemList.getCompoundTagAt(i);
            if (item.hasKey("Slot") && item.hasKey("Item") && item.hasKey("Data")) {
                byte slot = item.getByte("Slot");
                if (slot >= 0 && slot < this.containerStacks.length) {
                    Item itemObj = Item.getItemById(item.getShort("Item"));
                    if (itemObj != null) {
                        short itemData = item.getShort("Data");
                        this.setStackLocal(slot, new ItemStack(itemObj, 1, itemData));
                    }
                }
            }
        }

        this.customName = null;
        if (tag.hasKey("CustomName")) {
            this.customName = tag.getString("CustomName");
        }

        this.substrate = null;
        this.substrateSource = null;
        if (tag.hasKey("SubId")) {
            this.substrate = new ItemStack(Item.getItemById(tag.getShort("SubId")));
            if (tag.hasKey("SubDa")) {
                this.substrate.setItemDamage(tag.getShort("SubDa"));
            }

            if (tag.hasKey("SubSrcId")) {
                this.substrateSource = new ItemStack(Item.getItemById(tag.getShort("SubSrcId")));
                if (tag.hasKey("SubSrcDa")) {
                    this.substrateSource.setItemDamage(tag.getShort("SubSrcDa"));
                }

                if (tag.hasKey("SubSrcTag", 10)) {
                    this.substrateSource.stackTagCompound = tag.getCompoundTag("SubSrcTag");
                }
            }
        }

        this.hasBiomeOverride = tag.hasKey("Biom");
        this.biomeData = tag.hasKey("Biom") ? tag.getInteger("Biom") : 'ｿ';
    }

    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        NBTTagList itemList = new NBTTagList();

        for (int i = 0; i < this.containerStacks.length; ++i) {
            if (this.containerStacks[i] != null && this.containerStacks[i].getItem() != null) {
                NBTTagCompound item = new NBTTagCompound();
                item.setByte("Slot", (byte) i);
                item.setShort("Item", (short) Item.getIdFromItem(this.containerStacks[i].getItem()));
                item.setShort("Data", (short) this.containerStacks[i].getItemDamage());
                itemList.appendTag(item);
            }
        }

        tag.setTag("Items", itemList);
        if (this.hasCustomInventoryName()) {
            tag.setString("CustomName", this.customName);
        }

        if (this.substrate != null) {
            tag.setShort("SubId", (short) Item.getIdFromItem(this.substrate.getItem()));
            if (this.substrate.getItemDamage() != 0) {
                tag.setShort("SubDa", (short) this.substrate.getItemDamage());
            }

            if (this.substrateSource != null) {
                tag.setShort("SubSrcId", (short) Item.getIdFromItem(this.substrateSource.getItem()));
                if (this.substrateSource.getItemDamage() != 0) {
                    tag.setShort("SubSrcDa", (short) this.substrateSource.getItemDamage());
                }

                if (this.substrateSource.stackTagCompound != null) {
                    tag.setTag("SubSrcTag", this.substrateSource.stackTagCompound);
                }
            }
        }

        if (this.hasBiomeOverride || this.biomeData != 65407) {
            tag.setInteger("Biom", this.biomeData);
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
        int y = this.yCoord;

        while (true) {
            World var10000 = this.getWorldObj();
            ++y;
            if (!(var10000.getBlock(this.xCoord, y, this.zCoord) instanceof IPlantProxy)) {
                return;
            }

            this.getWorldObj()
                .func_147479_m(this.xCoord, y, this.zCoord);
        }
    }

    public int getSizeInventory() {
        return this.containerStacks.length;
    }

    public ItemStack getStackInSlot(int slot) {
        if (!this.isSharedSlot(slot)) {
            return this.getStackInSlotIsolated(slot);
        } else if (!this.isSlotValid(slot)) {
            return null;
        } else {
            ItemStack stack = this.getStackInSlotIsolated(slot);
            if (stack != null) {
                return stack;
            } else {
                BlockGarden block = this.getGardenBlock();
                ISlotMapping[] var4 = block.getSlotShareProfile()
                    .getNeighborsForSlot(slot);
                int var5 = var4.length;

                for (int var6 = 0; var6 < var5; ++var6) {
                    ISlotMapping mapping = var4[var6];
                    TileEntity te = this.worldObj.getTileEntity(
                        this.xCoord + mapping.getMappedX(),
                        this.yCoord,
                        this.zCoord + mapping.getMappedZ());
                    if (te != null && te instanceof TileEntityGarden) {
                        TileEntityGarden nGarden = (TileEntityGarden) te;
                        stack = nGarden.getStackInSlotIsolated(mapping.getMappedSlot());
                        if (stack != null) {
                            return stack;
                        }
                    }
                }

                return null;
            }
        }
    }

    public ItemStack getStackInSlotIsolated(int slot) {
        return this.containerStacks[slot];
    }

    public ItemStack decrStackSize(int slot, int count) {
        ItemStack stack = this.getStackInSlot(slot);
        if (stack != null) {
            if (stack.stackSize <= count) {
                this.setInventorySlotContents(slot, (ItemStack) null);
                return stack;
            } else {
                ItemStack split = stack.splitStack(count);
                if (stack.stackSize == 0) {
                    this.setInventorySlotContents(slot, (ItemStack) null);
                }

                this.markDirty();
                return split;
            }
        } else {
            return null;
        }
    }

    public ItemStack getStackInSlotOnClosing(int slot) {
        return null;
    }

    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        this.setInventorySlotContents(slot, itemStack, true);
    }

    public void setInventorySlotContents(int slot, ItemStack itemStack, boolean notify) {
        if (!this.isSharedSlot(slot)) {
            this.setInventorySlotContentsIsolated(slot, itemStack, notify);
        } else {
            BlockGarden block = this.getGardenBlock();
            ISlotMapping[] var5 = block.getSlotShareProfile()
                .getNeighborsForSlot(slot);
            int var6 = var5.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                ISlotMapping mapping = var5[var7];
                if (block.getConnectionProfile()
                    .isAttachedNeighbor(
                        this.worldObj,
                        this.xCoord,
                        this.yCoord,
                        this.zCoord,
                        this.xCoord + mapping.getMappedX(),
                        this.yCoord,
                        this.zCoord + mapping.getMappedZ())) {
                    TileEntity te = this.worldObj.getTileEntity(
                        this.xCoord + mapping.getMappedX(),
                        this.yCoord,
                        this.zCoord + mapping.getMappedZ());
                    if (te != null && te instanceof TileEntityGarden) {
                        TileEntityGarden nGarden = (TileEntityGarden) te;
                        nGarden.setInventorySlotContentsIsolated(mapping.getMappedSlot(), (ItemStack) null, notify);
                    }
                }
            }

            this.setInventorySlotContentsIsolated(slot, itemStack, notify);
        }
    }

    protected void setInventorySlotContentsIsolated(int slot, ItemStack itemStack, boolean notify) {
        this.containerStacks[slot] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
        if (notify) {
            BlockGarden.validateBlockState(this);
        }

    }

    public String getInventoryName() {
        return this.hasCustomInventoryName() ? this.customName : "container.gardencore.garden";
    }

    public boolean hasCustomInventoryName() {
        return this.customName != null && this.customName.length() > 0;
    }

    public int getInventoryStackLimit() {
        return 1;
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        if (this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this) {
            return false;
        } else {
            return player
                .getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D)
                <= 64.0D;
        }
    }

    public void openInventory() {}

    public void closeInventory() {}

    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
        if (!this.isSlotValid(slot)) {
            return false;
        } else {
            PlantItem plant = PlantItem.getForItem(this.worldObj, itemStack);
            if (plant == null) {
                return false;
            } else {
                BlockGarden block = this.getGardenBlock();
                if (block == null) {
                    return false;
                } else {
                    return block.isPlantValidForSlot(this.worldObj, this.xCoord, this.yCoord, this.zCoord, slot, plant);
                }
            }
        }
    }
}
