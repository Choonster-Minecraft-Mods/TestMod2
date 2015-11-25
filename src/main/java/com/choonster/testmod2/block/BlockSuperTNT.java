package com.choonster.testmod2.block;

import com.choonster.testmod2.TestMod2;
import com.choonster.testmod2.entity.EntitySuperTNTPrimed;
import net.minecraft.block.BlockTNT;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class BlockSuperTNT extends BlockTNT {
	public BlockSuperTNT() {
		super();
		setUnlocalizedName("supertnt");
		setTextureName("minecraft:tnt");
		setCreativeTab(TestMod2.tab);
	}

	// Does the same thing as super, but spawns EntitySuperTNTPrimed instead of EntityTNTPrimed
	@Override
	public void func_150114_a(World world, int x, int y, int z, int metadata, EntityLivingBase activatedBy) {
		if (!world.isRemote) {
			if ((metadata & 1) == 1) {
				EntitySuperTNTPrimed entitysupertntprimed = new EntitySuperTNTPrimed(world, (double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), activatedBy);
				world.spawnEntityInWorld(entitysupertntprimed);
				world.playSoundAtEntity(entitysupertntprimed, "game.tnt.primed", 1.0F, 1.0F);
			}
		}
	}
}
