package com.choonster.testmod2.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Miscellaneous debugging methods designed to be called from breakpoints.
 */
public class DebugMethods {

	/**
	 * Returns a string representation of an {@link ItemStack} using {@link ItemStack#toString()} and {@link ItemStack#hashCode()}.
	 *
	 * @param stack The {@link ItemStack}
	 * @return The string
	 */
	private static String formatItemStack(ItemStack stack) {
		if (stack == null) {
			return null;
		} else {
			return String.format("%s, %s", stack, stack.hashCode());
		}
	}

	/**
	 * Returns a string representation of the current call stack by skipping the two {@link DebugMethods} calls and using the first four calls after that.
	 *
	 * @return The stacktrace
	 */
	private static String getStackTrace() {
		StackTraceElement[] elements = new Exception().getStackTrace();
		return Stream.of(elements).skip(2).limit(4).map(stackTraceElement -> "\tat " + stackTraceElement).collect(Collectors.joining("\n"));
	}

	/**
	 * Called from {@link EntityPlayer#clearItemInUse()}
	 *
	 * @param oldStack The {@link ItemStack} that the player was previously using
	 * @return A message to print to the console
	 */
	public static String clearItemInUse(ItemStack oldStack) {
		return String.format("clearItemInUse - old: %s\n%s", formatItemStack(oldStack), getStackTrace());
	}

	/**
	 * Called from {@link EntityPlayer#setItemInUse(ItemStack, int)}
	 *
	 * @param oldStack The {@link ItemStack} that the player was previously using
	 * @param newStack The {@link ItemStack} that the player is now using
	 * @return A message to print to the console
	 */
	public static String setItemInUse(ItemStack oldStack, ItemStack newStack) {
		return String.format("setItemInUse - old: %s - new: %s\n%s", formatItemStack(oldStack), formatItemStack(newStack), getStackTrace());
	}

	/**
	 * Called from {@link EntityPlayer#onUpdate()}
	 *
	 * @param usingStack The {@link ItemStack} that the player is using
	 * @param heldStack  The {@link ItemStack} that the player is holding
	 * @return A message to print to the console
	 */
	public static String onUpdate(ItemStack usingStack, ItemStack heldStack) {
		return String.format("onUpdate - using: %s - held: %s\n%s", formatItemStack(usingStack), formatItemStack(heldStack), getStackTrace());
	}
}
