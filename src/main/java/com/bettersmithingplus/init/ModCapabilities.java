package com.bettersmithingplus.init;

import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public final class ModCapabilities {
    private ModCapabilities() {
    }

    public static void register(RegisterCapabilitiesEvent event) {
        // 注册方块物品处理器能力：漏斗/投掷器/其他模组管道据此自动输入输出
        event.registerBlockEntity(
            Capabilities.ItemHandler.BLOCK,
            ModBlockEntities.BETTER_SMITHING_TABLE.get(),
            (blockEntity, side) -> blockEntity.getAutomationHandler()
        );
    }
}
