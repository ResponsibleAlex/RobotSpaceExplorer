package RobotSpaceExplorer.cards;

import RobotSpaceExplorer.actions.RemoveFromMasterDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.characters.RobotSpaceExplorer;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeCardPath;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.relicRng;

public class Exploration extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = RobotSpaceExplorerMod.makeID(Exploration.class.getSimpleName());
    public static final String IMG = makeCardPath("Exploration.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = RobotSpaceExplorer.Enums.ROBOT_ORANGE;

    private static final int COST = 4;
    private static final int UPGRADED_COST = 3;

    // /STAT DECLARATION/


    public Exploration() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        tags.add(CardTags.HEALING);
        purgeOnUse = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractRelic.RelicTier tier = getRelicTier();
        AbstractDungeon.getCurrRoom().addRelicToRewards(tier);

        AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(this, (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));
        addToBot(new RemoveFromMasterDeckAction(this));
    }

    public static AbstractRelic.RelicTier getRelicTier() {
        int roll = relicRng.random(0, 99);
        if (roll < 50) {
            return AbstractRelic.RelicTier.COMMON;
        } else if (roll < 90) {
            return AbstractRelic.RelicTier.UNCOMMON;
        } else {
            return AbstractRelic.RelicTier.RARE;
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Exploration();
    }
}
