# Better Smithing Plus

A NeoForge 1.21.1 mod that adds a **Better Smithing Table** (改良锻造台) — smithing that never consumes templates.

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

This project is licensed under the **GNU Lesser General Public License v3.0** (LGPL-3.0).

It contains code and design patterns derived from [AnvilCraft](https://github.com/Anvil-Dev/AnvilCraft) (Copyright © 2024 Gugle), also licensed under LGPL-3.0. See [LICENSE](LICENSE) and [NOTICE](NOTICE) for details.

The template files originally supplied by the [NeoForged MDK](https://github.com/NeoForged/MDK) remain under the MIT license — see [TEMPLATE_LICENSE.txt](TEMPLATE_LICENSE.txt).

## Installation

- Minecraft 1.21.1
- NeoForge 21.1.244 or later

## Development

```bash
./gradlew.bat compileJava   # build
./gradlew.bat runData       # generate data (blockstates, models, lang, loot, recipes, tags)
./gradlew.bat runClient     # run the game
```
