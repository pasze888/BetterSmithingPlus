# Better Smithing Plus（更好的锻造）

一个 NeoForge 1.21.1 模组，添加了**改良锻造台**——锻造时不消耗模板。

## 功能特性

- **不消耗模板** —— 在改良锻造台上锻造不会消耗模板（基底与附加物照常消耗）
- **支持全部锻造配方** —— 原版与数据包配方（下界合金升级、盔甲纹饰）开箱即用，使用原版锻造台界面
- **漏斗自动化**（自 1.1.0 起）—— 锻造台是容器：
  - 漏斗/管道可将模板、基底、附加物按槽位自动输入
  - 漏斗可自动提取锻造成品（消耗基底与附加物，模板永不消耗）
  - 物品存储在方块实体中，拆方块时掉落，存档重载后保留，GUI 与自动化之间实时同步

## 合成配方

8 个铁锭环绕原版锻造台：

```
III
ISI
III
```

## 许可证

本项目基于 **GNU Lesser General Public License v3.0**（LGPL-3.0）发布。

其中包含改编自 [AnvilCraft](https://github.com/Anvil-Dev/AnvilCraft)（Copyright © 2024 Gugle）的代码与设计模式，后者同样采用 LGPL-3.0。详见 [LICENSE](LICENSE) 与 [NOTICE](NOTICE)。

由 [NeoForged MDK](https://github.com/NeoForged/MDK) 提供的模板文件仍遵循 MIT 许可证——见 [TEMPLATE_LICENSE.txt](TEMPLATE_LICENSE.txt)。

## 安装要求

- Minecraft 1.21.1
- NeoForge 21.1.219 或更高版本

## 开发

```bash
./gradlew.bat compileJava   # 构建
./gradlew.bat runData       # 生成数据（blockstates、模型、语言、掉落物表、配方、标签）
./gradlew.bat runClient     # 运行游戏
```
