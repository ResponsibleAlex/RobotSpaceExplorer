package RobotSpaceExplorer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RemoveFromMasterDeckAction extends AbstractGameAction {
    private final AbstractCard cardToRemove;

    public RemoveFromMasterDeckAction(AbstractCard cardToRemove) {
        this.cardToRemove = cardToRemove;

        actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        AbstractCard upgradeMatch = null;
        AbstractCard match = null;

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.cardID.equals(cardToRemove.cardID) && c.upgraded == cardToRemove.upgraded) {
                upgradeMatch = c;
                break;
            } else if (c.cardID.equals(cardToRemove.cardID)) {
                match = c;
            }
        }

        if (null != upgradeMatch) {
            doRemovalFromMasterDeck(upgradeMatch);
        } else if (null != match) {
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
