package RobotSpaceExplorer.relics;

import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeRelicOutlinePath;
import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeRelicPath;

public class RingOfTheNewt extends CustomRelic {
    public static final String ID = RobotSpaceExplorerMod.makeID("RingOfTheNewt");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RingOfTheNewt.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("RingOfTheNewt.png"));

    public RingOfTheNewt() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        counter = 0;
    }

    @Override
    public void atTurnStart() {
        if (counter == -1) {
            counter += 2;
        } else {
            ++counter;
        }

        if (counter == 2) {
            counter = 0;
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new DrawCardAction(1));
        }

    }

    @Override
    public AbstractRelic makeCopy() {
        return new RingOfTheNewt();
    }
}
