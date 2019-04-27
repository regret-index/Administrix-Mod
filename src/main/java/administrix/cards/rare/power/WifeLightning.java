package administrix.cards.rare.power;

import administrix.cards.AbstractAdministrixCard;
import administrix.vfx.SpiritFlameEffect;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;
import administrix.powers.LightningEmbracePower;

public class WifeLightning extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:WifeLightning";
    public static final String NAME = "Wife's Lightning";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static final int ARMOR_AMOUNT = 2;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardType type = CardType.POWER;

    public WifeLightning() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.WIFE_LIGHTNING, COST,
                CARD_STRINGS.DESCRIPTION,
                type, AbstractCardEnum.LichGold,
                rarity, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = ARMOR_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SpiritFlameEffect(AbstractDungeon.player.hb.cX - 30.0F * Settings.scale, AbstractDungeon.player.hb.cY - 15.0F * Settings.scale, new Color(0.20F, 0.35F, 0.3F, 1.0F)), 0.05F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SpiritFlameEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY - 60.0F * Settings.scale, new Color(0.05F, 0.5F, 0.1F, 1.0F)), 0.05F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SpiritFlameEffect(AbstractDungeon.player.hb.cX - 60.0F * Settings.scale, AbstractDungeon.player.hb.cY - 50.0F * Settings.scale, new Color(0.30F, 0.50F, 0.45F, 1.0F)), 0.05F));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("THUNDERCLAP", 0.05F));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.8F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LightningEmbracePower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new WifeLightning();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.isInnate = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}