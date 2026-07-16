package se.mickelus.tetra.blocks.workbench.action;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import se.mickelus.mutil.network.BlockPosPacket;
import se.mickelus.tetra.blocks.workbench.WorkbenchTile;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class WorkbenchActionPacket extends BlockPosPacket {

    private String actionKey;

    public WorkbenchActionPacket() {
    }

    public WorkbenchActionPacket(BlockPos pos, String actionKey) {
        super(pos);
        this.actionKey = actionKey;
    }

    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        super.toBytes(buffer);
        writeString(actionKey, buffer);
    }

    @Override
    public void fromBytes(FriendlyByteBuf buffer) {
        super.fromBytes(buffer);
        actionKey = readString(buffer);
    }

    @Override
    public void handle(Player player) {
        WorkbenchTile workbench = (WorkbenchTile) player.level().getBlockEntity(pos);
        if (workbench != null) {
            workbench.performAction(player, actionKey);
        }
    }
}
