package administrix.cards.common.skill;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;

public class TouchOfCinnabar extends CustomCard
{
    public static final String ID = "AdministrixMod:TouchOfCinnabar";
    public static final String NAME = "Touch of Cinnabar";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    private static final int PLATED_AMOUNT = 3;
    private static final int FRAIL_AMOUNT = 2;
    private static final int ARTIFACT_AMOUNT = 1;
    private static final CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.SELF;

    public TouchOfCinnabar() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.TOUCH_OF_CINNABAR, COST,
                CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.LichGold,
                rarity, target);
        this.baseMagicNumber = this.magicNumber = ARTIFACT_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, PLATED_AMOUNT), PLATED_AMOUNT));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FrailPower(p, FRAIL_AMOUNT, false), FRAIL_AMOUNT));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo)
    {
        super.calculateCardDamage(mo);
    }

    @Override
    public AbstractCard makeCopy() {
        return new TouchOfCinnabar();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
        }
    }

}