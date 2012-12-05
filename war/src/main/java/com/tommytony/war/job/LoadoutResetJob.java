package com.tommytony.war.job;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import com.tommytony.war.Team;
import com.tommytony.war.Warzone;
import com.tommytony.war.config.TeamConfig;

public class LoadoutResetJob implements Runnable {

	private final Player player;
	private final Warzone zone;
	private final Team team;
	private final boolean isFirstRespawn;
	private final boolean isToggle;

	public LoadoutResetJob(Warzone zone, Team team, Player player, boolean isFirstRespawn, boolean isToggle) {
		this.zone = zone;
		this.team = team;
		this.player = player;
		this.isFirstRespawn = isFirstRespawn;
		this.isToggle = isToggle;
	}
	
	public void run() {
		this.zone.equipPlayerLoadoutSelection(player, team, isFirstRespawn, isToggle);
		// Change how this works, we're gonna make it a protection timer instead.
		player.addPotionEffect(PotionEffectType.REGENERATION.createEffect((int)(team.getTeamConfig().resolveInt(TeamConfig.SPAWNPROTECTIONTIME) * 20L * 5L), (team.getTeamConfig().resolveInt(TeamConfig.SPAWNPROTECTIONPOTENCY))));
		// Stop fire here, since doing it in the same tick as death doesn't extinguish it
		this.player.setFireTicks(0);
	}

}
