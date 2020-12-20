package RobotSpaceExplorer.relics;

import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.actions.RoboCoreStrengthAction;
import RobotSpaceExplorer.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.ClockworkSouvenir;
import com.megacrit.cardcrawl.relics.MutagenicStrength;

import java.util.Iterator;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeRelicOutlinePath;
import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeRelicPath;

public class RoboCore extends CustomRelic {
    public static final String ID = RobotSpaceExplorerMod.makeID("RoboCore");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RoboCore.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("RoboCore.png"));

    private static final int STRENGTH = 1;
    private static final int CARDS = 1;

    public RoboCore() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.FLAT);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + STRENGTH + DESCRIPTIONS[1];
    }

    @Override
    public void atBattleStart() {
        flash();
        addToTop(new RoboCoreStrengthAction(STRENGTH));

        addToBot(new DrawCardAction(AbstractDungeon.player, CARDS, false));

        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new RoboCore();
    }
}
