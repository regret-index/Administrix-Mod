package administrix.cards.uncommon.attack;

import administrix.cards.AbstractAdministrixCard;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;
import com.megacrit.cardcrawl.vfx.combat.FlyingSpikeEffect;

import static administrix.AdministrixMod.*;
import static administrix.patches.CardTagsEnum.PLOT;

public class RisingSunPrince extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:RisingSunPrince";
    public static final String NAME = "Rising Sun's Prince";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.ENEMY;
    private static final CardType type = CardType.ATTACK;
    private static final int ATTACK_DMG = 4;
    private static final int UPGRADE_ATTACK_DMG = 2;
    private static final int STR_AMOUNT = 2;
    private static final int UPGRADE_STR_AMOUNT = 1;

    public RisingSunPrince() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.RISING_SUN_PRINCE, COST,
                CARD_STRINGS.DESCRIPTION,
                type, AbstractCardEnum.LichGold,
                rarity, target);
        this.tags.add(PLOT);
        setOrbTexture(ENERGY_ORB_LICH_GOLD_PLOT, ENERGY_ORB_LICH_GOLD_PORTRAIT);
        setBackgroundTexture(ATTACK_LICH_GOLD_PLOT, ATTACK_LICH_GOLD_PLOT_PORTRAIT);
        this.baseDamage = this.damage = ATTACK_DMG;
        this.baseMagicNumber = this.magicNumber = STR_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
        for(int i = 0; i < 12; i++) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new FlyingSpikeEffect(m.hb.cX - MathUtils.random(-40.0F, 40.0F) * Settings.scale, m.hb.cY + MathUtils.random(-80.0F, 80.0F) * Settings.scale, 45.0F + MathUtils.random(15.0F, 75.0F), 0.0F, MathUtils.random(200.0F, 50.0F) * Settings.scale, Color.GOLD.cpy())));
        }
    }

    @Override
    public void plotEffect() {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, new StrengthPower(mo, -this.magicNumber), -this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
            if (mo != null && !mo.hasPower("Artifact")) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, new GainStrengthPower(mo, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                for(int i = 0; i < 4; i++) {
                    AbstractDungeon.effectsQueue.add(new FlyingSpikeEffect(mo.hb.cX - MathUtils.random(-120.0F, 120.0F) * Settings.scale, mo.hb.cY - MathUtils.random(80.0F, 120.0F) * Settings.scale, 45.0F + MathUtils.random(15.0F, 75.0F), 0.0F, MathUtils.random(200.0F, 50.0F) * Settings.scale, Color.GOLD.cpy()));
                    AbstractDungeon.effectsQueue.add(new FlyingSpikeEffect(mo.hb.cX - MathUtils.random(-120.0F, 120.0F) * Settings.scale, mo.hb.cY + MathUtils.random(40.0F, 80.0F) * Settings.scale, 45.0F + MathUtils.random(15.0F, 75.0F), 0.0F, MathUtils.random(200.0F, 50.0F) * Settings.scale, Color.GOLD.cpy()));
                }
            }
        }
    }

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
    }

    @Override
    public AbstractCard makeCopy() {
        return new RisingSunPrince();
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.upgradeDamage((UPGRADE_ATTACK_DMG));
            this.upgradeMagicNumber(UPGRADE_STR_AMOUNT);
        }
    }

}