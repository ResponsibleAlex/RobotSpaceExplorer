package RobotSpaceExplorer.relics;

import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.powers.SolarFlarePower;
import RobotSpaceExplorer.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeRelicOutlinePath;
import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeRelicPath;

public class PlasmaHourglass extends CustomRelic {
    public static final String ID = RobotSpaceExplorerMod.makeID("PlasmaHourglass");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("PlasmaHourglass.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("PlasmaHourglass.png"));

    private static final int SOLAR_FLARE_PER_TURN = 1;

    public PlasmaHourglass() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new SolarFlarePower(SOLAR_FLARE_PER_TURN), SOLAR_FLARE_PER_TURN));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PlasmaHourglass();
    }
}
