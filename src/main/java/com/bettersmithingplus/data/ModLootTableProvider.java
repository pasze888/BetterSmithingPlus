package com.bettersmithingplus.data;

import com.bettersmithingplus.BetterSmithingPlus;
import com.bettersmithingplus.init.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(
            output,
            Set.of(),
            List.of(new SubProviderEntry(ModBlockLootSubProvider::new, LootContextParamSets.BLOCK)),
            registries
        );
    }

    private static class ModBlockLootSubProvider extends BlockLootSubProvider {
        protected ModBlockLootSubProvider(HolderLookup.Provider registries) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            // 只校验本模组的方块，避免因原版掉落表 provider 不参与模组 datagen 而报错
            return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
        }

        @Override
        protected void generate() {
            this.dropSelf(ModBlocks.BETTER_SMITHING_TABLE.get());
        }
    }
}
