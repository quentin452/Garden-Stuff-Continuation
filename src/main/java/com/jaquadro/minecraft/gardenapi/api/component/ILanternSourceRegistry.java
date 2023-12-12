package com.jaquadro.minecraft.gardenapi.api.component;

public interface ILanternSourceRegistry {
   void registerLanternSource(ILanternSource var1);

   ILanternSource getLanternSource(String var1);
}
