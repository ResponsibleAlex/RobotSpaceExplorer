package RobotSpaceExplorer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

public class SelfCleaningAction extends AbstractGameAction {

    AbstractPlayer p;

    public SelfCleaningAction(int blockAmount) {
        this.amount = blockAmount;
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        // exhaust and gain block/draw
        ArrayList<AbstractCard> cardsToExhaust = new ArrayList<>();
        AbstractCard c;

        Iterator<AbstractCard> i = p.hand.getCardsOfType(AbstractCard.CardType.STATUS).group.iterator();
        while (i.hasNext()) {
            c = (AbstractCard) i.next();
            cardsToExhaust.add(c);
        }

        i = cardsToExhaust.iterator();
        while (i.hasNext()) {
            c = (AbstractCard) i.next();
            this.addToTop(new DrawCardAction(1));
            this.addToTop(new GainBlockAction(p, p, amount));
        }

        i = cardsToExhaust.iterator();
        while (i.hasNext()) {
            c = (AbstractCard) i.next();
            this.addToTop(new ExhaustSpecificCardAction(c, p.hand));
        }

        this.isDone = true;
    }
}
