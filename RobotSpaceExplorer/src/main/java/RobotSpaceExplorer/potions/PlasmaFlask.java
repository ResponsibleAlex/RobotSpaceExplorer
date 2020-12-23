package RobotSpaceExplorer.potions;

import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.powers.SolarFlarePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public class PlasmaFlask extends AbstractPotion {
    public static final String POTION_ID = RobotSpaceExplorerMod.makeID("PlasmaFlask");
    private static final PotionStrings potionStrings = languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public PlasmaFlask() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.M, PotionColor.WHITE);
        isThrown = false;
    }

    public void initializeData() {
        this.potency = this.getPotency();
        this.description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public void use(AbstractCreature target) {
        target = AbstractDungeon.player;
        // If you are in combat
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.addToBot(new ApplyPowerAction(target, target, new SolarFlarePower(this.potency), this.potency));
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new PlasmaFlask();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 8;
    }

}
