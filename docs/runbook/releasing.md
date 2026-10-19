# 发版规范（RELEASING）

本模组采用**分支按版本**方案：一个 MC 版本一个长期分支，tag 与 Release 按分支各自发布。

## 分支与版本对应

| 分支 | MC 版本 / 加载器 | `mod_loader` | 构建产物 |
|---|---|---|---|
| `main` | 1.21.1 / NeoForge | `neoforge` | `bettersmithingplus-1.21.1-neoforge-<mod_version>.jar` |
| `1.20.1` | 1.20.1 / Forge | `forge` | `bettersmithingplus-1.20.1-forge-<mod_version>.jar` |

`build.gradle` 里 `version = mod_version`、`archivesName = "${mod_id}-${minecraft_version}-${mod_loader}"`，
所以 `build/libs` 的主 jar 名与发布 tag 逐字相同，发布流程不再重命名。
`java.toolchain.languageVersion` 读 `gradle.properties` 的 `java_version`，与 CI 安装的 JDK 同源。

## Tag 命名

```
<mod_id>-<mc_version>-<loader>-<mod_version>
```

例如：`bettersmithingplus-1.21.1-neoforge-1.2.0`。

- 触发条件是 `on.push.tags: ['*-*-*-*']`，只有这个形状的 tag 才会触发发布
- 真源是 `gradle.properties`：CI 用 `mod_id` / `minecraft_version` / `mod_loader` /
  `mod_version` 拼出期望名字，与 tag 做**整串相等**对账，不等就报错
- 不对 tag 做切分；MC 预发布版本（`1.21.1-pre1`）自带连字符也不受影响
- 历史 tag（`v1.0.0`~`v1.1.1`、`v<mod_version>+<mc_version>`）保留不改，不再新增

## 发版步骤

```bash
# 1. 把本次改动从 CHANGELOG 的 [Unreleased] 挪进 ## [<mod_version>] 段，
#    再改 gradle.properties 的 mod_version 并提交（tag 必须与它一致，否则 CI 报错）
git add -A && git commit -m "Bump version to <mod_version>"
git push origin main

# 2. 打 tag 并推送 —— 触发 release.yml：CI 构建、发 GitHub Release（+ CurseForge）
git tag bettersmithingplus-1.21.1-neoforge-<mod_version>
git push origin bettersmithingplus-1.21.1-neoforge-<mod_version>

# 3. 看结果
gh run watch
```

发布链路：`release.yml` 读 `gradle.properties` → 与 tag 整串对账 → 从 `CHANGELOG.md`
抽 `## [<mod_version>]` 段（抽不到或为空就失败）→ `./gradlew build` → 确认
`build/libs/<mod_id>-<mc_version>-<loader>-<mod_version>.jar` 存在 → mc-publish 把同一份
说明发到 GitHub Release 与 CurseForge。

- 版本类型只认 `-alpha` / `-beta`，其余一律 release。判错的后果：mc-publish 按
  `prerelease = version_type != release` 填，预发布会被当正式版发出去并抢占 Latest
- `main` 是主线，Latest 本来就指向它；`1.20.1` 发完之后要把 Latest 还回来：
  `gh release edit <main 的 tag> --latest`
- 有 `curseforge_project_id` 时才上传 CurseForge，否则只发 GitHub Release
- 仓库 secret 需要 `CURSEFORGE_TOKEN`（上传 CurseForge 时必需）
- 发布只上传 `build/libs` 里那一个主 jar，dev 产物不会带上
