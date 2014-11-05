package unwrittenfun.minecraft.unwrittenblocks.common.tileEntities;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import unwrittenfun.minecraft.unwrittenblocks.common.UnwrittenBlocks;
import unwrittenfun.minecraft.unwrittenblocks.common.items.ItemRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.network.NetworkRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.network.messages.TileEntityStackMessage;
import unwrittenfun.minecraft.unwrittenblocks.common.network.receivers.ITileEntityIntegerMessageReceiver;
import unwrittenfun.minecraft.unwrittenblocks.common.network.receivers.ITileEntityStackMessageReceiver;
import unwrittenfun.minecraft.unwrittenblocks.common.recipes.InfuserRecipes;

import java.util.ArrayList;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 04/11/2014.
 */
public class TEDarkInfuser extends TEConfigurableIO
    implements ITileEntityIntegerMessageReceiver, ITileEntityStackMessageReceiver {
  public int infuserTicks    = 1001;
  public int infuserMaxTicks = 1000;
  public int infuserProgress = 0; // Client only
  public EntityItem itemEntity;
  public float      itemYaw;
  private boolean loaded = false;

  public TEDarkInfuser() {
    items = new ItemStack[3];
  }

  @Override
  public ArrayList<Integer> getInputSlots() {
    ArrayList<Integer> inputs = new ArrayList<Integer>();
    inputs.add(0);
    return inputs;
  }

  @Override
  public ArrayList<Integer> getOutputSlots() {
    ArrayList<Integer> outputs = new ArrayList<Integer>();
    outputs.add(1);
    return outputs;
  }

  @Override
  public void onInventoryChanged() {
    if (hasWorldObj() && !worldObj.isRemote) {
      ItemStack stack = getStackInSlot(0);
      if (stack != null && InfuserRecipes.instance.getInfuserResult(stack) != null) {
        ItemStack infuserResult = InfuserRecipes.instance.getInfuserResult(stack);
        ItemStack resultSlotStack = getStackInSlot(1);

        infuserTicks = InfuserRecipes.instance.getInfuserTicks(stack);
        if (resultSlotStack == null || resultSlotStack.isItemEqual(infuserResult) &&
                                       (resultSlotStack.stackSize + infuserResult.stackSize) <=
                                       resultSlotStack.getMaxStackSize()) {
          if (infuserTicks > infuserMaxTicks) {
            infuserTicks = 0;
          }
        } else {
          infuserTicks = infuserMaxTicks + 1;
        }
      } else {
        infuserTicks = 1001;
        infuserMaxTicks = 1000;
      }

      NetworkRegister.wrapper
          .sendToDimension(TileEntityStackMessage.messageFrom(worldObj, xCoord, yCoord, zCoord, 0, getStackInSlot(0)),
                           worldObj.provider.dimensionId);
    }

    super.onInventoryChanged();
  }

  @Override
  public void updateEntity() {
    super.updateEntity();

    if (hasWorldObj()) {
      if (!worldObj.isRemote) { // We're on the server
        if (!loaded) {
          loaded = true;
          onInventoryChanged();
        }

        if (infuserTicks < infuserMaxTicks) {
          infuserTicks += getSpeedMultiplier();
          if (infuserTicks > infuserMaxTicks) infuserTicks = infuserMaxTicks;
        }

        if (infuserTicks == infuserMaxTicks && getStackInSlot(0) != null &&
            InfuserRecipes.instance.getInfuserResult(getStackInSlot(0)) != null) {
          ItemStack stack = getStackInSlot(0);
          ItemStack infuserResult = InfuserRecipes.instance.getInfuserResult(stack);
          ItemStack resultSlotStack = getStackInSlot(1);
          if (resultSlotStack == null || resultSlotStack.isItemEqual(infuserResult) &&
                                         (resultSlotStack.stackSize + infuserResult.stackSize) <=
                                         resultSlotStack.getMaxStackSize()) {
            stack.stackSize -= 1;
            if (stack.stackSize < 1) stack = null;
            if (resultSlotStack == null) {
              resultSlotStack = infuserResult.copy();
            } else {
              resultSlotStack.stackSize += infuserResult.stackSize;
            }
            items[0] = stack;
            items[1] = resultSlotStack;
            infuserMaxTicks = InfuserRecipes.instance.getInfuserTicks(getStackInSlot(0));
            infuserTicks = infuserMaxTicks + 1;
            onInventoryChanged();
          }
        }
      } else {
        if (itemEntity != null) itemEntity.rotationYaw = ++itemYaw;
      }
    }
  }

  @Override
  public void validate() {
    super.validate();
    //TODO: Request TE data
  }

  public int getSpeedMultiplier() {
    if (getStackInSlot(2) != null && getStackInSlot(2).isItemEqual(new ItemStack(ItemRegister.upgrade, 1, 1))) {
      return 1 + Math.min(getStackInSlot(2).stackSize, 8);
    }
    return 1;
  }

  @Override
  public void receiveIntegerMessage(byte id, int value) {
    UnwrittenBlocks.logger.info("Received integer message");
    switch (id) {
      case 0:
        infuserProgress = value;
        break;
    }
  }

  @Override
  public void receiveStackMessage(byte id, ItemStack stack) {
    UnwrittenBlocks.logger.info("Received stack message");
    switch (id) {
      case 0:
        if (stack == null) {
          itemEntity = null;
        } else {
          itemEntity = new EntityItem(worldObj, 0, 0, 0, stack);
          itemEntity.rotationYaw = itemYaw;
          itemEntity.hoverStart = 0;
        }
        break;
    }
  }
}