package RobotSpaceExplorer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Iterator;

public class ReloadAction extends AbstractGameAction {

    private boolean canChoose;
    private AbstractPlayer p;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    public ReloadAction(boolean canChooseACard) {
        canChoose = canChooseACard;
        p = AbstractDungeon.player;
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
    }

    public void update()
    {
        AbstractCard card;
        Iterator<AbstractCard> c;
        CardGroup pile = p.exhaustPile.getAttacks();

        if (duration == Settings.ACTION_DUR_FAST) {
            if (pile.isEmpty()) {
                isDone = true;
            } else if (pile.size() == 1) {
                // only 1, get it and play it
                card = pile.getTopCard();
                addToBot(new PlayExhaustedAttackAction(card));

                isDone = true;
            } else if (!canChoose) {
                // get a random one and play it
                card = pile.getRandomCard(true);
                addToBot(new PlayExhaustedAttackAction(card));

                isDone = true;
            } else {
                // open selection screen
                c = pile.group.iterator();

                while(c.hasNext()) {
                    card = c.next();
                    card.stopGlowing();
                    card.unhover();
                    card.unfadeOut();
                }

                AbstractDungeon.gridSelectScreen.open(pile, 1, TEXT[0], false);
                tickDuration();
            }
        } else {
            // we have selected a card, close selection screen and play it
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for(c = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); c.hasNext(); card.unhover()) {
                    card = c.next();
                    addToBot(new PlayExhaustedAttackAction(card));
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                p.hand.refreshHandLayout();

                // from ExhumeAction
                for(c = pile.group.iterator(); c.hasNext(); card.target_y = 0.0F) {
                    card = c.next();
                    card.unhover();
                    card.target_x = (float)CardGroup.DISCARD_PILE_X;
                }
            }

            tickDuration();
        }
    }

    static {
        // Reuse the Watcher's Wish text for "Choose a Card to play."
        // for the selection screen
        uiStrings = CardCrawlGame.languagePack.getUIString("WishAction");
        TEXT = uiStrings.TEXT;
    }
}
