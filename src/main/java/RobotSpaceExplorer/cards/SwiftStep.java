package RobotSpaceExplorer.cards;

import RobotSpaceExplorer.actions.SalvageAction;
import RobotSpaceExplorer.powers.SwiftStepPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.characters.RobotSpaceExplorer;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeCardPath;
import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public class SwiftStep extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = RobotSpaceExplorerMod.makeID(SwiftStep.class.getSimpleName());
    public static final String IMG = makeCardPath("SwiftStep.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = RobotSpaceExplorer.Enums.ROBOT_ORANGE;

    private static final int COST = 1;
    // private static final int UPGRADED_COST = 0;

    private static final int BLOCK = 2;
    //private static final int UPGRADE_PLUS_BLOCK = 1;

    // /STAT DECLARATION/


    public SwiftStep() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = BLOCK;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new SwiftStepPower(magicNumber), magicNumber));
        if (this.upgraded) {
            this.addToBot(new SalvageAction(1));
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            //upgradeMagicNumber(UPGRADE_PLUS_BLOCK);
            rawDescription = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new SwiftStep();
    }
}
