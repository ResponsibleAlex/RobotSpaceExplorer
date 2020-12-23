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
    private AbstractPlayer p;
    private ArrayList<AbstractCard> nonAttacks = new ArrayList<>();
    private String text;

    public AutoloaderAction(String chooseACardText) {
        text = chooseACardText;
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
        p = AbstractDungeon.player;
    }

    public void update() {
        Iterator<AbstractCard> i = p.hand.group.iterator();
        AbstractCard c;

        if (duration == Settings.ACTION_DUR_FAST) {
            while (i.hasNext()) {
                c = (AbstractCard) i.next();
                if (c.type != AbstractCard.CardType.ATTACK) {
                    nonAttacks.add(c);
                }
            }

            if (p.hand.group.size() - nonAttacks.size() == 1) {
                // only 1 valid card
                i = p.hand.group.iterator();

                while (i.hasNext()) {
                    c = (AbstractCard) i.next();
                    if (c.type == AbstractCard.CardType.ATTACK) {
                        // the only valid card, purge and add to autoloader
                        loadIntoPower(c);
                        isDone = true;
                        return;
                    }
                }
            }

            p.hand.group.removeAll(nonAttacks);
            if (p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(text, 1, false, false, false, false);
                tickDuration();
                return;
            }

            if (p.hand.group.size() == 1) {
                // only 1 valid card, should never reach here? from Armaments...
                c = p.hand.getTopCard();

                loadIntoPower(c);

                returnCards();
                isDone = true;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            i = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

            while(i.hasNext()) {
                c = (AbstractCard)i.next();
                loadIntoPower(c);
            }

            returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            isDone = true;
        }

        tickDuration();
    }

    private void returnCards() {
        Iterator<AbstractCard> i = nonAttacks.iterator();
        AbstractCard c;

        while(i.hasNext()) {
            c = (AbstractCard)i.next();
            p.hand.addToTop(c);
        }

        p.hand.refreshHandLayout();
    }

    private void loadIntoPower(AbstractCard c) {
        addToBot(new ApplyPowerAction(p, p,
                new AutoloaderPower(c), 0));
        AbstractDungeon.player.hand.removeCard(c);
    }
}
