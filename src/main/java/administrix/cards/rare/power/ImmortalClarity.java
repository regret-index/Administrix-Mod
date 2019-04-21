package administrix.cards.rare.power;

import administrix.cards.AbstractAdministrixCard;
import administrix.powers.ImmortalClarityPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import administrix.AdministrixMod;
import administrix.actions.ImmortalClarityAction;
import administrix.actions.ImmortalUpgradeAction;
import administrix.patches.AbstractCardEnum;

import static administrix.AdministrixMod.*;
import static administrix.patches.CardTagsEnum.PLOT;

public class ImmortalClarity extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:ImmortalClarity";
    public static final String NAME = "Immortal Clarity";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardType type = CardType.POWER;
    private static final int COST = 1;
    private static final int CLARITY_AMOUNT = 1;

    public ImmortalClarity() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.IMMORTAL_CLARITY, COST,
                CARD_STRINGS.DESCRIPTION,
                type, AbstractCardEnum.LichGold,
                rarity, CardTarget.SELF);
        this.tags.add(PLOT);
        setOrbTexture(ENERGY_ORB_LICH_GOLD_PLOT, ENERGY_ORB_LICH_GOLD_PORTRAIT);
        setBackgroundTexture(POWER_LICH_GOLD_PLOT, POWER_LICH_GOLD_PLOT_PORTRAIT);
        this.baseMagicNumber = this.magicNumber = CLARITY_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower("AdministrixMod:ImmortalClarity")) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, ImmortalClarityPower.POWER_ID));
        }
        AbstractDungeon.actionManager.addToBottom(new ImmortalClarityAction
                (p, p, this.magicNumber));
    }

    @Override
    public void plotEffect() {
        AbstractDungeon.actionManager.addToBottom(new ImmortalUpgradeAction(this.upgraded));
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
        return new ImmortalClarity();
    }

    @Override
    public void upgrade() {
        this.upgradeMagicNumber(1);
        this.timesUpgraded += 1;
        this.upgraded = true;
        this.name = (CARD_STRINGS.NAME + "+" + this.timesUpgraded);
        initializeTitle();
        this.rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    public boolean canUpgrade()
    {
        return true;
    }
}