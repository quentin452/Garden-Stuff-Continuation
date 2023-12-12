package com.jaquadro.minecraft.gardenapi.internal.registry;

import com.jaquadro.minecraft.gardenapi.api.component.ILanternSource;
import com.jaquadro.minecraft.gardenapi.api.component.ILanternSourceRegistry;
import cpw.mods.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LanternSourceRegistry implements ILanternSourceRegistry {
   private Map registry = new HashMap();

   public void registerLanternSource(ILanternSource lanternSource) {
      if (lanternSource != null) {
         if (this.registry.containsKey(lanternSource.getSourceID())) {
            FMLLog.log("GardenStuff", Level.ERROR, "Key '%s' already registered as a lantern source.", new Object[0]);
         } else {
            this.registry.put(lanternSource.getSourceID(), lanternSource);
         }
      }
   }

   public ILanternSource getLanternSource(String key) {
      return (ILanternSource)this.registry.get(key);
   }

   public Collection getAllLanternSources() {
      return this.registry.values();
   }
}
