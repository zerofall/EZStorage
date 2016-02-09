package com.zerofall.ezstorage.configuration;

import com.zerofall.ezstorage.EZStorage;

import net.minecraftforge.common.config.Configuration;

public class EZConfiguration {

	public static int basicCapacity;
	public static int condensedCapacity;
	public static int hyperCapacity;
	
	public static void syncConfig() {
		final Configuration config = EZStorage.config;
		config.load();
		
		final String OPTIONS = config.CATEGORY_GENERAL + config.CATEGORY_SPLITTER + "options";
		
		basicCapacity = config.getInt("Basic Storage Capacity", OPTIONS, 400, 100, 4000, "Basic");
		condensedCapacity = config.getInt("Condensed Storage Capacity", OPTIONS, 4000, 100, 40000, "Condensed");
		hyperCapacity = config.getInt("Hyper Storage Capacity", OPTIONS, 400000, 100, 4000000, "Hyper");
		if (config.hasChanged()) {
			config.save();
		}
	}
}
