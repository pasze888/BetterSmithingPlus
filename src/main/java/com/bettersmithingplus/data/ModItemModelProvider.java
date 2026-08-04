package com.bettersmithingplus.data;

import com.bettersmithingplus.BetterSmithingPlus;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BetterSmithingPlus.MODID, exFileHelper);
    }

    @Override
    protected void registerModels() {
        this.withExistingParent("better_smithing_table", this.modLoc("block/better_smithing_table"));
    }
}
