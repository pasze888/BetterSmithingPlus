package com.bettersmithingplus.init;

import com.bettersmithingplus.BetterSmithingPlus;
import com.bettersmithingplus.item.BetterSmithingTableItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, BetterSmithingPlus.MODID);

    public static final RegistryObject<BetterSmithingTableItem> BETTER_SMITHING_TABLE = ITEMS.register(
        "better_smithing_table",
        () -> new BetterSmithingTableItem(ModBlocks.BETTER_SMITHING_TABLE.get(), new Item.Properties())
    );

    private ModItems() {
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
