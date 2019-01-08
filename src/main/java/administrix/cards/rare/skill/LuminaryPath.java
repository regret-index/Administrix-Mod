package administrix.cards.rare.skill;

import administrix.cards.AbstractAdministrixCard;
import administrix.powers.YangPower;
import administrix.powers.YinPower;
import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;

public class LuminaryPath extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:LuminaryPath";
    public static final String NAME = "Luminary's Path";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    private static final int FAKE_DIVISION_POINT = 0;
    private static final int BASE_DIVISION_POINT = 10;
    private static final int UPGRADE_DIVISION_POINT = 8;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.SELF;
    private static final CardType type = CardType.SKILL;

    public LuminaryPath() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.LUMINARY_PATH, COST,
                CARD_STRINGS.DESCRIPTION, type,
                AbstractCardEnum.LichGold,
                rarity, target);
        this.secondMagicNumber = this.baseSecondMagicNumber = FAKE_DIVISION_POINT;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        // Discard your hand, then draw and gain energy equal to
        // the difference between one's yin and yang divided.
        int divideBy = (this.upgraded) ? UPGRADE_DIVISION_POINT : BASE_DIVISION_POINT;
        int yinAmount = p.hasPower(YinPower.POWER_ID) ?
                        p.getPower(YinPower.POWER_ID).amount : 0;
        int yangAmount = p.hasPower(YangPower.POWER_ID) ?
                         p.getPower(YangPower.POWER_ID).amount : 0;
        int difference = (Math.abs(yinAmount - yangAmount)) / divideBy;

        int count = BaseMod.MAX_HAND_SIZE;
        if (count != 0)
        {
            AbstractDungeon.actionManager.addToTop(new DiscardAction(p, p, count, true));
        }

        if (difference > 0) {
            if (yinAmount > yangAmount) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new VerticalAuraEffect(Color.WHITE, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.33F));
            } else if (yangAmount > yinAmount) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new VerticalAuraEffect(Color.BLACK, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.33F));
            }
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(difference));
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, difference));
        }
    }

    @Override
    public void applyPowers()
    {
        super.applyPowers();

        int divideBy = (this.upgraded) ? UPGRADE_DIVISION_POINT : BASE_DIVISION_POINT;
        int yinAmount = AbstractDungeon.player.hasPower(YinPower.POWER_ID) ?
                        AbstractDungeon.player.getPower(YinPower.POWER_ID).amount : 0;
        int yangAmount = AbstractDungeon.player.hasPower(YangPower.POWER_ID) ?
                         AbstractDungeon.player.getPower(YangPower.POWER_ID).amount : 0;
        int difference = (Math.abs(yinAmount - yangAmount)) / divideBy;

        this.baseMagicNumber = this.magicNumber = difference;

        if (this.magicNumber == 1)
        {
            if (this.upgraded) {
                this.rawDescription = (UPGRADE_DESCRIPTION + EXTENDED_DESCRIPTION[0]);
            } else {
                this.rawDescription = (DESCRIPTION + EXTENDED_DESCRIPTION[0]);
            }
            initializeDescription();
        } else if (this.magicNumber > 1) {
            if (this.upgraded) {
                this.rawDescription = (UPGRADE_DESCRIPTION + EXTENDED_DESCRIPTION[1]);
            } else {
                this.rawDescription = (DESCRIPTION + EXTENDED_DESCRIPTION[1]);
            }
            initializeDescription();
        }
    }

    @Override
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
    public AbstractCard makeCopy() {
        return new LuminaryPath();
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.upgradeSecondMagicNumber(UPGRADE_DIVISION_POINT);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}