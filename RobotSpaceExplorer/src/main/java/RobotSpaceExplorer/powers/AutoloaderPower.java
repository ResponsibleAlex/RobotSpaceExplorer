package RobotSpaceExplorer.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.util.TextureLoader;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makePowerPath;

// Choose an attack in your hand and remove it from combat.
// At the start of your turn, play that card.

public class AutoloaderPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = RobotSpaceExplorerMod.makeID("AutoloaderPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Autoloader84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Autoloader32.png"));

    private static int IdOffset = 0;
    private AbstractCard cardToPlay;

    public AutoloaderPower(final AbstractCard cardToPlay) {
        name = NAME;
        ID = POWER_ID + IdOffset;
        ++IdOffset;

        owner = AbstractDungeon.player;
        this.cardToPlay = cardToPlay;

        type = PowerType.BUFF;

        // We load those txtures here.
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        playCard(cardToPlay);
    }

    private void playCard(AbstractCard card) {
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        AbstractCard tmp = card.makeSameInstanceOf();
        AbstractDungeon.player.limbo.addToBottom(tmp);
        tmp.current_x = card.current_x;
        tmp.current_y = card.current_y;
        tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
        tmp.target_y = (float)Settings.HEIGHT / 2.0F;
        if (m != null) {
            tmp.calculateCardDamage(m);
        }

        // if it exhausts, let it exhaust; otherwise purge it.
        if (!tmp.exhaust) {
            tmp.purgeOnUse = true;
        }

        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + getHighlighted(cardToPlay.name) + DESCRIPTIONS[1];
    }

    private String getHighlighted(String s) {
        String[] strings = s.split(" ");
        for (int i = 0; i < strings.length; i++) {
            strings[i] = "#y" + strings[i];
        }
        return String.join(" ", strings);
    }

    @Override
    public AbstractPower makeCopy() {
        return new AutoloaderPower(cardToPlay);
    }
}
