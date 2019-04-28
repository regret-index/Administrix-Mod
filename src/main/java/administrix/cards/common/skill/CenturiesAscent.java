package administrix.cards.common.skill;

import administrix.AdministrixMod;
import administrix.actions.ImmortalUpgradeAction;
import administrix.cards.AbstractAdministrixCard;
import administrix.cards.common.attack.Daybreak;
import administrix.patches.AbstractCardEnum;
import administrix.powers.YangPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static administrix.AdministrixMod.*;
import static administrix.patches.CardTagsEnum.PLOT;

public class CenturiesAscent extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:CenturiesAscent";
    public static final String NAME = "Centuries Ascent";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK_AMOUNT = 4;
    private static final int UPGRADE_BLOCK_AMOUNT = 2;
    private static final int YANG_AMOUNT = 0;
    private static final int UPGRADE_YANG_AMOUNT = 2;
    private static final CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.SELF;

    public CenturiesAscent() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.CENTURIES_ASCENT,
                COST, CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.LichGold,
                rarity, target);
        this.tags.add(PLOT);
        setOrbTexture(ENERGY_ORB_LICH_GOLD_PLOT, ENERGY_ORB_LICH_GOLD_PORTRAIT);
        setBackgroundTexture(SKILL_LICH_GOLD_PLOT, SKILL_LICH_GOLD_PLOT_PORTRAIT);
        this.baseBlock = this.block = BLOCK_AMOUNT;
        this.baseMagicNumber = this.magicNumber = YANG_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new YangPower(p, this.magicNumber), this.magicNumber));
        }
    }

    @Override
    public void plotEffect() {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Daybreak(), 1));
    }

    @Override
    public void triggerWhenDrawn()
    {
        String cardName = (this.upgraded) ? this.CARD_STRINGS.NAME  + "+" : this.CARD_STRINGS.NAME;
        doPlotEffect(cardName);
    }

    @Override
    public void triggerOnManualDiscard()
    {
        String cardName = (this.upgraded) ? this.CARD_STRINGS.NAME  + "+" : this.CARD_STRINGS.NAME;
        doPlotEffect(cardName);
    }

    @Override
    public AbstractCard makeCopy() {
        return new CenturiesAscent();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_BLOCK_AMOUNT);
            this.upgradeMagicNumber(UPGRADE_YANG_AMOUNT);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}