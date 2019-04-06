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

public class Overdrive extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:Overdrive";
    public static final String NAME = "Overdrive";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final int COST = 0;
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
        this.baseMagicNumber = this.magicNumber = STRENGTH_GAIN;
        this.baseSecondMagicNumber = this.secondMagicNumber = STRENGTH_LOSS;
        this.isEthereal = true;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(ENERGY_AMOUNT));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.secondMagicNumber), this.secondMagicNumber));
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