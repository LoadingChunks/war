package com.tommytony.war;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import bukkit.tommytony.war.War;

import com.tommytony.war.volumes.Volume;

/**
 * 
 * @author tommytony
 *
 */
public class WarHub {
	private final War war;
	private Location location;
	private Volume volume;
	private List<Block> zoneGateBlocks = new ArrayList<Block>();
	
	public WarHub(War war, Location location) {
		this.war = war;
		this.location = location;
		this.volume = new Volume("warHub", war, location.getWorld());
	}

	public Volume getVolume() {
		return volume;
	}

	public void setLocation(Location loc) {
		this.location = loc;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public Warzone getDestinationWarzoneForLocation(Location playerLocation) {
		Warzone zone = null;
		for(Block gate : zoneGateBlocks) {
			if(gate.getX() == playerLocation.getBlockX()
					&& gate.getY() == playerLocation.getBlockY()
					&& gate.getZ() == playerLocation.getBlockZ()) {
				int zoneIndex = zoneGateBlocks.indexOf(gate);
				zone = war.getWarzones().get(zoneIndex);
			}
		}
		return zone;
	}

	public void initialize() {
		// for now, draw the wall of gates to the west
		zoneGateBlocks.clear();
		int noOfWarzones = war.getWarzones().size();
		if(noOfWarzones > 0) {
			int hubWidth = noOfWarzones * 4 + 1;
			int halfHubWidth = hubWidth / 2;
			int hubDepth = 5;
			int hubHeigth = 4;
			
			Block locationBlock = location.getWorld().getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
			volume.setCornerOne(locationBlock.getFace(BlockFace.SOUTH, halfHubWidth).getFace(BlockFace.DOWN));
			volume.setCornerTwo(locationBlock.getFace(BlockFace.NORTH, halfHubWidth).getFace(BlockFace.WEST, hubDepth).getFace(BlockFace.NORTH, hubHeigth));
			volume.saveBlocks();
			
			// draw gates
			Block currentGateBlock = locationBlock.getFace(BlockFace.SOUTH, halfHubWidth - 2).getFace(BlockFace.WEST, hubDepth);
			for(Warzone zone : war.getWarzones()) {	// gonna use the index to find it again
				zoneGateBlocks.add(currentGateBlock);
				currentGateBlock.setType(Material.PORTAL);
				currentGateBlock.getFace(BlockFace.UP).setType(Material.PORTAL);
				currentGateBlock.getFace(BlockFace.SOUTH).setType(Material.OBSIDIAN);
				currentGateBlock.getFace(BlockFace.NORTH).getFace(BlockFace.UP).setType(Material.OBSIDIAN);
				currentGateBlock.getFace(BlockFace.SOUTH).getFace(BlockFace.UP).getFace(BlockFace.UP).setType(Material.OBSIDIAN);
				currentGateBlock.getFace(BlockFace.NORTH).setType(Material.OBSIDIAN);
				currentGateBlock.getFace(BlockFace.SOUTH).getFace(BlockFace.UP).setType(Material.OBSIDIAN);
				currentGateBlock.getFace(BlockFace.NORTH).getFace(BlockFace.UP).getFace(BlockFace.UP).setType(Material.OBSIDIAN);
				currentGateBlock.getFace(BlockFace.UP).getFace(BlockFace.UP).setType(Material.OBSIDIAN);
				currentGateBlock = currentGateBlock.getFace(BlockFace.NORTH, 4);
			}
		}
	}
	
	public void resetSigns() {
		// TODO Signs
	}

	public void setVolume(Volume vol) {
		this.volume = vol;
	}

}
