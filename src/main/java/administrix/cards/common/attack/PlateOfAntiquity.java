package administrix.cards.common.attack;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import basemod.abstracts.CustomCard;

public class PlateOfAntiquity extends CustomCard
{
    public static final String ID = "AdministrixMod:PlateOfAntiquity";
    public static final String NAME = "Plate Of Antiquity";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.SELF_AND_ENEMY;
    private static final CardType type = CardType.ATTACK;
    private static final int ATTACK_DMG = 7;
    private static final int UPGRADE_ATTACK_DMG = 1;
    private static final int DEX_AMOUNT = 2;
    private static final int UPGRADE_DEX_AMOUNT = 1;

    public PlateOfAntiquity() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.PLATE_OF_ANTIQUITY, COST,
                CARD_STRINGS.DESCRIPTION,
                type, AbstractCardEnum.LichGold,
                rarity, target);
        this.baseDamage = this.damage = ATTACK_DMG;
        this.baseMagicNumber = this.magicNumber = DEX_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new DexterityPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new LoseDexterityPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new PlateOfAntiquity();
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.upgradeDamage((UPGRADE_ATTACK_DMG));
            this.upgradeMagicNumber(UPGRADE_DEX_AMOUNT);
        }
    }

}