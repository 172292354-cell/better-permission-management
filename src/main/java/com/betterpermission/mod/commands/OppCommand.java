package com.betterpermission.mod.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.betterpermission.mod.BetterPermissionMod;
import com.betterpermission.mod.PermissionUtils;

import java.util.List;

/**
 * /opp 权限管理命令。
 *
 * 权限为固定的 OPP（大写）权限。
 *
 * 子命令：
 *   /opp grant <玩家>   - 授予 OPP 权限（需 OP）
 *   /opp revoke <玩家>  - 撤销 OPP 权限（需 OP）
 *   /opp check <玩家>   - 查看玩家是否拥有 OPP（需 OP 或查询本人）
 *   /opp list           - 列出拥有 OPP 权限的在线玩家（需 OP）
 *
 * 命名说明：本文件中的 Minecraft 类方法/字段使用 SRG（混淆）名称，
 * 例如 m_82127_() 对应 Commands.literal()，当前构建环境无法联网执行 Gradle
 * reobf，运行时 Forge 以 srg 命名域加载 mod，因此直接编译 SRG 名称。
 */
@Mod.EventBusSubscriber(modid = BetterPermissionMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OppCommand {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
            Commands.m_82127_("opp")
                .then(grant())
                .then(revoke())
                .then(check())
                .then(list())
        );
        logCommandTree(event);
        BetterPermissionMod.LOGGER.info("Registered /opp command.");
    }

    /** 输出 /opp 命令树到日志，便于验证命令注册 */
    private static void logCommandTree(RegisterCommandsEvent event) {
        for (com.mojang.brigadier.tree.CommandNode<CommandSourceStack> child
                : event.getDispatcher().getRoot().getChildren()) {
            if ("opp".equals(child.getName())) {
                StringBuilder sb = new StringBuilder("/opp");
                for (com.mojang.brigadier.tree.CommandNode<CommandSourceStack> sub : child.getChildren()) {
                    sb.append("  ").append(sub.getName());
                }
                BetterPermissionMod.LOGGER.info("OPP command tree: " + sb);
                return;
            }
        }
    }

    // ---------- 子命令注册 ----------

    private static com.mojang.brigadier.builder.LiteralArgumentBuilder<CommandSourceStack> grant() {
        return Commands.m_82127_("grant")
            .then(Commands.m_82129_("player", StringArgumentType.word())
                .executes(OppCommand::grant));
    }

    private static com.mojang.brigadier.builder.LiteralArgumentBuilder<CommandSourceStack> revoke() {
        return Commands.m_82127_("revoke")
            .then(Commands.m_82129_("player", StringArgumentType.word())
                .executes(OppCommand::revoke));
    }

    private static com.mojang.brigadier.builder.LiteralArgumentBuilder<CommandSourceStack> check() {
        return Commands.m_82127_("check")
            .then(Commands.m_82129_("player", StringArgumentType.word())
                .executes(OppCommand::check));
    }

    private static com.mojang.brigadier.builder.LiteralArgumentBuilder<CommandSourceStack> list() {
        return Commands.m_82127_("list")
            .executes(OppCommand::list);
    }

    // ---------- 处理器 ----------

    private static int grant(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        if (!requireOp(source)) return 0;

        ServerPlayer operator = source.m_230896_();
        ServerPlayer target = findPlayer(operator,
            context.getArgument("player", String.class));
        if (target == null) {
            source.m_81352_(Component.m_237113_("玩家不存在。"));
            return 0;
        }
        PermissionUtils.grantOPP(target);
        source.m_288197_(() -> Component.m_237113_("已授予 " + target.m_5446_().getString() + " OPP 权限。"), false);
        return 1;
    }

    private static int revoke(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        if (!requireOp(source)) return 0;

        ServerPlayer operator = source.m_230896_();
        ServerPlayer target = findPlayer(operator,
            context.getArgument("player", String.class));
        if (target == null) {
            source.m_81352_(Component.m_237113_("玩家不存在。"));
            return 0;
        }
        PermissionUtils.revokeOPP(target);
        source.m_288197_(() -> Component.m_237113_("已撤销 " + target.m_5446_().getString() + " 的 OPP 权限。"), false);
        return 1;
    }

    private static int check(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        if (!source.m_230897_()) {
            source.m_81352_(Component.m_237113_("该命令只能由玩家执行。"));
            return 0;
        }

        ServerPlayer operator = source.m_230896_();
        ServerPlayer target = findPlayer(operator,
            context.getArgument("player", String.class));
        if (target == null) {
            source.m_81352_(Component.m_237113_("玩家不存在。"));
            return 0;
        }

        // 非本人查询需要 OP 权限
        if (!target.equals(operator) && !source.m_6761_(2)) {
            source.m_81352_(Component.m_237113_("你没有权限查看其他玩家的权限。"));
            return 0;
        }

        if (PermissionUtils.hasOPP(target)) {
            source.m_288197_(() -> Component.m_237113_(target.m_5446_().getString() + " 拥有 OPP 权限。"), false);
        } else {
            source.m_288197_(() -> Component.m_237113_(target.m_5446_().getString() + " 没有 OPP 权限。"), false);
        }
        return 1;
    }

    private static int list(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        if (!requireOp(source)) return 0;

        ServerPlayer operator = source.m_230896_();
        List<ServerPlayer> players = operator.f_8924_.m_6846_().m_11314_();
        boolean any = false;
        for (ServerPlayer sp : players) {
            if (PermissionUtils.hasOPP(sp)) {
                any = true;
                final ServerPlayer p = sp;
                source.m_288197_(() -> Component.m_237113_(p.m_5446_().getString() + " 拥有 OPP 权限。"), false);
            }
        }
        if (!any) {
            source.m_288197_(() -> Component.m_237113_("当前没有玩家拥有 OPP 权限。"), false);
        }
        return 1;
    }

    // ---------- 辅助 ----------

    private static boolean requireOp(CommandSourceStack source) {
        if (!source.m_230897_()) {
            source.m_81352_(Component.m_237113_("该命令只能由玩家执行。"));
            return false;
        }
        if (!source.m_6761_(2)) {
            source.m_81352_(Component.m_237113_("你没有足够的权限使用该命令。"));
            return false;
        }
        return true;
    }

    private static ServerPlayer findPlayer(ServerPlayer operator, String name) {
        return operator.f_8924_.m_6846_().m_11255_(name);
    }
}