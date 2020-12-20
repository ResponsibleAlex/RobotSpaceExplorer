package RobotSpaceExplorer.relics;

import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.actions.RoboCoreStrengthAction;
import RobotSpaceExplorer.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Iterator;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeRelicOutlinePath;
import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeRelicPath;

public class TurboCore extends CustomRelic {
    public static final String ID = RobotSpaceExplorerMod.makeID("TurboCore");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("TurboCore.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("TurboCore.png"));

    private static final int STRENGTH = 2;
    private static final int CARDS = 2;
    private static final int ENERGY = 1;

    public TurboCore() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.HEAVY);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + STRENGTH + this.DESCRIPTIONS[1];
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.addToTop(new RoboCoreStrengthAction(STRENGTH));

        this.addToBot(new DrawCardAction(AbstractDungeon.player, CARDS, false));
        this.addToBot(new GainEnergyAction(ENERGY));

        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(RoboCore.ID);
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(RoboCore.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(RoboCore.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
            Iterator i = AbstractDungeon.player.relics.iterator();
            AbstractRelic r;
            while (i.hasNext()) {
                r = (AbstractRelic)i.next();
                if (r.relicId.equals(this.ID)) {
                    r.flash();
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new TurboCore();
    }
}
