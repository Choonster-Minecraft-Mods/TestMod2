package com.choonster.testmod2.init;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids {
	public static Fluid collisionTest;
	public static Fluid staticFluid;

	public static void registerFluids() {
		collisionTest = registerFluid(new Fluid("collisionTest").setLuminosity(10).setDensity(800).setViscosity(1500).setUnlocalizedName("collisionTest"));
		staticFluid = registerFluid(new Fluid("static").setLuminosity(10).setDensity(800).setViscosity(1500).setUnlocalizedName("static"));
	}

	private static Fluid registerFluid(Fluid fluid) {
		if (!FluidRegistry.registerFluid(fluid)) {
			throw new IllegalStateException(String.format("Unable to register fluid %s", fluid.getID()));
		}

		return fluid;
	}
}
