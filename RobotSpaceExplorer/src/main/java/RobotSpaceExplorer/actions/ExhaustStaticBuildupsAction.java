package RobotSpaceExplorer.actions;

import RobotSpaceExplorer.cards.StaticBuildup;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

public class ExhaustStaticBuildupsAction extends AbstractGameAction {
    AbstractPlayer p = AbstractDungeon.player;

    public ExhaustStaticBuildupsAction() {
        this.actionType = ActionType.EXHAUST;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        ArrayList<AbstractCard> cardsToExhaust = new ArrayList<>();
        Iterator i = p.hand.group.iterator();
        AbstractCard c;

        while (i.hasNext()) {
            c = (AbstractCard) i.next();
            if (c.cardID.equals(StaticBuildup.ID)) {
                cardsToExhaust.add(c);
            }
        }

        i = cardsToExhaust.iterator();
        while (i.hasNext()) {
            c = (AbstractCard) i.next();
            this.addToTop(new ExhaustSpecificCardAction(c, p.hand));
        }

        this.isDone = true;
    }
}
