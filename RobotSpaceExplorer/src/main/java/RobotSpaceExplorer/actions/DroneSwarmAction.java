package RobotSpaceExplorer.actions;

import RobotSpaceExplorer.powers.DroneSwarmPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class DroneSwarmAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private AbstractPlayer p;
    private int energyOnUse;
    private boolean upgraded;

    public DroneSwarmAction(boolean freeToPlayOnce, int energyOnUse, boolean upgraded) {
        actionType = ActionType.POWER;
        this.freeToPlayOnce = freeToPlayOnce;
        this.energyOnUse = energyOnUse;
        this.upgraded = upgraded;
        p = AbstractDungeon.player;
    }

    @Override
    public void update() {
        int effect = EnergyPanel.totalCount;
        if (energyOnUse != -1) {
            effect = energyOnUse;
        }
        if (p.hasRelic(ChemicalX.ID)) {
            effect += 2;
            p.getRelic(ChemicalX.ID).flash();
        }

        /*if (this.upgraded) {
            effect++;
        }*/

        if (effect > 0) {
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
