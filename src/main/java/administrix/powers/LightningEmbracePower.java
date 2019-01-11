package administrix.powers;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
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
    private static int attackCounter;
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

    public void onInitialApplication() {
        attackCounter = 0;
    }

    public void onAfterCardPlayed(AbstractCard c)
    {
        if (c.type == AbstractCard.CardType.ATTACK) {
            attackCounter++;
        }
        if (attackCounter >= THRESHOLD) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new SFXAction("THUNDERCLAP", 0.05F));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.05F));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PlatedArmorPower(AbstractDungeon.player, this.amount), this.amount));
            attackCounter = 0;
        }
        updateDescription();
    }

    public void updateDescription()
    {
        if (THRESHOLD - attackCounter == 1) {
            this.description = (DESCRIPTIONS[0] + THRESHOLD + DESCRIPTIONS[1] +
                    this.amount + DESCRIPTIONS[2] + DESCRIPTIONS[3] +
                    (THRESHOLD - attackCounter)) + DESCRIPTIONS[5];
        } else {
            this.description = (DESCRIPTIONS[0] + THRESHOLD + DESCRIPTIONS[1] +
                    this.amount + DESCRIPTIONS[2] + DESCRIPTIONS[3] +
                    (THRESHOLD - attackCounter)) + DESCRIPTIONS[4];
        }
    }

}