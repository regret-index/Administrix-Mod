package administrix.powers;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

import static administrix.AdministrixMod.ADMIN_POWERS_ATLAS;

public class FlamesOfFaithPower extends AbstractPower {
    public static final String POWER_ID = "AdministrixMod:FlamesOfFaith";
    private static PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static int attackCounter;
    private static final int THRESHOLD = 3;

    public FlamesOfFaithPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = powerStrings.NAME;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.region48 = ADMIN_POWERS_ATLAS.findRegion("flames_of_faith_48");
        this.region128 = ADMIN_POWERS_ATLAS.findRegion("flames_of_faith");
    }

    public void onInitialApplication() {
        attackCounter = 0;
    }

    public void onAfterCardPlayed(AbstractCard c)
    {
        if (c.type == AbstractCard.CardType.ATTACK) {
            attackCounter++;
        }
        if (attackCounter == THRESHOLD) {
            flash();
            if (Settings.FAST_MODE) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new InflameEffect(AbstractDungeon.player), 0.4F));
            } else {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new InflameEffect(AbstractDungeon.player), 0.8F));
            }
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ThornsPower(AbstractDungeon.player, this.amount), this.amount));
            attackCounter = 0;
        }
    }

    public void updateDescription()
    {
        this.description = (DESCRIPTIONS[0] + THRESHOLD +
                            DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2]);
    }

}