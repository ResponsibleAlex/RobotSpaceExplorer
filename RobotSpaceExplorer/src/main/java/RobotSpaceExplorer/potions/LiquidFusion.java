package RobotSpaceExplorer.potions;

import RobotSpaceExplorer.RobotSpaceExplorerMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.SacredBark;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.Iterator;

public class LiquidFusion extends AbstractPotion {
    public static final String POTION_ID = RobotSpaceExplorerMod.makeID("LiquidFusion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public LiquidFusion() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.BOTTLE, PotionColor.WHITE);

        // Potency is the damage/magic number equivalent of potions.
        potency = getPotency();

        // Initialize the Description
        description = DESCRIPTIONS[0];

        // Do you throw this potion at an enemy or do you just consume it.
        isThrown = false;

        // Initialize the on-hover name + description
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    public void initializeData() {
        potency = getPotency();
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(SacredBark.ID)) {
            description = DESCRIPTIONS[1] + DESCRIPTIONS[0];
        } else {
            description = DESCRIPTIONS[0];
        }

        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void use(AbstractCreature target) {
        target = AbstractDungeon.player;
        // If you are in combat
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(SacredBark.ID)) {
                addToBot(new DrawCardAction(1));
            }
            Iterator<AbstractCard> i  = AbstractDungeon.player.hand.group.iterator();
            AbstractCard c;
            while (i.hasNext()) {
                c = i.next();
                if (c.costForTurn > 0) {
                    c.setCostForTurn(0);
                    c.superFlash();
                }
            }
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new LiquidFusion();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 0;
    }

}