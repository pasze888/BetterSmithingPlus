package com.bettersmithingplus.init;

import com.bettersmithingplus.BetterSmithingPlus;
import com.bettersmithingplus.block.BetterSmithingTableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(BetterSmithingPlus.MODID);

    public static final DeferredBlock<BetterSmithingTableBlock> BETTER_SMITHING_TABLE = BLOCKS.register(
        "better_smithing_table",
        () -> new BetterSmithingTableBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .strength(5.0F, 1200.0F)
            .requiresCorrectToolForDrops())
    );

    private ModBlocks() {
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
