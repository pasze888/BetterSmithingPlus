package com.bettersmithingplus.block.entity;

import com.bettersmithingplus.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import java.util.List;

/**
 * 改良锻造台方块实体。
 *
 * <p>持有模板/基底/附加物三个输入槽的容器。锻造结果不在此存储，由
 * {@link #computeResult()} 按配方实时计算。</p>
 */
public class BetterSmithingTableBlockEntity extends BlockEntity {
    private final SimpleContainer container = new SimpleContainer(3) {
        @Override
        public void setChanged() {
            super.setChanged();
            BetterSmithingTableBlockEntity.this.setChanged();
        }
    };
    public BetterSmithingTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BETTER_SMITHING_TABLE.get(), pos, state);
    }

    public SimpleContainer getContainer() {
        return this.container;
    }

    /** 用当前输入实时计算锻造结果；配方不匹配或材料不足时返回空。 */
    public ItemStack computeResult() {
        if (this.level == null) return ItemStack.EMPTY;
        List<SmithingRecipe> list =
            this.level.getRecipeManager().getRecipesFor(RecipeType.SMITHING, this.container, this.level);
        if (list.isEmpty()) return ItemStack.EMPTY;
        SmithingRecipe recipe = list.get(0);
        ItemStack stack = recipe.assemble(this.container, this.level.registryAccess());
        return stack.isItemEnabled(this.level.enabledFeatures()) ? stack : ItemStack.EMPTY;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.copyItems());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        NonNullList<ItemStack> list = NonNullList.withSize(this.container.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, list);
        for (int i = 0; i < list.size(); i++) {
            this.container.setItem(i, list.get(i));
        }
    }

    private NonNullList<ItemStack> copyItems() {
        NonNullList<ItemStack> list = NonNullList.withSize(this.container.getContainerSize(), ItemStack.EMPTY);
        for (int i = 0; i < list.size(); i++) {
            list.set(i, this.container.getItem(i));
        }
        return list;
    }
}
