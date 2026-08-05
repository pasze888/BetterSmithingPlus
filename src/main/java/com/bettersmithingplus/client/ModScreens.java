package com.bettersmithingplus.client;

import com.bettersmithingplus.BetterSmithingPlus;
import com.bettersmithingplus.init.ModMenuTypes;
import net.minecraft.client.gui.screens.inventory.SmithingScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

/**
 * 客户端屏幕绑定：改良锻造台复用原版锻造台界面。
 */
@EventBusSubscriber(modid = BetterSmithingPlus.MODID, value = Dist.CLIENT)
public final class ModScreens {
    private ModScreens() {
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.BETTER_SMITHING.get(), SmithingScreen::new);
    }
}
