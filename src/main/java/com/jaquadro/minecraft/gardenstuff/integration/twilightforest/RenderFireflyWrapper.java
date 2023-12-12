package com.jaquadro.minecraft.gardenstuff.integration.twilightforest;

import com.jaquadro.minecraft.gardenstuff.integration.TwilightForestIntegration;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderFireflyWrapper extends Render {

    private static final ResourceLocation textureLoc = new ResourceLocation(
        "twilightforest:textures/model/firefly-tiny.png");
    private Render render;

    public RenderFireflyWrapper() {
        try {
            this.render = (Render) TwilightForestIntegration.constRenderFirefly.newInstance();
        } catch (Throwable var2) {}

    }

    public void setRenderManager(RenderManager renderMan) {
        super.setRenderManager(renderMan);
        if (this.render != null) {
            this.render.setRenderManager(renderMan);
        }

    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTickTime) {
        if (entity instanceof EntityFireflyWrapper) {
            boolean lightingEnabled = GL11.glIsEnabled(2896);
            if (lightingEnabled) {
                GL11.glDisable(2896);
            }

            if (this.render != null) {
                this.render.doRender(((EntityFireflyWrapper) entity).entity, x, y, z, yaw, partialTickTime);
            }

            if (lightingEnabled) {
                GL11.glEnable(2896);
            }
        }

    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return textureLoc;
    }
}
