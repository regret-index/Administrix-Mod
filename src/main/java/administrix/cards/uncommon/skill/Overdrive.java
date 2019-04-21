package administrix.cards.uncommon.skill;

import administrix.cards.AbstractAdministrixCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;
import administrix.powers.DualityPower;

import static administrix.AdministrixMod.*;
import static administrix.AdministrixMod.SKILL_LICH_GOLD_PLOT_PORTRAIT;
import static administrix.patches.CardTagsEnum.PLOT;

public class Overdrive extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:Overdrive";
    public static final String NAME = "Overdrive";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String[] EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION;
    private static final int COST = -2;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF;
    private static final CardType type = CardType.SKILL;
    private static final int ENERGY_AMOUNT = 1;
    private static final int STRENGTH_GAIN = 3;
    private static final int STRENGTH_LOSS = 5;
    private static final int UPGRADE_STRENGTH = 1;

    public Overdrive() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.OVERDRIVE, COST,
                CARD_STRINGS.DESCRIPTION,
                type, AbstractCardEnum.LichGold,
                rarity, target);
        this.tags.add(PLOT);
        setBannerTexture(UNCOMMON_BANNER_LICH_GOLD_PLOT, UNCOMMON_BANNER_PORTRAIT);
        setBackgroundTexture(SKILL_LICH_GOLD_PLOT, SKILL_LICH_GOLD_PLOT_PORTRAIT);
        this.baseMagicNumber = this.magicNumber = STRENGTH_GAIN;
        this.baseSecondMagicNumber = this.secondMagicNumber = STRENGTH_LOSS;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = EXTENDED_DESCRIPTION[0];
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) { return false; }

    @Override
    public void plotEffect() {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(ENERGY_AMOUNT));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoseStrengthPower(AbstractDungeon.player, this.secondMagicNumber), this.secondMagicNumber));
    }

    // This exhausting when drawn is implemented in DrawToExhaustPatch.
    @Override
    public void triggerWhenDrawn()
    {
        String cardName = (this.upgraded) ? this.CARD_STRINGS.NAME : this.CARD_STRINGS.NAME + "+";
        doPlotEffect(cardName);
    }

    @Override
    public void triggerOnManualDiscard()
    {
        String cardName = (this.upgraded) ? this.CARD_STRINGS.NAME : this.CARD_STRINGS.NAME + "+";
        doPlotEffect(cardName);
        AbstractDungeon.player.discardPile.moveToExhaustPile(this);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Overdrive();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_STRENGTH);
            this.upgradeSecondMagicNumber(UPGRADE_STRENGTH);
        }
    }

}