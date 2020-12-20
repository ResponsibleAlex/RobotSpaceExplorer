package RobotSpaceExplorer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Iterator;

public class ReloadAction extends AbstractGameAction {

    private boolean canChoose;
    private AbstractPlayer p;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    public ReloadAction(boolean canChooseACard) {
        canChoose = canChooseACard;
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update()
    {
        AbstractCard card;
        Iterator c;
        CardGroup pile = p.exhaustPile.getAttacks();

        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (pile.isEmpty()) {
                this.isDone = true;
            } else if (pile.size() == 1) {
                // only 1, get it and play it
                card = pile.getTopCard();
                this.addToBot(new PlayExhaustedAttackAction(card));

                this.isDone = true;
            } else if (!canChoose) {
                // get a random one and play it
                card = pile.getRandomCard(true);
                this.addToBot(new PlayExhaustedAttackAction(card));

                this.isDone = true;
            } else {
                // open selection screen
                c = pile.group.iterator();

                while(c.hasNext()) {
                    card = (AbstractCard)c.next();
                    card.stopGlowing();
                    card.unhover();
                    card.unfadeOut();
                }

                AbstractDungeon.gridSelectScreen.open(pile, 1, TEXT[0], false);
                this.tickDuration();
            }
        } else {
            // we have selected a card, close selection screen and play it
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for(c = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); c.hasNext(); card.unhover()) {
                    card = (AbstractCard)c.next();
                    this.addToBot(new PlayExhaustedAttackAction(card));
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.p.hand.refreshHandLayout();

                // from ExhumeAction
                for(c = pile.group.iterator(); c.hasNext(); card.target_y = 0.0F) {
                    card = (AbstractCard)c.next();
                    card.unhover();
                    card.target_x = (float)CardGroup.DISCARD_PILE_X;
                }
            }

            this.tickDuration();
        }
    }

    static {
        // Reuse the Watcher's Wish text for "Choose a Card to play."
        // for the selection screen
        uiStrings = CardCrawlGame.languagePack.getUIString("WishAction");
        TEXT = uiStrings.TEXT;
    }
}
