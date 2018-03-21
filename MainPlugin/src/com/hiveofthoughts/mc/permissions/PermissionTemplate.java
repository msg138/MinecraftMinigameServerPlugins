package com.hiveofthoughts.mc.permissions;

import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.server.ServerBalance;
import com.hiveofthoughts.mc.server.ServerInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Michael George on 11/18/2017.
 */
public enum PermissionTemplate {

    DEFAULT("",new String[]{
            "help.all",
            "permission.*.all",
            "test.test",
            Config.PermissionBuild + ".test",
            Config.PermissionDig + ".test",
            "serverlist.all"
    }, null, "default"),
    ADMIN("[" + ChatColor.DARK_RED + "ADMIN" + ChatColor.WHITE + "]",new String[]{
            "*.all",
            Config.PermissionBuild + ".all",
            Config.PermissionDig + ".all",
            Config.PermissionServerChangeAll + ".all"
    }, null, "admin"),
    OWNER("[" + ChatColor.DARK_RED + ChatColor.BOLD + "OWNER" + ChatColor.WHITE + "]",new String[]{
            "*.all",
            Config.PermissionBuild + ".all",
            Config.PermissionDig + ".all",
            Config.PermissionServerChangeAll + ".all"
    }, null, "owner"),
    DEVELOPER("[" + ChatColor.BLUE + ChatColor.BOLD + "DEVELOPER" + ChatColor.WHITE + "]",new String[]{
            "*.all",
            Config.PermissionBuild + ".all",
            Config.PermissionDig + ".all",
            Config.PermissionServerChangeAll + ".all"
    }, null, "dev"),
    JUNIOR_DEVELOPER("[" + ChatColor.BLUE + ChatColor.BOLD + "JR DEV" + ChatColor.WHITE + "]",new String[]{
            "*.all",
            Config.PermissionBuild + ".all",
            Config.PermissionDig + ".all",
            Config.PermissionServerChangeAll + ".all"
    }, null, "jrdev"),
    MODERATOR("[" + ChatColor.GOLD + "MODERATOR" + ChatColor.WHITE + "]",new String[]{
            "gamemode.*.all",
            "serverlist.all",
            "help.all"}, new PermissionTemplate[]{DEFAULT}, "mod"),

    /**
     * The many builder permissions. One for each build server, as well as a Master builder, that is able to build on all build servers.
     */
    MASTER_BUILDER("[" + ChatColor.LIGHT_PURPLE + "MASTER " + ChatColor.DARK_GREEN + "BUILDER" + ChatColor.WHITE + "]",new String[]{
            "gamemode.2.build",
            "gamemode.1.build",
            "gamemode.0.build",
            Config.PermissionBuild + ".build",
            Config.PermissionDig +".build",
            "serverlist.all",
            "help.all"}, new PermissionTemplate[]{DEFAULT}, "master_builder"),
    BUILDER1("[" + ChatColor.DARK_GREEN + "BUILDER" + ChatColor.WHITE + "]",new String[]{
            "gamemode.2.build-1",
            "gamemode.1.build-1",
            "gamemode.0.build-1",
            Config.PermissionBuild + ".build-1",
            Config.PermissionDig +".build-1",
            "serverlist.all",
            "help.all"}, new PermissionTemplate[]{DEFAULT}, "builder1"),
    BUILDER2("[" + ChatColor.DARK_GREEN + "BUILDER" + ChatColor.WHITE + "]",new String[]{
            "gamemode.2.build-2",
            "gamemode.1.build-2",
            "gamemode.0.build-2",
            Config.PermissionBuild + ".build-2",
            Config.PermissionDig +".build-2",
            "serverlist.all",
            "help.all"}, new PermissionTemplate[]{DEFAULT}, "builder2"),
    BUILDER3("[" + ChatColor.DARK_GREEN + "BUILDER" + ChatColor.WHITE + "]",new String[]{
            "gamemode.2.build-3",
            "gamemode.1.build-3",
            "gamemode.0.build-3",
            Config.PermissionBuild + ".build-3",
            Config.PermissionDig +".build-3",
            "serverlist.all",
            "help.all"}, new PermissionTemplate[]{DEFAULT}, "builder3"),
    BUILDER4("[" + ChatColor.DARK_GREEN + "BUILDER" + ChatColor.WHITE + "]",new String[]{
            "gamemode.2.build-4",
            "gamemode.1.build-4",
            "gamemode.0.build-4",
            Config.PermissionBuild + ".build-4",
            Config.PermissionDig +".build-4",
            "serverlist.all",
            "help.all"}, new PermissionTemplate[]{DEFAULT}, "builder4"),
    BUILDER5("[" + ChatColor.DARK_GREEN + "BUILDER" + ChatColor.WHITE + "]",new String[]{
            "gamemode.2.build-5",
            "gamemode.1.build-5",
            "gamemode.0.build-5",
            Config.PermissionBuild + ".build-5",
            Config.PermissionDig +".build-5",
            "serverlist.all",
            "help.all"}, new PermissionTemplate[]{DEFAULT}, "builder5"),
    BUILDER6("[" + ChatColor.DARK_GREEN + "BUILDER" + ChatColor.WHITE + "]",new String[]{
            "gamemode.2.build-6",
            "gamemode.1.build-6",
            "gamemode.0.build-6",
            Config.PermissionBuild + ".build-6",
            Config.PermissionDig +".build-6",
            "serverlist.all",
            "help.all"}, new PermissionTemplate[]{DEFAULT}, "builder6"),
    BUILDER7("[" + ChatColor.DARK_GREEN + "BUILDER" + ChatColor.WHITE + "]",new String[]{
            "gamemode.2.build-7",
            "gamemode.1.build-7",
            "gamemode.0.build-7",
            Config.PermissionBuild + ".build-7",
            Config.PermissionDig +".build-7",
            "serverlist.all",
            "help.all"}, new PermissionTemplate[]{DEFAULT}, "builder7"),
    BUILDER8("[" + ChatColor.DARK_GREEN + "BUILDER" + ChatColor.WHITE + "]",new String[]{
            "gamemode.2.build-8",
            "gamemode.1.build-8",
            "gamemode.0.build-8",
            Config.PermissionBuild + ".build-8",
            Config.PermissionDig +".build-8",
            "serverlist.all",
            "help.all"}, new PermissionTemplate[]{DEFAULT}, "builder8"),
    BUILDER9("[" + ChatColor.DARK_GREEN + "BUILDER" + ChatColor.WHITE + "]",new String[]{
            "gamemode.2.build-9",
            "gamemode.1.build-9",
            "gamemode.0.build-9",
            Config.PermissionBuild + ".build-9",
            Config.PermissionDig +".build-9",
            "serverlist.all",
            "help.all"}, new PermissionTemplate[]{DEFAULT}, "builder9"),
    BUILDER10("[" + ChatColor.DARK_GREEN + "BUILDER" + ChatColor.WHITE + "]",new String[]{
            "gamemode.2.build-10",
            "gamemode.1.build-10",
            "gamemode.0.build-10",
            Config.PermissionBuild + ".build-10",
            Config.PermissionDig +".build-10",
            "serverlist.all",
            "help.all"}, new PermissionTemplate[]{DEFAULT}, "builder10"),;

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
        if(Config.EnforceServerRestriction){
            boolean r_has = hasPermission(permission, Config.PermissionServerAll);
            if(!r_has)
                r_has = hasPermission(permission, ServerBalance.getMainServer(ServerInfo.getInstance().getServerName()));
            if(!r_has)
                r_has = hasPermission(permission, ServerInfo.getInstance().getServerName());
            return r_has;
        }else
            return hasPermission(permission, Config.PermissionServerAll);
    }

    public boolean hasPermission(String permission, String server){
        boolean hasPerm = false;

        if(Config.EnforceServerRestriction && !server.isEmpty())
            permission = permission + "." + server;

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
