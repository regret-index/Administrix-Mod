package administrix.cards.common.skill;

import administrix.actions.TransposeAction;
import administrix.cards.AbstractAdministrixCard;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;
import administrix.powers.YangPower;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;

public class LightOfYourLife extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:LightOfYourLife";
    public static final String NAME = "Light of Your Life";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int TRANSPOSE_AMOUNT = 1;
    private static final int DRAW_AMOUNT = 1;
    private static final int YANG_AMOUNT = 4;
    private static final CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.SELF;

    public LightOfYourLife() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.LIGHT_OF_YOUR_LIFE, COST,
                CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.LichGold,
                rarity, target);
        this.magicNumber = this.baseMagicNumber = DRAW_AMOUNT;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new BorderLongFlashEffect(Color.GOLD), 0.8F));
            AbstractDungeon.actionManager.addToBottom(new TransposeAction(TRANSPOSE_AMOUNT));
        }
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new YangPower(p, YANG_AMOUNT), YANG_AMOUNT));
    }


    @Override
    public AbstractCard makeCopy() {
        return new LightOfYourLife();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}
