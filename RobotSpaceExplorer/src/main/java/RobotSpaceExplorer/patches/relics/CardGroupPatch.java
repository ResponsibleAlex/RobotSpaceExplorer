package RobotSpaceExplorer.patches.relics;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

import java.util.Iterator;

@SpirePatch(clz = CardGroup.class, method = SpirePatch.CLASS)
public class CardGroupPatch {

    @SpirePatch(clz = CardGroup.class, method = "getPurgeableCards")
    public static class getPurgeableCards {

        // remove the BottledGravity card from this CardGroup result to avoid double-bottling
        public static CardGroup Postfix(CardGroup result) {
            CardGroup retVal = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

            Iterator<AbstractCard> i = result.group.iterator();
            AbstractCard c;
            while (i.hasNext()) {
                c = (AbstractCard)i.next();
                if (!BottledGravityPatch.inBottledGravity.get(c)) {
                    retVal.group.add(c);
                }
            }

            return retVal;
        }
    }
}
