package administrix.cards.common.skill;

import administrix.cards.AbstractAdministrixCard;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;

public class SipOfSparks extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:SipOfSparks";
    public static final String NAME = "Sip of Sparks";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int BLOCK_AMOUNT = 7;
    private static final int UPGRADE_BLOCK_DMG = 1;
    private static final int STR_AMOUNT = 2;
    private static final int UPGRADE_STR_AMOUNT = 1;
    private static final CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.SELF;

    public SipOfSparks() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.SIP_OF_SPARKS, COST,
                CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.LichGold,
                rarity, target);
        this.baseBlock = this.block = BLOCK_AMOUNT;
        this.baseMagicNumber = this.magicNumber = STR_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo)
    {
        super.calculateCardDamage(mo);
    }

    @Override
    public AbstractCard makeCopy() {
        return new SipOfSparks();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock((UPGRADE_BLOCK_DMG));
            this.upgradeMagicNumber(UPGRADE_STR_AMOUNT);
        }
    }
}