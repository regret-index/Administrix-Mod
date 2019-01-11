package administrix.powers;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import administrix.relics.AlphaAttackCounter;
import administrix.relics.AlphaSkillCounter;

import static administrix.AdministrixMod.ADMIN_POWERS_ATLAS;

public class DualityPower extends AbstractPower {
    public static final String POWER_ID = "AdministrixMod:Duality";
    private static PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int attackCount = 0;
    private int skillCount = 0;

    public DualityPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = powerStrings.NAME;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.region48 = ADMIN_POWERS_ATLAS.findRegion("duality_48");
        this.region128 = ADMIN_POWERS_ATLAS.findRegion("duality");
    }

    public void atStartOfTurn() {
        attackCount = 0;
        skillCount = 0;
    }

    public void onAfterCardPlayed(AbstractCard c) { updateDescription(); }

    public void atEndOfRound() {
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (c.type == AbstractCard.CardType.ATTACK) {
                attackCount++;
            } else if (c.type == AbstractCard.CardType.SKILL) {
                skillCount++;
            }
        }
        if (attackCount == skillCount && attackCount != 0) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new VerticalAuraEffect(Color.PURPLE, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.33F));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_FIRE"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new VerticalAuraEffect(Color.GOLD, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.33F));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, this.amount), this.amount));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, this.amount), this.amount));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoseStrengthPower(AbstractDungeon.player, this.amount), this.amount));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoseDexterityPower(AbstractDungeon.player, this.amount), this.amount));
        }
    }

    public void updateDescription()
    {
        attackCount = (AbstractDungeon.player.hasRelic(AlphaAttackCounter.ID)) ?
                       AbstractDungeon.player.getRelic(AlphaAttackCounter.ID).counter : 0;
        skillCount = (AbstractDungeon.player.hasRelic(AlphaSkillCounter.ID)) ?
                      AbstractDungeon.player.getRelic(AlphaSkillCounter.ID).counter : 0;

        if (attackCount == 1 && skillCount == 1)
        {
            this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] +
                                DESCRIPTIONS[2] + attackCount +
                                DESCRIPTIONS[4] + skillCount +
                                DESCRIPTIONS[6]);
        }
        else if (attackCount != 1 && skillCount == 1)
        {
            this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] +
                                DESCRIPTIONS[2] + attackCount +
                                DESCRIPTIONS[3] + skillCount +
                                DESCRIPTIONS[6]);
        }
        else if (attackCount == 1 && skillCount != 1)
        {
            this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] +
                                DESCRIPTIONS[2] + attackCount +
                                DESCRIPTIONS[4] + skillCount +
                                DESCRIPTIONS[5]);
        }
        else
        {
            this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] +
                                DESCRIPTIONS[2] + attackCount +
                                DESCRIPTIONS[3] + skillCount +
                                DESCRIPTIONS[5]);
        }
    }

}
