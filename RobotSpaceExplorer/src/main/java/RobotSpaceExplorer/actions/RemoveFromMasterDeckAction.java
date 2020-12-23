package RobotSpaceExplorer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class RemoveFromMasterDeckAction extends AbstractGameAction {
    private AbstractCard cardToRemove;

    public RemoveFromMasterDeckAction(AbstractCard cardToRemove) {
        this.cardToRemove = cardToRemove;

        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        AbstractCard upgradeMatch = null;
        AbstractCard match = null;

        Iterator<AbstractCard> i = AbstractDungeon.player.masterDeck.group.iterator();
        AbstractCard c;
        while (i.hasNext()) {
            c = (AbstractCard)i.next();
            if (c.cardID == this.cardToRemove.cardID && c.upgraded == this.cardToRemove.upgraded) {
                upgradeMatch = c;
                break;
            } else if (c.cardID == this.cardToRemove.cardID) {
                match = c;
            }
        }

        if (upgradeMatch != null) {
            doRemovalFromMasterDeck(upgradeMatch);
        } else if (match != null) {
            doRemovalFromMasterDeck(match);
        }

        this.isDone = true;
    }

    private void doRemovalFromMasterDeck(AbstractCard c) {
        this.cardToRemove.lighten(true);
        this.cardToRemove.stopGlowing();
        this.cardToRemove.unhover();
        this.cardToRemove.unfadeOut();
        AbstractDungeon.player.masterDeck.removeCard(c);
    }
}
