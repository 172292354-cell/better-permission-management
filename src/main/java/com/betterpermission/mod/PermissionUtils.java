package com.betterpermission.mod;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.extensions.IForgeEntity;

/**
 * OPP 权限工具类。
 *
 * 权限为一个固定的 OPP（大写）权限，通过 Forge 的 getPersistentData()
 * 随玩家存档持久化保存。玩家要么拥有 OPP，要么没有。
 *
 * 命名说明：
 * - getPersistentData() 是 Forge 接口方法，运行时不被混淆，使用正规名。
 * - CompoundTag 的 getString/putString 是 Minecraft 方法，运行时以 SRG 名称存在，
 *   因此这里使用 SRG 名称 m_128461_ / m_128359_。
 */
public class PermissionUtils {
    private static final String TAG_PERMISSIONS = "BPM_OPP";

    /** 唯一的权限名称 */
    public static final String PERMISSION_OPP = "OPP";

    /** 玩家是否拥有 OPP 权限 */
    public static boolean hasOPP(Player player) {
        if (!(player instanceof ServerPlayer)) {
            return false;
        }
        return PERMISSION_OPP.equals(data(player).m_128461_(TAG_PERMISSIONS));
    }

    /** 授予玩家 OPP 权限 */
    public static void grantOPP(Player player) {
        if (!(player instanceof ServerPlayer)) {
            return;
        }
        data(player).m_128359_(TAG_PERMISSIONS, PERMISSION_OPP);
    }

    /** 撤销玩家的 OPP 权限 */
    public static void revokeOPP(Player player) {
        if (!(player instanceof ServerPlayer)) {
            return;
        }
        data(player).m_128359_(TAG_PERMISSIONS, "");
    }

    private static CompoundTag data(Player player) {
        return ((IForgeEntity) player).getPersistentData();
    }
}