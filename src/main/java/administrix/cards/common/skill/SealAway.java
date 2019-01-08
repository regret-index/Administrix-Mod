package administrix.cards.common.skill;

import administrix.cards.AbstractAdministrixCard;
import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;

public class SealAway extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:SealAway";
    public static final String NAME = "Seal Away";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int DRAW_AMOUNT = 2;
    private static final int UPGRADE_DRAW_AMOUNT = 1;
    private static final CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.SELF;

    public SealAway() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.SEAL_AWAY,
                COST, CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.LichGold,
                rarity, target);
        this.magicNumber = this.baseMagicNumber = DRAW_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
        AbstractDungeon.actionManager.addToBottom(new ArmamentsAction(true));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
        int count = BaseMod.MAX_HAND_SIZE;
        if (count != 0)
        {
            AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, count, true));
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new SealAway();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_DRAW_AMOUNT);
        }
    }

}
