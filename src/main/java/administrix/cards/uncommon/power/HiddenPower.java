package administrix.cards.uncommon.power;

import administrix.cards.AbstractAdministrixCard;
import administrix.powers.NextTurnDrawReductionPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;
import administrix.powers.AffinityPower;

public class HiddenPower extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:HiddenPower";
    public static final String NAME = "Hidden Power";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF;
    private static final CardType type = CardType.POWER;
    private static final int STACK_BASE = 2;
    private static final int DRAWLESS_AMOUNT = 1;

    public HiddenPower() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.HIDDEN_POWER, COST,
                CARD_STRINGS.DESCRIPTION,
                type, AbstractCardEnum.LichGold,
                rarity, target);
        this.baseMagicNumber = this.magicNumber = STACK_BASE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AffinityPower(p, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NextTurnDrawReductionPower(p, DRAWLESS_AMOUNT), DRAWLESS_AMOUNT));
    }

    @Override
    public AbstractCard makeCopy() {
        return new HiddenPower();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
        }
    }

}