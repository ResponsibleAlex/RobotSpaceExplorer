package RobotSpaceExplorer.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.characters.RobotSpaceExplorer;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeCardPath;

public class Fix extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = RobotSpaceExplorerMod.makeID(Fix.class.getSimpleName());
    public static final String IMG = makeCardPath("Fix.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = RobotSpaceExplorer.Enums.ROBOT_ORANGE;

    private static final int COST = 1;
    // private static final int UPGRADED_COST = 0;

    private static final int ARTIFACT = 1;
    private static final int UPGRADE_PLUS_ARTIFACT = 1;
    private static final int HEAL = 4;
    private static final int UPGRADE_PLUS_HEAL = 2;

    // /STAT DECLARATION/


    public Fix() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = 0;
        magicNumber = baseMagicNumber = ARTIFACT;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = HEAL;
        exhaust = true;
        tags.add(CardTags.HEALING);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ArtifactPower(p, magicNumber), magicNumber));
        addToBot(new HealAction(p, p, defaultSecondMagicNumber));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_ARTIFACT);
            upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_HEAL);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Fix();
    }
}
