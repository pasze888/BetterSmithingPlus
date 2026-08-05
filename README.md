# Better Smithing Plus

A NeoForge 1.21.1 mod that adds a **Better Smithing Table** (改良锻造台) — smithing that never consumes templates.

[![License](https://img.shields.io/badge/License-LGPL--3.0--or--later-blue.svg)](LICENSE)

## Features

- **No template consumption** — smithing on the Better Smithing Table does not consume the template (base and addition are consumed as usual)
- **All smithing recipes supported** — vanilla and datapack recipes (netherite upgrades, armor trims) work out of the box, using the vanilla smithing table GUI
- **Hopper automation** (since 1.1.0) — the table is a container:
  - Hoppers/pipes can insert template, base, and addition ingredients into the matching slots
  - Hoppers can extract the crafted result (base and addition consumed, template never)
  - Items persist in the block entity, drop when the block is broken, and stay in sync between GUI and automation

## Crafting Recipe

8 iron ingots around a vanilla smithing table:

```
III
ISI
III
```


## License

This project is licensed under the **GNU Lesser General Public License v3.0 or later** (LGPL-3.0-or-later).

- This project contains code and design patterns derived from
  [AnvilCraft](https://github.com/Anvil-Dev/AnvilCraft) (Copyright © 2024 Gugle),
  modified since August 2026 in accordance with the terms of the LGPL-3.0.
- The full corresponding source code is available in this repository:
  <https://github.com/yourname/Better-Smithing-Plus>
- See [LICENSE](LICENSE) for the full license text and [NOTICE](NOTICE) for copyright attributions.
- All textures, models, and sounds in this project are original creations and are
  **not** derived from AnvilCraft, whose assets are All Rights Reserved.
- The template files originally supplied by the
  [NeoForged MDK](https://github.com/NeoForged/MDK) remain under the MIT license —
  see [TEMPLATE_LICENSE.txt](TEMPLATE_LICENSE.txt).

## Installation

- Minecraft 1.21.1
- NeoForge 21.1.219 or later

## Development

```bash
./gradlew.bat compileJava   # build
./gradlew.bat runData       # generate data (blockstates, models, lang, loot, recipes, tags)
./gradlew.bat runClient     # run the game
./gradlew.bat runClient     # run the game
```
