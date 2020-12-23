package RobotSpaceExplorer.powers;

import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makePowerPath;

// Gain 2 (3) block when you play an attack card.

public class ExhaustFanPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = RobotSpaceExplorerMod.makeID("ExhaustFanPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("ExhaustFan84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("ExhaustFan32.png"));

    public ExhaustFanPower(final int amount) {
        name = NAME;
        ID = POWER_ID;

        owner = AbstractDungeon.player;
        this.amount = amount;
        if (this.amount >= 999) {
            this.amount = 999;
        }

        type = PowerType.BUFF;

        // We load those txtures here.
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (amount >= 999) {
            amount = 999;
        }
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            flash();
            addToBot(new SFXAction("ATTACK_HEAVY"));
            if (Settings.FAST_MODE) {
                addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, amount, true));
            } else {
                addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, amount));
            }
        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ExhaustFanPower(amount);
    }
}
