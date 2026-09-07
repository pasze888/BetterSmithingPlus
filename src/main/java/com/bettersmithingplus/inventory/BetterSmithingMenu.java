package com.bettersmithingplus.inventory;

import com.bettersmithingplus.block.entity.BetterSmithingTableBlockEntity;
import com.bettersmithingplus.init.ModBlocks;
import com.bettersmithingplus.init.ModMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 改良锻造台菜单（服务端）。
 *
 * <p>输入槽（0-2）绑定方块实体容器，结果槽（3）使用 {@link ResultContainer} 以保留
 * 配方解锁进度。菜单类型为 {@link ModMenuTypes#BETTER_SMITHING}，客户端据此重建
 * {@link ClientBetterSmithingMenu} 并复用原版锻造台界面。
 * 取走成品时只消耗基底（槽 1）与附加物（槽 2），不消耗模板（槽 0）。</p>
 */
public class BetterSmithingMenu extends AbstractContainerMenu implements ContainerListener {
    private static final int TEMPLATE_SLOT = 0;
    private static final int BASE_SLOT = 1;
    private static final int ADDITION_SLOT = 2;
    private static final int RESULT_SLOT = 3;
    private static final int PLAYER_INVENTORY_START = 4;
    private static final int PLAYER_INVENTORY_END = 31;
    private static final int PLAYER_HOTBAR_START = 31;
    private static final int PLAYER_HOTBAR_END = 40;

    private final ContainerLevelAccess access;
    private final Level level;
    private final SimpleContainer container;
    private final ResultContainer resultSlots = new ResultContainer();
    private final List<SmithingRecipe> recipes;

    @Nullable
    private SmithingRecipe selectedRecipe;

    public BetterSmithingMenu(
        int containerId, Inventory playerInventory, ContainerLevelAccess access, BetterSmithingTableBlockEntity blockEntity
    ) {
        super(ModMenuTypes.BETTER_SMITHING.get(), containerId);
        this.access = access;
        this.level = playerInventory.player.level();
        this.container = blockEntity.getContainer();
        this.recipes = this.level.getRecipeManager().getAllRecipesFor(RecipeType.SMITHING);

        this.addSlot(new Slot(this.container, TEMPLATE_SLOT, 8, 48) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return BetterSmithingMenu.this.recipes.stream()
                    .anyMatch(recipe -> recipe.isTemplateIngredient(stack));
            }
        });
        this.addSlot(new Slot(this.container, BASE_SLOT, 26, 48) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return BetterSmithingMenu.this.recipes.stream()
                    .anyMatch(recipe -> recipe.isBaseIngredient(stack));
            }
        });
        this.addSlot(new Slot(this.container, ADDITION_SLOT, 44, 48) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return BetterSmithingMenu.this.recipes.stream()
                    .anyMatch(recipe -> recipe.isAdditionIngredient(stack));
            }
        });
        this.addSlot(new Slot(this.resultSlots, RESULT_SLOT, 98, 48) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public boolean mayPickup(Player player) {
                return BetterSmithingMenu.this.mayPickup(player, this.hasItem());
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                BetterSmithingMenu.this.onTake(player, stack);
            }
        });

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int k = 0; k < 9; k++) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }

        this.container.addListener(this);
        this.createResult();
    }

    @Override
    public boolean stillValid(Player player) {
        return this.access.evaluate(
            (level, pos) -> level.getBlockState(pos).is(ModBlocks.BETTER_SMITHING_TABLE.get())
                && pos.distToCenterSqr(player.position()) < 64.0,
            true
        );
    }

    @Override
    public void containerChanged(Container container) {
        if (container == this.container) {
            this.createResult();
        }
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.container.removeListener(this);
    }

    @Override
    public void broadcastChanges() {
        // 先重算再广播，漏斗等自动输入导致的容器变化当 tick 反映到结果槽
        if (!this.level.isClientSide) {
            this.createResult();
        }
        super.broadcastChanges();
    }

    private void createResult() {
        List<SmithingRecipe> list =
            this.level.getRecipeManager().getRecipesFor(RecipeType.SMITHING, this.container, this.level);
        if (list.isEmpty()) {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
            return;
        }
        SmithingRecipe recipe = list.get(0);
        ItemStack stack = recipe.assemble(this.container, this.level.registryAccess());
        if (stack.isItemEnabled(this.level.enabledFeatures())) {
            this.selectedRecipe = recipe;
            this.resultSlots.setRecipeUsed(recipe);
            this.resultSlots.setItem(0, stack);
        } else {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
        }
    }

    private boolean mayPickup(Player player, boolean hasStack) {
        return this.selectedRecipe != null
            && this.selectedRecipe.matches(this.container, this.level);
    }

    private void onTake(Player player, ItemStack stack) {
        stack.onCraftedBy(player.level(), player, stack.getCount());
        this.resultSlots.awardUsedRecipes(player, this.getRelevantItems());
        this.shrinkStackInSlot(BASE_SLOT);
        this.shrinkStackInSlot(ADDITION_SLOT);
        // 模板槽（0）不消耗
        this.access.execute((level, blockPos) -> level.levelEvent(1044, blockPos, 0));
    }

    private List<ItemStack> getRelevantItems() {
        return List.of(
            this.container.getItem(0), this.container.getItem(1), this.container.getItem(2));
    }

    private void shrinkStackInSlot(int index) {
        ItemStack itemStack = this.container.getItem(index);
        if (!itemStack.isEmpty()) {
            itemStack.shrink(1);
            this.container.setItem(index, itemStack);
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index == RESULT_SLOT) {
                if (!this.moveItemStackTo(itemstack1, PLAYER_INVENTORY_START, PLAYER_HOTBAR_END, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index != TEMPLATE_SLOT && index != BASE_SLOT && index != ADDITION_SLOT) {
                if (this.canMoveIntoInputSlots(itemstack1)) {
                    if (!this.moveItemStackTo(itemstack1, TEMPLATE_SLOT, RESULT_SLOT, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= PLAYER_INVENTORY_START && index < PLAYER_INVENTORY_END) {
                    if (!this.moveItemStackTo(itemstack1, PLAYER_HOTBAR_START, PLAYER_HOTBAR_END, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= PLAYER_HOTBAR_START && index < PLAYER_HOTBAR_END) {
                    if (!this.moveItemStackTo(itemstack1, PLAYER_INVENTORY_START, PLAYER_INVENTORY_END, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemstack1, PLAYER_INVENTORY_START, PLAYER_HOTBAR_END, false)) {
                return ItemStack.EMPTY;
            }
            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, itemstack1);
        }
        return itemstack;
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.resultSlots && super.canTakeItemForPickAll(stack, slot);
    }

    private boolean canMoveIntoInputSlots(ItemStack stack) {
        return this.recipes.stream().anyMatch(recipe -> recipe.isTemplateIngredient(stack)
            || recipe.isBaseIngredient(stack)
            || recipe.isAdditionIngredient(stack));
    }
}
