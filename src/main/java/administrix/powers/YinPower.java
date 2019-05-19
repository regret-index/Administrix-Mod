package administrix.powers;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.AnimatedSlashEffect;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import static administrix.AdministrixMod.ADMIN_POWERS_ATLAS;

// Yin also provides damage if it's higher than Yang,
// scaled to Affinity.

public class YinPower extends AbstractPower {
    public static final String POWER_ID = "AdministrixMod:Yin";
    private static PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public YinPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = powerStrings.NAME;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.region48 = ADMIN_POWERS_ATLAS.findRegion("yin_48");
        this.region128 = ADMIN_POWERS_ATLAS.findRegion("yin");
    }

    @Override
    public void stackPower(int amount)
    {
        if (!this.owner.hasPower(YangPower.POWER_ID) ||
             this.owner.getPower(YangPower.POWER_ID).amount < this.amount) {

            int toDamage = 1;

            if (this.owner.hasPower(AffinityPower.POWER_ID)) {
                this.owner.getPower(AffinityPower.POWER_ID).flash();
                toDamage += this.owner.getPower(AffinityPower.POWER_ID).amount;
            }

            AbstractMonster leftmost = null;
            AbstractMonster rightmost = null;

            for (AbstractMonster mon : AbstractDungeon.getCurrRoom().monsters.monsters)
            {
                if (!mon.isDeadOrEscaped()) {
                    if (leftmost == null) { leftmost = mon; }
                    if (rightmost == null) { rightmost = mon; }

                    if (mon.hb.cX < leftmost.hb.cX) {
                        leftmost = mon;
                    } else if (mon.hb.cX > rightmost.hb.cX) {
                        rightmost = mon;
                    }
                }
            }

            float lX = leftmost.hb.cX;
            float lY = leftmost.hb.cY;
            float rX = rightmost.hb.cX;
            float rY = rightmost.hb.cY;

            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_DEFECT_BEAM"));
            if (lX == rX) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new AnimatedSlashEffect(lX + 140.0F * Settings.scale, lY, 240F * Settings.scale, -25.0F * Settings.scale, 105.0F, 3.0F, Color.PURPLE.cpy(), Color.PURPLE.cpy())));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new AnimatedSlashEffect(lX - 40.0F * Settings.scale, lY, 240F * Settings.scale, -125.0F * Settings.scale, 105.0F, 3.0F, Color.PURPLE.cpy(), Color.PURPLE.cpy())));
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(toDamage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new AnimatedSlashEffect(rX - 60.0F * Settings.scale, rY, -240F * Settings.scale, 125.F * Settings.scale, 75.0F, 3.0F, Color.PURPLE.cpy(), Color.PURPLE.cpy())));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new AnimatedSlashEffect(rX + 120.0F * Settings.scale, rY, -240F * Settings.scale, 25.0F * Settings.scale, 75.0F, 3.0F, Color.PURPLE.cpy(), Color.PURPLE.cpy())));
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(toDamage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
            } else {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new AnimatedSlashEffect(lX + 600.0F * Settings.scale, lY, (rX - lX) * 2.8F, -25.0F * Settings.scale, 105.0F, 3.0F, Color.PURPLE.cpy(), Color.PURPLE.cpy())));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new AnimatedSlashEffect(lX + 420.0F * Settings.scale, lY, (rX - lX) * 2.8F, -125.0F * Settings.scale, 105.0F, 3.0F, Color.PURPLE.cpy(), Color.PURPLE.cpy())));
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(toDamage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new AnimatedSlashEffect(rX - 320.0F * Settings.scale, rY, (lX - rX) * 2.8F, 125.F * Settings.scale, 75.0F, 3.0F, Color.PURPLE.cpy(), Color.PURPLE.cpy())));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new AnimatedSlashEffect(rX - 500.0F * Settings.scale, rY, (lX - rX) * 2.8F, 25.0F * Settings.scale, 75.0F, 3.0F, Color.PURPLE.cpy(), Color.PURPLE.cpy())));
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(toDamage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
            }


        }

        super.stackPower(amount);
        this.updateDescription();
        if (this.owner.hasPower(YangPower.POWER_ID)) {
            this.owner.getPower(YangPower.POWER_ID).updateDescription();
        }
    }

    public void updateDescription()
    {
        int power = (this.owner.hasPower(AffinityPower.POWER_ID)) ?
                     this.owner.getPower(AffinityPower.POWER_ID).amount + 1 : 1;

        if (!this.owner.hasPower(YangPower.POWER_ID) ||
            this.owner.getPower(YangPower.POWER_ID).amount < this.amount) {
            this.description = DESCRIPTIONS[0] + power +
                               DESCRIPTIONS[1] + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0] + power +
                               DESCRIPTIONS[1] + DESCRIPTIONS[3];
        }
    }
}