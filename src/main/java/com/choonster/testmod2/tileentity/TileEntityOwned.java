package com.choonster.testmod2.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.UUID;

// http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2424209-owned-blocks
public class TileEntityOwned extends TileEntity {
	private UUID ownerUUID;

	public UUID getOwnerUUID() {
		return ownerUUID;
	}

	public void setOwnerUUID(UUID ownerUUID) {
		this.ownerUUID = ownerUUID;
		markDirty();
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		UUID ownerUUID = getOwnerUUID();
		tagCompound.setLong("OwnerUUIDMost", ownerUUID.getMostSignificantBits());
		tagCompound.setLong("OwnerUUIDLeast", ownerUUID.getLeastSignificantBits());
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		setOwnerUUID(new UUID(tagCompound.getLong("OwnerUUIDMost"), tagCompound.getLong("OwnerUUIDLeast")));
	}
}
