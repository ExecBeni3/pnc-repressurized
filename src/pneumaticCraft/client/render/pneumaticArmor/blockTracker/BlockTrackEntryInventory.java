package pneumaticCraft.client.render.pneumaticArmor.blockTracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import pneumaticCraft.api.client.pneumaticHelmet.IBlockTrackEntry;
import pneumaticCraft.client.render.pneumaticArmor.HUDHandler;
import pneumaticCraft.common.util.PneumaticCraftUtils;
import pneumaticCraft.lib.Log;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class BlockTrackEntryInventory implements IBlockTrackEntry{

    public static Map tileEntityClassToNameMapping;
    private static List<String> invBlackList = new ArrayList<String>();// list of all inventories that could have crashed the helmet.

    public static void addTileEntityToBlackList(TileEntity te, Throwable e){
        e.printStackTrace();
        String title = te.getWorldObj().getBlock(te.xCoord, te.yCoord, te.zCoord).getLocalizedName();
        HUDHandler.instance().addMessage("Block tracking failed for " + title + "! A stacktrace can be found in the log.", new ArrayList<String>(), 60, 0xFFFF0000);
        invBlackList.add((String)BlockTrackEntryInventory.tileEntityClassToNameMapping.get(te.getClass()));
    }

    @Override
    public boolean shouldTrackWithThisEntry(IBlockAccess world, int x, int y, int z, Block block){
        if(tileEntityClassToNameMapping == null) {
            try {
                tileEntityClassToNameMapping = (Map)ReflectionHelper.findField(TileEntity.class, "field_145853_j", "classToNameMap").get(null);
            } catch(Exception e) {
                Log.error("[BlockTrackEntryInventory.class] Uhm reflection failed here!");
                e.printStackTrace();
            }
        }
        TileEntity te = world.getTileEntity(x, y, z);
        return te != null && !invBlackList.contains(tileEntityClassToNameMapping.get(te.getClass())) && te instanceof IInventory;
    }

    @Override
    public boolean shouldBeUpdatedFromServer(){
        return true;
    }

    @Override
    public int spamThreshold(){
        return 6;
    }

    @Override
    public void addInformation(World world, int x, int y, int z, List<String> infoList){
        IInventory inventory = (IInventory)world.getTileEntity(x, y, z);
        boolean empty = true;
        ItemStack[] inventoryStacks = new ItemStack[inventory.getSizeInventory()];
        for(int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack iStack = inventory.getStackInSlot(i);
            if(iStack != null) empty = false;
            inventoryStacks[i] = iStack;
        }
        if(empty) {
            infoList.add("Contents: Empty");
        } else {
            infoList.add("Contents:");
            PneumaticCraftUtils.sortCombineItemStacksAndToString(infoList, inventoryStacks);
        }
    }

    @Override
    public String getEntryName(){
        return "blockTracker.module.inventories";
    }
}
