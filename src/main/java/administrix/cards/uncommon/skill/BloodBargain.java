package administrix.cards.uncommon.skill;

import administrix.cards.AbstractAdministrixCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;
import com.megacrit.cardcrawl.vfx.combat.*;

public class BloodBargain extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:BloodBargain";
    public static final String NAME = "Blood Bargain";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
    private static final CardType type = CardType.SKILL;
    private static final int DEBUFF_AMOUNT = 2;
    private static final int BUFF_AMOUNT = 3;

    public BloodBargain() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.BLOOD_BARGAIN,
                COST, CARD_STRINGS.DESCRIPTION,
                type, AbstractCardEnum.LichGold,
                rarity, TARGET);
        this.magicNumber = this.baseMagicNumber = DEBUFF_AMOUNT;
        this.secondMagicNumber = this.baseSecondMagicNumber = BUFF_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        // Apply the vfx and Vulnerable to target enemy, upgrade to all enemies.
        boolean flip = true;
        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new BorderLongFlashEffect(Color.RED), 1.2F));
            AbstractDungeon.effectsQueue.add(new RoomTintEffect(Color.BLACK.cpy(), 0.8F, 1.2F, true));
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (mo != null && !mo.isDeadOrEscaped()) {
                    Color intent = ((mo.intent == AbstractMonster.Intent.ATTACK) || (mo.intent == AbstractMonster.Intent.ATTACK_BUFF) || (mo.intent == AbstractMonster.Intent.ATTACK_DEBUFF) || (mo.intent == AbstractMonster.Intent.ATTACK_DEFEND)) ? Color.RED.cpy() : Color.PURPLE.cpy();

                    for (float i = 9.0F; i > 1.0F; i -= 1.0F) {
                        AbstractDungeon.effectsQueue.add(new DarkOrbPassiveEffect(mo.hb.cX + MathUtils.random(-40F, 40F), (float)Settings.HEIGHT / 9.0F * i));
                        AbstractDungeon.effectsQueue.add(new DarkOrbPassiveEffect(mo.hb.cX + MathUtils.random(-40F, 40F), (float)Settings.HEIGHT / 9.0F * i));
                        AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(mo.hb.cX + ((flip) ? 40.0F * Settings.scale : -40.0F * Settings.scale), (float)Settings.HEIGHT / 9.0F * i, 0.0F, 40.0F * Settings.scale, ((flip) ? 30.0F : 330.0F), 1.2F, Color.PURPLE.cpy(), intent));
                        flip = !flip;
                        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.05F));
                    }

                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new VulnerablePower(mo, this.magicNumber, false), this.magicNumber));
                }

                flip = !flip;
            }
        } else {
            Color intent = ((m.intent == AbstractMonster.Intent.ATTACK) || (m.intent == AbstractMonster.Intent.ATTACK_BUFF) || (m.intent == AbstractMonster.Intent.ATTACK_DEBUFF) || (m.intent == AbstractMonster.Intent.ATTACK_DEFEND)) ? Color.RED.cpy() : Color.PURPLE.cpy();

            AbstractDungeon.effectsQueue.add(new RoomTintEffect(Color.BLACK.cpy(), 0.8F, 0.8F, true));
            for (float i = 9.0F; i > 1.0F; i -= 1.0F) {
                AbstractDungeon.effectsQueue.add(new DarkOrbPassiveEffect(m.hb.cX + MathUtils.random(-40F, 40F), (float)Settings.HEIGHT / 9.0F * i));
                AbstractDungeon.effectsQueue.add(new DarkOrbPassiveEffect(m.hb.cX + MathUtils.random(-40F, 40F), (float)Settings.HEIGHT / 9.0F * i));
                AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(m.hb.cX + ((flip) ? 40.0F * Settings.scale : -40.0F * Settings.scale), (float)Settings.HEIGHT / 9.0F * i, 0.0F, 40.0F * Settings.scale, ((flip) ? 30.0F : 330.0F), 1.2F, Color.PURPLE.cpy(), intent));
                flip = !flip;
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.05F));
            }

            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
            flip = !flip;
        }

        // Debuff the Administrix.
        Color art = (p.hasPower("Artifact")) ? Color.PURPLE.cpy() : Color.RED.cpy();
        for (float i = 9.0F; i > 1.0F; i -= 1.0F) {
            AbstractDungeon.effectsQueue.add(new DarkOrbPassiveEffect(p.hb.cX + MathUtils.random(-40F, 40F), (float)Settings.HEIGHT / 9.0F * i));
            AbstractDungeon.effectsQueue.add(new DarkOrbPassiveEffect(p.hb.cX + MathUtils.random(-40F, 40F), (float)Settings.HEIGHT / 9.0F * i));
            AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(p.hb.cX + ((flip) ? 40.0F * Settings.scale : -40.0F * Settings.scale), (float)Settings.HEIGHT / 9.0F * i, 0.0F, 40.0F * Settings.scale, ((flip) ? 30.0F : 330.0F), 1.2F, Color.PURPLE.cpy(), art));
            flip = !flip;
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FrailPower(p, this.magicNumber, false), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.8F));

        // Check each enemy if upgraded, otherwise check single target;
        // for each attacking, gain Thorns and Armor.
        if (this.upgraded) {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if ((mo != null) && !mo.isDeadOrEscaped() && ((mo.intent == AbstractMonster.Intent.ATTACK) || (mo.intent == AbstractMonster.Intent.ATTACK_BUFF) || (mo.intent == AbstractMonster.Intent.ATTACK_DEBUFF) || (mo.intent == AbstractMonster.Intent.ATTACK_DEFEND))) {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new WaterDropEffect(mo.hb.cX, mo.hb.cY - 50.0F * Settings.scale)));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThornsPower(p, this.secondMagicNumber), this.secondMagicNumber));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, this.secondMagicNumber), this.secondMagicNumber));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
                }
            }
        } else {
            if ((m != null) && ((m.intent == AbstractMonster.Intent.ATTACK) || (m.intent == AbstractMonster.Intent.ATTACK_BUFF) || (m.intent == AbstractMonster.Intent.ATTACK_DEBUFF) || (m.intent == AbstractMonster.Intent.ATTACK_DEFEND))) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new WaterDropEffect(m.hb.cX, m.hb.cY - 50.0F * Settings.scale)));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThornsPower(p, this.secondMagicNumber), this.secondMagicNumber));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, this.secondMagicNumber), this.secondMagicNumber));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BloodBargain();
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.target = CardTarget.ALL;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}