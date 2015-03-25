package unwrittenfun.minecraft.unwrittenblocks.common.blocks;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import unwrittenfun.minecraft.unwrittenblocks.common.ModInfo;
import unwrittenfun.minecraft.unwrittenblocks.common.UnwrittenBlocks;
import unwrittenfun.minecraft.unwrittenblocks.common.helpers.InventoryHelpers;
import unwrittenfun.minecraft.unwrittenblocks.common.items.ItemRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.IWallTeleporterBlock;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TEWallTeleporter;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TEWallTeleporterBase;

/**
 * Author: James Birtles
 */
public class BlockWallTeleporterBase extends BlockContainer {
  public BlockWallTeleporterBase(String key) {
    super(Material.iron);
    setCreativeTab(UnwrittenBlocks.creativeTabUB);
    setBlockName(key);
    setBlockTextureName(ModInfo.RESOURCE_LOCATION + ":" + key);
    setHardness(2F);
  }

  public static IIcon[] icons;

  @Override
  public void registerBlockIcons(IIconRegister register) {
    icons = new IIcon[40];

    icons[0] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":wallTeleporter_0");
    icons[1] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":wallTeleporter_1");
    icons[2] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":wallTeleporter_2");
    icons[3] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":wallTeleporter_3");
    icons[4] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":wallTeleporter_4");
    icons[5] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":wallTeleporter_5");
    icons[6] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":wallTeleporter_6");
    icons[7] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":wallTeleporter_7");
    icons[8] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":wallTeleporter_8");
    icons[9] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":wallTeleporter_9");
    icons[10] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":wallTeleporter_10");
    icons[11] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":wallTeleporter_11");
    icons[12] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":wallTeleporter_12");
    icons[13] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":wallTeleporter_13");
    icons[14] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":wallTeleporter_14");
    icons[15] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":wallTeleporter_15");

    blockIcon = icons[0];
  }

  @Override
  public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
    TEWallTeleporter teleporter = (TEWallTeleporter) world.getTileEntity(x, y, z);
    ItemStack mask = teleporter.mask;
    if (mask.isItemEqual(ItemRegister.wallStack)) {
      int connectedSides = 0; // Left 1, Right 2, Up 4, Down 8

      ForgeDirection direction = ForgeDirection.getOrientation(side);
      if (direction.offsetX == 0) {
        for (int dx = -1; dx <= 1; dx += 2) {
          connectedSides = connectedSides | BlockWallTeleporterBase.connectedSidesForDelta(side, world, x, y, z, dx, 0, 0);
        }
      }

      if (direction.offsetY == 0) {
        for (int dy = -1; dy <= 1; dy += 2) {
          connectedSides = connectedSides | BlockWallTeleporterBase.connectedSidesForDelta(side, world, x, y, z, 0, dy, 0);
        }
      }

      if (direction.offsetZ == 0) {
        for (int dz = -1; dz <= 1; dz += 2) {
          connectedSides = connectedSides | BlockWallTeleporterBase.connectedSidesForDelta(side, world, x, y, z, 0, 0, dz);
        }
      }

      System.out.println(connectedSides);
      return BlockWallTeleporterBase.icons[connectedSides];

    } else {
      Block block = Block.getBlockFromItem(mask.getItem());
      return block.getIcon(side, mask.getItemDamage());
    }
  }

  public static int connectedSidesForDelta(int side, IBlockAccess world, int x, int y, int z, int dx, int dy, int dz) {
    int connectedSides = 0;
    TileEntity tileEntity = world.getTileEntity(x + dx, y + dy, z + dz);
    if (tileEntity instanceof TEWallTeleporter) {
      TEWallTeleporter wall = (TEWallTeleporter) tileEntity;
      if (wall.mask.isItemEqual(ItemRegister.wallStack)) {
        if ((dx == 1 && side == 2) || (dx == -1 && (side == 3 || side == 1 || side == 0))
            || (dz == 1 && side == 5) || (dz == -1 && side == 4)) {
          connectedSides = connectedSides | 1;
        } else if ((dx == -1 && side == 2) || (dx == 1 && (side == 3 || side == 1 || side == 0))
            || (dz == -1 && side == 5) || (dz == 1 && side == 4)) {
          connectedSides = connectedSides | 2;
        }

        if (dy == -1 || (dz == 1 && (side == 1 || side == 0))) {
          connectedSides = connectedSides | 8;
        } else if (dy == 1 || (dz == -1 && (side == 1 || side == 0))) {
          connectedSides = connectedSides | 4;
        }
      }
    }
    return connectedSides;
  }

  @Override
  public boolean isOpaqueCube() {
    return false;
  }

  @Override
  public boolean renderAsNormalBlock() {
    return false;
  }

  @Override
  public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
    TEWallTeleporterBase teleporterBase = (TEWallTeleporterBase) world.getTileEntity(x, y, z);
    if (teleporterBase != null && teleporterBase.getWTNetwork().hasDestination() && teleporterBase.getWTNetwork().fuel > 0)
      return AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0);
    return super.getCollisionBoundingBoxFromPool(world, x, y, z);
  }

  @Override
  public TileEntity createNewTileEntity(World world, int idk) {
    return new TEWallTeleporterBase();
  }

  @Override
  public boolean canPlaceBlockAt(World world, int x, int y, int z) {
    for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
      TileEntity tileEntity = world.getTileEntity(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ);
      if (tileEntity instanceof IWallTeleporterBlock) {
        IWallTeleporterBlock teleporter = (IWallTeleporterBlock) tileEntity;
        if (teleporter.hasWTNetwork()) {
          return false;
        }
      }
    }
    return super.canPlaceBlockAt(world, x, y, z);
  }

  @Override
  public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
    if (!player.isSneaking() && player.getHeldItem() == null) { // TODO: Also check if mask is locked
      FMLNetworkHandler.openGui(player, UnwrittenBlocks.instance, 0, world, x, y, z);
      return true;
    }

    if (player.getHeldItem().getItem() instanceof ItemBlock) {
      TEWallTeleporterBase teleporterBase = (TEWallTeleporterBase) world.getTileEntity(x, y, z);

      if (!(player.getHeldItem().isItemEqual(ItemRegister.wallStack) && teleporterBase.mask.isItemEqual(ItemRegister.wallStack))) {
        if (!world.isRemote) teleporterBase.setMask(player.getHeldItem().copy());
        return true;
      }
    }

    return false;
  }

  @Override
  public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
    if (!world.isRemote) {
      if (entity instanceof EntityPlayerMP) {
        TEWallTeleporterBase teleporterBase = (TEWallTeleporterBase) world.getTileEntity(x, y, z);
        if (teleporterBase.hasWTNetwork()) teleporterBase.getWTNetwork().playerCollided(world, (EntityPlayerMP) entity);
      }
    }
    super.onEntityCollidedWithBlock(world, x, y, z, entity);
  }

  @Override
  public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
    if (!world.isRemote) {
      InventoryHelpers.dropInventory(world, x, y, z);
    }
    super.breakBlock(world, x, y, z, block, meta);
  }
}
