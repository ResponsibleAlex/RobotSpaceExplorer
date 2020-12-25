package RobotSpaceExplorer.actions;

import RobotSpaceExplorer.cards.AbstractDynamicCard;
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

        if (duration == Settings.ACTION_DUR_FAST) {
            p.hand.group.stream()
                        .filter(c -> !Wormhole.canRemove(c))
                        .forEach(cannotRemove::add);

            if (1 == p.hand.group.size() - cannotRemove.size()) {
                // only 1 valid card

                for (AbstractCard c : p.hand.group) {
                    if (Wormhole.canRemove(c)) {
                        // the only valid card, do removal on c
                        removeFromDeck(c);
                        isDone = true;
                        return;
                    }
                }
            }

            p.hand.group.removeAll(cannotRemove);
            if (1 < p.hand.group.size()) {
                AbstractDungeon.handCardSelectScreen.open(text, 1, false, false, false, false);
                tickDuration();
                return;
            }

            if (1 == p.hand.group.size()) {
                // only 1 valid card, should never reach here? from Armaments...
                AbstractCard c = p.hand.getTopCard();
                removeFromDeck(c);
                returnCards();
                isDone = true;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            AbstractDungeon.handCardSelectScreen.selectedCards.group.forEach(this::removeFromDeck);

            returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            isDone = true;
        }

        tickDuration();
    }

    private void returnCards() {
        cannotRemove.forEach(c -> p.hand.addToTop(c));

        p.hand.refreshHandLayout();

        // glow if playable
        p.hand.group.stream()
                    .filter(AbstractDynamicCard::shouldGlow)
                    .forEach(AbstractCard::beginGlowing);
    }

    private void removeFromDeck(AbstractCard c) {
        AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));

        addToBot(new RemoveFromMasterDeckAction(c));
        AbstractDungeon.player.hand.removeCard(c);

        addToBot(new RemoveFromMasterDeckAction(wormholeCard));
    }
}
