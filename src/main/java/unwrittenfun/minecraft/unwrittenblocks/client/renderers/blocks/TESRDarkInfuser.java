package unwrittenfun.minecraft.unwrittenblocks.client.renderers.blocks;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import unwrittenfun.minecraft.unwrittenblocks.client.models.ModelDarkInfuser;
import unwrittenfun.minecraft.unwrittenblocks.common.ModInfo;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TEDarkInfuser;

/**
 * Project: UnwrittenBlocks Author: UnwrittenFun Created: 04/11/2014.
 */
public class TESRDarkInfuser extends TileEntitySpecialRenderer {
  public static ResourceLocation texture =
      new ResourceLocation(ModInfo.RESOURCE_LOCATION, "textures/models/darkInfuser.png");

  public ModelDarkInfuser model;

  public TESRDarkInfuser(ModelDarkInfuser model) {
    this.model = model;
  }

  @Override
  public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTickTime) {
    if (tileEntity instanceof TEDarkInfuser) {
      renderDarkInfuser((TEDarkInfuser) tileEntity, x, y, z);
    }
  }

  public void renderDarkInfuser(TEDarkInfuser darkInfuser, double x, double y, double z) {
    GL11.glPushMatrix();

    GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
    GL11.glScalef(0.5F, 0.5F, 0.5F);
    bindTexture(texture);
    model.render(0.0625F);

    if (darkInfuser != null && darkInfuser.itemEntity != null) {
      GL11.glPushMatrix();
      GL11.glTranslatef(0F, 1F, 0F);
      RenderManager.instance
          .renderEntityWithPosYaw(darkInfuser.itemEntity, 0D, 0D, 0D, 0F, darkInfuser.itemEntity.rotationYaw);
      RenderItem.renderInFrame = false;
      GL11.glPopMatrix();
    }

    GL11.glPopMatrix();
  }
}
