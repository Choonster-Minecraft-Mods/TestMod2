package com.choonster.testmod2.compat.waila;

import com.choonster.testmod2.block.BlockCandyButton;
import com.choonster.testmod2.init.ModItems;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

public class HUDHandlerCandyButton implements IWailaDataProvider {

	/**
	 * Callback used to override the default Waila lookup system.</br>
	 * Will be used if the implementing class is registered via {@link IWailaRegistrar#registerStackProvider}.</br>
	 *
	 * @param accessor Contains most of the relevant information about the current environment.
	 * @param config   Current configuration of Waila.
	 * @return null if override is not required, an ItemStack otherwise.
	 */
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		if (accessor.getBlock() instanceof BlockCandyButton) {
			return new ItemStack(ModItems.candyButton, 1, ((BlockCandyButton) accessor.getBlock()).colourIndex + 1);
		}

		return null;
	}

	/**
	 * Callback used to add lines to one of the three sections of the tooltip (Head, Body, Tail).</br>
	 * Will be used if the implementing class is registered via {@link IWailaRegistrar#registerHeadProvider} client side.</br>
	 * You are supposed to always return the modified input currenttip.</br>
	 *
	 * @param itemStack  Current block scanned, in ItemStack form.
	 * @param currenttip Current list of tooltip lines (might have been processed by other providers and might be processed by other providers).
	 * @param accessor   Contains most of the relevant information about the current environment.
	 * @param config     Current configuration of Waila.
	 * @return Modified input currenttip
	 */
	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}

	/**
	 * Callback used to add lines to one of the three sections of the tooltip (Head, Body, Tail).</br>
	 * Will be used if the implementing class is registered via {@link IWailaRegistrar#registerBodyProvider} client side.</br>
	 * You are supposed to always return the modified input currenttip.</br>
	 *
	 * @param itemStack  Current block scanned, in ItemStack form.
	 * @param currenttip Current list of tooltip lines (might have been processed by other providers and might be processed by other providers).
	 * @param accessor   Contains most of the relevant information about the current environment.
	 * @param config     Current configuration of Waila.
	 * @return Modified input currenttip
	 */
	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}

	/**
	 * Callback used to add lines to one of the three sections of the tooltip (Head, Body, Tail).</br>
	 * Will be used if the implementing class is registered via {@link IWailaRegistrar#registerTailProvider} client side.</br>
	 * You are supposed to always return the modified input currenttip.</br>
	 *
	 * @param itemStack  Current block scanned, in ItemStack form.
	 * @param currenttip Current list of tooltip lines (might have been processed by other providers and might be processed by other providers).
	 * @param accessor   Contains most of the relevant information about the current environment.
	 * @param config     Current configuration of Waila.
	 * @return Modified input currenttip
	 */
	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}

	/**
	 * Callback used server side to return a custom synchronization NBTTagCompound.</br>
	 * Will be used if the implementing class is registered via {@link IWailaRegistrar#registerNBTProvider} server and client side.</br>
	 * You are supposed to always return the modified input NBTTagCompound tag.</br>
	 *
	 * @param player The player requesting data synchronization (The owner of the current connection).
	 * @param te     The TileEntity targeted for synchronization.
	 * @param tag    Current synchronization tag (might have been processed by other providers and might be processed by other providers).
	 * @param world  TileEntity's World.
	 * @param x      X position of the TileEntity.
	 * @param y      Y position of the TileEntity.
	 * @param z      Z position of the TileEntity.
	 * @return Modified input NBTTagCompound tag.
	 */
	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
		return tag;
	}
}
