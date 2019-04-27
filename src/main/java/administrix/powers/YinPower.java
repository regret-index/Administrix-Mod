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
            boolean stop = false;

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

            float lX = leftmost.hb.cX * Settings.scale;
            float lY = leftmost.hb.cY * Settings.scale;
            float rX = rightmost.hb.cX * Settings.scale;
            float rY = rightmost.hb.cY * Settings.scale;

            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_DEFECT_BEAM"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new AnimatedSlashEffect(lX + 500.0f * Settings.scale, lY, (rX - lX) * 4.0F, -15.0F * Settings.scale, 105.0F, 4.0F, Color.PURPLE.cpy(), Color.PURPLE.cpy())));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new AnimatedSlashEffect(lX + 320.0F * Settings.scale, lY, (rX - lX) * 4.0F, -60.0F * Settings.scale, 105.0F, 4.0F, Color.PURPLE.cpy(), Color.PURPLE.cpy())));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(toDamage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new AnimatedSlashEffect(rX, rY, (lX - rX) * 4.0F, 15.F * Settings.scale, 75.0F, 4.0F, Color.PURPLE.cpy(), Color.PURPLE.cpy())));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new AnimatedSlashEffect(rX + 180.0F * Settings.scale, rY, (lX - rX) * 4.0F, 60.0F * Settings.scale, 75.0F, 4.0F, Color.PURPLE.cpy(), Color.PURPLE.cpy())));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(toDamage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
        }

        super.stackPower(amount);
    }

    public void updateDescription()
    {
        int power = (this.owner.hasPower(AffinityPower.POWER_ID)) ?
                     this.owner.getPower(AffinityPower.POWER_ID).amount + 1 : 1;
        this.description = DESCRIPTIONS[0] + power + DESCRIPTIONS[1];

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