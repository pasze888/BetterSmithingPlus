package com.bettersmithingplus.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

/**
 * 改良锻造台方块物品，附带"锻造时不消耗模板"的提示文案。
 */
public class BetterSmithingTableItem extends BlockItem {
    public BetterSmithingTableItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(
        ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag
    ) {
        tooltipComponents.add(Component.translatable("tooltip.bettersmithingplus.no_template_consume"));
        tooltipComponents.add(Component.translatable("tooltip.bettersmithingplus.hopper_automation"));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
