package administrix.cards.uncommon.skill;

import administrix.cards.AbstractAdministrixCard;
import administrix.powers.YangPower;
import administrix.powers.YinPower;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;
import administrix.powers.DualityPower;

public class Wuji extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:Wuji";
    public static final String NAME = "Wuji";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION;
    private static final int COST = 0;
    private static final int DUALITY_SCALE = 4;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF;
    private static final CardType type = CardType.SKILL;

    public Wuji() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.WUJI, COST,
                CARD_STRINGS.DESCRIPTION, type,
                AbstractCardEnum.LichGold,
                rarity, target);
        this.baseMagicNumber = this.magicNumber = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        // Gain Duality equal to 1/4th of the lower of your Yin or your Yang.
        int yinAmount = p.hasPower(YinPower.POWER_ID) ?
                        p.getPower(YinPower.POWER_ID).amount : 0;
        int yangAmount = p.hasPower(YangPower.POWER_ID) ?
                         p.getPower(YangPower.POWER_ID).amount : 0;
        int equalPoint = (yinAmount > yangAmount) ? yangAmount : yinAmount;

        this.magicNumber = equalPoint / DUALITY_SCALE;

        AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new VerticalAuraEffect(Color.WHITE, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.2F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new VerticalAuraEffect(Color.BLACK, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.2F));

        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, YinPower.POWER_ID));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, YangPower.POWER_ID));

        if (this.magicNumber > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DualityPower(p, this.magicNumber), this.magicNumber));
        }
    }

    public void applyPowers()
    {
        super.applyPowers();

        int yinAmount = AbstractDungeon.player.hasPower(YinPower.POWER_ID) ?
                        AbstractDungeon.player.getPower(YinPower.POWER_ID).amount : 0;
        int yangAmount = AbstractDungeon.player.hasPower(YangPower.POWER_ID) ?
                         AbstractDungeon.player.getPower(YangPower.POWER_ID).amount : 0;
        int equalPoint = (yinAmount > yangAmount) ? yangAmount : yinAmount;

        this.baseMagicNumber = this.magicNumber = equalPoint / DUALITY_SCALE;

        if (this.magicNumber > 0)
        {
            if (this.upgraded) {
                this.rawDescription = (UPGRADE_DESCRIPTION + EXTENDED_DESCRIPTION[0]);
            } else {
                this.rawDescription = (DESCRIPTION + EXTENDED_DESCRIPTION[0]);
            }
            initializeDescription();
        }
    }

    public void onMoveToDiscard()
    {
        if (this.upgraded) {
            this.rawDescription = UPGRADE_DESCRIPTION;
        } else {
            this.rawDescription = DESCRIPTION;
        }
        initializeDescription();
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        if (this.upgraded) { this.retain = true; }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Wuji();
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.retain = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}