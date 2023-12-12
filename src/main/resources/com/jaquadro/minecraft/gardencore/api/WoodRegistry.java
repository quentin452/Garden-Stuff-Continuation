package com.jaquadro.minecraft.gardencore.api;

import com.jaquadro.minecraft.gardencore.core.ModBlocks;
import com.jaquadro.minecraft.gardencore.util.UniqueMetaIdentifier;
import com.jaquadro.minecraft.gardencore.util.UniqueMetaRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import java.util.Set;

public final class WoodRegistry {
   private final UniqueMetaRegistry registry = new UniqueMetaRegistry();
   private static WoodRegistry instance = new WoodRegistry();

   public static WoodRegistry instance() {
      return instance;
   }

   private WoodRegistry() {
      this.registerWoodType(Blocks.log, 0);
      this.registerWoodType(Blocks.log, 1);
      this.registerWoodType(Blocks.log, 2);
      this.registerWoodType(Blocks.log, 3);
      this.registerWoodType(Blocks.log2, 0);
      this.registerWoodType(Blocks.log2, 1);
   }

   public void registerWoodType(Block block, int meta) {
      if (block != null) {
         UniqueMetaIdentifier id = ModBlocks.getUniqueMetaID(block, meta);
         if (id != null) {
            this.registry.register(id, block);
         }

      }
   }

   public Set registeredTypes() {
      return this.registry.entrySet();
   }

   public boolean contains(Block wood, int woodMeta) {
      return this.contains(ModBlocks.getUniqueMetaID(wood, woodMeta));
   }

   public boolean contains(UniqueMetaIdentifier wood) {
      return this.registry.getEntry(wood) != null;
   }
}
