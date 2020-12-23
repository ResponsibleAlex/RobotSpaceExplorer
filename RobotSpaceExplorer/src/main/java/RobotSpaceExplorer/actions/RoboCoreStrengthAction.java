package RobotSpaceExplorer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.ClockworkSouvenir;
import com.megacrit.cardcrawl.relics.MutagenicStrength;

import java.util.Iterator;

public class RoboCoreStrengthAction extends AbstractGameAction {

    AbstractPlayer p;

    public RoboCoreStrengthAction(int strength) {
        amount = strength;
        actionType = ActionType.SPECIAL;
        p = AbstractDungeon.player;
    }

    public void update() {
        strUp();

        // If you have both MutagenicStrength and ClockworkSouvenir,
        // try to let them all stack intuitively. Gremlin's Visage might
        // mess this up.
        boolean hasMutagenic = false;
        boolean hasSouvenir = false;

        for (AbstractRelic r : p.relics) {
            if (r.relicId == MutagenicStrength.ID) {
                hasMutagenic = true;
            }
            if (r.relicId == ClockworkSouvenir.ID) {
                hasSouvenir = true;
            }
        }

        // if we have both relics, do some logic, otherwise apply the Strength down
        if (hasMutagenic && hasSouvenir) {

            if (p.hasPower(LoseStrengthPower.POWER_ID) && p.hasPower(ArtifactPower.POWER_ID)) {
                // If we see some existing Strength Down and a charge of Artifact
                // assume we need to clean it all up.
                addToBot(new RemoveSpecificPowerAction(p, p, LoseStrengthPower.POWER_ID));
                addToBot(new RemoveSpecificPowerAction(p, p, ArtifactPower.POWER_ID));
            } else if (p.hasPower(LoseStrengthPower.POWER_ID)) {
                // If we see the Strength Down but no Artifact, assume something else
                // removed the Artifact and apply our Strength Down
                strDown();
            }
            // There's no way to only see Artifact so don't care about checking.
            // If we don't see either power, assume they cancelled and
            // DON'T apply Strength Down so that we can stack with Mutagenic permanently.

        } else {
            // Don't have both relics, so just apply the Strength Down normally.
            strDown();
        }

        isDone = true;
    }

    public void strUp() {
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, amount), amount));
    }

    public void strDown() {
        addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, amount), amount));
    }
}
