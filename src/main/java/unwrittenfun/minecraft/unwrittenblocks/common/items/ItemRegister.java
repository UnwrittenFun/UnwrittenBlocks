package unwrittenfun.minecraft.unwrittenblocks.common.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import unwrittenfun.minecraft.unwrittenblocks.common.blocks.BlockRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.recipes.InfuserRecipes;
import unwrittenfun.minecraft.unwrittenblocks.common.recipes.StorageBallRecipe;
import unwrittenfun.minecraft.unwrittenblocks.common.recipes.StorageBallReturnRecipe;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 29/08/2014.
 */

public class ItemRegister {
  public static final ItemArmor.ArmorMaterial darkInfusedArmourMaterial =
      EnumHelper.addArmorMaterial("DARK_INFUSED", 50, new int[]{4, 10, 8, 4}, 50);
  public static final Item.ToolMaterial darkInfusedToolMaterial =
      EnumHelper.addToolMaterial("DARK", 3, 5000, 25.0F, 6.0F, 50);

  public static final String[] DARK_INFUSED_ARMOUR_KEYS =
      new String[]{"darkInfusedHelm", "darkInfusedChest", "darkInfusedLeggings", "darkInfusedBoots"};

  public static final String DARK_INFUSED_SWORD_KEY = "darkInfusedSword";
  public static final String DARK_INFUSED_PICK_KEY = "darkInfusedPick";
  public static final String DARK_INFUSED_AXE_KEY = "darkInfusedAxe";
  public static final String DARK_INFUSED_SPADE_KEY = "darkInfusedSpade";
  public static final String DARK_INFUSED_HOE_KEY = "darkInfusedHoe";

  public static final String DARK_INFUSED_DIAMOND = "darkInfusedDiamond";
  public static final String STORAGE_BALL_KEY = "storageBall";
  public static final String STORAGE_BALL_CONTAINER_KEY = "storageBallContainer";
  public static final String PLEATHER_STRIPS_KEY = "pleatherStrips";
  public static final String PLEATHER_BULB_KEY = "pleatherBulb";

  public static final String UPGRADE_KEY = "upgrade";
  public static final String[] UPGRADE_TYPES = new String[]{"upgradeBasic", "upgradeSpeed"};

  public static ItemDarkInfusedArmour darkInfusedHelm;
  public static ItemDarkInfusedArmour darkInfusedChest;
  public static ItemDarkInfusedArmour darkInfusedLeggings;
  public static ItemDarkInfusedArmour darkInfusedBoots;

  public static ItemDarkInfusedSword darkInfusedSword;
  public static ItemDarkInfusedPick darkInfusedPick;
  public static ItemDarkInfusedAxe darkInfusedAxe;
  public static ItemDarkInfusedSpade darkInfusedSpade;
  public static ItemDarkInfusedHoe darkInfusedHoe;

  public static ItemDarkInfusedDiamond darkInfusedDiamond;
  public static ItemStorageBall storageBall;
  public static ItemStorageBallContainer storageBallContainer;
  public static ItemUpgrade upgrade;
  public static ItemPleatherStrips pleatherStrips;
  public static ItemPleatherBulb pleatherBulb;

  // For comparing
  public static ItemStack stackPleatherBulb, stackStorageBallContainer, stackStorageBall, stackSpeedUpgrade;

  public static void registerItems() {
    darkInfusedHelm = new ItemDarkInfusedArmour(0);
    darkInfusedChest = new ItemDarkInfusedArmour(1);
    darkInfusedLeggings = new ItemDarkInfusedArmour(2);
    darkInfusedBoots = new ItemDarkInfusedArmour(3);

    darkInfusedSword = new ItemDarkInfusedSword(DARK_INFUSED_SWORD_KEY);
    darkInfusedPick = new ItemDarkInfusedPick(DARK_INFUSED_PICK_KEY);
    darkInfusedAxe = new ItemDarkInfusedAxe(DARK_INFUSED_AXE_KEY);
    darkInfusedSpade = new ItemDarkInfusedSpade(DARK_INFUSED_SPADE_KEY);
    darkInfusedHoe = new ItemDarkInfusedHoe(DARK_INFUSED_HOE_KEY);

    storageBall = new ItemStorageBall();
    storageBallContainer = new ItemStorageBallContainer(STORAGE_BALL_CONTAINER_KEY);
    upgrade = new ItemUpgrade(UPGRADE_KEY, UPGRADE_TYPES);
    pleatherStrips = new ItemPleatherStrips(PLEATHER_STRIPS_KEY);
    pleatherBulb = new ItemPleatherBulb(PLEATHER_BULB_KEY);
    darkInfusedDiamond = new ItemDarkInfusedDiamond(DARK_INFUSED_DIAMOND);

    GameRegistry.registerItem(darkInfusedHelm, DARK_INFUSED_ARMOUR_KEYS[0]);
    GameRegistry.registerItem(darkInfusedChest, DARK_INFUSED_ARMOUR_KEYS[1]);
    GameRegistry.registerItem(darkInfusedLeggings, DARK_INFUSED_ARMOUR_KEYS[2]);
    GameRegistry.registerItem(darkInfusedBoots, DARK_INFUSED_ARMOUR_KEYS[3]);

    GameRegistry.registerItem(darkInfusedSword, DARK_INFUSED_SWORD_KEY);
    GameRegistry.registerItem(darkInfusedPick, DARK_INFUSED_PICK_KEY);
    GameRegistry.registerItem(darkInfusedAxe, DARK_INFUSED_AXE_KEY);
    GameRegistry.registerItem(darkInfusedSpade, DARK_INFUSED_SPADE_KEY);
    GameRegistry.registerItem(darkInfusedHoe, DARK_INFUSED_HOE_KEY);

    GameRegistry.registerItem(darkInfusedDiamond, DARK_INFUSED_DIAMOND);
    GameRegistry.registerItem(storageBall, STORAGE_BALL_KEY);
    GameRegistry.registerItem(storageBallContainer, STORAGE_BALL_CONTAINER_KEY);
    GameRegistry.registerItem(upgrade, UPGRADE_KEY);
    GameRegistry.registerItem(pleatherStrips, PLEATHER_STRIPS_KEY);
    GameRegistry.registerItem(pleatherBulb, PLEATHER_BULB_KEY);

    StorageBallRegistry.addStorageBall(new ItemStack(Blocks.stone));
    StorageBallRegistry.addStorageBall(new ItemStack(Blocks.dirt));
    StorageBallRegistry.addStorageBall(new ItemStack(Blocks.cobblestone));
    StorageBallRegistry.addStorageBall(new ItemStack(Blocks.planks, 1, 0));
    StorageBallRegistry.addStorageBall(new ItemStack(Blocks.planks, 1, 1));
    StorageBallRegistry.addStorageBall(new ItemStack(Blocks.planks, 1, 2));
    StorageBallRegistry.addStorageBall(new ItemStack(Blocks.planks, 1, 3));
    StorageBallRegistry.addStorageBall(new ItemStack(Blocks.planks, 1, 4));
    StorageBallRegistry.addStorageBall(new ItemStack(Blocks.planks, 1, 5));
    StorageBallRegistry.addStorageBall(new ItemStack(Blocks.sand));
    StorageBallRegistry.addStorageBall(new ItemStack(Blocks.gravel));
    StorageBallRegistry.addStorageBall(new ItemStack(Blocks.log, 1, 0));
    StorageBallRegistry.addStorageBall(new ItemStack(Blocks.log, 1, 1));
    StorageBallRegistry.addStorageBall(new ItemStack(Blocks.log, 1, 2));
    StorageBallRegistry.addStorageBall(new ItemStack(Blocks.log, 1, 3));
    StorageBallRegistry.addStorageBall(new ItemStack(Blocks.log2, 1, 0));
    StorageBallRegistry.addStorageBall(new ItemStack(Blocks.log2, 1, 1));
    StorageBallRegistry.addStorageBall(new ItemStack(Blocks.glass));
    StorageBallRegistry.addStorageBall(new ItemStack(Blocks.sandstone));
    StorageBallRegistry.addStorageBall(new ItemStack(Blocks.brick_block));
    StorageBallRegistry.addStorageBall(new ItemStack(Blocks.mossy_cobblestone));
    StorageBallRegistry.addStorageBall(new ItemStack(Blocks.clay));
    StorageBallRegistry.addStorageBall(new ItemStack(Blocks.netherrack));
    StorageBallRegistry.addStorageBall(new ItemStack(Blocks.soul_sand));
    StorageBallRegistry.addStorageBall(new ItemStack(Blocks.stonebrick));
    StorageBallRegistry.addStorageBall(new ItemStack(Blocks.quartz_block));
    StorageBallRegistry.addStorageBall(new ItemStack(Blocks.end_stone));
    StorageBallRegistry.addStorageBall(new ItemStack(Blocks.nether_brick));

    stackPleatherBulb = new ItemStack(pleatherBulb);
    stackStorageBallContainer = new ItemStack(storageBallContainer);
    stackStorageBall = new ItemStack(storageBall); // FOR COMPARING ONLY
    stackSpeedUpgrade = new ItemStack(upgrade, 1, 1);
  }

  public static void registerRecipes() {
    GameRegistry.addRecipe(new StorageBallRecipe());
    GameRegistry.addRecipe(new StorageBallReturnRecipe());

    GameRegistry.addRecipe(new ItemStack(Items.leather), "pp", "pp", 'p', pleatherStrips);
    GameRegistry.addRecipe(new ItemStack(storageBallContainer, 64), " g ", "g g", " g ", 'g', Blocks.glass_pane);

    GameRegistry.addRecipe(new ItemStack(Items.blaze_rod), "bb", "bb", "bb", 'b', Items.blaze_powder);

    GameRegistry.addRecipe(new ItemStack(upgrade, 1, 0), "nnn", "ndn", "nnn", 'n', Items.netherbrick, 'd', darkInfusedDiamond);
    GameRegistry.addRecipe(new ItemStack(upgrade, 1, 1), "nsn", "sus", "nsn", 'n', Items.netherbrick, 's', Items.sugar, 'u', new ItemStack(upgrade, 1, 0));

    InfuserRecipes.instance.addRecipe(StorageBallRegistry.getBallFromContainer(new ItemStack(Blocks.end_stone)), new ItemStack(Items.ender_pearl), 1500);
    InfuserRecipes.instance.addRecipe(StorageBallRegistry.getBallFromContainer(new ItemStack(Blocks.mossy_cobblestone)), new ItemStack(Items.slime_ball), 1000);
    InfuserRecipes.instance.addRecipe(StorageBallRegistry.getBallFromContainer(new ItemStack(Blocks.nether_brick)), new ItemStack(Items.blaze_powder), 1200);

    InfuserRecipes.instance.addRecipe(new ItemStack(Items.diamond), new ItemStack(darkInfusedDiamond), 2000);
    InfuserRecipes.instance.addRecipe(new ItemStack(Items.diamond_helmet), new ItemStack(darkInfusedHelm), 8000);
    InfuserRecipes.instance.addRecipe(new ItemStack(Items.diamond_chestplate), new ItemStack(darkInfusedChest), 12800);
    InfuserRecipes.instance.addRecipe(new ItemStack(Items.diamond_leggings), new ItemStack(darkInfusedLeggings), 11200);
    InfuserRecipes.instance.addRecipe(new ItemStack(Items.diamond_boots), new ItemStack(darkInfusedBoots), 6400);
    InfuserRecipes.instance.addRecipe(new ItemStack(Items.diamond_sword), new ItemStack(darkInfusedSword), 3200);
    InfuserRecipes.instance.addRecipe(new ItemStack(Items.diamond_pickaxe), new ItemStack(darkInfusedPick), 4800);
    InfuserRecipes.instance.addRecipe(new ItemStack(Items.diamond_shovel), new ItemStack(darkInfusedSpade), 1600);
    InfuserRecipes.instance.addRecipe(new ItemStack(Items.diamond_axe), new ItemStack(darkInfusedAxe), 4800);
    InfuserRecipes.instance.addRecipe(new ItemStack(Items.diamond_hoe), new ItemStack(darkInfusedHoe), 3200);

    GameRegistry.addRecipe(new ItemStack(darkInfusedHelm), "ddd", "d d", 'd', darkInfusedDiamond);
    GameRegistry.addRecipe(new ItemStack(darkInfusedChest), "d d", "ddd", "ddd", 'd', darkInfusedDiamond);
    GameRegistry.addRecipe(new ItemStack(darkInfusedLeggings), "ddd", "d d", "d d", 'd', darkInfusedDiamond);
    GameRegistry.addRecipe(new ItemStack(darkInfusedBoots), "d d", "d d", 'd', darkInfusedDiamond);
    GameRegistry.addRecipe(new ItemStack(darkInfusedSword), " d ", " d ", " s ", 'd', darkInfusedDiamond, 's', Items.stick);
    GameRegistry.addRecipe(new ItemStack(darkInfusedPick), "ddd", " s ", " s ", 'd', darkInfusedDiamond, 's', Items.stick);
    GameRegistry.addRecipe(new ItemStack(darkInfusedSpade), " d ", " s ", " s ", 'd', darkInfusedDiamond, 's', Items.stick);
    GameRegistry.addRecipe(new ItemStack(darkInfusedAxe), " dd", " sd", " s ", 'd', darkInfusedDiamond, 's', Items.stick);
    GameRegistry.addRecipe(new ItemStack(darkInfusedHoe), "dd ", " s ", " s ", 'd', darkInfusedDiamond, 's', Items.stick);
  }
}
