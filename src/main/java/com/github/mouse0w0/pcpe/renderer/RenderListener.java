package com.github.mouse0w0.pcpe.renderer;

import com.github.mouse0w0.pcpe.PCPE;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber(modid = PCPE.MOD_ID)
public class RenderListener {

    @SubscribeEvent
    public static void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
    }
}
