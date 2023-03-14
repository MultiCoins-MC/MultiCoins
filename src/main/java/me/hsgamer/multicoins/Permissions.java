package me.hsgamer.multicoins;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public final class Permissions {
    public static final Permission SET = new Permission("multicoins.set", PermissionDefault.OP);
    public static final Permission GET = new Permission("multicoins.get", PermissionDefault.OP);

    private Permissions() {
        // EMPTY
    }
}
