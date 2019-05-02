package com.hiveofthoughts.mc.server;

import com.hiveofthoughts.mc.Config;
import com.hiveofthoughts.mc.Main;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.*;
import org.bukkit.util.Vector;

/**
 * Created by Michael George on 3/15/2018.
 */
public enum ServerType {
    DEFAULT("default", false, Material.DIRT.toString(), "0"),
    RPG("RPG"){/**

        private ScoreboardManager sbm;
        public int uPlayerCheckTown = -1, uVehicleDirection=-1, uRacialBuff = -1, uScoreboard = -1, uManaRegen = -1, uMineralSpawn = -1, uForge = -1,
                uMonsterSpawn = -1, uAfkKick;
        @Override
        public void init(Main a_main){

            uPlayerCheckTown = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(a_main, new Runnable(){
                public void run(){
                    updatePlayerCheckTown();
                }
            }, 0, 5);
            uRacialBuff = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(a_main, new Runnable(){
                public void run(){
                    updateRacialBuffs();
                }
            }, 0, 1);
            uScoreboard = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(a_main, new Runnable(){
                public void run(){
                    updateScoreboard();
                }
            }, 0, 30);
            uManaRegen = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(a_main, new Runnable(){
                public void run(){
                    updateManaRegen();
                }
            }, 0, 100);
            uMineralSpawn = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(a_main, new Runnable(){
                public void run(){
                    updateMineralSpawn();
                }
            }, 0, 1);
            uForge = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(a_main, new Runnable(){
                public void run(){
                    updateForge();
                }
            }, 0, 10);
            uMonsterSpawn = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(a_main, new Runnable(){
                public void run(){
                    updateMonsterSpawn(Bukkit.getWorld("rpmakestuff"));
                }
            }, 0, 1);
            uAfkKick = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(a_main, new Runnable(){
                public void run(){
                    updateAfkKick();
                }
            }, 0, 1);

            // Add players that were already logged in.
            for(World w : Bukkit.getWorlds())
            {
                for(Player p : w.getPlayers())
                {
                    Data.players.add(new PlayerData(p.getUniqueId()));
                }
            }

            sbm = Bukkit.getScoreboardManager();

            MobDrop.mDrops.add(new MobDrop("The Forbidden",10, EntityType.ZOMBIE,Spell.createSpell("spawnfish"),100));
            MobDrop.mDrops.add(new MobDrop("The Forbidden",10,EntityType.ZOMBIE,Item.setLore(new ItemStack(Material.IRON_SWORD,1), "Swift Edge", "Wielded by only the worthiest of warriors.\n6"),100));
            MobDrop.mDrops.add(new MobDrop("The Forbidden",11,EntityType.PIG_ZOMBIE,Item.setLore(new ItemStack(Material.IRON_SWORD,1), "Swift Edge", "Wielded by only the worthiest of warriors.\n6"),100));
            MobDrop.mDrops.add(new MobDrop("The Forbidden",12,EntityType.SKELETON,Item.setLore(new ItemStack(Material.PAPER,1), "RECIPE: PAPER", "TOOL: ANVIL\nONEUSE\nNAME: Legendary Paper\nLORE: Used by kings.\nAMOUNT: 2\nREQ: DIAMOND"),100));
            MobDrop.mDrops.add(new MobDrop("The Forbidden",12,EntityType.SKELETON,Item.setLore(new ItemStack(Material.PAPER,1), "RECIPE: DIAMOND_SWORD", "TOOL: ANVIL\nONEUSE\nNAME: "+ ChatColor.GOLD+"King Slayer\nLORE: Killed many kings.&999\nAMOUNT: 1\nREQ: DIAMOND,DIAMOND"),100));
            MobDrop.mDrops.add(new MobDrop("The Forbidden",12,EntityType.SKELETON,Item.setLore(new ItemStack(Material.PAPER,1), "RECIPE: WRITTEN_BOOK", "TOOL: ANVIL\nNAME: SPELL:spawnfish\nLORE: spawnfish\nAMOUNT: 1\nREQ: PAPER,PAPER"),100));

        }
        public void updateMonsterSpawn(World w)
        {
            if(w == null || Data.townConfig.getString("towns.list").isEmpty() || Data.townConfig.getString("towns.list").split(",").length<=0)
                return;
            for(String ss:Data.townConfig.getString("towns.list").split(","))
            {

                Location loc1 = new Location(w, 0, 0, 0);
                loc1.setX(Data.townConfig.getDouble("towns."+ss+".x1"));
                loc1.setY(Data.townConfig.getDouble("towns."+ss+".y1"));
                loc1.setZ(Data.townConfig.getDouble("towns."+ss+".z1"));
                String[] args = Data.townConfig.getString("towns."+ss+".args").split(",");
                String[] cre = (args[2]).split("\\.");

                int entsInChunk = 0;
                for(Entity e : loc1.getChunk().getEntities())
                {
                    if(args[2].indexOf(e.getType().toString()) != -1)
                        entsInChunk++;

                }
                if(args.length<4)
                {
                    //System.out.println("IN TOWN NO ARGS");
                    continue;
                }
                if(entsInChunk >= Integer.parseInt(args[1]))
                {
                    continue;
                }else
                {
                    //Spawn in a random entity from the wanted list.

                    loc1.setY(loc1.getY()+2);
                    EntityType n = EntityType.fromName(cre[(int)Math.floor(cre.length*Math.random())]);
                    if(n == null)
                        n = EntityType.CREEPER;
                    LivingEntity ll = (LivingEntity)w.spawnEntity(loc1,n);

                    if(args.length>8)// nine for equipment
                    {
                        if(args[4].equals("0") == false)
                        {
                            ll.getEquipment().setHelmet(new ItemStack(Material.getMaterial(Integer.parseInt(args[4]))));
                            ll.getEquipment().setHelmetDropChance(0);
                        }if(args[5].equals("0") == false)
                    {
                        ll.getEquipment().setChestplate(new ItemStack(Material.getMaterial(Integer.parseInt(args[5]))));
                        ll.getEquipment().setChestplateDropChance(0);
                    }if(args[6].equals("0") == false)
                    {
                        ll.getEquipment().setLeggings(new ItemStack(Material.getMaterial(Integer.parseInt(args[6]))));
                        ll.getEquipment().setLeggingsDropChance(0);
                    }if(args[7].equals("0") == false)
                    {
                        ll.getEquipment().setBoots(new ItemStack(Material.getMaterial(Integer.parseInt(args[7]))));
                        ll.getEquipment().setBootsDropChance(0);
                    }if(args[8].equals("0") == false)
                    {
                        ll.getEquipment().setItemInHand(new ItemStack(Material.getMaterial(Integer.parseInt(args[8]))));
                        ll.getEquipment().setItemInHandDropChance(0);
                    }
                    }

                    if(ll != null)
                        return;

                }
            }
        }

        public void updateForge()
        {
        }

        public void updateAfkKick()
        {
            for(PlayerData pd : Data.players)
            {
                pd.idleSince++;

                if(pd.idleSince>=Data.KICK_AFTER_SEC*24)
                {
                    Bukkit.getPlayer(pd.uuid).kickPlayer("Kicked for being afk.");
                }else if(pd.idleSince>=Data.AFK_AFTER_SEC*24 && pd.afk == false)
                {
                    pd.afk = true;
                    Bukkit.broadcastMessage(pd.uuid+" is afk.");
                }else if(pd.idleSince<Data.AFK_AFTER_SEC*24)
                    pd.afk = false;
            }
        }

        public void updateMineralSpawn()
        {
            for(int i=0;i<Data.mineralBlocks.size();i++)
            {
                Data.mineralTimes.set(i, Data.mineralTimes.get(i)+1);

                boolean isDone = false;
                int curTime = Data.mineralTimes.get(i);
                switch(Data.mineralType.get(i))
                {
                    case COAL_ORE:
                        if(curTime >= Data.COAL_TIME)
                            isDone = true;
                        break;
                    case DIAMOND_ORE:
                        if(curTime >= Data.DIAMOND_TIME)
                            isDone = true;
                        break;
                    case IRON_ORE:
                        if(curTime >= Data.IRON_TIME)
                            isDone = true;
                        break;
                    case EMERALD_ORE:
                        if(curTime >= Data.EMERALD_TIME)
                            isDone = true;
                        break;
                    case LAPIS_ORE:
                        if(curTime >= Data.LAPIS_TIME)
                            isDone = true;
                        break;
                    case GOLD_ORE:
                        if(curTime >= Data.GOLD_TIME)
                            isDone = true;
                        break;
                    case REDSTONE_ORE:
                        if(curTime >= Data.REDSTONE_TIME)
                            isDone = true;
                        break;
                    case GLOWING_REDSTONE_ORE:
                        if(curTime >= Data.REDSTONE_TIME)
                            isDone = true;
                        break;
                }

                if(isDone)
                {
                    Data.mineralBlocks.get(i).setType(Data.mineralType.get(i));
                    Data.mineralBlocks.remove(i);
                    Data.mineralTimes.remove(i);
                    Data.mineralType.remove(i);
                    i--;
                }
            }
        }

        public void updateManaRegen()
        {
            for(PlayerData pd : Data.players)
            {
                Player p = Bukkit.getServer().getPlayer(pd.uuid);
                pd.mana+= pd.manaRegen;
                if(pd.mana>pd.maxmana)
                {
                    pd.mana = pd.maxmana;
                }
            }
        }

        public void updateScoreboard()
        {
            for(int i=0;i<Data.players.size();i++)
            {
                Player p = Bukkit.getServer().getPlayer(Data.players.get(i).uuid);
                Scoreboard s = sbm.getNewScoreboard();
                Team t = s.registerNewTeam(p.getName());
                t.addPlayer(p);
                t.setDisplayName(p.getName());
                // The race of the player.
                Objective score = s.registerNewObjective("scoreboard", "dummy");
                score.setDisplayName(p.getName());
                score.setDisplaySlot(DisplaySlot.SIDEBAR);
                score.getScore((String)Data.getUserData(p.getUniqueId(),"raceName")).setScore(-2);
                // The last town of tah player.
                score.getScore(Data.players.get(i).lastTown).setScore(-1);
                // The mana of tah player.
                score.getScore("Mana").setScore(Data.players.get(i).mana);


                p.setScoreboard(s);
            }
        }

        public void updateRacialBuffs()
        {
            for(int i=0;i<Data.players.size();i++)
            {
                Player p = Bukkit.getServer().getPlayer(Data.players.get(i).uuid);
                p.setExp(0);

                if(Data.players.get(i).mineMaterial != null)
                    p.setLevel(Data.getHitsRequired(Data.players.get(i).mineMaterial.getType(),Data.players.get(i).getCurrentLevelSkill()) - Data.players.get(i).mineHits);
                else
                    p.setLevel(100);

                if(Data.playerExists(p.getUniqueId()) == false || Data.raceConfig.contains("races."+Data.getUserData(p.getUniqueId(),"raceName")+".buff.amount") == false)
                {
                    continue;
                }
                int l = Data.raceConfig.getInt("races."+Data.getUserData(p.getUniqueId(),"raceName")+".buff.amount");
                for(int j=0;j<l;j++)
                {
                    PotionEffectType pet = PotionEffectType.getByName(Data.raceConfig.getString("races."+Data.getUserData(p.getUniqueId(),"raceName")+".buff."+j+".effecttype"));
                    PotionEffect pot = new PotionEffect(pet,1000,Data.raceConfig.getInt("races."+Data.getUserData(p.getUniqueId(),"raceName")+".buff."+j+".amplifier"));
                    p.addPotionEffect(pot);
                }
                if(Data.raceConfig.contains("races."+Data.getUserData(p.getUniqueId(),"raceName")+".eventbuff.oncrouch"))
                {
                    ///System.out.println("checking1");
                    // Now get the block type
                    if(p.isSneaking())
                    {
                        //p.sendMessage("you feel a surge of energy as you touch the water.");

                        PotionEffectType pet = PotionEffectType.getByName(Data.raceConfig.getString("races."+Data.getUserData(p.getUniqueId(),"raceName")+".eventbuff.oncrouch.effecttype"));
                        PotionEffect pot = new PotionEffect(pet,30,Data.raceConfig.getInt("races."+Data.getUserData(p.getUniqueId(),"raceName")+".eventbuff.oncrouch.amplifier"));
                        p.addPotionEffect(pot);


                    }

                }

            }
        }

        public void updatePlayerCheckTown()
        {
            for(int i=0;i<Data.players.size();i++)
            {
                Player p = Bukkit.getServer().getPlayer(Data.players.get(i).uuid);

			/**

            }
        }

        public void updateVehicleDirection()
        {
            for(int i=0;i<Data.vehicles.size();i++)
            {
                if(Data.vehicles.get(i).running==false)
                    continue;
                Minecart mc = Data.vehicles.get(i).minecart;
                //Block b = Data.vehicles.get(i).player.getTargetBlock(null, 100);
                Vector vel = Data.getLookAt(Data.vehicles.get(i).player);
                vel.multiply(Data.vehicles.get(i).speed);
                vel.setY(0);
                mc.setVelocity(vel);
            }
        }

        @Override
        public void deinit(Main a_main){

            a_main.getLogger().info("Plugin being disabled.");
            Bukkit.getServer().getScheduler().cancelTask(uPlayerCheckTown);
            Bukkit.getServer().getScheduler().cancelTask(uVehicleDirection);
            Bukkit.getServer().getScheduler().cancelTask(uRacialBuff);

            // kick players.
            for(PlayerData pd : Data.players)
            {
                Player p = Bukkit.getServer().getPlayer(pd.uuid);
                //p.kickPlayer("Server closed. Try again soon.");
            }
            for(int i=0;i<Data.mineralBlocks.size();i++){
                Data.mineralBlocks.get(i).setType(Data.mineralType.get(i));
            }

            try {
                Data.saveData();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }*/
    },
    TEST("TEST", false, Material.REDSTONE.toString(), "0"),
    BUILD("BUILD", true, Material.GRASS.toString(), "1"),
    ADVENTURE("ADVENTURE", true, Material.IRON_SWORD.toString(), "0"),
    HUB("MAIN", false, Material.END_CRYSTAL.toString(), "3", 10, 1, .8f);

    private String m_name;

    private boolean m_permanent;

    private String m_version;

    private String m_serverBlock;

    private int m_maxPlayers;
    private int m_minServers;

    private int m_okayServerCount;

    private float m_serverFullRatio;

    ServerType(String a_name){
        this(a_name, false);
    }

    ServerType(String a_name, boolean a_permanent){
        this(a_name, a_permanent, Material.EMERALD_BLOCK.toString());
    }

    ServerType(String a_name, boolean a_permanent, String a_serverBlock){
        this(a_name, a_permanent, a_serverBlock, "");
    }

    ServerType(String a_name, boolean a_permanent, String a_serverBlock, String a_version){
        this(a_name, a_permanent, a_serverBlock, a_version, Config.ServerPlayersMaxDefault, Config.ServerMinDefault, Config.ServerFullRatioDefault);
    }

    ServerType(String a_name, boolean a_permanent, String a_serverBlock, String a_version, int a_maxPlayers, int a_minServers, float a_serverRatio){
        m_name = a_name;
        m_permanent = a_permanent;
        m_serverBlock = a_serverBlock;
        m_version = a_version;

        m_maxPlayers = a_maxPlayers;
        m_minServers = a_minServers;
        m_serverFullRatio = a_serverRatio;

        m_okayServerCount = Config.ServerCountOkayDefault;
    }

    public String getName(){
        return m_name;
    }

    public boolean isPermanent(){
        return m_permanent;
    }

    public int getMaxPlayers(){
        return m_maxPlayers;
    }

    public int getMinServers(){
        return m_minServers;
    }

    public int getOkayServers(){
        return m_okayServerCount;
    }

    public float getFullRatio(){
        return m_serverFullRatio;
    }

    public String getVersion(){
        return m_version;
    }

    public String getServerBlock(){
        return m_serverBlock;
    }

    public static ServerType getFromName(String a_name){
        for(int t_i = 0; t_i < ServerType.values().length; t_i ++){
            if(ServerType.values()[t_i].getName().equalsIgnoreCase(a_name)){
                return ServerType.values()[t_i];
            }
        }
        return ServerType.DEFAULT;
    }
    public static ServerType getFromFullName(String a_name){
        String t_start = a_name.substring(0, a_name.indexOf(Config.ServerNameMiddle));
        return getFromName(t_start);
    }

    public void init(Main a_main){ }

    public void deinit(Main a_main) { }
}
