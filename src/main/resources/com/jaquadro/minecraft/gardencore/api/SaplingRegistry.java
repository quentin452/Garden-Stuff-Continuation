package com.jaquadro.minecraft.gardencore.api;

import com.jaquadro.minecraft.gardencore.core.ModBlocks;
import com.jaquadro.minecraft.gardencore.core.ModItems;
import com.jaquadro.minecraft.gardencore.util.UniqueMetaIdentifier;
import com.jaquadro.minecraft.gardencore.util.UniqueMetaRegistry;
import java.util.HashMap;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public final class SaplingRegistry {
   private final UniqueMetaRegistry registry = new UniqueMetaRegistry();
   private static final SaplingRegistry instance = new SaplingRegistry();

   public static SaplingRegistry instance() {
      return instance;
   }

   private SaplingRegistry() {
      Item sapling = Item.getItemFromBlock(Blocks.sapling);
      this.registerSapling(sapling, 0, Blocks.log, 0, Blocks.leaves, 0);
      this.registerSapling(sapling, 1, Blocks.log, 1, Blocks.leaves, 1);
      this.registerSapling(sapling, 2, Blocks.log, 2, Blocks.leaves, 2);
      this.registerSapling(sapling, 3, Blocks.log, 3, Blocks.leaves, 3);
      this.registerSapling(sapling, 4, Blocks.log2, 0, Blocks.leaves2, 0);
      this.registerSapling(sapling, 5, Blocks.log2, 1, Blocks.leaves2, 1);
   }

   public void registerSapling(Item sapling, int saplingMeta, Block wood, int woodMeta, Block leaf, int leafMeta) {
      if (sapling != null && wood != null && leaf != null) {
         this.registerSapling(ModItems.getUniqueMetaID(sapling, saplingMeta), ModBlocks.getUniqueMetaID(wood, woodMeta), ModBlocks.getUniqueMetaID(leaf, leafMeta));
      }
   }

   public void registerSapling(UniqueMetaIdentifier sapling, UniqueMetaIdentifier wood, UniqueMetaIdentifier leaf) {
      SaplingRegistry.SaplingRecord record = new SaplingRegistry.SaplingRecord();
      record.saplingType = sapling;
      record.woodType = wood;
      record.leafType = leaf;
      this.registry.register(sapling, record);
   }

   public UniqueMetaIdentifier getLeavesForSapling(Item sapling) {
      return this.getLeavesForSapling(sapling, 32767);
   }

   public UniqueMetaIdentifier getLeavesForSapling(Item sapling, int saplingMeta) {
      return this.getLeavesForSapling(ModItems.getUniqueMetaID(sapling, saplingMeta));
   }

   public UniqueMetaIdentifier getLeavesForSapling(UniqueMetaIdentifier sapling) {
      SaplingRegistry.SaplingRecord record = (SaplingRegistry.SaplingRecord)this.registry.getEntry(sapling);
      return record == null ? null : record.leafType;
   }

   public UniqueMetaIdentifier getWoodForSapling(Item sapling) {
      return this.getWoodForSapling(sapling, 32767);
   }

   public UniqueMetaIdentifier getWoodForSapling(Item sapling, int saplingMeta) {
      return this.getWoodForSapling(ModItems.getUniqueMetaID(sapling, saplingMeta));
   }

   public UniqueMetaIdentifier getWoodForSapling(UniqueMetaIdentifier sapling) {
      SaplingRegistry.SaplingRecord record = (SaplingRegistry.SaplingRecord)this.registry.getEntry(sapling);
      return record == null ? null : record.woodType;
   }

   public Object getExtendedData(Item sapling, String key) {
      return this.getExtendedData(sapling, 32767, key);
   }

   public Object getExtendedData(Item sapling, int saplingMeta, String key) {
      return this.getExtendedData(ModItems.getUniqueMetaID(sapling, saplingMeta), key);
   }

   public Object getExtendedData(UniqueMetaIdentifier sapling, String key) {
      SaplingRegistry.SaplingRecord record = (SaplingRegistry.SaplingRecord)this.registry.getEntry(sapling);
      return record == null ? null : record.extraData.get(key);
   }

   public void putExtendedData(Item sapling, String key, Object data) {
      this.putExtendedData(sapling, 32767, key, data);
   }

   public void putExtendedData(Item sapling, int saplingMeta, String key, Object data) {
      this.putExtendedData(ModItems.getUniqueMetaID(sapling, saplingMeta), key, data);
   }

   public void putExtendedData(UniqueMetaIdentifier sapling, String key, Object data) {
      SaplingRegistry.SaplingRecord record = (SaplingRegistry.SaplingRecord)this.registry.getEntry(sapling);
      if (record == null) {
         this.registerSapling(sapling, (UniqueMetaIdentifier)null, (UniqueMetaIdentifier)null);
         record = (SaplingRegistry.SaplingRecord)this.registry.getEntry(sapling);
      }

      record.extraData.put(key, data);
   }

   private static class SaplingRecord {
      public UniqueMetaIdentifier saplingType;
      public UniqueMetaIdentifier woodType;
      public UniqueMetaIdentifier leafType;
      public HashMap extraData;

      private SaplingRecord() {
         this.extraData = new HashMap();
      }

      // $FF: synthetic method
      SaplingRecord(Object x0) {
         this();
      }
   }
}
