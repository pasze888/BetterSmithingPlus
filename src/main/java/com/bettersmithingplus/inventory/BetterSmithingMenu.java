package com.bettersmithingplus.inventory;

import com.bettersmithingplus.init.ModBlocks;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * 改良锻造台菜单。
 *
 * <p>与原版 {@link SmithingMenu} 的唯一差异：{@link #onTake} 只消耗基底（槽 1）与附加物（槽 2），
 * 不消耗模板（槽 0）。配方匹配、结果合成等逻辑全部沿用原版。</p>
 */
public class BetterSmithingMenu extends SmithingMenu {
    public BetterSmithingMenu(int containerId, Inventory playerInventory) {
        super(containerId, playerInventory);
    }

    public BetterSmithingMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(containerId, playerInventory, access);
    }

    @Override
    protected boolean isValidBlock(BlockState state) {
        return state.is(ModBlocks.BETTER_SMITHING_TABLE.get());
    }

    @Override
    protected void onTake(Player player, ItemStack stack) {
        stack.onCraftedBy(player.level(), player, stack.getCount());
        this.resultSlots.awardUsedRecipes(player, this.getRelevantItems());
        this.shrinkStackInSlot(1);
        this.shrinkStackInSlot(2);
        // 模板槽（0）不消耗
        this.access.execute((level, blockPos) -> level.levelEvent(1044, blockPos, 0));
    }

    private List<ItemStack> getRelevantItems() {
        return List.of(this.inputSlots.getItem(0), this.inputSlots.getItem(1), this.inputSlots.getItem(2));
    }

    private void shrinkStackInSlot(int index) {
        ItemStack itemStack = this.inputSlots.getItem(index);
        if (!itemStack.isEmpty()) {
            itemStack.shrink(1);
            this.inputSlots.setItem(index, itemStack);
        }
    }
}
