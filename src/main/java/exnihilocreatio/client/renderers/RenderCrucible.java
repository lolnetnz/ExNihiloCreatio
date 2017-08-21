package exnihilocreatio.client.renderers;

import exnihilocreatio.texturing.Color;
import exnihilocreatio.texturing.SpriteColor;
import exnihilocreatio.tiles.TileCrucibleBase;
import exnihilocreatio.tiles.TileCrucibleStone;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class RenderCrucible extends TileEntitySpecialRenderer<TileCrucibleBase> {
    @Override
    public void render(TileCrucibleBase te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        Tessellator tes = Tessellator.getInstance();
        BufferBuilder wr = tes.getBuffer();


        RenderHelper.disableStandardItemLighting();
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        SpriteColor sprite = te.getSpriteAndColor();

        if (sprite != null) {
            TextureAtlasSprite icon = sprite.getSprite();
            double minU = (double) icon.getMinU();
            double maxU = (double) icon.getMaxU();
            double minV = (double) icon.getMinV();
            double maxV = (double) icon.getMaxV();

            // determine the tint for the fluid/block
            Color color = sprite.getColor();

            this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

            wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
            //wr.begin(GL11.GL_QUADS, new VertexFormat().addElement(DefaultVertexFormats.POSITION_3F).addElement(DefaultVertexFormats.COLOR_4UB).addElement(DefaultVertexFormats.NORMAL_3B));
            // Offset by bottome of crucibleStone, which is 4 pixels above the base of the block (and make it stop on pixel below the top)
            float fillAmount = ((12F / 16F) * te.getFilledAmount() + (4F / 16F)) * 0.9375F;

            wr.pos(0.125F, fillAmount, 0.125F).tex(minU, minV).color(color.r, color.g, color.b, color.a).normal(0, 1, 0).endVertex();
            wr.pos(0.125F, fillAmount, 0.875F).tex(minU, maxV).color(color.r, color.g, color.b, color.a).normal(0, 1, 0).endVertex();
            wr.pos(0.875F, fillAmount, 0.875F).tex(maxU, maxV).color(color.r, color.g, color.b, color.a).normal(0, 1, 0).endVertex();
            wr.pos(0.875F, fillAmount, 0.125F).tex(maxU, minV).color(color.r, color.g, color.b, color.a).normal(0, 1, 0).endVertex();

            tes.draw();
        }

        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
        RenderHelper.enableStandardItemLighting();

    }

}