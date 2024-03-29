package RobotSpaceExplorer.powers;

import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makePowerPath;

// The first time you play a card costing 0 each turn, draw 1 card and gain 1E.

public class AcceleratorPower extends AbstractPower implements CloneablePowerInterface {

    private int zeroCostCardsPlayedThisTurn;

    public static final String POWER_ID = RobotSpaceExplorerMod.makeID("AcceleratorPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Accelerator84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Accelerator32.png"));

    public AcceleratorPower(final int amount) {
        name = NAME;
        ID = POWER_ID;

        owner = AbstractDungeon.player;
        this.amount = amount;
        if (999 <= this.amount) {
            this.amount = 999;
        }
        zeroCostCardsPlayedThisTurn = 0;

        type = PowerType.BUFF;

        // We load those txtures here.
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (999 <= amount) {
            amount = 999;
        }
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (0 == card.costForTurn) {
            zeroCostCardsPlayedThisTurn++;
            if (zeroCostCardsPlayedThisTurn <= amount) {
                flash();
                addToBot(new DrawCardAction(owner, 1));
                addToBot(new GainEnergyAction(1));
            }
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        zeroCostCardsPlayedThisTurn = 0;
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (1 == amount) {
            description = DESCRIPTIONS[0];
        } else if (1 < amount) {
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new AcceleratorPower(amount);
    }
}
