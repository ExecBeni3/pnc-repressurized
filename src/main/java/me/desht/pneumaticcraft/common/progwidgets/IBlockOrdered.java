package me.desht.pneumaticcraft.common.progwidgets;

import net.minecraft.client.resources.I18n;

public interface IBlockOrdered {
    enum EnumOrder {
        CLOSEST("closest"), LOW_TO_HIGH("lowToHigh"), HIGH_TO_LOW("highToLow");
        public final String name;

        EnumOrder(String name) {
            this.name = name;
        }

        public String getLocalizedName() {
            return I18n.format("pneumaticcraft.gui.progWidget.blockOrder." + this);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    EnumOrder getOrder();

    void setOrder(EnumOrder order);
}
