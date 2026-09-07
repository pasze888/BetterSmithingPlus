package com.bettersmithingplus.init;

import com.bettersmithingplus.BetterSmithingPlus;
import com.bettersmithingplus.block.BetterSmithingTableBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
        DeferredRegister.create(ForgeRegistries.BLOCKS, BetterSmithingPlus.MODID);

    public static final RegistryObject<BetterSmithingTableBlock> BETTER_SMITHING_TABLE = BLOCKS.register(
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
