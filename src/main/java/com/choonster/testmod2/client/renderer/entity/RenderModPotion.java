package com.choonster.testmod2.client.renderer.entity;

import com.choonster.testmod2.entity.EntityModPotion;
import com.choonster.testmod2.init.ModItems;
import com.choonster.testmod2.item.ItemModPotion;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * Adapted from {@link RenderSnowball} for use with {@link EntityModPotion}/{@link ItemModPotion}, but uses the liquid colour from the CustomPotionEffects tag list instead of the metadata.
 */
public class RenderModPotion extends Render {
	public static final RenderModPotion INSTANCE = new RenderModPotion();

	protected RenderModPotion() {
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float rotationYaw, float partialTicks) {
		doRender((EntityModPotion) entity, x, y, z, rotationYaw, partialTicks);
	}

	public void doRender(EntityModPotion entityModPotion, double x, double y, double z, float rotationYaw, float partialTicks) {
		ItemStack potionItemStack = entityModPotion.getPotion();

		IIcon iIcon = potionItemStack.getIconIndex();
		if (iIcon != null) {
			GL11.glPushMatrix();
			GL11.glTranslatef((float) x, (float) y, (float) z);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			this.bindEntityTexture(entityModPotion);
			Tessellator tessellator = Tessellator.instance;

			if (iIcon == ItemPotion.func_94589_d("bottle_splash")) {
				int colour = ModItems.potion.getColorFromItemStack(potionItemStack, 0);
				float red = (float) (colour >> 16 & 255) / 255.0F;
				float green = (float) (colour >> 8 & 255) / 255.0F;
				float blue = (float) (colour & 255) / 255.0F;
				GL11.glColor3f(red, green, blue);
				GL11.glPushMatrix();
				this.drawIcon(tessellator, ItemPotion.func_94589_d("overlay"));
				GL11.glPopMatrix();
				GL11.glColor3f(1.0F, 1.0F, 1.0F);
			}

			this.drawIcon(tessellator, iIcon);
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return TextureMap.locationItemsTexture;
	}

	private void drawIcon(Tessellator tessellator, IIcon iIcon) {
		float minU = iIcon.getMinU();
		float maxU = iIcon.getMaxU();
		float minV = iIcon.getMinV();
		float maxV = iIcon.getMaxV();
		float f4 = 1.0F;
		float f5 = 0.5F;
		float f6 = 0.25F;
		GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		tessellator.addVertexWithUV((double) (0.0F - f5), (double) (0.0F - f6), 0.0D, (double) minU, (double) maxV);
		tessellator.addVertexWithUV((double) (f4 - f5), (double) (0.0F - f6), 0.0D, (double) maxU, (double) maxV);
		tessellator.addVertexWithUV((double) (f4 - f5), (double) (f4 - f6), 0.0D, (double) maxU, (double) minV);
		tessellator.addVertexWithUV((double) (0.0F - f5), (double) (f4 - f6), 0.0D, (double) minU, (double) minV);
		tessellator.draw();
	}
}
