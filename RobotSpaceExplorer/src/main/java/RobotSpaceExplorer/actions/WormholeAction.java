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
        text = chooseACardText;
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
        p = AbstractDungeon.player;
    }

    public void update() {
        Iterator i = p.hand.group.iterator();
        AbstractCard c;

        if (duration == Settings.ACTION_DUR_FAST) {
            while (i.hasNext()) {
                c = (AbstractCard) i.next();
                if (!Wormhole.canRemove(c)) {
                    cannotRemove.add(c);
                }
            }

            if (p.hand.group.size() - cannotRemove.size() == 1) {
                // only 1 valid card
                i = p.hand.group.iterator();

                while (i.hasNext()) {
                    c = (AbstractCard) i.next();
                    if (Wormhole.canRemove(c)) {
                        // the only valid card, do removal on c
                        removeFromDeck(c);
                        isDone = true;
                        return;
                    }
                }
            }

            p.hand.group.removeAll(cannotRemove);
            if (p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(text, 1, false, false, false, false);
                tickDuration();
                return;
            }

            if (p.hand.group.size() == 1) {
                // only 1 valid card, should never reach here? from Armaments...
                c = p.hand.getTopCard();
                removeFromDeck(c);
                returnCards();
                isDone = true;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            i = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

            while (i.hasNext()) {
                c = (AbstractCard) i.next();
                removeFromDeck(c);
            }

            returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            isDone = true;
        }

        tickDuration();
    }

    private void returnCards() {
        Iterator i = cannotRemove.iterator();
        AbstractCard c;

        while (i.hasNext()) {
            c = (AbstractCard) i.next();
            p.hand.addToTop(c);
        }

        p.hand.refreshHandLayout();

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

        addToBot(new RemoveFromMasterDeckAction(c));
        AbstractDungeon.player.hand.removeCard(c);

        addToBot(new RemoveFromMasterDeckAction(wormholeCard));
    }
}
