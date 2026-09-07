package com.bettersmithingplus.init;

import com.bettersmithingplus.BetterSmithingPlus;
import com.bettersmithingplus.inventory.ClientBetterSmithingMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public final class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
        DeferredRegister.create(Registries.MENU, BetterSmithingPlus.MODID);

    /**
     * 改良锻造台菜单类型。工厂仅在客户端重建菜单时调用（服务端菜单由方块创建），
     * 因此直接构造客户端专用菜单 {@link ClientBetterSmithingMenu}。
     */
    public static final RegistryObject<MenuType<ClientBetterSmithingMenu>> BETTER_SMITHING =
        MENU_TYPES.register("better_smithing_table",
            () -> IForgeMenuType.create((containerId, inventory, buffer) ->
                new ClientBetterSmithingMenu(containerId, inventory)));

    private ModMenuTypes() {
    }

    public static void register(IEventBus modEventBus) {
        MENU_TYPES.register(modEventBus);
    }
}
