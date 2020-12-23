package RobotSpaceExplorer.actions;

import RobotSpaceExplorer.powers.AutoloaderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

public class AutoloaderAction extends AbstractGameAction {
    private final AbstractPlayer p;
    private final ArrayList<AbstractCard> nonAttacks = new ArrayList<>();
    private final String text;

    public AutoloaderAction(String chooseACardText) {
        text = chooseACardText;
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
        p = AbstractDungeon.player;
    }

    public void update() {

        if (duration == Settings.ACTION_DUR_FAST) {
            p.hand.group.stream()
                        .filter(c -> AbstractCard.CardType.ATTACK != c.type)
                        .forEach(nonAttacks::add);

            if (1 == p.hand.group.size() - nonAttacks.size()) {
                // only 1 valid card

                for (AbstractCard c : p.hand.group) {
                    if (AbstractCard.CardType.ATTACK == c.type) {
                        // the only valid card, purge and add to autoloader
                        loadIntoPower(c);
                        isDone = true;
                        return;
                    }
                }
            }

            p.hand.group.removeAll(nonAttacks);
            if (1 < p.hand.group.size()) {
                AbstractDungeon.handCardSelectScreen.open(text, 1, false, false, false, false);
                tickDuration();
                return;
            }

            if (1 == p.hand.group.size()) {
                // only 1 valid card, should never reach here? from Armaments...
                loadIntoPower(p.hand.getTopCard());

                returnCards();
                isDone = true;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            AbstractDungeon.handCardSelectScreen.selectedCards.group.forEach(this::loadIntoPower);

            returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            isDone = true;
        }

        tickDuration();
    }

    private void returnCards() {
        nonAttacks.forEach(c -> p.hand.addToTop(c));

        p.hand.refreshHandLayout();
    }

    private void loadIntoPower(AbstractCard c) {
        addToBot(new ApplyPowerAction(p, p, new AutoloaderPower(c), 0));
        AbstractDungeon.player.hand.removeCard(c);
    }
}
