package com.bettersmithingplus.block;

import com.bettersmithingplus.inventory.BetterSmithingMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SmithingTableBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 改良锻造台方块。
 *
 * <p>继承原版 {@link SmithingTableBlock} 沿用其右键交互与统计逻辑，仅替换菜单为
 * {@link BetterSmithingMenu}（锻造时不消耗模板）。</p>
 */
public class BetterSmithingTableBlock extends SmithingTableBlock {
    private static final Component CONTAINER_TITLE =
        Component.translatable("container.bettersmithingplus.better_smithing_table");

    public BetterSmithingTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider(
            (id, inventory, player) -> new BetterSmithingMenu(id, inventory, ContainerLevelAccess.create(level, pos)),
            CONTAINER_TITLE
        );
    }
}
