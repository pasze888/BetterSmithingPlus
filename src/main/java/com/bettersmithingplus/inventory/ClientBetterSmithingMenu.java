package com.bettersmithingplus.inventory;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.SmithingMenu;

/**
 * 客户端专用锻造台菜单。
 *
 * <p>继承原版 {@link SmithingMenu} 仅用于客户端重建界面。覆写 {@link #createResult()} 为
 * 空实现——结果槽内容完全由服务端广播驱动，避免客户端逐槽同步输入时本地重算产生的
 * 中间态导致结果槽闪烁。</p>
 */
public class ClientBetterSmithingMenu extends SmithingMenu {
    public ClientBetterSmithingMenu(int containerId, Inventory playerInventory) {
        super(containerId, playerInventory);
    }

    @Override
    public void createResult() {
        // 结果槽由服务端同步，客户端不计算
    }
}
