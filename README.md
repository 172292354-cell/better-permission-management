# Better Permission Management

一个 Minecraft Java 版 **Forge 1.20.1** 的权限管理模组。

基于权限节点（permission node）的轻量权限系统，权限数据通过 Forge 的
`getPersistentData()` 随玩家存档持久化保存。

## 部署说明

这是一个**仅服务端**模组：

- **服务端**：必须安装，提供 `/opp` 命令与权限逻辑
- **客户端**：不用安装，命令仍可直接使用，也不会被"mod 不一致"拦截

## 权限节点

权限节点是任意命名的字符串（如 `opp`、`gamemode.creative`、`foo.bar`）。

- **默认节点 `opp`**：拥有后可执行 `/opp 1` 与 `/opp 0` 切换创造/生存模式。
- **兼容老数据**：旧版本存储的大写 `OPP` 会自动被识别为 `opp`，老玩家权限不丢失。

## 命令

所有命令都在 `/opp` 下。

### 新版子命令（推荐）

| 命令 | 权限要求 | 功能 |
|------|---------|------|
| `/opp grant <玩家> <节点>` | OP | 授予玩家指定权限节点 |
| `/opp revoke <玩家> <节点>` | OP | 撤销玩家的权限节点 |
| `/opp check <玩家>` | OP 或本人 | 查看玩家的权限列表 |
| `/opp list` | OP | 列出所有在线玩家及其权限 |

### 兼容旧版命令

用于兼容本模组最初设计的三条命令，用法完全不变：

| 命令 | 权限要求 | 功能 |
|------|---------|------|
| `/opp 1` | `opp` 权限 | 将执行者切换为创造模式 |
| `/opp 0` | `opp` 权限 | 将执行者切换为生存模式 |
| `/opp <玩家名>` | OP | 授予该玩家默认 `opp` 权限（等价于 `/opp grant 玩家名 opp`） |

> 说明："兼容旧版"指保留早期规划的这些直接参数写法，以及兼容旧档中的大写 `OPP` 权限，
> 让老玩家和旧数据无需任何调整即可继续使用。

## 使用示例

```
# 作为管理员，授予玩家 Steve 切换模式的权限
/opp grant Steve opp

# Steve 切换为创造模式
/opp 1

# Steve 切换回生存模式
/opp 0

# 查看 Steve 的权限
/opp check Steve

# 撤销 Steve 的模式切换权限
/opp revoke Steve opp

# 列出所有在线玩家的权限
/opp list
```

## 构建

运行 `build.ps1` 自动编译、打包并部署到服务端 mods 目录：

```powershell
powershell -ExecutionPolicy Bypass -File build.ps1
```

生成的 jar 位于 `build/libs/better_permission_management-1.0.jar`。

## 环境

- Minecraft 1.20.1
- Forge 47.4.0
- Java 17