package administrix.cards.common.skill;

import administrix.cards.AbstractAdministrixCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import administrix.AdministrixMod;
import administrix.actions.ConspiracyAction;
import administrix.patches.AbstractCardEnum;

public class QuietConspiracy extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:QuietConspiracy";
    public static final String NAME = "Quiet Conspiracy";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int PROGRAM_AMOUNT = 3;
    private static final int UPGRADE_PROGRAM_AMOUNT = 2;
    private static final int DRAW_AMOUNT = 2;
    private static final CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.SELF_AND_ENEMY;

    public QuietConspiracy() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.QUIET_CONSPIRACY, COST,
                CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.LichGold,
                rarity, target);
        this.baseMagicNumber = this.magicNumber = PROGRAM_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if ((m != null) && ((m.intent != AbstractMonster.Intent.ATTACK) && (m.intent != AbstractMonster.Intent.ATTACK_BUFF) && (m.intent != AbstractMonster.Intent.ATTACK_DEBUFF) && (m.intent != AbstractMonster.Intent.ATTACK_DEFEND))) {
            AbstractDungeon.actionManager.addToBottom(new ConspiracyAction(this.magicNumber));
        }
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, DRAW_AMOUNT));
    }

    @Override
    public AbstractCard makeCopy() {
        return new QuietConspiracy();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PROGRAM_AMOUNT);
        }
    }

}