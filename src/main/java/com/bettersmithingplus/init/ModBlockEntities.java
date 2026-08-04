package com.bettersmithingplus.init;

import com.bettersmithingplus.BetterSmithingPlus;
import com.bettersmithingplus.block.entity.BetterSmithingTableBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
        DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, BetterSmithingPlus.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BetterSmithingTableBlockEntity>>
        BETTER_SMITHING_TABLE = BLOCK_ENTITY_TYPES.register(
            "better_smithing_table",
            () -> BlockEntityType.Builder.of(
                BetterSmithingTableBlockEntity::new, ModBlocks.BETTER_SMITHING_TABLE.get()).build(null)
        );

    private ModBlockEntities() {
    }

    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITY_TYPES.register(modEventBus);
    }
}
