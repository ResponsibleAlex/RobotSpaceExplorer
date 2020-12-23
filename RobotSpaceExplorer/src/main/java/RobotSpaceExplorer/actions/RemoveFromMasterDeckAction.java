package RobotSpaceExplorer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class RemoveFromMasterDeckAction extends AbstractGameAction {
    private AbstractCard cardToRemove;

    public RemoveFromMasterDeckAction(AbstractCard cardToRemove) {
        this.cardToRemove = cardToRemove;

        actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        AbstractCard upgradeMatch = null;
        AbstractCard match = null;

        Iterator<AbstractCard> i = AbstractDungeon.player.masterDeck.group.iterator();
        AbstractCard c;
        while (i.hasNext()) {
            c = (AbstractCard)i.next();
            if (c.cardID == cardToRemove.cardID && c.upgraded == cardToRemove.upgraded) {
                upgradeMatch = c;
                break;
            } else if (c.cardID == cardToRemove.cardID) {
                match = c;
            }
        }

        if (upgradeMatch != null) {
            doRemovalFromMasterDeck(upgradeMatch);
        } else if (match != null) {
            doRemovalFromMasterDeck(match);
        }

        isDone = true;
    }

    private void doRemovalFromMasterDeck(AbstractCard c) {
        cardToRemove.lighten(true);
        cardToRemove.stopGlowing();
        cardToRemove.unhover();
        cardToRemove.unfadeOut();
        AbstractDungeon.player.masterDeck.removeCard(c);
    }
}
