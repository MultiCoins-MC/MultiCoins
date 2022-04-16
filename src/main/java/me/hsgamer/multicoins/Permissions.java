package me.hsgamer.multicoins;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public final class Permissions {
    public static final Permission SET = new Permission("multicoins.set", PermissionDefault.OP);
    public static final Permission GET = new Permission("multicoins.get", PermissionDefault.OP);

    private Permissions() {
        // EMPTY
    }

    public static void register() {
        Bukkit.getPluginManager().addPermission(SET);
        Bukkit.getPluginManager().addPermission(GET);
    }

    public static void unregister() {
        Bukkit.getPluginManager().removePermission(SET);
        Bukkit.getPluginManager().removePermission(GET);
    }
}
