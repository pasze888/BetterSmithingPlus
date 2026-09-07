# Better Smithing Plus

A Minecraft mod that adds a **Better Smithing Table** (改良锻造台) — smithing that never consumes templates.

[![License](https://img.shields.io/badge/License-LGPL--3.0--or--later-blue.svg)](LICENSE)

## Features

- **No template consumption** — smithing on the Better Smithing Table does not consume the template (base and addition are consumed as usual)
- **All smithing recipes supported** — vanilla and datapack recipes (netherite upgrades, armor trims) work out of the box, using the vanilla smithing table GUI
- **In-block storage** — ingredients persist in the block entity and drop when the block is broken

## Supported Versions

Minecraft versions are maintained per branch (see [docs/RELEASING.md](docs/RELEASING.md)):

| Minecraft | Loader | Branch | Since |
|---|---|---|---|
| 1.21.1 | NeoForge 21.1.219+ | `main` | 1.0.0 |
| 1.20.1 | Forge 47.4+ | `1.20.1` | 1.1.1 |

Releases are tagged `v<version>+<mc_version>` on each branch, e.g. [`v1.1.1+1.21.1`](https://github.com/pasze888/BetterSmithingPlus/releases/tag/v1.1.1%2B1.21.1) and [`v1.1.1+1.20.1`](https://github.com/pasze888/BetterSmithingPlus/releases/tag/v1.1.1%2B1.20.1).

## Crafting Recipe

8 iron ingots around a vanilla smithing table:

```
III
ISI
III
```

## License

This project is licensed under the **GNU Lesser General Public License v3.0 or later** (LGPL-3.0-or-later).

- It contains code and design patterns derived from [AnvilCraft](https://github.com/Anvil-Dev/AnvilCraft) (Copyright © 2024 Gugle), also licensed under LGPL-3.0-or-later, modified for this project since August 2026. The 1.20.1 port additionally references AnvilCraft 1.4.1's Forge implementation — see [NOTICE](NOTICE).
- The full corresponding source code is available in this repository: <https://github.com/pasze888/BetterSmithingPlus>
- See [LICENSE](LICENSE) for the full license text and [NOTICE](NOTICE) for copyright attributions.
- All textures, models, and sounds in this project are original creations and are not taken from AnvilCraft, whose assets are All Rights Reserved.
- The template files originally supplied by the [NeoForged MDK](https://github.com/NeoForged/MDK) remain under the MIT license — see [TEMPLATE_LICENSE.txt](TEMPLATE_LICENSE.txt).

## Installation

- **1.21.1**: Minecraft 1.21.1 + NeoForge 21.1.219 or later
- **1.20.1**: Minecraft 1.20.1 + Forge 47.4.0 or later

Download the jar matching your Minecraft version from the [Releases](https://github.com/pasze888/BetterSmithingPlus/releases) page.

## Development

```bash
./gradlew.bat compileJava   # build
./gradlew.bat runData       # generate data (blockstates, models, lang, loot, recipes, tags)
./gradlew.bat runClient     # run the game
```
