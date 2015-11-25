package com.choonster.testmod2.world.gen;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;

public class MapGenWrapper extends MapGenBase {
	private final MapGenBase base;
	private final ImmutableList<MapGenBase> generators;

	public MapGenWrapper(MapGenBase base, MapGenBase... generators) {
		this.base = base;
		this.generators = ImmutableList.copyOf(generators);
	}

	@Override
	public void generate(IChunkProvider chunkProvider, World world, int centreX, int centreZ, Block[] blocks) {
		base.generate(chunkProvider, world, centreX, centreZ, blocks);

		for (MapGenBase generator : generators) {
			generator.generate(chunkProvider, world, centreX, centreZ, blocks);
		}
	}
}
