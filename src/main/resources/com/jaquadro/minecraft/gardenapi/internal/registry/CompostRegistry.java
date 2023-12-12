package com.jaquadro.minecraft.gardenapi.internal.registry;

import com.jaquadro.minecraft.gardenapi.api.machine.ICompostMaterial;
import com.jaquadro.minecraft.gardenapi.api.machine.ICompostRegistry;
import com.jaquadro.minecraft.gardenapi.api.machine.StandardCompostMaterial;
import com.jaquadro.minecraft.gardencore.util.UniqueMetaIdentifier;
import com.jaquadro.minecraft.gardencore.util.UniqueMetaRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.BlockVine;
import net.minecraft.block.IGrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;
import java.util.Map;

public class CompostRegistry implements ICompostRegistry {
   private static ICompostMaterial defaultMaterial = new StandardCompostMaterial();
   private UniqueMetaRegistry itemRegistry = new UniqueMetaRegistry();
   private Map oreDictRegistry = new HashMap();
   private Map classRegistry = new HashMap();

   public CompostRegistry() {
      this.init();
   }

   private void init() {
      this.registerCompostMaterial(new ItemStack(Blocks.melon_block), defaultMaterial);
      this.registerCompostMaterial(new ItemStack(Blocks.pumpkin), defaultMaterial);
      this.registerCompostMaterial(new ItemStack(Blocks.hay_block), defaultMaterial);
      this.registerCompostMaterial((ItemStack)(new ItemStack(Items.string)), new StandardCompostMaterial(100, 0.0625F));
      this.registerCompostMaterial((ItemStack)(new ItemStack(Items.wheat)), new StandardCompostMaterial(100, 0.125F));
      this.registerCompostMaterial((ItemStack)(new ItemStack(Items.reeds)), new StandardCompostMaterial(150, 0.125F));
      this.registerCompostMaterial((ItemStack)(new ItemStack(Items.feather)), new StandardCompostMaterial(50, 0.0625F));
      this.registerCompostMaterial((ItemStack)(new ItemStack(Items.rotten_flesh)), new StandardCompostMaterial(150, 0.125F));
      this.registerCompostMaterial((ItemStack)(new ItemStack(Items.leather)), new StandardCompostMaterial(150, 0.125F));
      this.registerCompostMaterial((String)"treeWood", new StandardCompostMaterial(300, 0.25F));
      this.registerCompostMaterial((String)"logWood", new StandardCompostMaterial(300, 0.25F));
      this.registerCompostMaterial("treeLeaves", defaultMaterial);
      this.registerCompostMaterial("treeSapling", defaultMaterial);
      this.registerCompostMaterial("stickWood", defaultMaterial);
      this.registerCompostMaterial(IPlantable.class, defaultMaterial);
      this.registerCompostMaterial(IGrowable.class, defaultMaterial);
      this.registerCompostMaterial(BlockLeavesBase.class, defaultMaterial);
      this.registerCompostMaterial(BlockVine.class, defaultMaterial);
      this.registerCompostMaterial(ItemFood.class, defaultMaterial);
   }

   public void registerCompostMaterial(String modId, String itemId, int meta, ICompostMaterial materialInfo) {
      if (modId != null && itemId != null && materialInfo != null) {
         UniqueMetaIdentifier id = new UniqueMetaIdentifier(modId, itemId, meta);
         this.itemRegistry.register(id, materialInfo);
      }

   }

   public void registerCompostMaterial(String modId, String itemId, ICompostMaterial materialInfo) {
      if (modId != null && itemId != null && materialInfo != null) {
         UniqueMetaIdentifier id = new UniqueMetaIdentifier(modId, itemId);
         this.itemRegistry.register(id, materialInfo);
      }

   }

   public void registerCompostMaterial(ItemStack itemStack, ICompostMaterial materialInfo) {
      UniqueMetaIdentifier id = UniqueMetaIdentifier.createFor(itemStack);
      if (id != null && materialInfo != null) {
         this.itemRegistry.register(id, materialInfo);
      }

   }

   public void registerCompostMaterial(String oreDictionaryKey, ICompostMaterial materialInfo) {
      if (oreDictionaryKey != null && materialInfo != null) {
         this.oreDictRegistry.put(oreDictionaryKey, materialInfo);
      }

   }

   public void registerCompostMaterial(Class clazz, ICompostMaterial materialInfo) {
      if (clazz != null && materialInfo != null) {
         this.classRegistry.put(clazz, materialInfo);
      }

   }

   public void removeCompostMaterial(ItemStack itemStack) {
      this.itemRegistry.remove(UniqueMetaIdentifier.createFor(itemStack));
   }

   public void removeCompostMaterial(String oreDictionaryKey) {
      if (oreDictionaryKey != null) {
         this.oreDictRegistry.remove(oreDictionaryKey);
      }

   }

   public void removeCompostMaterial(Class clazz) {
      if (clazz != null) {
         this.classRegistry.remove(clazz);
      }

   }

   public void clear() {
      this.itemRegistry.clear();
      this.oreDictRegistry.clear();
      this.classRegistry.clear();
   }

   public ICompostMaterial getCompostMaterialInfo(ItemStack itemStack) {
      if (itemStack == null) {
         return null;
      } else {
         UniqueMetaIdentifier id = UniqueMetaIdentifier.createFor(itemStack);
         if (id != null) {
            ICompostMaterial entry = (ICompostMaterial)this.itemRegistry.getEntry(id);
            if (entry != null) {
               return entry;
            }
         }

         int[] var9 = OreDictionary.getOreIDs(itemStack);
         int var4 = var9.length;

         int var5;
         int oreId;
         for(var5 = 0; var5 < var4; ++var5) {
            oreId = var9[var5];
            String oreEntry = OreDictionary.getOreName(oreId);
            if (oreEntry != null) {
               ICompostMaterial entry = (ICompostMaterial)this.oreDictRegistry.get(oreEntry);
               if (entry != null) {
                  return entry;
               }
            }
         }

         if (itemStack.getItem() instanceof ItemBlock) {
            Block block = Block.getBlockFromItem(itemStack.getItem());

            for(Class clazz = block.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
               if (this.classRegistry.containsKey(clazz)) {
                  return (ICompostMaterial)this.classRegistry.get(clazz);
               }

               Class[] var14 = clazz.getInterfaces();
               oreId = var14.length;

               for(int var15 = 0; var15 < oreId; ++var15) {
                  Class iface = var14[var15];
                  if (this.classRegistry.containsKey(iface)) {
                     return (ICompostMaterial)this.classRegistry.get(iface);
                  }
               }
            }
         } else if (itemStack.getItem() != null) {
            for(Class clazz = itemStack.getItem().getClass(); clazz != null; clazz = clazz.getSuperclass()) {
               if (this.classRegistry.containsKey(clazz)) {
                  return (ICompostMaterial)this.classRegistry.get(clazz);
               }

               Class[] var13 = clazz.getInterfaces();
               var5 = var13.length;

               for(oreId = 0; oreId < var5; ++oreId) {
                  Class iface = var13[oreId];
                  if (this.classRegistry.containsKey(iface)) {
                     return (ICompostMaterial)this.classRegistry.get(iface);
                  }
               }
            }
         }

         return null;
      }
   }
}
