package administrix.cards.common.attack;

import administrix.AdministrixMod;
import administrix.cards.AbstractAdministrixCard;
import administrix.patches.AbstractCardEnum;
import administrix.powers.YangPower;
import administrix.powers.YinPower;
import administrix.vfx.SpiritFlameEffect;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.FlyingSpikeEffect;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbPassiveEffect;

public class NoblePath extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:NoblePath";
    public static final String NAME = "Noble Path";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.SELF_AND_ENEMY;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 8;
    private static final int TAIJITU_AMOUNT = 3;
    private static final int ENERGY_AMOUNT = 1;

    public NoblePath() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.NOBLE_PATH, COST,
                CARD_STRINGS.DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.LichGold,
                rarity, target);
        this.baseDamage = this.damage = ATTACK_DMG;
        this.baseMagicNumber = this.magicNumber = TAIJITU_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new YinPower(p, this.magicNumber), this.magicNumber));
        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new YinPower(p, this.magicNumber), this.magicNumber));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new YangPower(p, this.magicNumber), this.magicNumber));
        }
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new YangPower(p, this.magicNumber), this.magicNumber));

        for (float i = 1.0F; i < 9.0F; i += 1.0F) {
            AbstractDungeon.effectsQueue.add(new DarkOrbPassiveEffect(m.hb.cX + MathUtils.random(-40F, 40F), (float)Settings.HEIGHT / 9.0F * i));
            AbstractDungeon.effectsQueue.add(new DarkOrbPassiveEffect(m.hb.cX + MathUtils.random(-40F, 40F), (float)Settings.HEIGHT / 9.0F * i));
            if (this.upgraded) {
                AbstractDungeon.effectsQueue.add(new DarkOrbPassiveEffect(m.hb.cX + MathUtils.random(-40F, 40F), (float)Settings.HEIGHT / 9.0F * i));
                AbstractDungeon.effectsQueue.add(new PlasmaOrbPassiveEffect(m.hb.cX + MathUtils.random(-40F, 40F), (float)Settings.HEIGHT / 9.0F * i));
            }
            AbstractDungeon.effectsQueue.add(new PlasmaOrbPassiveEffect(m.hb.cX + MathUtils.random(-40F, 40F), (float)Settings.HEIGHT / 9.0F * i));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new FlyingSpikeEffect(m.hb.cX + MathUtils.random(-40.0F, 40.0F) * Settings.scale, (float)Settings.HEIGHT / 9.0F * i, 45.0F + MathUtils.random(15.0F, 75.0F), 0.0F, MathUtils.random(200.0F, 50.0F) * Settings.scale, Color.GOLD.cpy())));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new NoblePath();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}