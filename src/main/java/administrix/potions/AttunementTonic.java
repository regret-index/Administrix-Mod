package administrix.potions;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.EnergyPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import administrix.powers.AffinityPower;
import administrix.powers.DualityPower;

public class AttunementTonic extends AbstractPotion {
    public static final String POTION_ID = "AdministrixMod:AttunementTonic";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    private static final PowerStrings affinityStrings = CardCrawlGame.languagePack.getPowerStrings(AffinityPower.POWER_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public AttunementTonic() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.M, PotionColor.SMOKE);
        this.potency = this.getPotency();
        this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        this.isThrown = false;
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip((TipHelper.capitalize(affinityStrings.NAME)),
                     (String)GameDictionary.keywords.get("affinity")));
    }

    @Override
    public void use(AbstractCreature target) {
        target = AbstractDungeon.player;
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
        {
            if (AbstractDungeon.player.hasPower(AffinityPower.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new AffinityPower(target, this.potency), this.potency));
            }
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new EnergyPotion();
    }

    @Override
    public int getPotency(int ascensionLevel) {
        // return ascensionLevel < 11 ? 3 : 2;
        return 3;
    }
}
