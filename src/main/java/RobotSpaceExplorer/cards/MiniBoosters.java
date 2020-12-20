package RobotSpaceExplorer.cards;

import RobotSpaceExplorer.powers.MiniBoostersPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.characters.RobotSpaceExplorer;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeCardPath;

public class MiniBoosters extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = RobotSpaceExplorerMod.makeID(MiniBoosters.class.getSimpleName());
    public static final String IMG = makeCardPath("MiniBoosters.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = RobotSpaceExplorer.Enums.ROBOT_ORANGE;

    private static final int COST = 1;
    //private static final int UPGRADED_COST = 0;

    private static final int MAGIC_NUMBER = 1;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 1;

    // /STAT DECLARATION/


    public MiniBoosters() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC_NUMBER;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p,
                new MiniBoostersPower(magicNumber), magicNumber));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            //upgradeBaseCost(UPGRADED_COST);
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC_NUMBER);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new MiniBoosters();
    }
}
