# 更好的权限管理 (Better Permission Management)

一个 Minecraft Java 版 **Forge 1.20.1** 的权限管理模组。

基于权限节点（permission node）的轻量权限系统，权限数据通过 Forge 的
`getPersistentData()` 随玩家存档持久化保存。

## 部署说明

这是一个**仅服务端**模组：

- **服务端**：必须安装，提供 `/opp` 权限管理命令
- **客户端**：不用安装，权限数据由服务端统一管理

## 权限节点

权限节点是任意命名的字符串（如 `opp`、`gamemode.creative`、`foo.bar`）。

- 所有权限通过 `/opp` 命令统一授予、撤销与查询。
- 权限节点由管理员自定义，name 由第三方 mod / 服务器逻辑自行约定。

## 命令

所有命令都在 `/opp` 下，均为子命令形式：

| 命令 | 权限要求 | 功能 |
|------|---------|------|
| `/opp grant <玩家> <节点>` | OP | 授予玩家指定权限节点 |
| `/opp revoke <玩家> <节点>` | OP | 撤销玩家的权限节点 |
| `/opp check <玩家>` | OP 或本人 | 查看玩家的权限列表 |
| `/opp list` | OP | 列出所有在线玩家及其权限 |

## 使用示例

```
# 授予玩家 Steve 权限节点 opp
/opp grant Steve opp

# 授予玩家 Steve 权限节点 gamemode.creative
/opp grant Steve gamemode.creative

# 查看 Steve 的权限
/opp check Steve

# 撤销 Steve 的 gamemode.creative 权限
/opp revoke Steve gamemode.creative

# 列出所有在线玩家的权限
/opp list
```

## 构建

运行 `build.ps1` 自动编译、打包并部署到服务端 mods 目录：

```powershell
powershell -ExecutionPolicy Bypass -File build.ps1
```

生成的 jar 位于 `build/libs/better_permission_management-1.2.jar`。

## 环境

- Minecraft 1.20.1
- Forge 47.4.0
- Java 17