# 发版规范（RELEASING）

本模组采用**分支按版本**方案（参照 AE2 / AnvilCraft）：
一个 MC 版本一个长期分支，tag 与 Release 按 MC 版本各自发布。

## 分支与版本对应

| 分支 | MC 版本 / 加载器 | jar 版本号 |
|---|---|---|
| `main` | 1.21.1 / NeoForge | `<mod_version>`（如 `1.1.1`） |
| `1.20.1` | 1.20.1 / Forge | `<mod_version>+1.20.1` |

新 MC 版本 → 从 `main` 开分支（如 `1.21.5`、`26.1`），`gradle.properties`
里改 `minecraft_version` 与加载器版本，jar 版本号统一加 MC 后缀。

## Tag 命名

```
v<mod_version>+<mc_version>
```

例如：`v1.1.1+1.21.1`、`v1.1.1+1.20.1`。

- 同一 mod 版本的多个 MC 移植是**多个 tag**，靠 MC 后缀区分，永远唯一
- tag 打在**对应分支**的最新提交上（annotated tag，附一句话说明）
- 2026-08 之前的历史 tag（`v1.0.0`~`v1.1.1`）无后缀，保留不改

## 发版步骤

```bash
# 1. 确认对应分支构建通过（jar 产物在 build/libs/）
./gradlew build

# 2. 推分支与 tag
git checkout <分支> && git push origin <分支>
git tag -a v<mod_version>+<mc_version> -m "一句话说明"
git push origin v<mod_version>+<mc_version>

# 3. 发 Release，挂上对应分支构建的 jar
gh release create v<mod_version>+<mc_version> build/libs/bettersmithingplus-<mod_version>+<mc_version>.jar \
  --verify-tag \
  --title "Better Smithing Plus <mod_version> for <mc_version>" \
  --notes "对应 Minecraft <mc_version>（加载器与版本）。…改动摘要…"
```

- `Latest` 标记始终指向主线（`main` 对应的 Release）：
  `gh release edit v<...> --latest`
- 1.20.1（Forge）分支的 jar 经 MDG Legacy 自动 reobf 到 SRG，`build/libs`
  产物可直接发布；dev 环境产物（`build/devlibs`）不要发
- 进阶：后续可加 GitHub Actions 按 tag 自动构建发布，届时本文件相应更新
