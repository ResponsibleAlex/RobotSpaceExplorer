package RobotSpaceExplorer.cards;

import RobotSpaceExplorer.powers.DualCorePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.characters.RobotSpaceExplorer;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeCardPath;

public class DualCore extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = RobotSpaceExplorerMod.makeID(DualCore.class.getSimpleName());
    public static final String IMG = makeCardPath("DualCore.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = RobotSpaceExplorer.Enums.ROBOT_ORANGE;

    private static final int COST = 1;
    // private static final int UPGRADED_COST = 0;

    private static final int VIGOR_AND_BLOCK = 3;
    private static final int UPGRADE_PLUS_VIGOR_AND_BLOCK = 2;

    // /STAT DECLARATION/


    public DualCore() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = VIGOR_AND_BLOCK;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p,
                new DualCorePower(magicNumber), magicNumber));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            //upgradeBaseCost(UPGRADED_COST);
            upgradeMagicNumber(UPGRADE_PLUS_VIGOR_AND_BLOCK);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new DualCore();
    }
}
