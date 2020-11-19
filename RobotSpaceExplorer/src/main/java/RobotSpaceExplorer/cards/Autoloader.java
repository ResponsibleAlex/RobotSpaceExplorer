package RobotSpaceExplorer.cards;

import RobotSpaceExplorer.actions.AutoloaderAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.characters.RobotSpaceExplorer;

import java.util.Iterator;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeCardPath;
import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public class Autoloader extends AbstractDynamicCard {
    private static final CardStrings cardStrings;

    // TEXT DECLARATION

    public static final String ID = RobotSpaceExplorerMod.makeID(Autoloader.class.getSimpleName());
    public static final String IMG = makeCardPath("Autoloader.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = RobotSpaceExplorer.Enums.ROBOT_ORANGE;

    private static final int COST = 3;
    //private static final int UPGRADE_COST = 2;

    // /STAT DECLARATION/


    public Autoloader() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        isEthereal = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new AutoloaderAction(cardStrings.EXTENDED_DESCRIPTION[1]));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            Iterator i = p.hand.group.iterator();
            canUse = false;

            while(i.hasNext()) {
                AbstractCard c = (AbstractCard) i.next();
                if (c.type == CardType.ATTACK) {
                    canUse = true;
                    break;
                }
            }

            if (!canUse)
                this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return canUse;
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            //upgradeBaseCost(UPGRADE_COST);
            isEthereal = false;
            rawDescription = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Autoloader();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(Autoloader.ID);
    }
}
