package unwrittenfun.minecraft.unwrittenblocks.handlers

import java.io.{ByteArrayOutputStream, DataOutputStream, IOException}

import com.google.common.io.{ByteArrayDataInput, ByteStreams}
import cpw.mods.fml.common.network.{IPacketHandler, PacketDispatcher, Player}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Container
import net.minecraft.item.ItemStack
import net.minecraft.network.INetworkManager
import net.minecraft.network.packet.{Packet, Packet250CustomPayload}
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import net.minecraftforge.common.DimensionManager
import unwrittenfun.minecraft.unwrittenblocks.CHANNEL
import unwrittenfun.minecraft.unwrittenblocks.blocks.multiblocks.MultiblockWallTeleporter
import unwrittenfun.minecraft.unwrittenblocks.blocks.tileentities.TileEntityWallTeleporter
import unwrittenfun.minecraft.unwrittenblocks.gui.containers.ContainerWallTeleporter
import unwrittenfun.minecraft.unwrittenblocks.inventory.TCustomSidedInventory
import unwrittenfun.minecraft.unwrittenblocks.network.{TButtonPacketReceiver, PacketReceiver, TRequestPacketReceiver, TStackPacketReceiver}

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
object PacketHandler {
  def sendNewMaskPacket(id: Byte, teleporter: TileEntityWallTeleporter, player: Player) {
    val byteStream: ByteArrayOutputStream = new ByteArrayOutputStream
    val dataStream: DataOutputStream = new DataOutputStream(byteStream)
    try {
      dataStream writeByte 0
      dataStream writeByte id
      dataStream writeInt teleporter.mask(0)
      dataStream writeByte teleporter.mask(1)
      dataStream writeInt teleporter.xCoord
      dataStream writeInt teleporter.yCoord
      dataStream writeInt teleporter.zCoord
      if (player == null) PacketDispatcher.sendPacketToAllInDimension(PacketDispatcher.getPacket(CHANNEL, byteStream.toByteArray), teleporter.worldObj.provider.dimensionId)
      else PacketDispatcher.sendPacketToPlayer(PacketDispatcher.getPacket(CHANNEL, byteStream.toByteArray), player)
    }
    catch {
      case ex: IOException => System.err append "[UnwrittenBlocks] [Wall Teleporter] Failed to send new mask (" + id + ") packet"
    }
  }

  def onNewMaskPacket(reader: ByteArrayDataInput, world: World) {
    val id: Byte = reader.readByte
    val maskId: Int = reader.readInt
    val maskMeta: Byte = reader.readByte
    val x: Int = reader.readInt
    val y: Int = reader.readInt
    val z: Int = reader.readInt
    val tileEntity: TileEntity = world.getBlockTileEntity(x, y, z)
    id match {
      case 0 =>
        tileEntity match {
          case teleporter: TileEntityWallTeleporter =>
            teleporter.setMask(maskId, maskMeta)
            world.markBlockForRenderUpdate(x, y, z)
          case _ =>
        }
      case _ =>
    }
  }

  def requestMaskPacket(id: Byte, teleporter: TileEntityWallTeleporter) {
    val byteStream: ByteArrayOutputStream = new ByteArrayOutputStream
    val dataStream: DataOutputStream = new DataOutputStream(byteStream)
    try {
      dataStream writeByte 1
      dataStream writeByte id
      dataStream writeInt teleporter.xCoord
      dataStream writeInt teleporter.yCoord
      dataStream writeInt teleporter.zCoord
      PacketDispatcher sendPacketToServer PacketDispatcher.getPacket(CHANNEL, byteStream.toByteArray)
    }
    catch {
      case ex: IOException => System.err append "[UnwrittenBlocks] [Wall Teleporter] Failed to request mask (" + id + ") packet"
    }
  }

  def onRequestMaskPacket(reader: ByteArrayDataInput, world: World, player: Player) {
    val id: Byte = reader.readByte
    val x: Int = reader.readInt
    val y: Int = reader.readInt
    val z: Int = reader.readInt
    val tileEntity: TileEntity = world.getBlockTileEntity(x, y, z)
    id match {
      case 0 =>
        tileEntity match {
          case teleporter: TileEntityWallTeleporter => sendNewMaskPacket(0.toByte, teleporter, player)
          case _ =>
        }
      case _ =>
    }
  }

  def sendDestinationPacket(multiblock: MultiblockWallTeleporter, player: Player) {
    val byteStream: ByteArrayOutputStream = new ByteArrayOutputStream
    val dataStream: DataOutputStream = new DataOutputStream(byteStream)
    try {
      dataStream writeByte 3
      dataStream writeInt multiblock.controller.worldObj.provider.dimensionId
      dataStream writeInt multiblock.controller.xCoord
      dataStream writeInt multiblock.controller.yCoord
      dataStream writeInt multiblock.controller.zCoord
      dataStream writeInt multiblock.destinationWorldId
      dataStream writeFloat multiblock.destinationX
      dataStream writeFloat multiblock.destinationY
      dataStream writeFloat multiblock.destinationZ
      dataStream writeFloat multiblock.destinationRotation
      dataStream writeUTF multiblock.destinationWorldName
      if (player == null) PacketDispatcher.sendPacketToAllInDimension(PacketDispatcher.getPacket(CHANNEL, byteStream.toByteArray), multiblock.controller.worldObj.provider.dimensionId)
      else PacketDispatcher.sendPacketToPlayer(PacketDispatcher.getPacket(CHANNEL, byteStream.toByteArray), player)
    }
    catch {
      case ex: IOException => System.err append "[UnwrittenBlocks] [Wall Teleporter] Failed to send destination packet"
    }
  }

  def onDestinationPacket(reader: ByteArrayDataInput, player: EntityPlayer) {
    val cWorldId: Int = reader.readInt
    val cX: Int = reader.readInt
    val cY: Int = reader.readInt
    val cZ: Int = reader.readInt
    val dWorldId: Int = reader.readInt
    val dX: Float = reader.readFloat
    val dY: Float = reader.readFloat
    val dZ: Float = reader.readFloat
    val dR: Float = reader.readFloat
    val dWorldName: String = reader.readUTF
    if (player.worldObj.provider.dimensionId == cWorldId) {
      player.worldObj.getBlockTileEntity(cX, cY, cZ) match {
        case teleporter: TileEntityWallTeleporter => teleporter.multiblock.setDestination(dWorldName, dWorldId, dX, dY, dZ, dR)
        case _ =>
      }
    }
  }

  def requestMultiblockInfoPacket(teleporter: TileEntityWallTeleporter) {
    val byteStream: ByteArrayOutputStream = new ByteArrayOutputStream
    val dataStream: DataOutputStream = new DataOutputStream(byteStream)
    try {
      dataStream writeByte 4
      dataStream writeInt teleporter.worldObj.provider.dimensionId
      dataStream writeInt teleporter.xCoord
      dataStream writeInt teleporter.yCoord
      dataStream writeInt teleporter.zCoord
      PacketDispatcher sendPacketToServer PacketDispatcher.getPacket(CHANNEL, byteStream.toByteArray)
    }
    catch {
      case ex: IOException => System.err append "[UnwrittenBlocks] [Wall Teleporter] Failed to send destination packet"
    }
  }

  def onRequestMultiblockInfoPacket(reader: ByteArrayDataInput, player: Player) {
    val worldId: Int = reader.readInt
    val world: World = DimensionManager getWorld worldId
    val x: Int = reader.readInt
    val y: Int = reader.readInt
    val z: Int = reader.readInt
    world.getBlockTileEntity(x, y, z) match {
      case teleporter: TileEntityWallTeleporter =>
        sendDestinationPacket(teleporter.multiblock, player)
        sendLockedOrRotationPacket(0, teleporter.multiblock, player)
        sendLockedOrRotationPacket(1, teleporter.multiblock, player)
        sendTEIntegerPacket(teleporter, 0, teleporter.multiblock.getTrips, player)
      case _ =>
    }
  }

  def sendButtonPacket(buttonId: Int) {
    val byteStream: ByteArrayOutputStream = new ByteArrayOutputStream
    val dataStream: DataOutputStream = new DataOutputStream(byteStream)
    try {
      dataStream writeByte 5
      dataStream writeInt  buttonId
      PacketDispatcher sendPacketToServer PacketDispatcher.getPacket(CHANNEL, byteStream.toByteArray)
    }
    catch {
      case ex: IOException => System.err append "[UnwrittenBlocks] [Wall Teleporter] Failed to send button (" + buttonId + ") packet"
    }
  }

  def onButtonPacket(reader: ByteArrayDataInput, player: EntityPlayer) {
    val buttonId: Int = reader.readByte
    val container: Container = player.openContainer
    container match {
      case buttonContainer: TButtonPacketReceiver =>
        buttonContainer.onButtonPacket(buttonId)
    }
  }

  def sendLockedOrRotationPacket(id: Byte, multiblock: MultiblockWallTeleporter, player: Player) {
    val byteStream: ByteArrayOutputStream = new ByteArrayOutputStream
    val dataStream: DataOutputStream = new DataOutputStream(byteStream)
    try {
      dataStream writeByte 6
      dataStream writeByte id
      dataStream writeInt multiblock.controller.worldObj.provider.dimensionId
      dataStream writeInt multiblock.controller.xCoord
      dataStream writeInt multiblock.controller.yCoord
      dataStream writeInt multiblock.controller.zCoord
      id match {
        case 0 => dataStream writeBoolean multiblock.locked
        case 1 => dataStream writeBoolean multiblock.useRotation
        case _ =>
      }
      if (player == null) PacketDispatcher.sendPacketToAllInDimension(PacketDispatcher.getPacket(CHANNEL, byteStream.toByteArray), multiblock.controller.worldObj.provider.dimensionId)
      else PacketDispatcher.sendPacketToPlayer(PacketDispatcher.getPacket(CHANNEL, byteStream.toByteArray), player)
    }
    catch {
      case ex: IOException => System.err append "[UnwrittenBlocks] [Wall Teleporter] Failed to send locked packet"
    }
  }

  def onLockedOrRotationPacket(reader: ByteArrayDataInput, player: EntityPlayer) {
    val id: Byte = reader.readByte
    val cWorldId: Int = reader.readInt
    val cX: Int = reader.readInt
    val cY: Int = reader.readInt
    val cZ: Int = reader.readInt
    val bool: Boolean = reader.readBoolean
    if (player.worldObj.provider.dimensionId == cWorldId) {
      player.worldObj.getBlockTileEntity(cX, cY, cZ) match {
        case teleporter: TileEntityWallTeleporter =>
          id match {
            case 0 => teleporter.multiblock.locked = bool
            case 1 => teleporter.multiblock.useRotation = bool
            case _ =>
          }
        case _ =>
      }
    }
  }

  def sendTEIntegerPacket(tileEntity: TileEntity, id: Byte, integer: Int, player: Player) {
    val byteStream: ByteArrayOutputStream = new ByteArrayOutputStream
    val dataStream: DataOutputStream = new DataOutputStream(byteStream)
    try {
      dataStream writeByte 2
      dataStream writeByte id
      dataStream writeInt integer
      dataStream writeInt tileEntity.xCoord
      dataStream writeInt tileEntity.yCoord
      dataStream writeInt tileEntity.zCoord
      if (player == null) PacketDispatcher.sendPacketToAllInDimension(PacketDispatcher.getPacket(CHANNEL, byteStream.toByteArray), tileEntity.worldObj.provider.dimensionId)
      else PacketDispatcher.sendPacketToPlayer(PacketDispatcher.getPacket(CHANNEL, byteStream.toByteArray), player)
    }
    catch {
      case ex: IOException => System.err append "[UnwrittenBlocks] Failed to send integer packet"
    }
  }

  def onTEIntegerPacket(reader: ByteArrayDataInput, world: World) {
    val id: Byte = reader.readByte
    val integer: Int = reader.readInt
    val x: Int = reader.readInt
    val y: Int = reader.readInt
    val z: Int = reader.readInt
    world.getBlockTileEntity(x, y, z) match {
      case receiver: PacketReceiver =>
        receiver receiveIntPacket(id, integer)
      case _ =>
    }
  }

  def sendTEStackPacket(tileEntity: TileEntity, id: Byte, slot: Int, stack: ItemStack, player: Player) {
    val byteStream: ByteArrayOutputStream = new ByteArrayOutputStream
    val dataStream: DataOutputStream = new DataOutputStream(byteStream)
    try {
      dataStream writeByte 7
      dataStream writeByte id
      dataStream writeInt tileEntity.xCoord
      dataStream writeInt tileEntity.yCoord
      dataStream writeInt tileEntity.zCoord
      dataStream writeInt slot
      Packet.writeItemStack(stack, dataStream)
      if (player == null) PacketDispatcher.sendPacketToAllInDimension(PacketDispatcher.getPacket(CHANNEL, byteStream.toByteArray), tileEntity.worldObj.provider.dimensionId)
      else PacketDispatcher.sendPacketToPlayer(PacketDispatcher.getPacket(CHANNEL, byteStream.toByteArray), player)
    }
    catch {
      case ex: IOException => System.err append "[UnwrittenBlocks] Failed to send stack packet"
    }
  }

  def onTEStackPacket(reader: ByteArrayDataInput, world: World) {
    val id: Byte = reader.readByte
    val x: Int = reader.readInt
    val y: Int = reader.readInt
    val z: Int = reader.readInt
    val slot: Int = reader.readInt
    val stack: ItemStack = Packet.readItemStack(reader)
    world.getBlockTileEntity(x, y, z) match {
      case receiver: TStackPacketReceiver =>
        receiver.receiveStackPacket(id, slot, stack)
      case _ =>
    }
  }

  def requestTEPacket(tileEntity: TileEntity, id: Byte) {
    val byteStream: ByteArrayOutputStream = new ByteArrayOutputStream
    val dataStream: DataOutputStream = new DataOutputStream(byteStream)
    try {
      dataStream writeByte 8
      dataStream writeByte id
      dataStream writeInt tileEntity.xCoord
      dataStream writeInt tileEntity.yCoord
      dataStream writeInt tileEntity.zCoord
      PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket(CHANNEL, byteStream.toByteArray))
    }
    catch {
      case ex: IOException => System.err append "[UnwrittenBlocks] Failed to send TE request packet"
    }
  }

  def onRequestTEPacket(reader: ByteArrayDataInput, world: World, player: Player) {
    val id: Byte = reader.readByte
    val x: Int = reader.readInt
    val y: Int = reader.readInt
    val z: Int = reader.readInt
    world.getBlockTileEntity(x, y, z) match {
      case receiver: TRequestPacketReceiver =>
        receiver.receiveRequestPacket(id, player)
      case _ =>
    }
  }

  def sendSetIoPacket(tileEntity: TileEntity, side: Int, io: Int) {
    val byteStream: ByteArrayOutputStream = new ByteArrayOutputStream
    val dataStream: DataOutputStream = new DataOutputStream(byteStream)
    try {
      dataStream writeByte 9
      dataStream writeInt tileEntity.xCoord
      dataStream writeInt tileEntity.yCoord
      dataStream writeInt tileEntity.zCoord
      dataStream.writeInt(side)
      dataStream.writeInt(io)
      PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket(CHANNEL, byteStream.toByteArray))
    }
    catch {
      case ex: IOException => System.err append "[UnwrittenBlocks] Failed to send Set IO packet"
    }
  }

  def onSetIoPacket(reader: ByteArrayDataInput, world: World) {
    val x: Int = reader.readInt
    val y: Int = reader.readInt
    val z: Int = reader.readInt
    val side: Int = reader.readInt
    val io: Int = reader.readInt
    world.getBlockTileEntity(x, y, z) match {
      case receiver: TCustomSidedInventory =>
        receiver.receiveSetIOPacket(side, io)
      case _ =>
    }
  }
}

class PacketHandler extends IPacketHandler {
  def onPacketData(manager: INetworkManager, packet: Packet250CustomPayload, player: Player) {
    val reader: ByteArrayDataInput = ByteStreams newDataInput packet.data
    val entityPlayer: EntityPlayer = player.asInstanceOf[EntityPlayer]
    val packetId: Byte = reader.readByte

    packetId match {
      case 0 => PacketHandler.onNewMaskPacket(reader, entityPlayer.worldObj)
      case 1 => PacketHandler.onRequestMaskPacket(reader, entityPlayer.worldObj, player)
      case 2 => PacketHandler.onTEIntegerPacket(reader, entityPlayer.worldObj)
      case 3 => PacketHandler.onDestinationPacket(reader, entityPlayer)
      case 4 => PacketHandler.onRequestMultiblockInfoPacket(reader, player)
      case 5 => PacketHandler.onButtonPacket(reader, entityPlayer)
      case 6 => PacketHandler.onLockedOrRotationPacket(reader, entityPlayer)
      case 7 => PacketHandler.onTEStackPacket(reader, entityPlayer.worldObj)
      case 8 => PacketHandler.onRequestTEPacket(reader, entityPlayer.worldObj, player)
      case 9 => PacketHandler.onSetIoPacket(reader, entityPlayer.worldObj)
      case _ =>
    }
  }
}