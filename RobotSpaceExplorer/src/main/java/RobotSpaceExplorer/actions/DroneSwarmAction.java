package RobotSpaceExplorer.actions;

import RobotSpaceExplorer.powers.DroneSwarmPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class DroneSwarmAction extends AbstractGameAction {
    private final boolean freeToPlayOnce;
    private final AbstractPlayer p;
    private final int energyOnUse;

    public DroneSwarmAction(boolean freeToPlayOnce, int energyOnUse, boolean upgraded) {
        actionType = ActionType.POWER;
        this.freeToPlayOnce = freeToPlayOnce;
        this.energyOnUse = energyOnUse;
        p = AbstractDungeon.player;
    }

    @Override
    public void update() {
        int effect = EnergyPanel.totalCount;
        if (-1 != energyOnUse) {
            effect = energyOnUse;
        }
        if (p.hasRelic(ChemicalX.ID)) {
            effect += 2;
            p.getRelic(ChemicalX.ID)
             .flash();
        }

        if (0 < effect) {
            effect *= 2; // 2X
            addToBot(new ApplyPowerAction(p, p,
                    new DroneSwarmPower(effect), effect));

            if (!freeToPlayOnce) {
                p.energy.use(EnergyPanel.totalCount);
            }
        }
        isDone = true;
    }
}
