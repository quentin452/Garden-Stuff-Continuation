package com.jaquadro.minecraft.gardencore.block;

import com.jaquadro.minecraft.gardencore.api.GardenCoreAPI;
import com.jaquadro.minecraft.gardencore.core.ClientProxy;
import com.jaquadro.minecraft.gardencore.core.ModBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

public class BlockSmallFire extends Block {

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public BlockSmallFire(String name) {
        super(Material.fire);
        this.setHardness(0.0F);
        this.setLightLevel(1.0F);
        this.setBlockName(name);
        this.setBlockTextureName("fire");
        this.disableStats();
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public int getRenderType() {
        return ClientProxy.smallFireRenderID;
    }

    public int quantityDropped(Random random) {
        return 0;
    }

    public boolean func_149698_L() {
        return false;
    }

    public boolean isCollidable() {
        return false;
    }

    private static boolean blockCanHostSmallFlame(World world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        int metadata = world.getBlockMetadata(x, y, z);
        return GardenCoreAPI.instance()
            .blockCanHostSmallFlame(block, metadata);
    }

    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return blockCanHostSmallFlame(world, x, y - 1, z);
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        if (!blockCanHostSmallFlame(world, x, y - 1, z)) {
            world.setBlockToAir(x, y, z);
        }

    }

    public void onBlockAdded(World world, int x, int y, int z) {
        if (!blockCanHostSmallFlame(world, x, y - 1, z)) {
            world.setBlockToAir(x, y, z);
        } else {
            world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world) + world.rand.nextInt(10));
        }

    }

    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        entity.setFire(1);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        if (random.nextInt(64) == 0) {
            world.playSound(
                (double) ((float) x + 0.5F),
                (double) ((float) y + 0.5F),
                (double) ((float) z + 0.5F),
                "fire.fire",
                0.3F + random.nextFloat() * 0.5F,
                random.nextFloat() * 0.5F + 0.2F,
                false);
        }

        for (int i = 0; i < 3; ++i) {
            float spawnX = (float) x + random.nextFloat();
            float spawnY = (float) y + random.nextFloat() * 0.5F + 0.2F;
            float spawnZ = (float) z + random.nextFloat();
            world.spawnParticle("smoke", (double) spawnX, (double) spawnY, (double) spawnZ, 0.0D, 0.0D, 0.0D);
        }

    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return Blocks.fire.getIcon(side, meta);
    }

    public MapColor getMapColor(int meta) {
        return MapColor.tntColor;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.icons = new IIcon[] { register.registerIcon("GardenCore:" + this.getTextureName() + "_layer_0"),
            register.registerIcon("GardenCore:" + this.getTextureName() + "_layer_1") };
    }

    @SideOnly(Side.CLIENT)
    public IIcon getFireIcon(int layer) {
        return this.icons[layer];
    }

    public static boolean extinguishSmallFire(World world, EntityPlayer player, int x, int y, int z, int direction) {
        switch (direction) {
            case 0:
                --y;
                break;
            case 1:
                ++y;
                break;
            case 2:
                --z;
                break;
            case 3:
                ++z;
                break;
            case 4:
                --x;
                break;
            case 5:
                ++x;
        }

        if (world.getBlock(x, y, z) == ModBlocks.smallFire) {
            world.playAuxSFXAtEntity(player, 1004, x, y, z, 0);
            world.setBlockToAir(x, y, z);
            return true;
        } else {
            return false;
        }
    }
}
