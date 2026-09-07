package com.bettersmithingplus;

import com.bettersmithingplus.init.ModBlockEntities;
import com.bettersmithingplus.init.ModBlocks;
import com.bettersmithingplus.init.ModCreativeTabs;
import com.bettersmithingplus.init.ModItems;
import com.bettersmithingplus.init.ModMenuTypes;
import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BetterSmithingPlus.MODID)
public class BetterSmithingPlus {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "bettersmithingplus";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    public BetterSmithingPlus() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
    }
}
