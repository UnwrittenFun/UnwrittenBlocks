package unwrittenfun.minecraft.unwrittenblocks.items

import net.minecraft.item.{EnumToolMaterial, EnumArmorMaterial, ItemStack, Item}
import cpw.mods.fml.common.registry.{GameRegistry, LanguageRegistry}
import net.minecraftforge.common.{EnumHelper, MinecraftForge}
import net.minecraft.block.Block
import unwrittenfun.minecraft.unwrittenblocks.recipes.InfuserRecipes
import cpw.mods.fml.client.registry.RenderingRegistry

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
object UBItems {
  final var armourMatDark: EnumArmorMaterial = EnumHelper.addArmorMaterial("DARK", 50, Array(4, 10, 8, 4), 50)
  var armourDarkHelm: Item = null
  var armourDarkChest: Item = null
  var armourDarkLeggings: Item = null
  var armourDarkBoots: Item = null

  final var toolMatDark: EnumToolMaterial = EnumHelper.addToolMaterial("DARK", 3, 5000, 25.0F, 6.0F, 50)
  var swordDarkInfused: Item = null
  var pickDarkInfused: Item = null
  var spadeDarkInfused: Item = null
  var axeDarkInfused: Item = null
  var hoeDarkInfused: Item = null
  var diamondDarkInfused: Item = null

  var pleatherBulb: Item = null
  var pleatherStrips: Item = null
  var gpsChip: Item = null
  var storageBall: Item = null
  var relayerTool: Item = null
  var upgrade: Item = null

  def registerItems() {
    pleatherBulb = new ItemPleatherBulb(PLEATHER_BULB_ID, PLEATHER_BULB_KEY)
    pleatherStrips = new ItemPleatherStrips(PLEATHER_STRIPS_ID, PLEATHER_STRIPS_KEY)
    gpsChip = new ItemGpsChip(GPS_CHIP_ID, GPS_CHIP_KEY)
    storageBall = new ItemStorageBall(STORAGE_BALL_ID, STORAGE_BALL_KEYS)
    relayerTool = new ItemRelayer(RELAYER_ID, RELAYER_KEY)
    upgrade = new ItemUpgrade(UPGRADE_ID, UPGRADE_KEYS)

    armourDarkHelm = new ItemDarkArmour(DARK_ARMOUR_IDS(0), DARK_ARMOUR_KEYS(0), 0)
    armourDarkChest = new ItemDarkArmour(DARK_ARMOUR_IDS(1), DARK_ARMOUR_KEYS(1), 1)
    armourDarkLeggings = new ItemDarkArmour(DARK_ARMOUR_IDS(2), DARK_ARMOUR_KEYS(2), 2)
    armourDarkBoots = new ItemDarkArmour(DARK_ARMOUR_IDS(3), DARK_ARMOUR_KEYS(3), 3)

    swordDarkInfused = new ItemDarkSword(DARK_SWORD_ID, DARK_SWORD_KEY)
    pickDarkInfused = new ItemDarkPick(DARK_PICK_ID, DARK_PICK_KEY)
    spadeDarkInfused = new ItemDarkSpade(DARK_SPADE_ID, DARK_SPADE_KEY)
    axeDarkInfused = new ItemDarkAxe(DARK_AXE_ID, DARK_AXE_KEY)
    hoeDarkInfused = new ItemDarkHoe(DARK_HOE_ID, DARK_HOE_KEY)
    diamondDarkInfused = new ItemDarkDiamond(DARK_DIAMOND_ID, DARK_DIAMOND_KEY)
  }


  def registerNames() {
    LanguageRegistry.addName(new ItemStack(pleatherBulb), PLEATHER_BULB_NAME)
    LanguageRegistry.addName(new ItemStack(pleatherStrips), PLEATHER_STRIPS_NAME)
    LanguageRegistry.addName(new ItemStack(gpsChip), GPS_CHIP_NAME)
    LanguageRegistry.addName(new ItemStack(gpsChip, 1, 1), GPS_CHIP_LINKED_NAME)
    LanguageRegistry.addName(new ItemStack(relayerTool), RELAYER_NAME)

    LanguageRegistry.addName(new ItemStack(armourDarkHelm), DARK_ARMOUR_NAMES(0))
    LanguageRegistry.addName(new ItemStack(armourDarkChest), DARK_ARMOUR_NAMES(1))
    LanguageRegistry.addName(new ItemStack(armourDarkLeggings), DARK_ARMOUR_NAMES(2))
    LanguageRegistry.addName(new ItemStack(armourDarkBoots), DARK_ARMOUR_NAMES(3))

    LanguageRegistry.addName(new ItemStack(swordDarkInfused), DARK_SWORD_NAME)
    LanguageRegistry.addName(new ItemStack(pickDarkInfused), DARK_PICK_NAME)
    LanguageRegistry.addName(new ItemStack(spadeDarkInfused), DARK_SPADE_NAME)
    LanguageRegistry.addName(new ItemStack(axeDarkInfused), DARK_AXE_NAME)
    LanguageRegistry.addName(new ItemStack(hoeDarkInfused), DARK_HOE_NAME)
    LanguageRegistry.addName(new ItemStack(diamondDarkInfused), DARK_DIAMOND_NAME)

    for (i <- 0 to (STORAGE_BALL_KEYS.length - 1)) {
      LanguageRegistry.addName(new ItemStack(storageBall, 1, i), STORAGE_BALL_NAMES(i))
    }

    for (i <- 0 to (UPGRADE_KEYS.length - 1)) {
      LanguageRegistry.addName(new ItemStack(upgrade, 1, i), UPGRADE_NAMES(i))
    }
  }

  def registerRecipes() {
    GameRegistry.addShapelessRecipe(new ItemStack(Item.leather, 1), pleatherStrips, pleatherStrips, pleatherStrips, pleatherStrips)

    GameRegistry.addRecipe(new ItemStack(gpsChip), "trt", "clc", "iri", 't'.asInstanceOf[Character], Block.torchRedstoneActive, 'l'.asInstanceOf[Character], Block.redstoneLampIdle, 'r'.asInstanceOf[Character], Item.redstone, 'i'.asInstanceOf[Character], Item.ingotIron, 'c'.asInstanceOf[Character], new ItemStack(Item.dyePowder, 1, 2))
    GameRegistry.addRecipe(new ItemStack(Item.blazeRod), "bb", "bb", "bb", 'b'.asInstanceOf[Character], Item.blazePowder)
    GameRegistry.addRecipe(new ItemStack(relayerTool), "dsd", "ese", " s ", 'd'.asInstanceOf[Character], Item.diamond, 's'.asInstanceOf[Character], Item.stick, 'e'.asInstanceOf[Character], Item.enderPearl)

    InfuserRecipes.addRecipe(storageBall.itemID, 0, new ItemStack(Item.enderPearl), 2000)
    InfuserRecipes.addRecipe(storageBall.itemID, 1, new ItemStack(Item.slimeBall), 1500)
    InfuserRecipes.addRecipe(storageBall.itemID, 2, new ItemStack(Item.blazePowder), 1000)
    GameRegistry.addShapelessRecipe(new ItemStack(Block.cobblestoneMossy, 5), new ItemStack(storageBall, 1, 0))
    GameRegistry.addShapelessRecipe(new ItemStack(Block.cobblestone, 5), new ItemStack(storageBall, 1, 1))
    GameRegistry.addShapelessRecipe(new ItemStack(Block.netherrack, 5), new ItemStack(storageBall, 1, 2))
    GameRegistry.addShapelessRecipe(new ItemStack(Block.wood, 5, 0), new ItemStack(storageBall, 1, 3))
    GameRegistry.addRecipe(new ItemStack(storageBall, 1, 0), " c ", "ccc", " c ", 'c'.asInstanceOf[Character], Block.cobblestoneMossy)
    GameRegistry.addRecipe(new ItemStack(storageBall, 1, 1), " c ", "ccc", " c ", 'c'.asInstanceOf[Character], Block.cobblestone)
    GameRegistry.addRecipe(new ItemStack(storageBall, 1, 2), " c ", "ccc", " c ", 'c'.asInstanceOf[Character], Block.netherrack)
    GameRegistry.addRecipe(new ItemStack(storageBall, 1, 3), " c ", "ccc", " c ", 'c'.asInstanceOf[Character], new ItemStack(Block.wood, 1, 0))

    GameRegistry.addRecipe(new ItemStack(upgrade, 1, 0), "nnn", "ndn", "nnn", 'n'.asInstanceOf[Character], Item.netherrackBrick, 'd'.asInstanceOf[Character], diamondDarkInfused)
    GameRegistry.addRecipe(new ItemStack(upgrade, 1, 1), "nsn", "sus", "nsn", 'n'.asInstanceOf[Character], Item.netherrackBrick, 's'.asInstanceOf[Character], Item.sugar, 'u'.asInstanceOf[Character], new ItemStack(upgrade, 1, 0))


    // Armour and tools
    InfuserRecipes.addRecipe(Item.helmetDiamond.itemID, 0, new ItemStack(armourDarkHelm), 5000)
    InfuserRecipes.addRecipe(Item.plateDiamond.itemID, 0, new ItemStack(armourDarkChest), 5000)
    InfuserRecipes.addRecipe(Item.legsDiamond.itemID, 0, new ItemStack(armourDarkLeggings), 5000)
    InfuserRecipes.addRecipe(Item.bootsDiamond.itemID, 0, new ItemStack(armourDarkBoots), 5000)
    InfuserRecipes.addRecipe(Item.swordDiamond.itemID, 0, new ItemStack(swordDarkInfused), 3000)
    InfuserRecipes.addRecipe(Item.pickaxeDiamond.itemID, 0, new ItemStack(pickDarkInfused), 3000)
    InfuserRecipes.addRecipe(Item.shovelDiamond.itemID, 0, new ItemStack(spadeDarkInfused), 3000)
    InfuserRecipes.addRecipe(Item.axeDiamond.itemID, 0, new ItemStack(axeDarkInfused), 3000)
    InfuserRecipes.addRecipe(Item.hoeDiamond.itemID, 0, new ItemStack(hoeDarkInfused), 3000)
    InfuserRecipes.addRecipe(Item.diamond.itemID, 0, new ItemStack(diamondDarkInfused), 2000)

    GameRegistry.addRecipe(new ItemStack(armourDarkHelm), "ddd", "d d", 'd'.asInstanceOf[Character], diamondDarkInfused)
    GameRegistry.addRecipe(new ItemStack(armourDarkChest), "d d", "ddd", "ddd", 'd'.asInstanceOf[Character], diamondDarkInfused)
    GameRegistry.addRecipe(new ItemStack(armourDarkLeggings), "ddd", "d d", "d d", 'd'.asInstanceOf[Character], diamondDarkInfused)
    GameRegistry.addRecipe(new ItemStack(armourDarkBoots), "d d", "d d", 'd'.asInstanceOf[Character], diamondDarkInfused)
    GameRegistry.addRecipe(new ItemStack(swordDarkInfused), " d ", " d ", " s ", 'd'.asInstanceOf[Character], diamondDarkInfused, 's'.asInstanceOf[Character], Item.stick)
    GameRegistry.addRecipe(new ItemStack(pickDarkInfused), "ddd", " s ", " s ", 'd'.asInstanceOf[Character], diamondDarkInfused, 's'.asInstanceOf[Character], Item.stick)
    GameRegistry.addRecipe(new ItemStack(spadeDarkInfused), " d ", " s ", " s ", 'd'.asInstanceOf[Character], diamondDarkInfused, 's'.asInstanceOf[Character], Item.stick)
    GameRegistry.addRecipe(new ItemStack(axeDarkInfused), " dd", " sd", " s ", 'd'.asInstanceOf[Character], diamondDarkInfused, 's'.asInstanceOf[Character], Item.stick)
    GameRegistry.addRecipe(new ItemStack(axeDarkInfused), "dd ", "ds ", " s ", 'd'.asInstanceOf[Character], diamondDarkInfused, 's'.asInstanceOf[Character], Item.stick)
    GameRegistry.addRecipe(new ItemStack(hoeDarkInfused), "dd ", " s ", " s ", 'd'.asInstanceOf[Character], diamondDarkInfused, 's'.asInstanceOf[Character], Item.stick)
    GameRegistry.addRecipe(new ItemStack(hoeDarkInfused), " dd", " s ", " s ", 'd'.asInstanceOf[Character], diamondDarkInfused, 's'.asInstanceOf[Character], Item.stick)
  }
}