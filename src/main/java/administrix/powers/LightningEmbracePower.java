package administrix.powers;

import administrix.vfx.SpiritFlameEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import static administrix.AdministrixMod.ADMIN_POWERS_ATLAS;

public class LightningEmbracePower extends AbstractPower {
    public static final String POWER_ID = "AdministrixMod:LightningEmbrace";
    private static PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static int armorAmount;
    private static final int THRESHOLD = 3;

    public LightningEmbracePower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = powerStrings.NAME;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.region48 = ADMIN_POWERS_ATLAS.findRegion("lightning_embrace_48");
        this.region128 = ADMIN_POWERS_ATLAS.findRegion("lightning_embrace");
    }

    @Override
    public void onInitialApplication() {
        this.armorAmount = this.amount;
        this.amount = THRESHOLD;
        this.updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.armorAmount += stackAmount;
        this.updateDescription();
    }

    public void onAfterCardPlayed(AbstractCard c)
    {
        if (c.type == AbstractCard.CardType.ATTACK) {
            this.amount--;
        }
        if (this.amount <= 0) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new SpiritFlameEffect(AbstractDungeon.player.hb.cX - 30.0F * Settings.scale, AbstractDungeon.player.hb.cY - 15.0F * Settings.scale, new Color(0.20F, 0.35F, 0.3F, 1.0F)), 0.05F));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new SpiritFlameEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY - 60.0F * Settings.scale, new Color(0.05F, 0.5F, 0.1F, 1.0F)), 0.05F));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new SpiritFlameEffect(AbstractDungeon.player.hb.cX - 60.0F * Settings.scale, AbstractDungeon.player.hb.cY - 50.0F * Settings.scale, new Color(0.30F, 0.50F, 0.45F, 1.0F)), 0.05F));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
            flash();
            AbstractDungeon.actionManager.addToBottom(new SFXAction("THUNDERCLAP", 0.05F));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.05F));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PlatedArmorPower(AbstractDungeon.player, this.armorAmount), this.armorAmount));
            this.amount = THRESHOLD;
        }
        updateDescription();
    }

    public void updateDescription()
    {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + THRESHOLD + DESCRIPTIONS[1] +
                    this.armorAmount + DESCRIPTIONS[2] + DESCRIPTIONS[3] +
                    (this.amount) + DESCRIPTIONS[5];
        } else {
            this.description = DESCRIPTIONS[0] + THRESHOLD + DESCRIPTIONS[1] +
                    this.armorAmount + DESCRIPTIONS[2] + DESCRIPTIONS[3] +
                    (this.amount) + DESCRIPTIONS[4];
        }
    }

}