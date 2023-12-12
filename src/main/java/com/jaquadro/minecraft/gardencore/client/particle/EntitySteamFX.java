package com.jaquadro.minecraft.gardencore.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.world.World;

public class EntitySteamFX extends EntitySmokeFX {

    public EntitySteamFX(World world, double x, double y, double z) {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D);
        float color = 0.7F + (float) Math.random() * 0.3F;
        this.setRBGColorF(color, color, color);
    }

    public static EntityFX spawnParticle(World world, double x, double y, double z) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc != null && mc.renderViewEntity != null && mc.effectRenderer != null) {
            int setting = mc.gameSettings.particleSetting;
            if (setting == 1 && mc.theWorld.rand.nextInt(3) == 0) {
                setting = 2;
            }

            double dx = mc.renderViewEntity.posX - x;
            double dy = mc.renderViewEntity.posY - y;
            double dz = mc.renderViewEntity.posZ - z;
            if (dx * dx + dy * dy + dz * dz > 256.0D) {
                return null;
            } else if (setting > 1) {
                return null;
            } else {
                EntityFX effect = new EntitySteamFX(world, x, y, z);
                mc.effectRenderer.addEffect(effect);
                return effect;
            }
        } else {
            return null;
        }
    }
}
