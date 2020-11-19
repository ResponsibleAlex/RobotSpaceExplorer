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
    private AbstractPlayer p;

    public AmmunitionAction(int numberOfAttacksToUnExhaust) {
        this.numberOfAttacks = numberOfAttacksToUnExhaust;
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (numberOfAttacks == 0) {
            p.hand.refreshHandLayout();
            this.isDone = true;
        } else {
            this.addOne();
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
            this.addToBot(new MakeTempCardInHandAction(c, true, true));
        }
    }
}
