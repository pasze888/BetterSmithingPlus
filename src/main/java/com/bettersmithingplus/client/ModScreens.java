package com.bettersmithingplus.client;

import com.bettersmithingplus.BetterSmithingPlus;
import com.bettersmithingplus.init.ModMenuTypes;
import net.minecraft.client.gui.screens.inventory.SmithingScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * 客户端屏幕绑定：改良锻造台复用原版锻造台界面。
 */
@Mod.EventBusSubscriber(modid = BetterSmithingPlus.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModScreens {
    private ModScreens() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() ->
            MenuScreens.register(ModMenuTypes.BETTER_SMITHING.get(), SmithingScreen::new));
    }
}
