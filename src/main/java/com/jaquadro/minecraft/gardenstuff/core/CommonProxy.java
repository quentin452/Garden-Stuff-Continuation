package com.jaquadro.minecraft.gardenstuff.core;

import com.jaquadro.minecraft.gardencore.util.BindingStack;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.World;

public abstract class CommonProxy {
   private Map bindingStacksClient = new HashMap();
   private Map bindingStacksServer = new HashMap();

   public void registerRenderers() {
   }

   public void postInit() {
      this.registerBindingStack(ModBlocks.lantern);
   }

   public void registerBindingStack(Object object) {
      this.bindingStacksClient.put(object, new BindingStack());
      this.bindingStacksServer.put(object, new BindingStack());
   }

   public BindingStack getClientBindingStack(Object object) {
      return (BindingStack)this.bindingStacksClient.get(object);
   }

   public BindingStack getBindingStack(World world, Object object) {
      return world.isRemote ? (BindingStack)this.bindingStacksClient.get(object) : (BindingStack)this.bindingStacksServer.get(object);
   }
}
