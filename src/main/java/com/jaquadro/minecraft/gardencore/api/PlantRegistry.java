package com.jaquadro.minecraft.gardencore.api;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IPlantable;

import com.jaquadro.minecraft.gardencore.api.plant.DefaultPlantInfo;
import com.jaquadro.minecraft.gardencore.api.plant.IPlantInfo;
import com.jaquadro.minecraft.gardencore.api.plant.PlantSize;
import com.jaquadro.minecraft.gardencore.api.plant.PlantType;
import com.jaquadro.minecraft.gardencore.api.plant.SimplePlantInfo;
import com.jaquadro.minecraft.gardencore.client.renderer.plant.CropsPlantRenderer;
import com.jaquadro.minecraft.gardencore.client.renderer.plant.CrossedSquaresPlantRenderer;
import com.jaquadro.minecraft.gardencore.client.renderer.plant.DoublePlantRenderer;
import com.jaquadro.minecraft.gardencore.client.renderer.plant.GroundCoverPlantRenderer;
import com.jaquadro.minecraft.gardencore.client.renderer.plant.SunflowerRenderer;
import com.jaquadro.minecraft.gardencore.core.ModBlocks;
import com.jaquadro.minecraft.gardencore.integration.VanillaMetaResolver;
import com.jaquadro.minecraft.gardencore.util.UniqueMetaIdentifier;
import com.jaquadro.minecraft.gardencore.util.UniqueMetaRegistry;

public final class PlantRegistry {

    private static final DefaultPlantInfo defaultPlantInfo = new DefaultPlantInfo();
    public static final IPlantRenderer CROPS_RENDERER = new CropsPlantRenderer();
    public static final IPlantRenderer CROSSED_SQUARES_RENDERER = new CrossedSquaresPlantRenderer();
    public static final IPlantRenderer DOUBLE_PLANT_RENDERER = new DoublePlantRenderer();
    public static final IPlantRenderer GROUND_COVER_RENDERER = new GroundCoverPlantRenderer();
    private UniqueMetaRegistry renderRegistry = new UniqueMetaRegistry();
    private Map renderIdRegistry = new HashMap();
    private UniqueMetaRegistry metaResolverRegistry = new UniqueMetaRegistry();
    private UniqueMetaRegistry plantInfoRegistry = new UniqueMetaRegistry();
    private static PlantRegistry instance = new PlantRegistry();

    public static PlantRegistry instance() {
        return instance;
    }

    public boolean isPlantBlacklisted(ItemStack plant) {
        return false;
    }

    public boolean plantRespondsToBonemeal(ItemStack plant) {
        return false;
    }

    private PlantRegistry() {
        this.registerPlantRenderer(1, new CrossedSquaresPlantRenderer());
        this.registerPlantRenderer(40, new DoublePlantRenderer());
        this.registerPlantRenderer(Blocks.double_plant, 0, new SunflowerRenderer());
        VanillaMetaResolver vanillaResolver = new VanillaMetaResolver();
        this.registerPlantMetaResolver(Blocks.red_flower, vanillaResolver);
        this.registerPlantMetaResolver(Blocks.yellow_flower, vanillaResolver);
        this.registerPlantMetaResolver(Blocks.red_mushroom, vanillaResolver);
        this.registerPlantMetaResolver(Blocks.brown_mushroom, vanillaResolver);
        this.registerPlantMetaResolver(Blocks.tallgrass, vanillaResolver);
        this.registerPlantMetaResolver(Blocks.double_plant, vanillaResolver);
        int[] var2 = new int[] { 0, 2, 3, 4, 5, 6, 7, 8 };
        int var3 = var2.length;

        int var4;
        int i;
        for (var4 = 0; var4 < var3; ++var4) {
            i = var2[var4];
            this.registerPlantInfo(Blocks.red_flower, i, new SimplePlantInfo(PlantType.GROUND, PlantSize.SMALL));
        }

        var2 = new int[] { 0 };
        var3 = var2.length;

        for (var4 = 0; var4 < var3; ++var4) {
            i = var2[var4];
            this.registerPlantInfo(Blocks.yellow_flower, i, new SimplePlantInfo(PlantType.GROUND, PlantSize.SMALL));
        }

        var2 = new int[] { 0, 1, 3, 4, 5 };
        var3 = var2.length;

        for (var4 = 0; var4 < var3; ++var4) {
            i = var2[var4];
            this.registerPlantInfo(
                Blocks.double_plant,
                i,
                new SimplePlantInfo(PlantType.GROUND, PlantSize.MEDIUM, 2, 2, new int[] { i, i | 8 }));
        }

        var2 = new int[] { 2 };
        var3 = var2.length;

        for (var4 = 0; var4 < var3; ++var4) {
            i = var2[var4];
            this.registerPlantInfo(
                Blocks.double_plant,
                i,
                new SimplePlantInfo(PlantType.GROUND, PlantSize.LARGE, 2, 2, new int[] { i, i | 8 }));
        }

        var2 = new int[] { 2 };
        var3 = var2.length;

        for (var4 = 0; var4 < var3; ++var4) {
            i = var2[var4];
            this.registerPlantInfo(Blocks.tallgrass, i, new SimplePlantInfo(PlantType.GROUND, PlantSize.SMALL));
        }

        var2 = new int[] { 1 };
        var3 = var2.length;

        for (var4 = 0; var4 < var3; ++var4) {
            i = var2[var4];
            this.registerPlantInfo(Blocks.tallgrass, i, new SimplePlantInfo(PlantType.GROUND, PlantSize.LARGE));
        }

        this.registerPlantInfo(Blocks.red_mushroom, 0, new SimplePlantInfo(PlantType.GROUND, PlantSize.SMALL));
        this.registerPlantInfo(Blocks.brown_mushroom, 0, new SimplePlantInfo(PlantType.GROUND, PlantSize.SMALL));
        this.registerPlantInfo((Block) Blocks.waterlily, new SimplePlantInfo(PlantType.GROUND_COVER, PlantSize.FULL));
        this.registerPlantInfo((Block) Blocks.wheat, new SimplePlantInfo(PlantType.GROUND, PlantSize.FULL));
        this.registerPlantInfo((Block) Blocks.carrots, new SimplePlantInfo(PlantType.GROUND, PlantSize.FULL));
        this.registerPlantInfo((Block) Blocks.potatoes, new SimplePlantInfo(PlantType.GROUND, PlantSize.FULL));
        this.registerPlantInfo((Block) Blocks.nether_wart, new SimplePlantInfo(PlantType.GROUND, PlantSize.FULL));
    }

    public void registerPlantRenderer(int renderId, IPlantRenderer renderer) {
        if (!this.renderIdRegistry.containsKey(renderId)) {
            this.renderIdRegistry.put(renderId, renderer);
        }
    }

    public void registerPlantRenderer(Block block, IPlantRenderer renderer) {
        this.registerPlantRenderer(block, 32767, renderer);
    }

    public void registerPlantRenderer(Block block, int meta, IPlantRenderer renderer) {
        UniqueMetaIdentifier id = ModBlocks.getUniqueMetaID(block, meta);
        if (id != null) {
            this.renderRegistry.register(id, renderer);
        }

    }

    public void registerPlantRenderer(String modId, String block, IPlantRenderer renderer) {
        UniqueMetaIdentifier id = new UniqueMetaIdentifier(modId, block, 32767);
        this.renderRegistry.register(id, renderer);
    }

    public void registerPlantRenderer(String modId, String block, int meta, IPlantRenderer renderer) {
        UniqueMetaIdentifier id = new UniqueMetaIdentifier(modId, block, meta);
        this.renderRegistry.register(id, renderer);
    }

    public IPlantRenderer getPlantRenderer(Block block, int meta) {
        UniqueMetaIdentifier id = ModBlocks.getUniqueMetaID(block, meta);
        if (id == null) {
            return null;
        } else {
            IPlantRenderer renderer = (IPlantRenderer) this.renderRegistry.getEntry(id);
            return renderer != null ? renderer : (IPlantRenderer) this.renderIdRegistry.get(block.getRenderType());
        }
    }

    public void registerPlantMetaResolver(Block block, IPlantMetaResolver resolver) {
        this.registerPlantMetaResolver(block, 32767, resolver);
    }

    public void registerPlantMetaResolver(Block block, int meta, IPlantMetaResolver resolver) {
        UniqueMetaIdentifier id = ModBlocks.getUniqueMetaID(block, meta);
        if (id != null) {
            this.metaResolverRegistry.register(id, resolver);
        }

    }

    public void registerPlantMetaResolver(String modId, String block, IPlantMetaResolver resolver) {
        UniqueMetaIdentifier id = new UniqueMetaIdentifier(modId, block, 32767);
        this.metaResolverRegistry.register(id, resolver);
    }

    public void registerPlantMetaResolver(String modId, String block, int meta, IPlantMetaResolver resolver) {
        UniqueMetaIdentifier id = new UniqueMetaIdentifier(modId, block, meta);
        this.metaResolverRegistry.register(id, resolver);
    }

    public IPlantMetaResolver getPlantMetaResolver(Block block, int meta) {
        UniqueMetaIdentifier id = ModBlocks.getUniqueMetaID(block, meta);
        return id == null ? null : (IPlantMetaResolver) this.metaResolverRegistry.getEntry(id);
    }

    public void registerPlantInfo(Block block, IPlantInfo info) {
        this.registerPlantInfo(block, 32767, info);
    }

    public void registerPlantInfo(Block block, int meta, IPlantInfo info) {
        UniqueMetaIdentifier id = ModBlocks.getUniqueMetaID(block, meta);
        if (id != null) {
            this.plantInfoRegistry.register(id, info);
            this.metaResolverRegistry.register(id, info);
        }

    }

    public void registerPlantInfo(String modId, IPlantInfo info) {
        UniqueMetaIdentifier id = new UniqueMetaIdentifier(modId);
        this.plantInfoRegistry.register(id, info);
        this.metaResolverRegistry.register(id, info);
    }

    public void registerPlantInfo(String modId, String block, IPlantInfo info) {
        UniqueMetaIdentifier id = new UniqueMetaIdentifier(modId, block);
        this.plantInfoRegistry.register(id, info);
        this.metaResolverRegistry.register(id, info);
    }

    public void registerPlantInfo(String modId, String block, int meta, IPlantInfo info) {
        UniqueMetaIdentifier id = new UniqueMetaIdentifier(modId, block, meta);
        this.plantInfoRegistry.register(id, info);
        this.metaResolverRegistry.register(id, info);
    }

    public IPlantInfo getPlantInfo(Block block, int meta) {
        UniqueMetaIdentifier id = ModBlocks.getUniqueMetaID(block, meta);
        return id == null ? null : (IPlantInfo) this.plantInfoRegistry.getEntry(id);
    }

    public IPlantInfo getPlantInfo(IBlockAccess world, IPlantable plant) {
        Block block = plant.getPlant(world, 0, 0, 0);
        int meta = plant.getPlantMetadata(world, 0, 0, 0);
        return this.getPlantInfo(block, meta);
    }

    public IPlantInfo getPlantInfoOrDefault(Block block, int meta) {
        IPlantInfo info = this.getPlantInfo(block, meta);
        return (IPlantInfo) (info != null ? info : defaultPlantInfo);
    }

    public boolean isValidPlantBlock(Block block) {
        if (block == null) {
            return false;
        } else if (block instanceof IPlantable) {
            return true;
        } else {
            Item item = Item.getItemFromBlock(block);
            return item instanceof IPlantable;
        }
    }

    public static IPlantable getPlantable(ItemStack plantItemStack) {
        if (plantItemStack != null && plantItemStack.getItem() != null) {
            IPlantable plantable = null;
            Item item = plantItemStack.getItem();
            if (item instanceof IPlantable) {
                plantable = (IPlantable) item;
            } else if (item instanceof ItemBlock) {
                Block itemBlock = Block.getBlockFromItem(item);
                if (itemBlock instanceof IPlantable) {
                    plantable = (IPlantable) itemBlock;
                }
            }

            return plantable;
        } else {
            return null;
        }
    }
}
