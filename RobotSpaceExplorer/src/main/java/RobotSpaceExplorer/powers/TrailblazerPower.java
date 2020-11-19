package RobotSpaceExplorer.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;
import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.util.TextureLoader;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makePowerPath;

// At the start of your turn, gain 1 Artifact and 2 Plated Armor.

public class TrailblazerPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = RobotSpaceExplorerMod.makeID("TrailblazerPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Trailblazer84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Trailblazer32.png"));

    public TrailblazerPower(final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = AbstractDungeon.player;
        this.amount = amount;
        if (this.amount >= 999) {
            this.amount = 999;
        }

        type = PowerType.BUFF;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount >= 999) {
            this.amount = 999;
        }
    }

    @Override
    public void atStartOfTurn() {
        this.addToBot(new ApplyPowerAction(owner, owner,
                new ArtifactPower(owner, amount), amount));
        this.addToTop(new ApplyPowerAction(owner, owner,
                new PlatedArmorPower(owner, (amount * 2)), (amount * 2)));
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + (amount * 2) + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new TrailblazerPower(amount);
    }
}
