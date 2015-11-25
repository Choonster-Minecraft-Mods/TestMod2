package com.choonster.testmod2.util;

import com.choonster.testmod2.Logger;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.StatisticsFile;

import java.lang.reflect.Field;

public class StatUtils {
	private static Field hasAchievementField = ReflectionHelper.findField(StatisticsFile.class, "field_150886_g");

	public static void forceStatsUpdate(EntityPlayerMP player) {
		try {
			StatisticsFile statisticsFile = player.getStatFile(); // Get the player's StatisticsFile
			hasAchievementField.set(statisticsFile, true); // Make it think that it has an unsynced achievement
			statisticsFile.func_150876_a(player); // Send the unsynced stats to the client
		} catch (IllegalAccessException e) {
			Logger.error(e, "Failed to send stats update");
		}
	}
}
