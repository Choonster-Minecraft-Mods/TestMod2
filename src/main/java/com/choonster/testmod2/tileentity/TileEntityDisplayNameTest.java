package com.choonster.testmod2.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

// http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2404916-need-help-with-display-names
public class TileEntityDisplayNameTest extends TileEntity {
	private String displayName;

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		tagCompound.setString("displayName", displayName);
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		displayName = tagCompound.getString("displayName");
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
