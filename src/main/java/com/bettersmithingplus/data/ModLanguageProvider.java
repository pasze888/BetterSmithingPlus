package com.bettersmithingplus.data;

import com.bettersmithingplus.BetterSmithingPlus;
import com.bettersmithingplus.init.ModBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    private final String locale;

    public ModLanguageProvider(PackOutput output, String locale) {
        super(output, BetterSmithingPlus.MODID, locale);
        this.locale = locale;
    }

    @Override
    protected void addTranslations() {
        if ("en_us".equals(this.locale)) {
            this.addBlock(ModBlocks.BETTER_SMITHING_TABLE, "Better Smithing Table");
            this.add("container.bettersmithingplus.better_smithing_table", "Better Smithing Table");
            this.add("tooltip.bettersmithingplus.no_template_consume", "Smithing does not consume templates");
            this.add("itemGroup.bettersmithingplus", "Better Smithing Plus");
        } else {
            this.addBlock(ModBlocks.BETTER_SMITHING_TABLE, "改良锻造台");
            this.add("container.bettersmithingplus.better_smithing_table", "改良锻造台");
            this.add("tooltip.bettersmithingplus.no_template_consume", "锻造时不消耗模板");
            this.add("itemGroup.bettersmithingplus", "更好的锻造");
        }
    }
}
