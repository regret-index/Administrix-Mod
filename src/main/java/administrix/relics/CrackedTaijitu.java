package administrix.relics;

import administrix.AdministrixMod;
import administrix.powers.AffinityPower;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class CrackedTaijitu extends CustomRelic {
    public static final String ID = "AdministrixMod:CrackedTaijitu";
    public static final RelicStrings STRINGS = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String[] DESCRIPTIONS = STRINGS.DESCRIPTIONS;
    private static final int STAT_AMOUNT = 1;

    public CrackedTaijitu() {
        super(ID, new Texture(AdministrixMod.CRACKED_TAIJITU), RelicTier.COMMON, LandingSound.SOLID);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + STAT_AMOUNT + this.DESCRIPTIONS[1];
    }

    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new AffinityPower(AbstractDungeon.player, STAT_AMOUNT), STAT_AMOUNT));
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public AbstractRelic makeCopy() { return new CrackedTaijitu(); }
}
