package RobotSpaceExplorer.relics;

import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.actions.PlayExhaustedAttackAction;
import RobotSpaceExplorer.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeRelicOutlinePath;
import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeRelicPath;

public class SlideRule extends CustomRelic {
    public static final String ID = RobotSpaceExplorerMod.makeID("SlideRule");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SlideRule.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SlideRule.png"));

    public SlideRule() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.CLINK);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onShuffle() {
        CardGroup pile = AbstractDungeon.player.exhaustPile.getAttacks();
        if (pile.size() > 0) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new PlayExhaustedAttackAction(pile.getRandomCard(true)));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SlideRule();
    }
}
