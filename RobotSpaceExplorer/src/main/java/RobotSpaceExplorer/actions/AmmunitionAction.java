package RobotSpaceExplorer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AmmunitionAction extends AbstractGameAction {

    private int numberOfAttacks;
    private final AbstractPlayer p;

    public AmmunitionAction(int numberOfAttacksToUnExhaust) {
        numberOfAttacks = numberOfAttacksToUnExhaust;
        p = AbstractDungeon.player;
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (numberOfAttacks == 0) {
            p.hand.refreshHandLayout();
            isDone = true;
        } else {
            addOne();
            numberOfAttacks--;
            tickDuration();
        }
    }

    public void addOne() {
        AbstractCard c;
        CardGroup attacks = p.exhaustPile.getAttacks();

        if (!attacks.isEmpty()) {
            c = attacks.getRandomCard(true);
            p.exhaustPile.removeCard(c);
            addToBot(new MakeTempCardInHandAction(c, true, true));
        }
    }
}
