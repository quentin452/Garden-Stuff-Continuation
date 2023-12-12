package com.jaquadro.minecraft.gardenstuff.integration.twilightforest;

import net.minecraft.entity.effect.EntityWeatherEffect;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.jaquadro.minecraft.gardenstuff.integration.TwilightForestIntegration;

public class EntityFireflyWrapper extends EntityWeatherEffect {

    public EntityWeatherEffect entity;

    public EntityFireflyWrapper(World world, double x, double y, double z) {
        super(world);

        try {
            this.entity = (EntityWeatherEffect) TwilightForestIntegration.constEntityFirefly
                .newInstance(world, x, y, z);
            this.setPositionAndRotation(x, y, z, 0.0F, 0.0F);
        } catch (Throwable var9) {}

    }

    public void onUpdate() {
        super.onUpdate();
        if (this.entity != null) {
            this.entity.onUpdate();
            if (!this.entity.isEntityAlive()) {
                this.setDead();
            }
        } else {
            this.setDead();
        }

    }

    protected void entityInit() {}

    protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {}

    protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {}
}
