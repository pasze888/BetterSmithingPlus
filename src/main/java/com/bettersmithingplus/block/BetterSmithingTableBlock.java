package com.bettersmithingplus.block;

import com.bettersmithingplus.block.entity.BetterSmithingTableBlockEntity;
import com.bettersmithingplus.inventory.BetterSmithingMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SmithingTableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

/**
 * 改良锻造台方块。
 *
 * <p>继承原版 {@link SmithingTableBlock} 沿用其右键交互与统计逻辑；作为 {@link EntityBlock}
 * 持有容器方块实体，GUI 内直接锻造。</p>
 */
public class BetterSmithingTableBlock extends SmithingTableBlock implements EntityBlock {
    private static final Component CONTAINER_TITLE =
        Component.translatable("container.bettersmithingplus.better_smithing_table");

    public BetterSmithingTableBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BetterSmithingTableBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof BetterSmithingTableBlockEntity blockEntity) {
            return new SimpleMenuProvider(
                (id, inventory, player) -> new BetterSmithingMenu(
                    id, inventory, ContainerLevelAccess.create(level, pos), blockEntity),
                CONTAINER_TITLE
            );
        }
        return null;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            if (level.getBlockEntity(pos) instanceof BetterSmithingTableBlockEntity blockEntity) {
                Containers.dropContents(level, pos, blockEntity.getContainer());
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }
}
