package administrix.powers;

import administrix.vfx.SpiritFlameEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static administrix.AdministrixMod.ADMIN_POWERS_ATLAS;

public class StarSwordSoulPower extends AbstractPower {
    public static final String POWER_ID = "AdministrixMod:StarSwordSoulPower";
    private static PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public StarSwordSoulPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = powerStrings.NAME;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.region48 = ADMIN_POWERS_ATLAS.findRegion("star_sword_soul_48");
        this.region128 = ADMIN_POWERS_ATLAS.findRegion("star_sword_soul");
        AbstractDungeon.player.gameHandSize += amount;
    }

    public void atStartOfTurn()
    {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new SpiritFlameEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, new Color(0.9F, 0.1F, 0.8F, 0.0F)), 0.05F));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new DualityPower(this.owner, this.amount), this.amount, true, AbstractGameAction.AttackEffect.NONE));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new AffinityPower(this.owner, this.amount), this.amount, true, AbstractGameAction.AttackEffect.NONE));
        }
    }

    public void updateDescription()
    {
        if (this.amount > 1) {
            this.description = (DESCRIPTIONS[0] + this.amount +
                                DESCRIPTIONS[1] + this.amount +
                                DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3]);
        } else {
            this.description = (DESCRIPTIONS[0] + this.amount +
                                DESCRIPTIONS[1] + this.amount +
                                DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[4]);
        }
    }

}