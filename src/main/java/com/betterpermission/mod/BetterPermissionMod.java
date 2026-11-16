package com.betterpermission.mod;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("better_permission_management")
public class BetterPermissionMod {
    public static final String MOD_ID = "better_permission_management";
    public static final Logger LOGGER = LogManager.getLogger();

    public BetterPermissionMod() {
        LOGGER.info("Better Permission Management mod loaded.");
    }
}
