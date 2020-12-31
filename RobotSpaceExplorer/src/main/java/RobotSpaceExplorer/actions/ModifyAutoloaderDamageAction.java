package RobotSpaceExplorer.actions;

import RobotSpaceExplorer.powers.AutoloaderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.UUID;

public class ModifyAutoloaderDamageAction extends AbstractGameAction {
    private final UUID uuid;

    public ModifyAutoloaderDamageAction(UUID targetUUID, int amount) {
        this.setValues(this.target, this.source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.uuid = targetUUID;
    }

    public void update() {
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p.name.equals(AutoloaderPower.NAME)) {
                AbstractCard c = ((AutoloaderPower) p).cardToPlay;
                if (c.uuid == this.uuid) {
                    c.baseDamage += this.amount;
                }
            }
        }

        this.isDone = true;
    }
}
