package administrix.cards.rare.skill;

import administrix.AdministrixMod;
import administrix.actions.TransposeAction;
import administrix.cards.AbstractAdministrixCard;
import administrix.patches.AbstractCardEnum;
import administrix.powers.MastermindPower;
import administrix.powers.NextTurnDrawReductionPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;


public class Mastermind extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:Mastermind";
    public static final String NAME = "Mastermind";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.SELF;
    private static final CardType type = CardType.SKILL;
    private static final int COST = 2;
    private static final int TRANSPOSE_AMOUNT = 1;
    private static final int UPGRADE_TRANSPOSE_AMOUNT = 1;
    private static final int SCHEMER_AMOUNT = 2;
    private static final int DRAWLESS_AMOUNT = 1;

    public Mastermind() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.MASTERMIND, COST,
                CARD_STRINGS.DESCRIPTION, type,
                AbstractCardEnum.LichGold,
                rarity, target);
        this.magicNumber = this.baseMagicNumber = TRANSPOSE_AMOUNT;
        this.exhaust = true;
        this.retain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new BorderLongFlashEffect(Color.RED), 0.4F));

        AbstractDungeon.actionManager.addToBottom(new TransposeAction(this.magicNumber));

        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MastermindPower(p, SCHEMER_AMOUNT), SCHEMER_AMOUNT));

        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NextTurnDrawReductionPower(p, DRAWLESS_AMOUNT), DRAWLESS_AMOUNT));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mastermind();
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        this.retain = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_TRANSPOSE_AMOUNT);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}