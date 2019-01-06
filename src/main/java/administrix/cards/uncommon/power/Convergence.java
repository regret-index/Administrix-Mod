package administrix.cards.uncommon.power;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;
import administrix.powers.ConvergencePlusPower;
import administrix.powers.ConvergencePower;

public class Convergence extends CustomCard
{
    public static final String ID = "AdministrixMod:Convergence";
    public static final String NAME = "Convergence";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static final int CARD_AMOUNT = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardType type = CardType.POWER;

    public Convergence() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.CONVERGENCE, COST,
                CARD_STRINGS.DESCRIPTION,
                type, AbstractCardEnum.LichGold,
                rarity, CardTarget.SELF);
        this.baseMagicNumber = this.magicNumber = CARD_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ConvergencePlusPower(p, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ConvergencePower(p, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Convergence();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}