package com.bettersmithingplus.data;

import com.bettersmithingplus.BetterSmithingPlus;
import com.bettersmithingplus.init.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BetterSmithingPlus.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // 复用原版锻造台纹理（bottom/top/front/side），零新贴图
        ModelFile model = this.models().cube(
            "better_smithing_table",
            this.mcLoc("block/smithing_table_bottom"),
            this.mcLoc("block/smithing_table_top"),
            this.mcLoc("block/smithing_table_front"),
            this.mcLoc("block/smithing_table_side"),
            this.mcLoc("block/smithing_table_side"),
            this.mcLoc("block/smithing_table_side")
        )
            // 破坏粒子与疾跑脚下粒子取模型 particle 贴图，与原版锻造台一致
            .texture("particle", this.mcLoc("block/smithing_table_front"));
        this.simpleBlock(ModBlocks.BETTER_SMITHING_TABLE.get(), model);
    }
}
