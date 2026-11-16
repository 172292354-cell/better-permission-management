# Better Permission Management

一个 Minecraft Java 版 Forge 1.20.1 的权限管理模组。

## 功能

基于权限节点（permission node）的轻量权限系统，权限数据通过 Forge 的
`getPersistentData()` 随玩家存档持久化保存。

## 命令

全部在 `/opp` 下：

| 命令 | 权限要求 | 功能 |
|------|---------|------|
| `/opp grant <玩家> <节点>` | OP | 授予玩家指定权限节点 |
| `/opp revoke <玩家> <节点>` | OP | 撤销玩家权限节点 |
| `/opp check <玩家>` | OP 或本人 | 查看玩家的权限列表 |
| `/opp list` | OP | 列出所有在线玩家的权限 |
| `/opp 1` | `opp` 权限 | 将执行者切换为创造模式 |
| `/opp 0` | `opp` 权限 | 将执行者切换为生存模式 |
| `/opp <玩家名>` | OP | 授予默认 `opp` 权限（兼容旧版） |

## 构建

运行 `build.ps1` 自动编译、打包并部署到客户端 mods 目录：

```powershell
powershell -ExecutionPolicy Bypass -File build.ps1
```

生成的 jar 位于 `build/libs/better_permission_management-1.0.jar`。

## 环境

- Minecraft 1.20.1
- Forge 47.4.0
- Java 17