package com.hiveofthoughts.mc.permissions;

import com.hiveofthoughts.mc.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Michael George on 11/18/2017.
 */
public enum PermissionTemplate {

    ADMIN("[" + ChatColor.DARK_RED + "ADMIN" + ChatColor.WHITE + "]",new String[]{"*", Config.PermissionBuild, Config.PermissionDig}, null, "admin"),
    MODERATOR("[" + ChatColor.GOLD + "MODERATOR" + ChatColor.WHITE + "]",new String[]{
            "gamemode.*",
            "help"}, null, "mod"),
    BUILDER("[" + ChatColor.DARK_GREEN + "BUILDER" + ChatColor.WHITE + "]",new String[]{
            "gamemode.2",
            "gamemode.1",
            "gamemode.0",
            Config.PermissionBuild,
            Config.PermissionDig,
            "help"}, null, "builder"),
    DEFAULT("",new String[]{"help", "permission.*", "test"}, null, "default"),
    ;

    public static PermissionTemplate getPermission(String pname){
        ///Bukkit.getLogger().info("Getting permission for: '" + pname + "'");
        for(PermissionTemplate pt : PermissionTemplate.values()){
            if(pt.getName().equalsIgnoreCase(pname)){
                ///Bukkit.getLogger().info("Permission Found: " + pt.getName());
                return pt;
            }
        }
        ///Bukkit.getLogger().info("No permission found.");
        return DEFAULT;
    }

    private ArrayList<String> m_permissions;
    private ArrayList<PermissionTemplate> m_inherit;

    private String m_prefix = "";
    private String m_suffix = "";

    private String m_name = "";

    PermissionTemplate(String pre){
        m_permissions = new ArrayList<>();
        m_inherit = new ArrayList<>();

        m_prefix = pre;
    }

    PermissionTemplate(String pre, String[] perms, PermissionTemplate[] inherit, String permissionName){
        this(pre);
        if(perms != null)
            for(String perm : perms){
                m_permissions.add(perm);
            }
        if(inherit != null)
            for(PermissionTemplate pt : inherit){
                m_inherit.add(pt);
            }
        m_name = permissionName;
    }

    public ArrayList<String> getPermissions(){
        return m_permissions;
    }

    public ArrayList<PermissionTemplate> getInheritance(){
        return m_inherit;
    }

    public boolean hasPermission(String permission){
        boolean hasPerm = false;
        for(String perm : m_permissions){
            if(perm.equals(permission))
            {
                hasPerm = true;
                break;
            }
        }
        for(PermissionTemplate pt : m_inherit){
            if(pt.hasPermission(permission)){
                hasPerm = true;
                break;
            }
        }
        return hasPerm; // Default no permissions
    }

    public void addPermission(String permission){
        m_permissions.add(permission);
    }

    public String getPrefix(){
        return m_prefix;
    }

    public String getSuffix(){
        return m_suffix;
    }

    public String getName(){
        return m_name;
    }

    public boolean hasPermission(String command, String[] args){
        boolean hasPerm = hasPermission(command);
        if(hasPermission("*"))
            return true;
        for(int i=0;i<args.length+1;i++){
            String cmd = formatCommandPermission(command, Arrays.copyOf(args, args.length-i));
            if(i > 0)
                cmd += ".*";
            Bukkit.getLogger().info("Testing command '" + cmd + "'");
            if(hasPermission(cmd)) {
                hasPerm = true;
                break;
            }
            Bukkit.getLogger().info("Does not have permission.");
        }
        return hasPerm;
    }

    public String formatCommandPermission(String command, String[] args){
        String ret = command;
        if(args.length > 0)
            ret += ".";
        for(int i=0;i<args.length;i++){
            ret += args[i];
            if(i != args.length-1)
                ret += ".";
        }
        return ret;
    }
}
