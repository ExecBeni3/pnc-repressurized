package me.desht.pneumaticcraft.api.item;

import me.desht.pneumaticcraft.lib.NBTKeys;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;

import static me.desht.pneumaticcraft.common.tileentity.TileEntityReinforcedChest.NBT_ITEMS;

/**
 * Implement this interface for your items that have an inventory. When you don't have access to the item, just create
 * a class that implements this interface, and register an instance of it with
 * {@link IItemRegistry#registerInventoryItem(IInventoryItem)}.
 * This will then will be used in the Pneumatic Helmet's item search and item tooltip generation.
 */
public interface IInventoryItem {
    /**
     * @param stack Item that potentially has an inventory.
     * @param curStacks List of all currently added stacks for this item. Add more stacks in here in your implementation when found the right item.
     */
    void getStacksInItem(ItemStack stack, List<ItemStack> curStacks);

    /**
     * Get a header for the inventory list, for tooltip purposes.  Default return of null will not add any header.
     * @return a header string (can be a translation string), or null for no header
     */
    default ITextComponent getInventoryHeader() { return null; }

    /**
     * A String to prepend to outputted tooltip lines. Can be used to apply colouring, for example.
     *
     * @param stack the itemstack currently being added to the tooltip
     * @return a string prefix
     */
    default String getTooltipPrefix(ItemStack stack) { return ""; }

    /**
     * Convenience implementation for {@link IInventoryItem#getStacksInItem(ItemStack, List)} for items have been
     * dropped from a tile entity block with serialized data.
     *
     * @param stack the stack
     * @param curStacks a list of stacks to fill
     */
    static void getStacks(ItemStack stack, List<ItemStack> curStacks) {
        CompoundNBT sub = stack.getChildTag(NBTKeys.BLOCK_ENTITY_TAG);
        if (sub != null && sub.contains(NBT_ITEMS, Constants.NBT.TAG_COMPOUND)) {
            ItemStackHandler handler = new ItemStackHandler();
            handler.deserializeNBT(sub.getCompound(NBT_ITEMS));
            for (int i = 0; i < handler.getSlots(); i++) {
                if (!handler.getStackInSlot(i).isEmpty()) curStacks.add(handler.getStackInSlot(i));
            }
        }
    }
}
