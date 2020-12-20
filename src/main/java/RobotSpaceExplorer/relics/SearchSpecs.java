package RobotSpaceExplorer.relics;

import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeRelicOutlinePath;
import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeRelicPath;

public class SearchSpecs extends CustomRelic {
    public static final String ID = RobotSpaceExplorerMod.makeID("SearchSpecs");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SearchSpecs.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SearchSpecs.png"));

    public SearchSpecs() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SearchSpecs();
    }
}
