package RobotSpaceExplorer.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.characters.RobotSpaceExplorer;
import com.megacrit.cardcrawl.powers.WeakPower;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeCardPath;

public class ShockKick extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = RobotSpaceExplorerMod.makeID(ShockKick.class.getSimpleName());
    public static final String IMG = makeCardPath("ShockKick.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = RobotSpaceExplorer.Enums.ROBOT_ORANGE;

    private static final int COST = 1;
    // private static final int UPGRADED_COST = 0;

    private static final int DAMAGE = 9;
    private static final int UPGRADE_PLUS_DMG = 4;
    private static final int WEAK = 1;
    private static final int UPGRADE_PLUS_WEAK = 1;

    // /STAT DECLARATION/


    public ShockKick() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = WEAK;
        this.cardsToPreview = new StaticBuildup();
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        lightningEffect(m);
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        this.addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new WeakPower(m, this.magicNumber, false), this.magicNumber));

        this.addToBot(new MakeTempCardInHandAction(new StaticBuildup(), 1));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_PLUS_WEAK);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ShockKick();
    }
}
