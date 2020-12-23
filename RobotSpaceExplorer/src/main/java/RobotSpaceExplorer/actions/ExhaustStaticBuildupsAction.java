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
import java.util.stream.Collectors;

public class ExhaustStaticBuildupsAction extends AbstractGameAction {
    final AbstractPlayer p = AbstractDungeon.player;

    public ExhaustStaticBuildupsAction() {
        actionType = ActionType.EXHAUST;
        duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        ArrayList<AbstractCard> cardsToExhaust = p.hand.group.stream()
                                                             .filter(c -> c.cardID.equals(StaticBuildup.ID))
                                                             .collect(Collectors.toCollection(ArrayList::new));

        cardsToExhaust.stream()
                      .map(c -> new ExhaustSpecificCardAction(c, p.hand))
                      .forEach(this::addToTop);

        isDone = true;
    }
}
