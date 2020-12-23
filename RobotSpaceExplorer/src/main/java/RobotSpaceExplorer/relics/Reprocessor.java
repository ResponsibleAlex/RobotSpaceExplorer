package RobotSpaceExplorer.relics;

import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.actions.SalvageAction;
import RobotSpaceExplorer.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeRelicOutlinePath;
import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeRelicPath;

public class Reprocessor extends CustomRelic {
    public static final String ID = RobotSpaceExplorerMod.makeID("Reprocessor");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Reprocessor.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Reprocessor.png"));

    public Reprocessor() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.SOLID);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart() {
        if (AbstractDungeon.player.discardPile.size() > 0 &&
                AbstractDungeon.player.hand.size() < 10) {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new SalvageAction(1));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Reprocessor();
    }
}
