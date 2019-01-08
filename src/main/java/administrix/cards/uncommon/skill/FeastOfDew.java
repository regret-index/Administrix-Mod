package administrix.cards.uncommon.skill;

import administrix.cards.AbstractAdministrixCard;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;
import administrix.powers.MistEaterPower;

public class FeastOfDew extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:FeastOfDew";
    public static final String NAME = "Feast of Dew";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF;
    private static final CardType type = CardType.SKILL;
    private static final int DRAW_AMOUNT = 2;
    private static final int FEAST_AMOUNT = 1;

    public FeastOfDew() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.FEAST_OF_DEW, COST,
                CARD_STRINGS.DESCRIPTION, type,
                AbstractCardEnum.LichGold,
                rarity, target);
        this.baseMagicNumber = this.magicNumber = DRAW_AMOUNT;
        this.exhaust = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(makeStatEquivalentCopy()));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new MistEaterPower(p, FEAST_AMOUNT), FEAST_AMOUNT));
    }

    @Override
    public AbstractCard makeCopy() {
        return new FeastOfDew();
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.exhaust = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}