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
    private ArrayList<AbstractCard> nonAttacks = new ArrayList();
    private String text;

    public AutoloaderAction(String chooseACardText) {
        this.text = chooseACardText;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        p = AbstractDungeon.player;
    }

    public void update() {
        Iterator i = p.hand.group.iterator();
        AbstractCard c;

        if (this.duration == Settings.ACTION_DUR_FAST) {
            while (i.hasNext()) {
                c = (AbstractCard) i.next();
                if (c.type != AbstractCard.CardType.ATTACK) {
                    nonAttacks.add(c);
                }
            }

            if (this.p.hand.group.size() - this.nonAttacks.size() == 1) {
                // only 1 valid card
                i = this.p.hand.group.iterator();

                while (i.hasNext()) {
                    c = (AbstractCard) i.next();
                    if (c.type == AbstractCard.CardType.ATTACK) {
                        // the only valid card, purge and add to autoloader
                        loadIntoPower(c);
                        this.isDone = true;
                        return;
                    }
                }
            }

            this.p.hand.group.removeAll(this.nonAttacks);
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(text, 1, false, false, false, false);
                this.tickDuration();
                return;
            }

            if (this.p.hand.group.size() == 1) {
                // only 1 valid card, should never reach here? from Armaments...
                c = this.p.hand.getTopCard();

                loadIntoPower(c);

                this.returnCards();
                this.isDone = true;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            i = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

            while(i.hasNext()) {
                c = (AbstractCard)i.next();
                loadIntoPower(c);
            }

            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }

        this.tickDuration();
    }

    private void returnCards() {
        Iterator i = this.nonAttacks.iterator();
        AbstractCard c;

        while(i.hasNext()) {
            c = (AbstractCard)i.next();
            this.p.hand.addToTop(c);
        }

        this.p.hand.refreshHandLayout();
    }

    private void loadIntoPower(AbstractCard c) {
        this.addToBot(new ApplyPowerAction(p, p,
                new AutoloaderPower(c), 0));
        AbstractDungeon.player.hand.removeCard(c);
    }
}
