package RobotSpaceExplorer.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.util.TextureLoader;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makePowerPath;

// whenever you play 5 cards in a turn, gain vigor and block

public class DualCorePower extends AbstractPower implements CloneablePowerInterface {

    private int magicNumber;

    public static final String POWER_ID = RobotSpaceExplorerMod.makeID("DualCorePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("DualCore84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("DualCore32.png"));

    public DualCorePower(final int amount) {
        name = NAME;
        ID = POWER_ID;

        owner = AbstractDungeon.player;
        magicNumber = amount;
        if (magicNumber >= 999) {
            magicNumber = 999;
        }
        this.amount = 5;

        type = PowerType.BUFF;

        // We load those txtures here.
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void stackPower(int stackAmount) {
        magicNumber += stackAmount;
        if (magicNumber >= 999) {
            magicNumber = 999;
        }
        updateDescription();
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        --amount;
        if (amount == 0) {
            flash();
            amount = 5;

            if (Settings.FAST_MODE) {
                addToBot(new VFXAction(owner, new FlameBarrierEffect(owner.hb.cX, owner.hb.cY), 0.1F));
            } else {
                addToBot(new VFXAction(owner, new FlameBarrierEffect(owner.hb.cX, owner.hb.cY), 0.5F));
            }

            addToBot(new ApplyPowerAction(owner, owner, new VigorPower(owner, magicNumber), magicNumber));
            addToBot(new GainBlockAction(owner, owner, magicNumber));
        }

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        amount = 5;
        updateDescription();
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] +
                    magicNumber + DESCRIPTIONS[3] + magicNumber + DESCRIPTIONS[4];
        } else {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2] +
                    magicNumber + DESCRIPTIONS[3] + magicNumber + DESCRIPTIONS[4];
        }

    }

    @Override
    public AbstractPower makeCopy() {
        return new DualCorePower(magicNumber);
    }
}
