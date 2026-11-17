# 更好的权限管理 (Better Permission Management)

一个 Minecraft Java 版 **Forge 1.20.1** 的权限管理模组。

提供 OPP 权限管理，以及基于 OPP/OP 权限的模式切换功能。权限数据通过
Forge 的 `getPersistentData()` 随玩家存档持久化保存。

## 部署说明

这是一个**仅服务端**模组：

- **服务端**：必须安装，提供 `/opp` 权限管理命令
- **客户端**：不用安装，权限数据由服务端统一管理

## OPP 权限

模组管理一个固定的 **OPP** 权限：

- 玩家要么**拥有 OPP**，要么**没有**。
- 权限随玩家存档保存，重启服务器、玩家离线后再上线依然保留。
- 拥有 OPP 或 OP 的玩家可以切换自己的游戏模式。

## 命令

所有命令都在 `/opp` 下：

| 命令 | 权限要求 | 功能 |
|------|---------|------|
| `/opp grant <玩家>` | OP | 授予指定玩家 OPP 权限 |
| `/opp revoke <玩家>` | OP | 撤销指定玩家的 OPP 权限 |
| `/opp check <玩家>` | OP 或本人 | 查看玩家是否拥有 OPP 权限 |
| `/opp list` | OP | 列出所有拥有 OPP 权限的在线玩家 |
| `/opp survival [玩家]` | OPP 或 OP（切自己）；指定玩家需 OP | 切换为生存模式 |
| `/opp creative [玩家]` | OPP 或 OP（切自己）；指定玩家需 OP | 切换为创造模式 |

### 模式切换说明

- **不写玩家**：切换**自己**的模式。要求执行者拥有 **OPP** 权限 **或** OP 权限。
- **写了玩家**：切换**指定玩家**的模式。要求执行者必须是 **OP**。

## 使用示例

```
# 授予玩家 Steve OPP 权限
/opp grant Steve

# Steve 查看自己的权限
/opp check Steve

# Steve 切换为自己的创造模式（Steve 拥有 OPP 或为 OP）
/opp creative

# 管理员把 Steve 切为生存模式（执行者必须是 OP）
/opp survival Steve

# 撤销 Steve 的 OPP 权限
/opp revoke Steve

# 列出拥有 OPP 权限的在线玩家
/opp list
```

## 构建

运行 `build.ps1` 自动编译、打包并部署到服务端 mods 目录：

```powershell
powershell -ExecutionPolicy Bypass -File build.ps1
```

生成的 jar 位于 `build/libs/better_permission_management-1.4.jar`。

## 环境

- Minecraft 1.20.1
- Forge 47.4.0
- Java 17

## 许可证

本项目采用 **CC BY-NC-SA 4.0**（署名-非商业性使用-相同方式共享 4.0 国际）许可证发布。

**你可以：**
- 复制、修改、分发本项目的代码

**前提条件：**
- **不得商用**
- 必须保留署名与版权声明
- 你的修改/衍生作品也**必须使用相同许可证**（保持开源）

详细条款见 [LICENSE](LICENSE) 文件，官方文本见
[creativecommons.org/licenses/by-nc-sa/4.0](https://creativecommons.org/licenses/by-nc-sa/4.0/)。