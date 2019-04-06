package administrix.cards.uncommon.power;

import administrix.cards.AbstractAdministrixCard;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;
import administrix.powers.AffinityPower;
import administrix.powers.YangPower;
import administrix.powers.YinPower;

public class HarmonicReverence extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:HarmonicReverence";
    public static final String NAME = "Harmonic Reverence";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION;
    private static final int COST = 2;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF;
    private static final CardType type = CardType.POWER;
    private static final int STACK_BASE = 3;
    private static final int UPGRADE_STACK_BASE = 2;

    public HarmonicReverence() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.HARMONIC_REVERENCE, COST,
                CARD_STRINGS.DESCRIPTION,
                type, AbstractCardEnum.LichGold,
                rarity, target);
        this.baseMagicNumber = this.magicNumber = STACK_BASE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        // Alongside gaining Affinity, double Yin & Yang.
        // Double Yang first if it'll trigger Affinity that way.

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AffinityPower(p, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));

        int yinAmount = p.hasPower(YinPower.POWER_ID) ?
                        p.getPower(YinPower.POWER_ID).amount : 0;
        int yangAmount = p.hasPower(YangPower.POWER_ID) ?
                         p.getPower(YangPower.POWER_ID).amount : 0;

        if (yangAmount > yinAmount)
        {
            if (yangAmount > 0) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new YangPower(p, yangAmount), yangAmount));
            }
            if (yinAmount > 0){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new YinPower(p, yinAmount), yinAmount));
            }
        } else {
            if (yinAmount > 0) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new YinPower(p, yinAmount), yinAmount));
            }
            if (yangAmount > 0) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new YangPower(p, yangAmount), yangAmount));
            }
        }
    }

    @Override
    public void applyPowers()
    {
        super.applyPowers();

        int yinAmount = AbstractDungeon.player.hasPower(YinPower.POWER_ID) ?
                        AbstractDungeon.player.getPower(YinPower.POWER_ID).amount : 0;
        int yangAmount = AbstractDungeon.player.hasPower(YangPower.POWER_ID) ?
                         AbstractDungeon.player.getPower(YangPower.POWER_ID).amount : 0;

        if (yangAmount > yinAmount)
        {
            this.rawDescription = EXTENDED_DESCRIPTION[0];
            initializeDescription();
        } else  {
            this.rawDescription = DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void onMoveToDiscard()
    {
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new HarmonicReverence();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_STACK_BASE);
        }
    }

}