package administrix.cards.uncommon.power;

import administrix.cards.AbstractAdministrixCard;
import administrix.powers.YangPower;
import administrix.powers.YinPower;
import administrix.vfx.KinesisEffect;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.vfx.CollectorStakeEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;

public class Wuji extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:Wuji";
    public static final String NAME = "Wuji";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION;
    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;
    private static final int DEXTERITY_SCALE = 5;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF;
    private static final CardType type = CardType.POWER;

    public Wuji() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.WUJI, COST,
                CARD_STRINGS.DESCRIPTION, type,
                AbstractCardEnum.LichGold,
                rarity, target);
        this.baseMagicNumber = this.magicNumber = 0;
        this.baseSecondMagicNumber = this.secondMagicNumber = 1;
        this.retain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        // Gain Duality equal to 1/6th of the lower of your Yin or your Yang.
        int yinAmount = p.hasPower(YinPower.POWER_ID) ?
                        p.getPower(YinPower.POWER_ID).amount : 0;
        int yangAmount = p.hasPower(YangPower.POWER_ID) ?
                         p.getPower(YangPower.POWER_ID).amount : 0;
        int halfYin = yinAmount / 2;
        int halfYang = yangAmount / 2;
        int equalPoint = (yinAmount > yangAmount) ? yangAmount : yinAmount;

        this.magicNumber = (equalPoint / DEXTERITY_SCALE) * secondMagicNumber;

        AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new VerticalAuraEffect(Color.WHITE, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.2F));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_FIRE"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new VerticalAuraEffect(Color.BLACK, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.2F));

        if (this.magicNumber > 0) {
            float length = Math.min(0.1F + this.magicNumber * 0.05F, 1.2F);
            CardCrawlGame.sound.playA("ORB_DARK_EVOKE", -0.4F);
            AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new KinesisEffect(p.hb.cX, p.hb.cY, p.hb.cX, p.hb.cY - 800F * Settings.scale, length, Color.WHITE.cpy(), Color.BLACK.cpy()), length));
            CardCrawlGame.sound.playA("ORB_DARK_EVOKE", -0.4F);
        }

        if (halfYin != 0) {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, YinPower.POWER_ID, halfYin));
        }

        if (halfYang != 0) {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, YangPower.POWER_ID, halfYang));
        }

        if (this.magicNumber > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
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

        this.baseMagicNumber = this.magicNumber = (equalPoint / DEXTERITY_SCALE) * secondMagicNumber;

        if (this.magicNumber > 0)
        {
            this.rawDescription = (DESCRIPTION + EXTENDED_DESCRIPTION[0]);
            initializeDescription();
        }
    }

    public void onMoveToDiscard()
    {
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        this.retain = true;
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
            this.upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }

}