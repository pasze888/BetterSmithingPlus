package com.bettersmithingplus.block.entity;

import com.bettersmithingplus.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import java.util.List;

/**
 * 改良锻造台方块实体。
 *
 * <p>持有模板/基底/附加物三个输入槽的容器。锻造结果不在此存储，由
 * {@link #computeResult()} 按配方实时计算——GUI 结果槽与漏斗提取共用同一份输入。</p>
 */
public class BetterSmithingTableBlockEntity extends BlockEntity {
    private final SimpleContainer container = new SimpleContainer(3) {
        @Override
        public void setChanged() {
            super.setChanged();
            BetterSmithingTableBlockEntity.this.setChanged();
        }
    };
    private final IItemHandler automationHandler = new BetterSmithingTableAutomationHandler(this);

    public BetterSmithingTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BETTER_SMITHING_TABLE.get(), pos, state);
    }

    public SimpleContainer getContainer() {
        return this.container;
    }

    public IItemHandler getAutomationHandler() {
        return this.automationHandler;
    }

    /** 用当前输入实时计算锻造结果；配方不匹配或材料不足时返回空。 */
    public ItemStack computeResult() {
        if (this.level == null) return ItemStack.EMPTY;
        SmithingRecipeInput input = new SmithingRecipeInput(
            this.container.getItem(0), this.container.getItem(1), this.container.getItem(2));
        List<RecipeHolder<SmithingRecipe>> list =
            this.level.getRecipeManager().getRecipesFor(RecipeType.SMITHING, input, this.level);
        if (list.isEmpty()) return ItemStack.EMPTY;
        RecipeHolder<SmithingRecipe> recipe = list.getFirst();
        ItemStack stack = recipe.value().assemble(input, this.level.registryAccess());
        return stack.isItemEnabled(this.level.enabledFeatures()) ? stack : ItemStack.EMPTY;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, this.container.getItems(), registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        ContainerHelper.loadAllItems(tag, this.container.getItems(), registries);
    }
}
