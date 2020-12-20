package RobotSpaceExplorer.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.characters.RobotSpaceExplorer;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeCardPath;

public class MakeshiftVolley extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = RobotSpaceExplorerMod.makeID(MakeshiftVolley.class.getSimpleName());
    public static final String IMG = makeCardPath("MakeshiftVolley.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = RobotSpaceExplorer.Enums.ROBOT_ORANGE;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    private static final int DAMAGE = 5;

    // /STAT DECLARATION/


    public MakeshiftVolley() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = AbstractDungeon.player.hand.size();
        count--; // this card still technically in hand, so ignore it for the count
        if (count != 0) {
            this.addToTop(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, count, true));

            for (int i = 0; i < count; i++) {
                this.addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }
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
        return new MakeshiftVolley();
    }
}
