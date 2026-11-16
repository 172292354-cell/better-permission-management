package com.betterpermission.mod;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.extensions.IForgeEntity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 权限工具类。
 *
 * 权限以节点字符串形式存储，使用 Forge 的 getPersistentData()（随玩家存档持久化）。
 * 存储格式：CompoundTag 中键 BPM_Permissions -> "node1,node2,node3"
 *
 * 命名说明：
 * - getPersistentData() 是 Forge 接口方法，运行时不被混淆，使用正规名。
 * - CompoundTag 的 getString/putString 是 Minecraft 方法，运行时以 SRG 名称存在，
 *   因此这里使用 SRG 名称 m_128461_ / m_128359_。
 */
public class PermissionUtils {
    private static final String TAG_PERMISSIONS = "BPM_Permissions";

    /** 根权限：拥有后可执行 /opp 1 与 /opp 0 切换模式 */
    public static final String PERMISSION_OPP = "opp";
    /** 根权限的兼容别名（旧版授予的 OPP） */
    public static final String PERMISSION_OPP_LEGACY = "OPP";

    /**
     * 校验玩家是否拥有指定权限节点。
     * 兼容历史数据中的 OPP 根权限（存储为大写 OPP）。
     */
    public static boolean hasPermission(Player player, String permission) {
        if (!(player instanceof ServerPlayer)) {
            return false;
        }
        Set<String> perms = getPermissions(player);
        return perms.contains(permission)
            || (PERMISSION_OPP.equals(permission) && perms.contains(PERMISSION_OPP_LEGACY));
    }

    /** 获取玩家全部权限节点 */
    public static Set<String> getPermissions(Player player) {
        Set<String> perms = new HashSet<>();
        if (!(player instanceof ServerPlayer)) {
            return perms;
        }
        String existing = data(player).m_128461_(TAG_PERMISSIONS);
        if (existing != null && !existing.isEmpty()) {
            for (String s : existing.split(",")) {
                if (!s.isEmpty()) {
                    perms.add(s);
                }
            }
        }
        return perms;
    }

    /** 授予权限节点 */
    public static void addPermission(Player player, String permission) {
        if (!(player instanceof ServerPlayer)) {
            return;
        }
        Set<String> perms = getPermissions(player);
        perms.add(permission);
        save(player, perms);
    }

    /** 撤销权限节点 */
    public static void removePermission(Player player, String permission) {
        if (!(player instanceof ServerPlayer)) {
            return;
        }
        Set<String> perms = getPermissions(player);
        perms.remove(permission);
        perms.remove(PERMISSION_OPP_LEGACY);
        save(player, perms);
    }

    private static void save(Player player, Set<String> perms) {
        data(player).m_128359_(TAG_PERMISSIONS, String.join(",", perms));
    }

    private static CompoundTag data(Player player) {
        return ((IForgeEntity) player).getPersistentData();
    }
}