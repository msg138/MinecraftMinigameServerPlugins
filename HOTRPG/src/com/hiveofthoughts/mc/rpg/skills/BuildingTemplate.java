package com.hiveofthoughts.mc.rpg.skills;

import com.hiveofthoughts.mc.rpg.Main;
import com.hiveofthoughts.mc.rpg.RPGConfig;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Michael George on 3/21/2018.
 */
public class BuildingTemplate {

    public static final int BUILD_N = 0;
    public static final int BUILD_E = 1;
    public static final int BUILD_S = 2;
    public static final int BUILD_W = 3;

    protected BuildingDirection[] m_builds;
    protected BuildingDirection[] m_buildComplete;

    protected String m_buildingName;

    protected BuildingTemplate(){
        m_builds = new BuildingDirection[4];
        m_buildComplete = new BuildingDirection[4];

        m_buildingName = RPGConfig.MetaBuildingNone;
    }

    public String getBuildingName(){
        return m_buildingName;
    }

    public boolean testTemplate(Block a_testBlock){
        boolean r_isBuild = false;
        for(int i=0;i<4;i++){
            if(m_builds[i].verifyStructure(a_testBlock)) {
                r_isBuild = true;
                break;
            }
        }
        return r_isBuild;
    }

    public boolean buildTemplate(Block a_testBlock){
        boolean r_isBuild = false;
        for(int i=0;i<m_builds.length;i++){
            if(m_builds[i].verifyStructure(a_testBlock)) {
                r_isBuild = true;
                m_buildComplete[i].setStructure(a_testBlock);
                break;
            }
        }
        return r_isBuild;
    }

    protected class BuildingDirection{
        private List<Vector > m_blockLocations;
        private List<Material > m_blockMaterial;
        private List<Byte > m_blockData;

        public BuildingDirection(){
            m_blockData = new ArrayList<>();
            m_blockLocations = new ArrayList<>();
            m_blockMaterial = new ArrayList<>();
        }

        public void addBlock(Vector a_location, Material a_material, byte a_data){
            m_blockData.add(a_data);
            m_blockLocations.add(a_location);
            m_blockMaterial.add(a_material);
        }

        public boolean verifyStructure(Block a_testBlock){
            for(int i=0;i<m_blockLocations.size();i++){
                Vector t_v = m_blockLocations.get(i);
                Block t_b = a_testBlock.getRelative(t_v.getBlockX(), t_v.getBlockY(), t_v.getBlockZ());
                if(!t_b.getType().equals(m_blockMaterial.get(i)) || !m_blockData.get(i).equals(t_b.getData())){
                    System.out.println("Block does not match, M: ");
                    return false;
                }
                System.out.println("Block matches");
            }
            return true;
        }

        public boolean setStructure(Block a_mainBlock){
            for(int i=0;i<m_blockLocations.size();i++){
                Vector t_v = m_blockLocations.get(i);
                Block t_b = a_mainBlock.getRelative(t_v.getBlockX(), t_v.getBlockY(), t_v.getBlockZ());
                t_b.setType(m_blockMaterial.get(i));
                t_b.setData(m_blockData.get(i));
                if(t_v.getBlockX() == 0 && t_v.getBlockY() == 0 && t_v.getBlockZ() == 0) // If it is the control block.
                    t_b.setMetadata(RPGConfig.MetaBuilding, new FixedMetadataValue(com.hiveofthoughts.mc.Main.GlobalMain, m_buildingName));
            }
            return true;
        }
    }
}
