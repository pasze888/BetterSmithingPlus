# KNOWLEDGE

已验证（编译通过 / 构建通过）的 API 事实与踩坑记录。

## 1.20.1 Forge 移植（1.20.1 分支，Forge 47.4.23 + MDG Legacy 2.0.143）

- 1.20.1 **没有 NeoForge**（NeoForge 自 1.20.2 才分叉），目标加载器是 MinecraftForge。
  构建用 `net.neoforged.moddev.legacyforge` 插件（与 moddev 同版本号），`legacyForge { version = "1.20.1-47.4.23" }`，
  Java 17。jar 需 reobf 到 SRG——插件已自动配置 `jar` 任务，发布用 `build/libs` 产物。
- Forge 47 主类必须**无参构造**，事件总线用 `FMLJavaModLoadingContext.get().getModEventBus()`；
  NeoForge 1.21 的 `@Mod(IEventBus)` 构造注入在 Forge 47 不存在（`NoSuchMethodException <init>()`）。
- 注册：`DeferredRegister.create(ForgeRegistries.X, MODID)` + `RegistryObject<T>`（`DeferredHolder`/
  `DeferredBlock`/`DeferredItem` 均为 NeoForge 专属）。
- 菜单类型：`IForgeMenuType.create((id, inv, buf) -> ...)`（NeoForge 是 `IMenuTypeExtension`）。
- 屏幕：Forge 47 无 `RegisterMenuScreensEvent`，在 `FMLClientSetupEvent#enqueueWork` 里
  `MenuScreens.register(...)`；`@Mod.EventBusSubscriber` 需显式 `bus = Bus.MOD`。
- 锻造配方：1.20.1 无 `SmithingRecipeInput`、无 `RecipeHolder`。`SmithingRecipe` 直接匹配 `Container`：
  `getRecipesFor(RecipeType.SMITHING, container, level)` / `matches(container, level)` /
  `assemble(container, registryAccess)`。`ResultContainer.setRecipeUsed(recipe)`、
  `awardUsedRecipes(player, items)` 可用；`ItemStack.onCraftedBy(level, player, count)` 带.Level 参数。
- `Player.canInteractWithBlock(pos, 4.0)` 在 1.20.1 不存在，用 `pos.distToCenterSqr(player.position()) < 64.0`。
- `ContainerHelper.saveAllItems/loadAllItems` 1.20.1 只有 `NonNullList<ItemStack>` 重载；
  `SimpleContainer` 无 `getItems()`、无 NonNullList 构造器，需自行拷贝（见 BlockEntity copyItems/load）。
- `Container.getItems()`（接口默认方法）是 1.20.5+ 才有的。
- datagen：`GatherDataEvent` 在 `net.minecraftforge.data.event`；1.20.1 的
  `LootTableProvider(PackOutput, Set, List<SubProviderEntry>)`、`RecipeProvider(PackOutput)` +
  `buildRecipes(Consumer<FinishedRecipe>)`、`BlockLootSubProvider(Set, FeatureFlagSet)` 均无 registry 参数。
- mods.toml（Forge 格式）：依赖块用 `mandatory=true` 而非 `type="required"`，漏写直接
  `InvalidModFileException: Missing required field mandatory`；依赖 `modId="forge"`。
  文件名 `META-INF/mods.toml`（不是 neoforge.mods.toml）。
- 数据包路径：1.20.1 是复数 `recipes/`、`loot_tables/`、`advancements/`、`tags/blocks/`
  （1.21 起改为单数）。
- 1.20.1 Parchment 映射：`1.20.1 / 2023.09.03`。

## 通用

- mod 版本号带 MC 后缀（`1.1.1+1.20.1`），分支按版本方案：main=1.21.1 NeoForge，`1.20.1`=Forge。
- 1.20.1 移植参照 AnvilCraft 1.4.1-pre-release.1（LGPL-3.0）的 Forge 侧写法，出处已记入 NOTICE。
