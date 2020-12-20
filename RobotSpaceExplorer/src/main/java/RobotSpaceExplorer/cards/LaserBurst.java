package RobotSpaceExplorer.cards;

import RobotSpaceExplorer.actions.LaserBurstAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.characters.RobotSpaceExplorer;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeCardPath;

public class LaserBurst extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = RobotSpaceExplorerMod.makeID(LaserBurst.class.getSimpleName());
    public static final String IMG = makeCardPath("LaserBurst.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = RobotSpaceExplorer.Enums.ROBOT_ORANGE;

    private static final int COST = 2;
    // private static final int UPGRADED_COST = 0;

    private static final int DAMAGE = 3;
    // private static final int UPGRADE_PLUS_DMG = 1;
    private static final int TIMES = 4;
    private static final int UPGRADE_PLUS_TIMES = 2;

    // /STAT DECLARATION/


    public LaserBurst() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = TIMES;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new LaserBurstAction(this));
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_TIMES);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new LaserBurst();
    }
}
