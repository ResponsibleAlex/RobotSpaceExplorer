package RobotSpaceExplorer.relics;

import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeRelicOutlinePath;
import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeRelicPath;

public class ToughPlating extends CustomRelic {
    public static final String ID = RobotSpaceExplorerMod.makeID("ToughPlating");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ToughPlating.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ToughPlating.png"));

    public ToughPlating() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.HEAVY);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ToughPlating();
    }
}
