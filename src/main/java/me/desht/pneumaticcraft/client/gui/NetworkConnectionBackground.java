package me.desht.pneumaticcraft.client.gui;

import me.desht.pneumaticcraft.client.util.ProgressingLine;
import me.desht.pneumaticcraft.common.tileentity.TileEntitySecurityStation;
import me.desht.pneumaticcraft.lib.TileEntityConstants;

public class NetworkConnectionBackground extends NetworkConnectionHandler {

    NetworkConnectionBackground(GuiSecurityStationBase gui, TileEntitySecurityStation station, int baseX,
                                int baseY, int nodeSpacing, int color) {
        super(gui, station, baseX, baseY, nodeSpacing, color, TileEntityConstants.NETWORK_AI_BRIDGE_SPEED);
    }

    public NetworkConnectionBackground(NetworkConnectionBackground copy) {
        super(copy);
    }

    NetworkConnectionBackground(NetworkConnectionBackground copy, int baseX, int baseY) {
        super(copy, baseX, baseY);
    }

    @Override
    public void update() {
        for (int node = 0; node < 35; node++) {
            if (station.connects(node, node + 1)) {
                addConnection(node, node + 1);
            } else {
                removeConnection(node, node + 1);
            }
            if (station.connects(node, node - 4)) {
                addConnection(node, node - 4);
            } else {
                removeConnection(node, node - 4);
            }
            if (station.connects(node, node - 5)) {
                addConnection(node, node - 5);
            } else {
                removeConnection(node, node - 5);
            }
            if (station.connects(node, node - 6)) {
                addConnection(node, node - 6);
            } else {
                removeConnection(node, node - 6);
            }
        }
        for (ProgressingLine line : lineList)
            //Don't use the super update, as we don't want the load speed to be influenced by the node rating.
            line.incProgress(TileEntityConstants.NETWORK_NORMAL_BRIDGE_SPEED);
    }

}
