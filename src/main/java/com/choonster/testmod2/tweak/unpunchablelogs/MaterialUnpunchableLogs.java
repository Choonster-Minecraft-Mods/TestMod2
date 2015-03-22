package com.choonster.testmod2.tweak.unpunchablelogs;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialUnpunchableLogs extends Material {
	public MaterialUnpunchableLogs() {
		super(MapColor.woodColor);
		setBurning();
		setRequiresTool();
	}
}
