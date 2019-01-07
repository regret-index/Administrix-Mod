package administrix.cards.common.skill;

import administrix.powers.DualityPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import administrix.AdministrixMod;
import administrix.cards.common.attack.Daybreak;
import administrix.patches.AbstractCardEnum;

public class DuskAndDawn extends CustomCard
{
    public static final String ID = "AdministrixMod:DuskAndDawn";
    public static final String NAME = "DuskAndDawn";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int CARD_AMOUNT = 1;
    private static final int DRAW_AMOUNT = 1;
    private static final CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.SELF;


    public DuskAndDawn() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.DUSK_AND_DAWN, COST,
                CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.LichGold,
                rarity, target);
        this.baseMagicNumber = this.magicNumber = CARD_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (this.upgraded)
        {
            AbstractCard d = new Daybreak().makeCopy();
            AbstractCard n = new Nightfall().makeCopy();
            d.upgrade();
            n.upgrade();
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(d, CARD_AMOUNT));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(n, CARD_AMOUNT));
        } else {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Daybreak(), CARD_AMOUNT));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Nightfall(), CARD_AMOUNT));
        }
        if (p.hasPower(DualityPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, DRAW_AMOUNT));
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo)
    {
        super.calculateCardDamage(mo);
    }

    @Override
    public AbstractCard makeCopy() {
        return new DuskAndDawn();
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