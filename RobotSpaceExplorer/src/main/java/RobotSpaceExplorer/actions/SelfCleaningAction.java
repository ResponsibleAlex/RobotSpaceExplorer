package RobotSpaceExplorer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Collection;

public class SelfCleaningAction extends AbstractGameAction {

    AbstractPlayer p;

    public SelfCleaningAction(int blockAmount) {
        amount = blockAmount;
        p = AbstractDungeon.player;
        actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        // exhaust and gain block/draw

        Collection<AbstractCard> cardsToExhaust = new ArrayList<>(p.hand.getCardsOfType(AbstractCard.CardType.STATUS).group);

        for (AbstractCard ignored : cardsToExhaust) {
            addToTop(new DrawCardAction(1));
            addToTop(new GainBlockAction(p, p, amount));
        }

        cardsToExhaust.stream()
                      .map(card -> new ExhaustSpecificCardAction(card, p.hand))
                      .forEach(this::addToTop);

        isDone = true;
    }
}
