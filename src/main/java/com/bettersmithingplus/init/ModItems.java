package com.bettersmithingplus.init;

import com.bettersmithingplus.BetterSmithingPlus;
import com.bettersmithingplus.item.BetterSmithingTableItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BetterSmithingPlus.MODID);

    public static final DeferredItem<BetterSmithingTableItem> BETTER_SMITHING_TABLE = ITEMS.register(
        "better_smithing_table",
        () -> new BetterSmithingTableItem(ModBlocks.BETTER_SMITHING_TABLE.get(), new Item.Properties())
    );

    private ModItems() {
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
