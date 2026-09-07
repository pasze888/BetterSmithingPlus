package com.bettersmithingplus.block.entity;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.List;

/**
 * 改良锻造台的自动化（漏斗/管道）物品处理器。
 *
 * <p>槽位语义：0=模板、1=基底、2=附加物（仅可插入），3=结果（仅可提取，按配方实时计算）。
 * 提取结果时消耗基底与附加物，模板永不消耗。</p>
 */
public class BetterSmithingTableAutomationHandler implements IItemHandler {
    private static final int TEMPLATE_SLOT = 0;
    private static final int BASE_SLOT = 1;
    private static final int ADDITION_SLOT = 2;
    private static final int RESULT_SLOT = 3;

    private final BetterSmithingTableBlockEntity blockEntity;

    public BetterSmithingTableAutomationHandler(BetterSmithingTableBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }

    @Override
    public int getSlots() {
        return 4;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot == RESULT_SLOT) return this.blockEntity.computeResult();
        return this.blockEntity.getContainer().getItem(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (slot < TEMPLATE_SLOT || slot > ADDITION_SLOT || stack.isEmpty()) return stack;
        if (!this.isItemValid(slot, stack)) return stack;
        Container container = this.blockEntity.getContainer();
        ItemStack current = container.getItem(slot);
        if (!current.isEmpty() && !ItemStack.isSameItemSameComponents(current, stack)) return stack;
        int space = Math.min(stack.getMaxStackSize(), this.getSlotLimit(slot)) - current.getCount();
        if (space <= 0) return stack;
        int count = Math.min(space, stack.getCount());
        if (!simulate) {
            if (current.isEmpty()) {
                container.setItem(slot, stack.copyWithCount(count));
            } else {
                current.grow(count);
                container.setChanged();
            }
        }
        ItemStack remainder = stack.copy();
        remainder.shrink(count);
        return remainder;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (slot != RESULT_SLOT || amount <= 0) return ItemStack.EMPTY;
        ItemStack result = this.blockEntity.computeResult();
        if (result.isEmpty()) return ItemStack.EMPTY;
        ItemStack extracted = result.copyWithCount(Math.min(amount, result.getCount()));
        if (!simulate) {
            this.shrink(BASE_SLOT);
            this.shrink(ADDITION_SLOT);
        }
        return extracted;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 64;
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        if (slot < TEMPLATE_SLOT || slot > ADDITION_SLOT || stack.isEmpty()) return false;
        if (this.blockEntity.getLevel() == null) return false;
        List<RecipeHolder<SmithingRecipe>> recipes = this.blockEntity.getLevel()
            .getRecipeManager().getAllRecipesFor(RecipeType.SMITHING);
        return switch (slot) {
            case TEMPLATE_SLOT -> recipes.stream().anyMatch(recipe -> recipe.value().isTemplateIngredient(stack));
            case BASE_SLOT -> recipes.stream().anyMatch(recipe -> recipe.value().isBaseIngredient(stack));
            case ADDITION_SLOT -> recipes.stream().anyMatch(recipe -> recipe.value().isAdditionIngredient(stack));
            default -> false;
        };
    }

    private void shrink(int slot) {
        Container container = this.blockEntity.getContainer();
        ItemStack stack = container.getItem(slot);
        if (!stack.isEmpty()) {
            stack.shrink(1);
            container.setChanged();
        }
    }
}
