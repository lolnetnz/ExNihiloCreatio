package exnihilocreatio.client.renderers;

import exnihilocreatio.ModBlocks;
import exnihilocreatio.blocks.BlockWaterwheel;
import exnihilocreatio.tiles.TileWaterwheel;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class RenderWaterwheel extends TileEntitySpecialRenderer<TileWaterwheel> {

    private static List<BakedQuad> quads;

    @Override
    public void render(TileWaterwheel tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if (quads==null)
        {
            final BlockRendererDispatcher blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
            IBlockState state = ModBlocks.watermill.getDefaultState().withProperty(BlockWaterwheel.IS_WHEEL, true);

            quads = blockRenderer.getModelForState(state).getQuads(state, null, 0);
        }

        Tessellator tessellator = Tessellator.getInstance();
        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y, z);
        GlStateManager.translate(0.5, 0.5, 0.5);

        GlStateManager.blendFunc(770, 771);
        GlStateManager.enableBlend();
        GlStateManager.disableCull();


        switch (tile.facing) {
            case DOWN:
                break;
            case UP:
                break;
            case NORTH:
                break;
            case SOUTH:
                GlStateManager.rotate(180, 0, 1, 0);
                break;
            case WEST:
                GlStateManager.rotate(90, 0, 1, 0);
                break;
            case EAST:
                GlStateManager.rotate(-90, 0, 1, 0);
                break;
            default:
                break;
        }

        if (tile.canTurn){
            tile.rotationValue = (tile.rotationValue + tile.perTickEffective) % 360;
        }

        GlStateManager.rotate(tile.rotationValue, 0, 0, 1);

        RenderHelper.disableStandardItemLighting();

        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        BufferBuilder worldRenderer = tessellator.getBuffer();

        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        // worldRenderer.setTranslation(-.5, -.5, -.5);

        RenderUtils.renderModelTESRFast(quads, worldRenderer, tile.getWorld(), tile.getPos());

        // worldRenderer.setTranslation(0, 0, 0);
        tessellator.draw();

        GlStateManager.popMatrix();
        RenderHelper.enableStandardItemLighting();

        GlStateManager.disableBlend();
        GlStateManager.enableCull();

    }

    private void renderWheel(TileWaterwheel te) {
        GlStateManager.pushMatrix();

        GlStateManager.translate(.5, 0.5, .5);

        switch (te.facing) {

            case DOWN:
                break;
            case UP:
                break;
            case NORTH:
                break;
            case SOUTH:
                GlStateManager.rotate(180, 0, 1, 0);
                break;
            case WEST:
                GlStateManager.rotate(90, 0, 1, 0);
                break;
            case EAST:
                GlStateManager.rotate(-90, 0, 1, 0);
                break;
            default:
                break;
        }

        if (te.canTurn){
            te.rotationValue = (te.rotationValue + te.perTickEffective) % 360;
        }

        GlStateManager.rotate(te.rotationValue, 0, 0, 1);

        World world = te.getWorld();
        // Translate back to local view coordinates so that we can do the acual rendering here
        GlStateManager.translate(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

        IBlockState state = ModBlocks.watermill.getDefaultState().withProperty(BlockWaterwheel.IS_WHEEL, true);

        BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        IBakedModel model = dispatcher.getModelForState(state);
        dispatcher.getBlockModelRenderer().renderModel(world, model, state, te.getPos(), bufferBuilder, true);
        tessellator.draw();

        // RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }
}