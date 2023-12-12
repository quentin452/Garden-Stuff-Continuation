package com.jaquadro.minecraft.gardenapi.internal.registry;

import com.jaquadro.minecraft.gardenapi.api.connect.IAttachable;
import com.jaquadro.minecraft.gardenapi.api.connect.IAttachableRegistry;
import com.jaquadro.minecraft.gardenapi.api.connect.StandardAttachable;
import com.jaquadro.minecraft.gardencore.util.UniqueMetaIdentifier;
import com.jaquadro.minecraft.gardencore.util.UniqueMetaRegistry;
import com.jaquadro.minecraft.gardenstuff.core.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class AttachableRegistry implements IAttachableRegistry {
   private UniqueMetaRegistry registry = new UniqueMetaRegistry();

   public AttachableRegistry() {
      this.init();
   }

   private void init() {
      int i;
      for(i = 0; i < 8; ++i) {
         this.registerAttachable(Blocks.stone_slab, i, StandardAttachable.createTop(0.5D));
         this.registerAttachable(Blocks.wooden_slab, i, StandardAttachable.createTop(0.5D));
      }

      for(i = 8; i < 16; ++i) {
         this.registerAttachable(Blocks.stone_slab, i, StandardAttachable.createBottom(0.5D));
         this.registerAttachable(Blocks.wooden_slab, i, StandardAttachable.createBottom(0.5D));
      }

      this.registerAttachable((Block)ModBlocks.candelabra, StandardAttachable.createBottom(0.0625D));
   }

   public void registerAttachable(String modId, String blockId, int meta, IAttachable attachable) {
      if (modId != null && blockId != null && attachable != null) {
         UniqueMetaIdentifier id = new UniqueMetaIdentifier(modId, blockId, meta);
         this.registry.register(id, attachable);
      }

   }

   public void registerAttachable(String modId, String blockId, IAttachable attachable) {
      if (modId != null && blockId != null && attachable != null) {
         UniqueMetaIdentifier id = new UniqueMetaIdentifier(modId, blockId);
         this.registry.register(id, attachable);
      }

   }

   public void registerAttachable(Block block, int meta, IAttachable attachable) {
      if (block != null && attachable != null) {
         UniqueMetaIdentifier id = UniqueMetaIdentifier.createFor(block, meta);
         this.registry.register(id, attachable);
      }

   }

   public void registerAttachable(Block block, IAttachable attachable) {
      if (block != null && attachable != null) {
         UniqueMetaIdentifier id = UniqueMetaIdentifier.createFor(block);
         this.registry.register(id, attachable);
      }

   }

   public void registerAttachable(ItemStack blockItemStack, IAttachable attachable) {
      UniqueMetaIdentifier id = UniqueMetaIdentifier.createFor(blockItemStack);
      if (id != null && attachable != null) {
         this.registry.register(id, attachable);
      }

   }

   public IAttachable getAttachable(Block block, int meta) {
      UniqueMetaIdentifier id = UniqueMetaIdentifier.createFor(block, meta);
      return id != null ? (IAttachable)this.registry.getEntry(id) : null;
   }
}
