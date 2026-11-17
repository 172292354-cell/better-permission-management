# 更好的权限管理 (Better Permission Management)

一个 Minecraft Java 版 **Forge 1.20.1** 的权限管理模组。

为玩家授予/撤销单一的 OPP 权限，权限数据通过 Forge 的
`getPersistentData()` 随玩家存档持久化保存。

## 部署说明

这是一个**仅服务端**模组：

- **服务端**：必须安装，提供 `/opp` 权限管理命令
- **客户端**：不用安装，权限数据由服务端统一管理

## OPP 权限

模组管理一个固定的 **OPP** 权限：

- 玩家要么**拥有 OPP**，要么**没有**，不存在多级/多节点。
- 权限随玩家存档保存，重启服务器、玩家离线后再上线依然保留。

## 命令

所有命令都在 `/opp` 下：

| 命令 | 权限要求 | 功能 |
|------|---------|------|
| `/opp grant <玩家>` | OP | 授予指定玩家 OPP 权限 |
| `/opp revoke <玩家>` | OP | 撤销指定玩家的 OPP 权限 |
| `/opp check <玩家>` | OP 或本人 | 查看玩家是否拥有 OPP 权限 |
| `/opp list` | OP | 列出所有拥有 OPP 权限的在线玩家 |

## 使用示例

```
# 授予玩家 Steve OPP 权限
/opp grant Steve

# 查看 Steve 是否拥有 OPP 权限
/opp check Steve

# 撤销 Steve 的 OPP 权限
/opp revoke Steve

# 列出所有拥有 OPP 权限的在线玩家
/opp list
```

## 构建

运行 `build.ps1` 自动编译、打包并部署到服务端 mods 目录：

```powershell
powershell -ExecutionPolicy Bypass -File build.ps1
```

生成的 jar 位于 `build/libs/better_permission_management-1.3.jar`。

## 环境

- Minecraft 1.20.1
- Forge 47.4.0
- Java 17