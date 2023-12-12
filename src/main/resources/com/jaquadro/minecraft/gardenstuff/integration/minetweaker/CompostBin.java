package com.jaquadro.minecraft.gardenstuff.integration.minetweaker;

import com.jaquadro.minecraft.gardenapi.api.GardenAPI;
import com.jaquadro.minecraft.gardenapi.api.machine.StandardCompostMaterial;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gardenstuff.CompostBin")
public class CompostBin {
   @ZenMethod
   public static void add(IItemStack item) {
      add((IItemStack)item, 150);
   }

   @ZenMethod
   public static void add(IItemStack item, int processTime) {
      ItemStack stack = MineTweakerMC.getItemStack(item);
      if (stack != null && stack.getItem() != null) {
         MineTweakerAPI.apply(new CompostBin.AddItemAction(stack, processTime));
      } else {
         MineTweakerAPI.logError("Tried to add invalid item to compost table.");
      }

   }

   @ZenMethod
   public static void add(String oredictKey) {
      add((String)oredictKey, 150);
   }

   @ZenMethod
   public static void add(String oredictKey, int processTime) {
      if (oredictKey != null && oredictKey.length() > 0) {
         MineTweakerAPI.apply(new CompostBin.AddOreAction(oredictKey, processTime));
      } else {
         MineTweakerAPI.logError("Tried to add empty ore dictionary key to compost table.");
      }

   }

   @ZenMethod
   public static void clear() {
      MineTweakerAPI.apply(new CompostBin.ClearAction());
   }

   private static class ClearAction implements IUndoableAction {
      private ClearAction() {
      }

      public void apply() {
         GardenAPI.instance().registries().compost().clear();
      }

      public boolean canUndo() {
         return false;
      }

      public void undo() {
      }

      public String describe() {
         return "Clearing compost table.";
      }

      public String describeUndo() {
         return "";
      }

      public Object getOverrideKey() {
         return null;
      }

      // $FF: synthetic method
      ClearAction(Object x0) {
         this();
      }
   }

   private static class AddOreAction implements IUndoableAction {
      private String material;
      private int processTime;

      public AddOreAction(String material, int processTime) {
         this.material = material;
         this.processTime = processTime > 0 ? processTime : 150;
      }

      public void apply() {
         GardenAPI.instance().registries().compost().registerCompostMaterial((String)this.material, new StandardCompostMaterial(this.processTime, 0.125F));
      }

      public boolean canUndo() {
         return true;
      }

      public void undo() {
         GardenAPI.instance().registries().compost().removeCompostMaterial(this.material);
      }

      public String describe() {
         return "Adding ore dictionary key '" + this.material + "' to compost table with processing time '" + this.processTime + "'";
      }

      public String describeUndo() {
         return "Removing previously added ore dictionary key '" + this.material + "' from compost table.";
      }

      public Object getOverrideKey() {
         return null;
      }
   }

   private static class AddItemAction implements IUndoableAction {
      private ItemStack material;
      private int processTime;

      public AddItemAction(ItemStack material, int processTime) {
         this.material = material.copy();
         this.processTime = processTime > 0 ? processTime : 150;
      }

      public void apply() {
         GardenAPI.instance().registries().compost().registerCompostMaterial((ItemStack)this.material, new StandardCompostMaterial(this.processTime, 0.125F));
      }

      public boolean canUndo() {
         return true;
      }

      public void undo() {
         GardenAPI.instance().registries().compost().removeCompostMaterial(this.material);
      }

      public String describe() {
         return "Adding item '" + this.material.getDisplayName() + "' to compost table with processing time '" + this.processTime + "'";
      }

      public String describeUndo() {
         return "Removing previously added item '" + this.material.getDisplayName() + "' from compost table.";
      }

      public Object getOverrideKey() {
         return null;
      }
   }
}
