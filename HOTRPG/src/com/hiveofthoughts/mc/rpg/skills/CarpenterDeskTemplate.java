package com.hiveofthoughts.mc.rpg.skills;

import com.hiveofthoughts.mc.rpg.RPGConfig;
import com.hiveofthoughts.mc.rpg.WorldData;
import com.hiveofthoughts.mc.rpg.config.WoodcuttingConfig;
import com.hiveofthoughts.mc.server.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 * Created by Michael George on 3/21/2018.
 */
public class CarpenterDeskTemplate extends BuildingTemplate {
    public CarpenterDeskTemplate(){
        super();

        m_buildingName = "carpenterdesk";

        // SEt the block recipes here.
        m_builds[BUILD_N] = new BuildingDirection();
        m_builds[BUILD_N].addBlock(new Vector(0,0,0), Material.WORKBENCH, (byte)0);
        m_builds[BUILD_N].addBlock(new Vector(1,0,0), Material.WOOD_STAIRS, (byte)3);

        m_builds[BUILD_E] = new BuildingDirection();
        m_builds[BUILD_E].addBlock(new Vector(0,0,0), Material.WORKBENCH, (byte)0);
        m_builds[BUILD_E].addBlock(new Vector(0,0,1), Material.WOOD_STAIRS, (byte)0);

        m_builds[BUILD_S] = new BuildingDirection();
        m_builds[BUILD_S].addBlock(new Vector(0,0,0), Material.WORKBENCH, (byte)0);
        m_builds[BUILD_S].addBlock(new Vector(-1,0,0), Material.WOOD_STAIRS, (byte)2);

        m_builds[BUILD_W] = new BuildingDirection();
        m_builds[BUILD_W].addBlock(new Vector(0,0,0), Material.WORKBENCH, (byte)0);
        m_builds[BUILD_W].addBlock(new Vector(0,0,-1), Material.WOOD_STAIRS, (byte)1);

        // SEt the completion recipes here.
        m_buildComplete[BUILD_N] = new BuildingDirection();
        m_buildComplete[BUILD_N].addBlock(new Vector(0,0,0), Material.WORKBENCH, (byte)0);
        m_buildComplete[BUILD_N].addBlock(new Vector(1,0,0), Material.WOOD_STAIRS, (byte)4);

        m_buildComplete[BUILD_E] = new BuildingDirection();
        m_buildComplete[BUILD_E].addBlock(new Vector(0,0,0), Material.WORKBENCH, (byte)0);
        m_buildComplete[BUILD_E].addBlock(new Vector(0,0,1), Material.WOOD_STAIRS, (byte)6);

        m_buildComplete[BUILD_S] = new BuildingDirection();
        m_buildComplete[BUILD_S].addBlock(new Vector(0,0,0), Material.WORKBENCH, (byte)0);
        m_buildComplete[BUILD_S].addBlock(new Vector(-1,0,0), Material.WOOD_STAIRS, (byte)5);

        m_buildComplete[BUILD_W] = new BuildingDirection();
        m_buildComplete[BUILD_W].addBlock(new Vector(0,0,0), Material.WORKBENCH, (byte)0);
        m_buildComplete[BUILD_W].addBlock(new Vector(0,0,-1), Material.WOOD_STAIRS, (byte)7);

        // Generate the Menus if they do not exist.
        if(WorldData.TemplateMenuMap.get(this.getBuildingName()) == null){
            BuildingMenu t_main = new BuildingMenu();
            t_main.setMenuSize(54);
            t_main.setMenuName("Carpenter's Desk Menu");
            t_main.addOption(new ItemStack(Material.WOOD, 1), 19, BuildingMenu.ActionMenu + "Craft Menu: Planks");
            t_main.addOption(new ItemStack(Material.STICK, 1), 20, BuildingMenu.ActionMenu + "Craft Menu: Sticks");
            t_main.addOption(new ItemStack(Material.WOOD, 1), 28, BuildingMenu.ActionMenu + "Upgrade Menu: Planks");
            t_main.addOption(new ItemStack(Material.STICK, 1), 29, BuildingMenu.ActionMenu + "Upgrade Menu: Sticks");
            WorldData.BuildingMenus.add(t_main);

            {
                BuildingMenu t_b = new BuildingMenu();
                t_b.setMenuSize(54);
                t_b.setMenuName("Craft Menu: Planks");
                t_b.addOption(new ItemStack(RPGConfig.BackButton, 1), t_b.getMenuSize()-9, BuildingMenu.ActionMenu + "Carpenter's Desk Menu");
                t_b.addOption(new ItemStack(Material.WOOD, 1), 1, BuildingMenu.ActionProduce + RPGConfig.QualityAbysmal + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)2), 10, BuildingMenu.ActionProduce + RPGConfig.QualityAbysmal + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)1), 19, BuildingMenu.ActionProduce + RPGConfig.QualityAbysmal + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)3), 28, BuildingMenu.ActionProduce + RPGConfig.QualityAbysmal + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)4), 37, BuildingMenu.ActionProduce + RPGConfig.QualityAbysmal + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)5), 46, BuildingMenu.ActionProduce + RPGConfig.QualityAbysmal + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1), 2, BuildingMenu.ActionProduce + RPGConfig.QualityPoor + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)2), 11, BuildingMenu.ActionProduce + RPGConfig.QualityPoor + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)1), 20, BuildingMenu.ActionProduce + RPGConfig.QualityPoor + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)3), 29, BuildingMenu.ActionProduce + RPGConfig.QualityPoor + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)4), 38, BuildingMenu.ActionProduce + RPGConfig.QualityPoor + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)5), 47, BuildingMenu.ActionProduce + RPGConfig.QualityPoor + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1), 3, BuildingMenu.ActionProduce + RPGConfig.QualityDecent + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)2), 12, BuildingMenu.ActionProduce + RPGConfig.QualityDecent + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)1), 21, BuildingMenu.ActionProduce + RPGConfig.QualityDecent + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)3), 30, BuildingMenu.ActionProduce + RPGConfig.QualityDecent + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)4), 39, BuildingMenu.ActionProduce + RPGConfig.QualityDecent + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)5), 48, BuildingMenu.ActionProduce + RPGConfig.QualityDecent + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1), 4, BuildingMenu.ActionProduce + RPGConfig.QualityOkay + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)2), 13, BuildingMenu.ActionProduce + RPGConfig.QualityOkay + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)1), 22, BuildingMenu.ActionProduce + RPGConfig.QualityOkay + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)3), 31, BuildingMenu.ActionProduce + RPGConfig.QualityOkay + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)4), 40, BuildingMenu.ActionProduce + RPGConfig.QualityOkay + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)5), 49, BuildingMenu.ActionProduce + RPGConfig.QualityOkay + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1), 5, BuildingMenu.ActionProduce + RPGConfig.QualityRefined + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)2), 14, BuildingMenu.ActionProduce + RPGConfig.QualityRefined + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)1), 23, BuildingMenu.ActionProduce + RPGConfig.QualityRefined + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)3), 32, BuildingMenu.ActionProduce + RPGConfig.QualityRefined + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)4), 41, BuildingMenu.ActionProduce + RPGConfig.QualityRefined + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)5), 50, BuildingMenu.ActionProduce + RPGConfig.QualityRefined + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1), 6, BuildingMenu.ActionProduce + RPGConfig.QualityReinforced + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)2), 15, BuildingMenu.ActionProduce + RPGConfig.QualityReinforced + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)1), 24, BuildingMenu.ActionProduce + RPGConfig.QualityReinforced + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)3), 33, BuildingMenu.ActionProduce + RPGConfig.QualityReinforced + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)4), 42, BuildingMenu.ActionProduce + RPGConfig.QualityReinforced + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)5), 51, BuildingMenu.ActionProduce + RPGConfig.QualityReinforced + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1), 7, BuildingMenu.ActionProduce + RPGConfig.QualityEpic + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)2), 16, BuildingMenu.ActionProduce + RPGConfig.QualityEpic + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)1), 25, BuildingMenu.ActionProduce + RPGConfig.QualityEpic + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)3), 34, BuildingMenu.ActionProduce + RPGConfig.QualityEpic + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)4), 43, BuildingMenu.ActionProduce + RPGConfig.QualityEpic + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)5), 52, BuildingMenu.ActionProduce + RPGConfig.QualityEpic + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1), 8, BuildingMenu.ActionProduce + RPGConfig.QualityLegendary + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)2), 17, BuildingMenu.ActionProduce + RPGConfig.QualityLegendary + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)1), 26, BuildingMenu.ActionProduce + RPGConfig.QualityLegendary + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)3), 35, BuildingMenu.ActionProduce + RPGConfig.QualityLegendary + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)4), 44, BuildingMenu.ActionProduce + RPGConfig.QualityLegendary + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)5), 53, BuildingMenu.ActionProduce + RPGConfig.QualityLegendary + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");


                String t_qual = RPGConfig.QualityAbysmal;
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)0), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)2), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)3), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG_2, 1), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)4), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG_2, 1), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)5), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_b.addExchange(t_be);
                }

                t_qual = RPGConfig.QualityPoor;
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)0), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)2), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)3), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG_2, 1), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)4), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG_2, 1), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)5), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_b.addExchange(t_be);
                }

                t_qual = RPGConfig.QualityDecent;
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)0), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)2), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)3), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG_2, 1), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)4), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG_2, 1), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)5), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_b.addExchange(t_be);
                }

                t_qual = RPGConfig.QualityOkay;
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)0), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)2), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)3), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG_2, 1), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)4), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG_2, 1), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)5), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_b.addExchange(t_be);
                }

                t_qual = RPGConfig.QualityRefined;
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)0), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)2), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)3), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG_2, 1), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)4), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG_2, 1), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)5), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_b.addExchange(t_be);
                }

                t_qual = RPGConfig.QualityReinforced;
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)0), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)2), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)3), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG_2, 1), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)4), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG_2, 1), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)5), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_b.addExchange(t_be);
                }

                t_qual = RPGConfig.QualityEpic;
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)0), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)2), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)3), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG_2, 1), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)4), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG_2, 1), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)5), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_b.addExchange(t_be);
                }

                t_qual = RPGConfig.QualityLegendary;
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)0), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)2), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG, 1), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)3), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG_2, 1), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)4), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.LOG_2, 1), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Log", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte)5), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_b.addExchange(t_be);
                }

                WorldData.BuildingMenus.add(t_b);
            }
            {
                BuildingMenu t_b = new BuildingMenu();
                t_b.setMenuSize(54);
                t_b.setMenuName("Craft Menu: Sticks");
                t_b.addOption(new ItemStack(RPGConfig.BackButton, 1), t_b.getMenuSize()-9, BuildingMenu.ActionMenu + "Carpenter's Desk Menu");
                t_b.addOption(new ItemStack(Material.STICK, 1), 1, BuildingMenu.ActionProduce + RPGConfig.QualityAbysmal + " " + WoodcuttingConfig.WOOD_OAK + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 10, BuildingMenu.ActionProduce + RPGConfig.QualityAbysmal + " " + WoodcuttingConfig.WOOD_BIRCH + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 19, BuildingMenu.ActionProduce + RPGConfig.QualityAbysmal + " " + WoodcuttingConfig.WOOD_SPRUCE + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 28, BuildingMenu.ActionProduce + RPGConfig.QualityAbysmal + " " + WoodcuttingConfig.WOOD_JUNGLE + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 37, BuildingMenu.ActionProduce + RPGConfig.QualityAbysmal + " " + WoodcuttingConfig.WOOD_ACACIA + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 46, BuildingMenu.ActionProduce + RPGConfig.QualityAbysmal + " " + WoodcuttingConfig.WOOD_DARKOAK + " Stick");

                t_b.addOption(new ItemStack(Material.STICK, 1), 2, BuildingMenu.ActionProduce + RPGConfig.QualityPoor + " " + WoodcuttingConfig.WOOD_OAK + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 11, BuildingMenu.ActionProduce + RPGConfig.QualityPoor + " " + WoodcuttingConfig.WOOD_BIRCH + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 20, BuildingMenu.ActionProduce + RPGConfig.QualityPoor + " " + WoodcuttingConfig.WOOD_SPRUCE + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 29, BuildingMenu.ActionProduce + RPGConfig.QualityPoor + " " + WoodcuttingConfig.WOOD_JUNGLE + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 38, BuildingMenu.ActionProduce + RPGConfig.QualityPoor + " " + WoodcuttingConfig.WOOD_ACACIA + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 47, BuildingMenu.ActionProduce + RPGConfig.QualityPoor + " " + WoodcuttingConfig.WOOD_DARKOAK + " Stick");

                t_b.addOption(new ItemStack(Material.STICK, 1), 3, BuildingMenu.ActionProduce + RPGConfig.QualityDecent + " " + WoodcuttingConfig.WOOD_OAK + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 12, BuildingMenu.ActionProduce + RPGConfig.QualityDecent + " " + WoodcuttingConfig.WOOD_BIRCH + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 21, BuildingMenu.ActionProduce + RPGConfig.QualityDecent + " " + WoodcuttingConfig.WOOD_SPRUCE + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 30, BuildingMenu.ActionProduce + RPGConfig.QualityDecent + " " + WoodcuttingConfig.WOOD_JUNGLE + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 39, BuildingMenu.ActionProduce + RPGConfig.QualityDecent + " " + WoodcuttingConfig.WOOD_ACACIA + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 48, BuildingMenu.ActionProduce + RPGConfig.QualityDecent + " " + WoodcuttingConfig.WOOD_DARKOAK + " Stick");

                t_b.addOption(new ItemStack(Material.STICK, 1), 4, BuildingMenu.ActionProduce + RPGConfig.QualityOkay + " " + WoodcuttingConfig.WOOD_OAK + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 13, BuildingMenu.ActionProduce + RPGConfig.QualityOkay + " " + WoodcuttingConfig.WOOD_BIRCH + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 22, BuildingMenu.ActionProduce + RPGConfig.QualityOkay + " " + WoodcuttingConfig.WOOD_SPRUCE + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 31, BuildingMenu.ActionProduce + RPGConfig.QualityOkay + " " + WoodcuttingConfig.WOOD_JUNGLE + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 40, BuildingMenu.ActionProduce + RPGConfig.QualityOkay + " " + WoodcuttingConfig.WOOD_ACACIA + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 49, BuildingMenu.ActionProduce + RPGConfig.QualityOkay + " " + WoodcuttingConfig.WOOD_DARKOAK + " Stick");

                t_b.addOption(new ItemStack(Material.STICK, 1), 5, BuildingMenu.ActionProduce + RPGConfig.QualityRefined + " " + WoodcuttingConfig.WOOD_OAK + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 14, BuildingMenu.ActionProduce + RPGConfig.QualityRefined + " " + WoodcuttingConfig.WOOD_BIRCH + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 23, BuildingMenu.ActionProduce + RPGConfig.QualityRefined + " " + WoodcuttingConfig.WOOD_SPRUCE + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 32, BuildingMenu.ActionProduce + RPGConfig.QualityRefined + " " + WoodcuttingConfig.WOOD_JUNGLE + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 41, BuildingMenu.ActionProduce + RPGConfig.QualityRefined + " " + WoodcuttingConfig.WOOD_ACACIA + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 50, BuildingMenu.ActionProduce + RPGConfig.QualityRefined + " " + WoodcuttingConfig.WOOD_DARKOAK + " Stick");

                t_b.addOption(new ItemStack(Material.STICK, 1), 6, BuildingMenu.ActionProduce + RPGConfig.QualityReinforced + " " + WoodcuttingConfig.WOOD_OAK + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 15, BuildingMenu.ActionProduce + RPGConfig.QualityReinforced + " " + WoodcuttingConfig.WOOD_BIRCH + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 24, BuildingMenu.ActionProduce + RPGConfig.QualityReinforced + " " + WoodcuttingConfig.WOOD_SPRUCE + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 33, BuildingMenu.ActionProduce + RPGConfig.QualityReinforced + " " + WoodcuttingConfig.WOOD_JUNGLE + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 42, BuildingMenu.ActionProduce + RPGConfig.QualityReinforced + " " + WoodcuttingConfig.WOOD_ACACIA + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 51, BuildingMenu.ActionProduce + RPGConfig.QualityReinforced + " " + WoodcuttingConfig.WOOD_DARKOAK + " Stick");

                t_b.addOption(new ItemStack(Material.STICK, 1), 7, BuildingMenu.ActionProduce + RPGConfig.QualityEpic + " " + WoodcuttingConfig.WOOD_OAK + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 16, BuildingMenu.ActionProduce + RPGConfig.QualityEpic + " " + WoodcuttingConfig.WOOD_BIRCH + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 25, BuildingMenu.ActionProduce + RPGConfig.QualityEpic + " " + WoodcuttingConfig.WOOD_SPRUCE + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 34, BuildingMenu.ActionProduce + RPGConfig.QualityEpic + " " + WoodcuttingConfig.WOOD_JUNGLE + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 43, BuildingMenu.ActionProduce + RPGConfig.QualityEpic + " " + WoodcuttingConfig.WOOD_ACACIA + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 52, BuildingMenu.ActionProduce + RPGConfig.QualityEpic + " " + WoodcuttingConfig.WOOD_DARKOAK + " Stick");

                t_b.addOption(new ItemStack(Material.STICK, 1), 8, BuildingMenu.ActionProduce + RPGConfig.QualityLegendary + " " + WoodcuttingConfig.WOOD_OAK + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 17, BuildingMenu.ActionProduce + RPGConfig.QualityLegendary + " " + WoodcuttingConfig.WOOD_BIRCH + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 26, BuildingMenu.ActionProduce + RPGConfig.QualityLegendary + " " + WoodcuttingConfig.WOOD_SPRUCE + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 35, BuildingMenu.ActionProduce + RPGConfig.QualityLegendary + " " + WoodcuttingConfig.WOOD_JUNGLE + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 44, BuildingMenu.ActionProduce + RPGConfig.QualityLegendary + " " + WoodcuttingConfig.WOOD_ACACIA + " Stick");
                t_b.addOption(new ItemStack(Material.STICK, 1), 53, BuildingMenu.ActionProduce + RPGConfig.QualityLegendary + " " + WoodcuttingConfig.WOOD_DARKOAK + " Stick");

                String t_qual = RPGConfig.QualityAbysmal;
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Stick", null));
                    t_b.addExchange(t_be);
                }

                t_qual = RPGConfig.QualityPoor;
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Stick", null));
                    t_b.addExchange(t_be);
                }

                t_qual = RPGConfig.QualityDecent;
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Stick", null));
                    t_b.addExchange(t_be);
                }

                t_qual = RPGConfig.QualityOkay;
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Stick", null));
                    t_b.addExchange(t_be);
                }

                t_qual = RPGConfig.QualityRefined;
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Stick", null));
                    t_b.addExchange(t_be);
                }

                t_qual = RPGConfig.QualityReinforced;
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Stick", null));
                    t_b.addExchange(t_be);
                }

                t_qual = RPGConfig.QualityEpic;
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Stick", null));
                    t_b.addExchange(t_be);
                }

                t_qual = RPGConfig.QualityLegendary;
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Stick", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(BuildingMenu.ActionProduce + t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Stick");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.STICK, 3), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Stick", null));
                    t_b.addExchange(t_be);
                }

                WorldData.BuildingMenus.add(t_b);
            }
            {
                BuildingMenu t_b = new BuildingMenu();
                t_b.setMenuSize(54);
                t_b.setMenuName("Upgrade Menu: Planks");
                t_b.addOption(new ItemStack(RPGConfig.BackButton, 1), t_b.getMenuSize() - 9, BuildingMenu.ActionMenu + "Carpenter's Desk Menu");
                /** t_b.addOption(new ItemStack(Material.WOOD, 1), 1, BuildingMenu.ActionProduce + RPGConfig.QualityAbysmal + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                 t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)2), 10, BuildingMenu.ActionProduce + RPGConfig.QualityAbysmal + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                 t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)1), 19, BuildingMenu.ActionProduce + RPGConfig.QualityAbysmal + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                 t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)3), 28, BuildingMenu.ActionProduce + RPGConfig.QualityAbysmal + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                 t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)4), 37, BuildingMenu.ActionProduce + RPGConfig.QualityAbysmal + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                 t_b.addOption(new ItemStack(Material.WOOD, 1, (byte)5), 46, BuildingMenu.ActionProduce + RPGConfig.QualityAbysmal + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");*/
                t_b.addOption(new ItemStack(Material.WOOD, 1), 2, BuildingMenu.ActionProduce + RPGConfig.QualityPoor + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 2), 11, BuildingMenu.ActionProduce + RPGConfig.QualityPoor + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 1), 20, BuildingMenu.ActionProduce + RPGConfig.QualityPoor + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 3), 29, BuildingMenu.ActionProduce + RPGConfig.QualityPoor + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 4), 38, BuildingMenu.ActionProduce + RPGConfig.QualityPoor + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 5), 47, BuildingMenu.ActionProduce + RPGConfig.QualityPoor + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1), 3, BuildingMenu.ActionProduce + RPGConfig.QualityDecent + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 2), 12, BuildingMenu.ActionProduce + RPGConfig.QualityDecent + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 1), 21, BuildingMenu.ActionProduce + RPGConfig.QualityDecent + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 3), 30, BuildingMenu.ActionProduce + RPGConfig.QualityDecent + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 4), 39, BuildingMenu.ActionProduce + RPGConfig.QualityDecent + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 5), 48, BuildingMenu.ActionProduce + RPGConfig.QualityDecent + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1), 4, BuildingMenu.ActionProduce + RPGConfig.QualityOkay + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 2), 13, BuildingMenu.ActionProduce + RPGConfig.QualityOkay + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 1), 22, BuildingMenu.ActionProduce + RPGConfig.QualityOkay + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 3), 31, BuildingMenu.ActionProduce + RPGConfig.QualityOkay + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 4), 40, BuildingMenu.ActionProduce + RPGConfig.QualityOkay + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 5), 49, BuildingMenu.ActionProduce + RPGConfig.QualityOkay + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1), 5, BuildingMenu.ActionProduce + RPGConfig.QualityRefined + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 2), 14, BuildingMenu.ActionProduce + RPGConfig.QualityRefined + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 1), 23, BuildingMenu.ActionProduce + RPGConfig.QualityRefined + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 3), 32, BuildingMenu.ActionProduce + RPGConfig.QualityRefined + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 4), 41, BuildingMenu.ActionProduce + RPGConfig.QualityRefined + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 5), 50, BuildingMenu.ActionProduce + RPGConfig.QualityRefined + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1), 6, BuildingMenu.ActionProduce + RPGConfig.QualityReinforced + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 2), 15, BuildingMenu.ActionProduce + RPGConfig.QualityReinforced + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 1), 24, BuildingMenu.ActionProduce + RPGConfig.QualityReinforced + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 3), 33, BuildingMenu.ActionProduce + RPGConfig.QualityReinforced + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 4), 42, BuildingMenu.ActionProduce + RPGConfig.QualityReinforced + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 5), 51, BuildingMenu.ActionProduce + RPGConfig.QualityReinforced + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1), 7, BuildingMenu.ActionProduce + RPGConfig.QualityEpic + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 2), 16, BuildingMenu.ActionProduce + RPGConfig.QualityEpic + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 1), 25, BuildingMenu.ActionProduce + RPGConfig.QualityEpic + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 3), 34, BuildingMenu.ActionProduce + RPGConfig.QualityEpic + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 4), 43, BuildingMenu.ActionProduce + RPGConfig.QualityEpic + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 5), 52, BuildingMenu.ActionProduce + RPGConfig.QualityEpic + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1), 8, BuildingMenu.ActionProduce + RPGConfig.QualityLegendary + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 2), 17, BuildingMenu.ActionProduce + RPGConfig.QualityLegendary + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 1), 26, BuildingMenu.ActionProduce + RPGConfig.QualityLegendary + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 3), 35, BuildingMenu.ActionProduce + RPGConfig.QualityLegendary + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 4), 44, BuildingMenu.ActionProduce + RPGConfig.QualityLegendary + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                t_b.addOption(new ItemStack(Material.WOOD, 1, (byte) 5), 53, BuildingMenu.ActionProduce + RPGConfig.QualityLegendary + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");


                String t_qual = "";
                String t_qual2 = "";
                t_qual2 = RPGConfig.QualityAbysmal;
                t_qual = RPGConfig.QualityPoor;
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 4), t_qual2 + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1, (byte) 0), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 4), t_qual2 + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1, (byte) 2), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 4), t_qual2 + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1, (byte) 1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 4), t_qual2 + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1, (byte) 3), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 4), t_qual2 + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1, (byte) 4), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 4), t_qual2 + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1, (byte) 5), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_b.addExchange(t_be);
                }

                t_qual2 = RPGConfig.QualityPoor;
                t_qual = RPGConfig.QualityDecent;
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 5), t_qual2 + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1, (byte) 0), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 5), t_qual2 + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1, (byte) 2), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 5), t_qual2 + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1, (byte) 1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 5), t_qual2 + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1, (byte) 3), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 5), t_qual2 + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1, (byte) 4), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 5), t_qual2 + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1, (byte) 5), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_b.addExchange(t_be);
                }

                t_qual2 = RPGConfig.QualityDecent;
                t_qual = RPGConfig.QualityOkay;
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 0), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 2), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 3), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 4), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 5), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_b.addExchange(t_be);
                }

                t_qual2 = RPGConfig.QualityOkay;
                t_qual = RPGConfig.QualityRefined;
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 0), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 2), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 3), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 4), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 5), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_b.addExchange(t_be);
                }

                t_qual2 = RPGConfig.QualityRefined;
                t_qual = RPGConfig.QualityReinforced;
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 0), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 2), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 3), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 4), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 5), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_b.addExchange(t_be);
                }

                t_qual2 = RPGConfig.QualityReinforced;
                t_qual = RPGConfig.QualityEpic;
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 0), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 2), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 3), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 4), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 5), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_b.addExchange(t_be);
                }

                t_qual2 = RPGConfig.QualityEpic;
                t_qual = RPGConfig.QualityLegendary;
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 0), t_qual + " " + WoodcuttingConfig.WOOD_OAK + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 2), t_qual + " " + WoodcuttingConfig.WOOD_BIRCH + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 1), t_qual + " " + WoodcuttingConfig.WOOD_SPRUCE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 3), t_qual + " " + WoodcuttingConfig.WOOD_JUNGLE + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 4), t_qual + " " + WoodcuttingConfig.WOOD_ACACIA + " Planks", null));
                    t_b.addExchange(t_be);
                }
                {
                    BuildingExchange t_be = new BuildingExchange(t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks");
                    t_be.addRequirement(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 1), t_qual2 + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_be.addResult(ItemBuilder.buildItem(new ItemStack(Material.WOOD, 2, (byte) 5), t_qual + " " + WoodcuttingConfig.WOOD_DARKOAK + " Planks", null));
                    t_b.addExchange(t_be);
                }

                WorldData.BuildingMenus.add(t_b);
            }

            WorldData.TemplateMenuMap.put(this.getBuildingName(), "Carpenter's Desk Menu");

            System.out.println("BUILDING MENU ADDED INTO MEMORY!!!" + this.getBuildingName());
        }
    }
}
