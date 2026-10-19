# 发版规范（RELEASING）

本模组采用**分支按版本**方案（参照 AE2 / AnvilCraft）：
一个 MC 版本一个长期分支，tag 与 Release 按 MC 版本各自发布。

## 分支与版本对应

| 分支 | MC 版本 / 加载器 | `mod_loader` | 本地构建产物 |
|---|---|---|---|
| `main` | 1.21.1 / NeoForge | `neoforge` | `bettersmithingplus-<mod_version>+1.21.1.jar` |
| `1.20.1` | 1.20.1 / Forge | `forge` | `bettersmithingplus-<mod_version>+1.20.1.jar` |

`build.gradle` 的 `version = "${mod_version}+${minecraft_version}"`，本地与 CI 的
构建产物都带 MC 后缀。**发布出去的 jar 文件名改由 tag 决定**（见下），
两者不同名是有意的：构建系统决定产物叫什么，发布流程决定发布叫什么。

Java 版本由 `gradle.properties` 的 `java_version` 唯一指定，`build.gradle` 的
toolchain 与 CI 安装的 JDK 都读它，不会各说各话。

## Tag 命名

```
<mod_id>-<mc_version>-<loader>-<mod_version>
```

例如：`bettersmithingplus-1.20.1-forge-1.2.0`、
`bettersmithingplus-1.21.1-neoforge-1.2.0`。

- 与 mc-mod-ci / netsourcecasting 等仓统一；`release.yml` 的触发条件是
  `on.push.tags: ['*-*-*-*']`，只有这个形状的 tag 才会触发发布
- 三段元信息必须与 `gradle.properties` 的 `mod_id` / `minecraft_version` /
  `mod_version` 逐字一致，否则发布工作流直接报错——不会静默发出错版本
- 切分以 `-<loader>-` 为锚点左右切，不按固定段号：MC 预发布版本
  （`1.21.1-pre1`、`26.1-snapshot2`）自带连字符，段数会变
- **历史 tag 保留不改**：`v1.0.0`~`v1.1.1`（无后缀）与
  `v<mod_version>+<mc_version>`（如 `v1.2.0+1.21.1`）是手动发版时期的产物，
  不再新增

## 发版步骤

构建与发布现在都由 CI 完成，本地不需要跑构建：

```bash
# 1. 改 gradle.properties 的 mod_version 并提交（tag 必须与它一致，否则 CI 报错）
git add -A && git commit -m "Bump version to <mod_version>"
git push origin <分支>

# 2. 打 tag 并推送 —— 触发 release.yml：CI 构建、重命名、发 GitHub Release（+ CurseForge）
git tag <mod_id>-<mc_version>-<loader>-<mod_version>
git push origin <mod_id>-<mc_version>-<loader>-<mod_version>

# 3. 看结果
gh run watch
```

发布链路：`release.yml` 读 `gradle.properties` 的元信息 → 校验 tag 三段与
properties 一致 → `./gradlew build -Pmod_version=<tag 版本>` → 把主 jar 重命名为
`<mod_id>-<mc_version>-<loader>-<mod_version>.jar` → mc-publish 上传。

- 版本类型只认 `-alpha` / `-beta`，其余一律 release。判错的后果：mc-publish 按
  `prerelease = version_type != release` 填，预发布会被当正式版发出去并抢占 Latest
- 有 `curseforge_project_id` 时才上传 CurseForge，否则只发 GitHub Release
- 仓库 secret 需要 `CURSEFORGE_TOKEN`（上传 CurseForge 时必需）
- 1.20.1（Forge）分支的 jar 经 MDG Legacy 自动 reobf 到 SRG，`build/libs`
  产物可直接发布；dev 产物（`*-dev.jar`）由工作流排除
