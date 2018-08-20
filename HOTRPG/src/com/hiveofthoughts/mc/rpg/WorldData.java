package com.hiveofthoughts.mc.rpg;

import com.hiveofthoughts.mc.rpg.skills.BuildingMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Michael George on 3/19/2018.
 */
public class WorldData {

    public static List<Block> MinedBlocks = new ArrayList<>();
    public static List<Integer> MinedBlocksTime = new ArrayList<>();
    public static List<Material> MinedBlocksType = new ArrayList<>();

    public static List<BuildingMenu> BuildingMenus = new ArrayList<>();

    public static Map<String, String> TemplateMenuMap = new HashMap<>();
}
