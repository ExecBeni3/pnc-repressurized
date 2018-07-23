package me.desht.pneumaticcraft.common.block;

import me.desht.pneumaticcraft.common.GuiHandler.EnumGuiId;
import me.desht.pneumaticcraft.common.tileentity.TileEntityAdvancedAirCompressor;
import net.minecraft.tileentity.TileEntity;

public class BlockAdvancedAirCompressor extends BlockAirCompressor {

    public BlockAdvancedAirCompressor() {
        super("advanced_air_compressor");
    }

    @Override
    public EnumGuiId getGuiID() {
        return EnumGuiId.ADVANCED_AIR_COMPRESSOR;
    }

    @Override
    protected Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityAdvancedAirCompressor.class;
    }

}
