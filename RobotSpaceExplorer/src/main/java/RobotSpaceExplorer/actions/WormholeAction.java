package RobotSpaceExplorer.actions;

import RobotSpaceExplorer.cards.Wormhole;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import java.util.ArrayList;
import java.util.Iterator;

import static RobotSpaceExplorer.cards.AbstractDynamicCard.shouldGlow;

public class WormholeAction extends AbstractGameAction {
    private final AbstractPlayer p;
    private final ArrayList<AbstractCard> cannotRemove = new ArrayList<>();
    private final AbstractCard wormholeCard;
    private final String text;

    public WormholeAction(AbstractCard wormholeCard, String chooseACardText) {
        this.wormholeCard = wormholeCard;
        this.text = chooseACardText;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        p = AbstractDungeon.player;
    }

    public void update() {
        Iterator<AbstractCard> i = p.hand.group.iterator();
        AbstractCard c;

        if (this.duration == Settings.ACTION_DUR_FAST) {
            while (i.hasNext()) {
                c = (AbstractCard) i.next();
                if (!Wormhole.canRemove(c)) {
                    cannotRemove.add(c);
                }
            }

            if (this.p.hand.group.size() - this.cannotRemove.size() == 1) {
                // only 1 valid card
                i = this.p.hand.group.iterator();

                while (i.hasNext()) {
                    c = (AbstractCard) i.next();
                    if (Wormhole.canRemove(c)) {
                        // the only valid card, do removal on c
                        removeFromDeck(c);
                        this.isDone = true;
                        return;
                    }
                }
            }

            this.p.hand.group.removeAll(this.cannotRemove);
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(text, 1, false, false, false, false);
                this.tickDuration();
                return;
            }

            if (this.p.hand.group.size() == 1) {
                // only 1 valid card, should never reach here? from Armaments...
                c = this.p.hand.getTopCard();
                removeFromDeck(c);
                this.returnCards();
                this.isDone = true;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            i = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

            while (i.hasNext()) {
                c = (AbstractCard) i.next();
                removeFromDeck(c);
            }

            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }

        this.tickDuration();
    }

    private void returnCards() {
        Iterator<AbstractCard> i = this.cannotRemove.iterator();
        AbstractCard c;

        while (i.hasNext()) {
            c = (AbstractCard) i.next();
            this.p.hand.addToTop(c);
        }

        this.p.hand.refreshHandLayout();

        // glow if playable
        i = p.hand.group.iterator();
        while (i.hasNext()) {
            c = (AbstractCard) i.next();
            if (shouldGlow(c)) {
                c.beginGlowing();
            }
        }
    }

    private void removeFromDeck(AbstractCard c) {
        AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));

        this.addToBot(new RemoveFromMasterDeckAction(c));
        AbstractDungeon.player.hand.removeCard(c);

        this.addToBot(new RemoveFromMasterDeckAction(this.wormholeCard));
    }
}
