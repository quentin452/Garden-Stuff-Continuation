package com.jaquadro.minecraft.gardencore.api.event;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event.HasResult;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;

@Cancelable
@HasResult
public class EnrichedSoilEvent extends PlayerEvent {

    public final World world;
    public final Block block;
    public final int x;
    public final int y;
    public final int z;

    public EnrichedSoilEvent(EntityPlayer player, World world, Block block, int x, int y, int z) {
        super(player);
        this.world = world;
        this.block = block;
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
