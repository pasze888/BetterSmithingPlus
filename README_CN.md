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

本项目基于 **GNU Lesser General Public License v3.0 或更高版本**（LGPL-3.0-or-later）发布。

- 其中包含改编自 [AnvilCraft](https://github.com/Anvil-Dev/AnvilCraft)（Copyright © 2024 Gugle，同样采用 LGPL-3.0-or-later）的代码与设计模式，本项目自 2026 年 8 月起进行了修改。
- 本项目完整对应源代码可在本仓库获取：<https://github.com/pasze888/BetterSmithingPlus>
- 完整许可证文本见 [LICENSE](LICENSE)，版权归属见 [NOTICE](NOTICE)。
- 本项目所有纹理、模型与音效均为原创，未使用 AnvilCraft 的资产（其资产保留所有权利）。
- 由 [NeoForged MDK](https://github.com/NeoForged/MDK) 提供的模板文件仍遵循 MIT 许可证——见 [TEMPLATE_LICENSE.txt](TEMPLATE_LICENSE.txt)。

## 安装要求

- Minecraft 1.21.1
- NeoForge 21.1.219 或更高版本

## 开发

```bash
./gradlew.bat compileJava   # 构建
./gradlew.bat runData       # 生成数据（blockstates、模型、语言、掉落物表、配方、标签）
./gradlew.bat runClient     # 运行游戏
```
