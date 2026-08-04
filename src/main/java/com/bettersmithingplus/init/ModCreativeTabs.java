package com.bettersmithingplus.init;

import com.bettersmithingplus.BetterSmithingPlus;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BetterSmithingPlus.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BETTER_SMITHING_PLUS =
        CREATIVE_MODE_TABS.register(
            "bettersmithingplus",
            () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.bettersmithingplus"))
                .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                .icon(() -> ModItems.BETTER_SMITHING_TABLE.get().getDefaultInstance())
                .displayItems((parameters, output) -> output.accept(ModItems.BETTER_SMITHING_TABLE.get()))
                .build()
        );

    private ModCreativeTabs() {
    }

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
