package com.hiveofthoughts.mc.rpgold;

/**
 * Created by Michael George on 3/15/2018.
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.*;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class Data {
    //Global data.
    public static ArrayList<Warp> warps = new ArrayList<>();
    public static ArrayList<PlayerData> players = new ArrayList<PlayerData>();

    public static ArrayList<Block> mineralBlocks = new ArrayList<Block>();
    public static ArrayList<Integer> mineralTimes = new ArrayList<Integer>();
    public static ArrayList<Material> mineralType = new ArrayList<Material>();

    public static ArrayList<Smelter> forgeViews = new ArrayList<Smelter>();
    public static ArrayList<String> forgePlayers = new ArrayList<String>();

    public static final String[] skills = {"magic","mining","woodcutting"};

    public static final int defaultCharacterCount = 1;

    // Sword damage types
    public static final int WOOD_DAMAGE = 1;
    public static final int STONE_DAMAGE = 2;
    public static final int IRON_DAMAGE = 5;
    public static final int GOLD_DAMAGE = 7;
    public static final int DIAMOND_DAMAGE = 10;

    // Afk kicker times
    public static final int KICK_AFTER_SEC = 15*60;
    public static final int AFK_AFTER_SEC = 10*60;

    // Mining respawn times
    public static final int DIAMOND_TIME = 2500;
    public static final int LAPIS_TIME = 500;
    public static final int IRON_TIME = 350;
    public static final int COAL_TIME = 200;
    public static final int GOLD_TIME = 600;
    public static final int EMERALD_TIME = 1000;
    public static final int REDSTONE_TIME = 450;

    // Mining exp bonus.
    public static final double MINE_EXP_BONUS = 1;

    // Hits it takes per mineral.
    public static final int MINE_DIAMOND_TIME = 25;
    public static final int MINE_EMERALD_TIME = 20;
    public static final int MINE_REDSTONE_TIME = 20;
    public static final int MINE_GOLD_TIME = 15;
    public static final int MINE_LAPIS_TIME = 15;
    public static final int MINE_IRON_TIME = 10;
    public static final int MINE_COAL_TIME = 5;

    // Minimum number of hits per ore.
    public static final int MINE_MINIMUM = 2;

    // How many levels per decrease in hits.
    public static final int MINE_LEVEL_CHANGE = 2;

    // HOw much exp that each ore provides upon successful mine.
    public static final int MINE_DIAMOND_EXP = 90;
    public static final int MINE_EMERALD_EXP = 70;
    public static final int MINE_REDSTONE_EXP = 50;
    public static final int MINE_GOLD_EXP = 40;
    public static final int MINE_LAPIS_EXP = 20;
    public static final int MINE_IRON_EXP = 10;
    public static final int MINE_COAL_EXP = 5;


    public static final int EXP_PER_LEVEL = 100;
    public static final int EXP_PER_COMPLETE = 1;



    public static ArrayList<Vehicle> vehicles = new ArrayList<>();

    public static String joinMessage;
    public static String quitMessage;

    //End
    public static String realityTag = ChatColor.ITALIC+""+ChatColor.GREEN+"[RPGSCJM] "+ChatColor.WHITE;

    public static File userfile;
    public static File configfile;
    public static File warpfile;
    public static File racefile;
    public static File townfile;
    public static File abilfile;
    public static File classfile;
    public static FileConfiguration userConfig;
    public static FileConfiguration warpConfig;
    public static FileConfiguration raceConfig;
    public static FileConfiguration townConfig;
    public static FileConfiguration abilConfig;
    public static FileConfiguration classConfig;
    public static FileConfiguration config;


    public static int getHitsRequired(Material m, int lvl)
    {
        int hits = 1;
        String stat = "none";
        switch(m)
        {
            case COAL_ORE:
                hits = Data.MINE_COAL_TIME;
                stat = "mining";
                break;
            case DIAMOND_ORE:
                hits = Data.MINE_DIAMOND_TIME;
                stat = "mining";
                break;
            case IRON_ORE:
                hits = Data.MINE_IRON_TIME;
                stat = "mining";
                break;
            case EMERALD_ORE:
                hits = Data.MINE_EMERALD_TIME;
                stat = "mining";
                break;
            case LAPIS_ORE:
                hits = Data.MINE_LAPIS_TIME;
                stat = "mining";
                break;
            case GOLD_ORE:
                hits = Data.MINE_GOLD_TIME;
                stat = "mining";
                break;
            case REDSTONE_ORE:
                hits = Data.MINE_REDSTONE_TIME;
                stat = "mining";
                break;
            case GLOWING_REDSTONE_ORE:
                hits = Data.MINE_REDSTONE_TIME;
                stat = "mining";
                break;
        }
        if(stat.equals("mining")){
            hits-= (lvl/Data.MINE_LEVEL_CHANGE);
            if(hits<Data.MINE_MINIMUM)
                hits = Data.MINE_MINIMUM;
        }
        return hits;
    }
    public static int getExpFromMaterial(Material m)
    {
        int exp = 0;
        switch(m)
        {
            case COAL_ORE:
                exp = Data.MINE_COAL_EXP;
                break;
            case DIAMOND_ORE:
                exp = Data.MINE_DIAMOND_EXP;
                break;
            case IRON_ORE:
                exp = Data.MINE_IRON_EXP;
                break;
            case EMERALD_ORE:
                exp = Data.MINE_EMERALD_EXP;
                break;
            case LAPIS_ORE:
                exp = Data.MINE_LAPIS_EXP;
                break;
            case GOLD_ORE:
                exp = Data.MINE_GOLD_EXP;
                break;
            case REDSTONE_ORE:
                exp = Data.MINE_REDSTONE_EXP;
                break;
            case GLOWING_REDSTONE_ORE:
                exp = Data.MINE_REDSTONE_EXP;
                break;
        }
        return exp;
    }


    public static void loadFiles(JavaPlugin jp) throws Exception
    {
        userfile = new File(jp.getDataFolder(),"users.yml");
        configfile = new File(jp.getDataFolder(),"config.yml");
        warpfile = new File(jp.getDataFolder(),"warps.yml");
        racefile = new File(jp.getDataFolder(),"races.yml");
        townfile = new File(jp.getDataFolder(),"towns.yml");
        classfile = new File(jp.getDataFolder(),"classes.yml");
        abilfile = new File(jp.getDataFolder(),"abilities.yml");

        createData(jp);

        userConfig = new YamlConfiguration();
        config = new YamlConfiguration();
        warpConfig = new YamlConfiguration();
        raceConfig = new YamlConfiguration();
        townConfig = new YamlConfiguration();
        abilConfig  = new YamlConfiguration();
        classConfig = new YamlConfiguration();

        userConfig.load(userfile);
        config.load(configfile);
        warpConfig.load(warpfile);
        raceConfig.load(racefile);
        townConfig.load(townfile);
        abilConfig.load(abilfile);
        classConfig.load(classfile);


        Set<String> w = warpConfig.getConfigurationSection("warps").getKeys(false);
        ArrayList<String> was = new ArrayList<>();
        was.addAll(w);
        for(int i=0;i<was.size();i++)
        {
            Warp warp = new Warp();
            warp.name = was.get(i);
            warp.x = warpConfig.getDouble("warps."+warp.name+".x");
            warp.y = warpConfig.getDouble("warps."+warp.name+".y");
            warp.z = warpConfig.getDouble("warps."+warp.name+".z");
            warps.add(warp);
        }

        {
            ItemStack it = new ItemStack(Material.IRON_INGOT);
            ItemMeta im = it.getItemMeta();
            im.setLore(Arrays.asList("MOLTEN"));
            it.setItemMeta(im);
            // Add some recipes
            ShapelessRecipe rec = new ShapelessRecipe(it);
            rec.addIngredient(Material.IRON_INGOT);
            rec.addIngredient(Material.LAVA_BUCKET);
            Bukkit.getServer().addRecipe(rec);
        }
    }

    public static int findVehicle(Minecart mc)
    {
        for(int i=0;i<vehicles.size();i++)
        {
            if(vehicles.get(i).minecart.equals(mc))
            {
                return i;
            }
        }
        return -1;
    }

    public static Vector getLookAt(Player p)
    {
        return getLookAt(p.getLocation().getPitch(),p.getLocation().getYaw());
    }

    public static Vector getLookAt(double p, double y)
    {
        Vector vel = new Vector();
        p = ((p+90)*Math.PI)/180;
        y = ((y+90)*Math.PI)/180;
        vel.setX(Math.sin(p)*Math.cos(y));
        vel.setZ(Math.sin(p)*Math.sin(y));
        vel.setY(Math.cos(p));
        return vel;
    }

    public static void createData(JavaPlugin jp) throws Exception
    {
        if(!userfile.exists()){                        // checks if the yaml does not exists
            userfile.getParentFile().mkdirs();         // creates the /plugins/<pluginName>/ directory if not found
            copy(jp.getResource("users.yml"), userfile); // copies the yaml from your jar to the folder /plugin/<pluginName>
        }
        if(!configfile.exists()){
            configfile.getParentFile().mkdirs();
            copy(jp.getResource("config.yml"), configfile);
        }
        if(!warpfile.exists()){
            warpfile.getParentFile().mkdirs();
            copy(jp.getResource("warps.yml"), warpfile);
        }
        if(!racefile.exists()){
            racefile.getParentFile().mkdirs();
            copy(jp.getResource("races.yml"), racefile);
        }
        if(!townfile.exists()){
            townfile.getParentFile().mkdirs();
            copy(jp.getResource("towns.yml"), townfile);
        }
        if(!abilfile.exists()){
            abilfile.getParentFile().mkdirs();
            copy(jp.getResource("abilities.yml"), abilfile);
        }
        if(!classfile.exists()){
            classfile.getParentFile().mkdirs();
            copy(jp.getResource("classes.yml"), classfile);
        }
    }public static void resetData(JavaPlugin jp) throws Exception
    {                     // checks if the yaml does not exists
        userfile.getParentFile().mkdirs();         // creates the /plugins/<pluginName>/ directory if not found
        copy(jp.getResource("users.yml"), userfile); // copies the yaml from your jar to the folder /plugin/<pluginName>

        configfile.getParentFile().mkdirs();
        copy(jp.getResource("config.yml"), configfile);

        warpfile.getParentFile().mkdirs();
        copy(jp.getResource("warps.yml"), warpfile);

        racefile.getParentFile().mkdirs();
        copy(jp.getResource("races.yml"), racefile);

        townfile.getParentFile().mkdirs();
        copy(jp.getResource("towns.yml"), townfile);

        classfile.getParentFile().mkdirs();
        copy(jp.getResource("classes.yml"), classfile);

        abilfile.getParentFile().mkdirs();
        copy(jp.getResource("abilities.yml"), abilfile);
    }

    public static void saveData() throws Exception
    {
        for(PlayerData pd : players)
            Data.savePlayer(pd);

        config.save(configfile);
        userConfig.save(userfile);
        warpConfig.save(warpfile);
        raceConfig.save(racefile);
        townConfig.save(townfile);
        abilConfig.save(abilfile);
        classConfig.save(classfile);
    }

    public static void addNewPlayer(Player p,String f, String l, int slot)
    {
        if(playerExists(p.getUniqueId())==false)
            userConfig.set("users."+p.getUniqueId(), null);

        if(userConfig.contains("users."+p.getUniqueId()+".cc") == false)
            userConfig.set("users."+p.getUniqueId()+".cc", defaultCharacterCount);

        int slotCount =0;
        try{slotCount= Integer.parseInt(userConfig.getString("users."+p.getName()+".cc"));}
        catch(Exception e){}
        if(slotCount<=0)
            slotCount = 1;
        if(slot>=slotCount)
            slot = slotCount - 1;
        if(slot <=-1)
        {
            slot = userConfig.getInt("users."+p.getName()+".curcharacter");
        }
        userConfig.set("users."+p.getName()+".cc", slotCount);
        userConfig.set("users."+p.getName()+".curcharacter", slot);
        setUserData(p.getUniqueId(),"firstname", f);
        setUserData(p.getUniqueId(),"lastname", l);
        setUserData(p.getUniqueId(),"flying",false);
        setUserData(p.getUniqueId(),"mana",0);
        setUserData(p.getUniqueId(),"manaregen",0);
        setUserData(p.getUniqueId(),"maxmana",0);
        setUserData(p.getUniqueId(),"lasttown","wild");
        setUserData(p.getUniqueId(),"raceName","human");
        p.setPlayerListName("("+p.getName()+") "+Data.getFirstName(p.getUniqueId()));

        p.sendMessage(realityTag+"Your profile has been created.");
    }

    public static void resetPlayer(Player p)
    {
        UUID pn = p.getUniqueId();
        String f=(String)getUserData(pn,"firstname"),l=(String)getUserData(pn,"lastname"),r=(String)getUserData(pn,"raceName");
        userConfig.set("users."+pn+userConfig.getInt("users."+pn+".curcharacter"), "");
        addNewPlayer(p,f,l,userConfig.getInt("users."+pn+".curcharacter"));

        p.sendMessage(Data.realityTag+"Your profile has been reset.");
    }

    public static String getFullName(UUID username)
    {
        return WordUtils.capitalize((String)getUserData(username,"firstname"))+" "+WordUtils.capitalize((String)getUserData(username,"lastname"));
    }

    public static  String getFirstName(UUID username)
    {
        return WordUtils.capitalize((String)getUserData(username,"firstname"));
    }

    public static ChatColor getChatColor(Player p)
    {
        ChatColor c = null;

        if(p.isOp())
        {
            c = ChatColor.RED;
        }else
        {
            c = ChatColor.AQUA;
        }

        return c;
    }

    public static Location getWarp(String name)
    {
        for(int i=0;i<warps.size();i++)
        {
            if(warps.get(i).name.equals(name))
            {
                Location loc = new Location(null,0,0,0);
                loc.setX(warps.get(i).x);
                loc.setY(warps.get(i).y);
                loc.setZ(warps.get(i).z);
                return loc;
            }
        }
        return null;
    }

    public static boolean playerExists(UUID playername)
    {
        if(userConfig.contains("users."+playername+""))
            return true;
        return false;
    }

    private static void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static LivingEntity getEntityLook(Player p,int range)
    {
		/*LivingEntity e = null;

		Block tb = p.getTargetBlock(new HashSet<Material>(Arrays.asList(Material.AIR)), 50);
		for(Entity ent: tb.getChunk().getEntities())
		{
			if(ent instanceof LivingEntity && tb.getLocation().distance(ent.getLocation())<2 && ent != p)
			{
				e = (LivingEntity)ent;
			}
		}

		return e;
		*/
        List<Entity> nearbyE = p.getNearbyEntities(range,
                range, range);
        ArrayList<LivingEntity> livingE = new ArrayList<LivingEntity>();

        for (Entity e : nearbyE) {
            if (e instanceof LivingEntity) {
                livingE.add((LivingEntity) e);
            }
        }

        LivingEntity target = null;
        BlockIterator bItr = new BlockIterator(p, range);
        Block block;
        Location loc;
        int bx, by, bz;
        double ex, ey, ez;
        // loop through player's line of sight
        while (bItr.hasNext()) {
            block = bItr.next();
            bx = block.getX();
            by = block.getY();
            bz = block.getZ();
            // check for entities near this block in the line of sight
            for (LivingEntity e : livingE) {
                loc = e.getLocation();
                ex = loc.getX();
                ey = loc.getY();
                ez = loc.getZ();
                if ((bx-.75 <= ex && ex <= bx+1.75) && (bz-.75 <= ez && ez <= bz+1.75) && (by-1 <= ey && ey <= by+2.5)) {
                    // entity is close enough, set target and stop
                    target = e;
                    break;
                }
            }
        }
        return target;
    }

    public static boolean hasPermission(String playerName, String command, String[] args){
        String path = "users."+playerName+".permissions";
        boolean doesIt = false;
        String fullCommand = command;
        if(userConfig.contains(path))
        {
            for(int i=0;args != null && i<args.length ;i++)
            {
                fullCommand = fullCommand + " " + args[i];
            }

            for(int i=0;i<userConfig.getInt(path+".amount");i++)
            {
                if(fullCommand.equals(userConfig.getString(path+"."+i)) || userConfig.getString(path+"."+i).equals(command+" *") || userConfig.getString(path+"."+i).equals("*"))
                {
                    doesIt = true;
                    break;
                }
            }
        }

        return doesIt;
    }
    public static boolean hasPermission(String playerName, String command){
        return hasPermission(playerName, command,null);
    }

    public static void addPermission(String perm, String playerName)
    {
        String path = "users."+playerName+".permissions";
        userConfig.set(path+"."+userConfig.getInt(path+".amount"), perm);
        userConfig.set(path+".amount", userConfig.getInt(path+".amount")+1);
    }
    public static void removePermission(String perm, String playerName)
    {
        String path = "users."+playerName+".permissions";
        userConfig.set(path+"."+userConfig.getInt(path+".amount"), perm);
        userConfig.set(path+".amount", userConfig.getInt(path+".amount")+1);
    }

    public static PlayerData getPlayerData(UUID playerName)
    {
        for(PlayerData pd : players)
        {
            if(pd.uuid.equals(playerName))
                return pd;
        }
        return null;
    }

    public static void savePlayer(PlayerData pd)
    {
        UUID pn = pd.uuid;
        setUserData(pn,"flying", pd.flying);
        setUserData(pn,"mana", pd.mana);
        setUserData(pn,"maxmana", pd.maxmana);
        setUserData(pn,"manaregen",pd.manaRegen);
        setUserData(pn,"lasttown", pd.lastTown);
    }

    public static PlayerData loadPlayer(Player pn, int cNum)
    {
        if(userConfig.getInt("users."+pn+".cc")<=cNum)
            cNum = userConfig.getInt("users."+pn+".cc")-1;
        if(userConfig.contains("users."+pn+"."+cNum) == false)
            cNum = userConfig.getInt("users."+pn+".curcharacter");


        userConfig.set("users."+pn+".curcharacter", cNum);

        return loadPlayer(pn);
    }

    public static PlayerData loadPlayer(Player pn)
    {
        if(Data.playerExists(pn.getUniqueId()) == false)
            return new PlayerData(pn.getUniqueId());
        PlayerData ret = new PlayerData(pn.getUniqueId());

        ret.flying = (boolean)getUserData(pn.getUniqueId(),"flying");

        ret.mana = (int)getUserData(pn.getUniqueId(),"mana");
        ret.maxmana = (int)getUserData(pn.getUniqueId(),"maxmana");

        ret.manaRegen = (int)getUserData(pn.getUniqueId(),"manaregen");

        ret.lastTown = (String)getUserData(pn.getUniqueId(),"lasttown");
        if(ret.lastTown == null || ret.lastTown.isEmpty())
        {
            ret.lastTown = "wild";
        }

        return ret;
    }

    public static void sendChat(Player p, String msg, String mode)
    {
        List<Player> player = p.getWorld().getPlayers();

    }

    public static Object getUserData(UUID username, String whatget)
    {
        Object ret = 0;
        ret = userConfig.get("users."+username+"."+userConfig.getInt("users."+username+".curcharacter")+"."+whatget);
        if(ret == null)
        {
            ret = 0;
            userConfig.set("users."+username+"."+userConfig.getInt("users."+username+".curcharacter")+"."+whatget,0);
        }
        return ret;
    }

    public static void setUserData(UUID username, String whatset, Object what)
    {
        userConfig.set("users."+username+"."+userConfig.getInt("users."+username+".curcharacter")+"."+whatset,what );
    }

    public static int getUserLevel(UUID username, String stat)
    {
        int lvl = 0;


        if(getUserData(username, "skills."+stat+".lvl") == null)
        {
            setUserData(username, "skills."+stat+".lvl",1);
        }
        lvl = (Integer)getUserData(username, "skills."+stat+".lvl");

        return lvl;
    }

    public static boolean addUserExperience(UUID username, String stat, int exp)
    {
        if(getUserData(username, "skills."+stat+".exp") == null)
        {
            setUserData(username, "skills."+stat+".exp",0);
        }
        setUserData(username, "skills."+stat+".exp",(Integer)getUserData(username,"skills."+stat+".exp")+exp);

        Bukkit.getPlayer(username).sendMessage("You earned "+exp+"exp in "+stat+"!");
        boolean leveled = false;

        while((Integer)getUserData(username, "skills."+stat+".exp") >= getUserLevel(username,stat)*EXP_PER_LEVEL)
        {
            setUserData(username, "skills."+stat+".exp",(Integer)getUserData(username,"skills."+stat+".exp")-getUserLevel(username,stat)*EXP_PER_LEVEL);
            setUserData(username,"skills."+stat+".lvl",getUserLevel(username,stat)+1);

            Bukkit.getPlayer(username).sendMessage("You leveled up in "+stat+"!");
            Bukkit.getPlayer(username).sendMessage("You are now level "+ getUserLevel(username,stat)+" in "+stat+"!");
            leveled = true;
        }
        if(leveled == true)
        {
            Title t = new Title(stat.toUpperCase()+" LEVEL UP!","Level "+getUserLevel(username,stat));
            t.fadeIn = 25;
            t.fadeOut = 25;
            t.stay = 75;
            t.send(Bukkit.getPlayer(username));
        }
        //Data.userConfig.set("users."+username+".skills.magic.experience",Data.userConfig.getInt("users."+username+".skills.magic.experience")+Data.abilConfig.getInt(spellname+".exp"));
        return leveled;
    }

    public static int getUserExperience(UUID username, String stat)
    {

        if(getUserData(username, "skills."+stat+".exp") == null)
        {
            setUserData(username, "skills."+stat+".exp",0);
        }
        return (int)getUserData(username, "skills."+stat+".exp");

    }

    public static ItemStack createBook(String title,String page1,String page2, String lore, String author)
    {
        ItemStack ret = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta)ret.getItemMeta();
        bookMeta.setTitle(title);
        bookMeta.setAuthor(author);
        bookMeta.addPage(page1);
        bookMeta.addPage(page2);
        bookMeta.setLore(Arrays.asList(lore));
        ret.setItemMeta(bookMeta);

        return ret;
    }
}
