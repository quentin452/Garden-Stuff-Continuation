package com.jaquadro.minecraft.gardencore.util;

import com.google.common.base.Objects;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class UniqueMetaIdentifier {
   public final String modId;
   public final String name;
   public final int meta;
   private UniqueIdentifier cachedUID;

   public UniqueMetaIdentifier(String modId, String name) {
      this.modId = modId;
      this.name = name;
      this.meta = 32767;
   }

   public UniqueMetaIdentifier(String modId, String name, int meta) {
      this.modId = modId;
      this.name = name;
      this.meta = meta;
   }

   public UniqueMetaIdentifier(String qualifiedName, int meta) {
      String[] parts = qualifiedName.split(":");
      this.modId = parts[0];
      this.name = parts[1];
      this.meta = meta;
   }

   public UniqueMetaIdentifier(String compoundName) {
      String[] parts1 = compoundName.split(";");
      String[] parts2 = parts1[0].split(":");
      this.modId = parts2[0];
      if (parts2.length >= 2) {
         this.name = parts2[1];
      } else {
         this.name = "";
      }

      if (parts1.length >= 2) {
         this.meta = Integer.parseInt(parts1[1]);
      } else if (parts2.length > 2) {
         this.meta = Integer.parseInt(parts2[parts2.length - 1]);
      } else {
         this.meta = 32767;
      }

   }

   public UniqueMetaIdentifier(String compoundName, char separator) {
      String[] parts1 = compoundName.split("[ ]*" + separator + "[ ]*");
      String[] parts2 = parts1[0].split(":");
      this.modId = parts2[0];
      if (parts2.length >= 2) {
         this.name = parts2[1];
      } else {
         this.name = "";
      }

      if (parts1.length >= 2) {
         this.meta = Integer.parseInt(parts1[1]);
      } else {
         this.meta = 32767;
      }

   }

   public UniqueIdentifier getUniqueIdentifier() {
      if (this.cachedUID == null) {
         this.cachedUID = new UniqueIdentifier(this.modId + ":" + this.name);
      }

      return this.cachedUID;
   }

   public Block getBlock() {
      return GameRegistry.findBlock(this.modId, this.name);
   }

   public Item getItem() {
      return GameRegistry.findItem(this.modId, this.name);
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (obj.getClass() != this.getClass()) {
         return false;
      } else {
         UniqueMetaIdentifier other = (UniqueMetaIdentifier)obj;
         return Objects.equal(this.modId, other.modId) && Objects.equal(this.name, other.name) && this.meta == other.meta;
      }
   }

   public int hashCode() {
      return Objects.hashCode(new Object[]{this.modId, this.name}) ^ this.meta * 37;
   }

   public String toString() {
      return String.format("%s:%s;%d", this.modId, this.name, this.meta);
   }

   public static UniqueMetaIdentifier createFor(ItemStack itemStack) {
      if (itemStack.getItem() == null) {
         return null;
      } else {
         String name = GameData.getItemRegistry().getNameForObject(itemStack.getItem());
         return new UniqueMetaIdentifier(name, itemStack.getItemDamage());
      }
   }

   public static UniqueMetaIdentifier createFor(Block block, int meta) {
      if (block == null) {
         return null;
      } else {
         String name = GameData.getBlockRegistry().getNameForObject(block);
         return new UniqueMetaIdentifier(name, meta);
      }
   }

   public static UniqueMetaIdentifier createFor(Block block) {
      if (block == null) {
         return null;
      } else {
         String name = GameData.getBlockRegistry().getNameForObject(block);
         return new UniqueMetaIdentifier(name);
      }
   }
}
