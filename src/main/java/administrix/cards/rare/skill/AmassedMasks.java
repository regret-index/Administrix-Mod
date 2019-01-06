package administrix.cards.rare.skill;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;

public class AmassedMasks extends CustomCard
{
    public static final String ID = "AdministrixMod:AmassedMasks";
    public static final String NAME = "Amassed Masks";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final int COST = 3;
    private static final int UPGRADE_COST = 2;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.SELF;
    private static final CardType type = CardType.SKILL;

    public AmassedMasks() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.AMASSED_MASKS, COST,
                CARD_STRINGS.DESCRIPTION, type,
                AbstractCardEnum.LichGold,
                rarity, target);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        // Generate a random Attack, Skill, and Power, all set to 0.
        // No generating itself, though, and no generating healing.
        AbstractCard a = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.ATTACK).makeCopy();
        AbstractCard b = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.SKILL).makeCopy();
        AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.POWER).makeCopy();

        while (c instanceof AmassedMasks)
            c = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.POWER).makeCopy();

        a.setCostForTurn(0);
        b.setCostForTurn(0);
        c.setCostForTurn(0);
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(a, true));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(b, true));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, true));
    }

    @Override
    public AbstractCard makeCopy() {
        return new AmassedMasks();
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
        }
    }

}