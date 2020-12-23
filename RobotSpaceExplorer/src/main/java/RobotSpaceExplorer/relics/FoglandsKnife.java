package RobotSpaceExplorer.relics;

import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeRelicOutlinePath;
import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeRelicPath;

public class FoglandsKnife extends CustomRelic {
    public static final String ID = RobotSpaceExplorerMod.makeID("FoglandsKnife");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("FoglandsKnife.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("FoglandsKnife.png"));

    public FoglandsKnife() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public float atDamageModify(float damage, AbstractCard c) {
        return 0 != c.costForTurn && (!c.freeToPlayOnce || -1 == c.cost) ? damage : damage + 2.0F;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new FoglandsKnife();
    }
}
