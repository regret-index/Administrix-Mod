package administrix.cards.rare.power;

import administrix.cards.AbstractAdministrixCard;
import administrix.powers.PermanentDrawReduction;
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
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;
import administrix.powers.GlamourPlusPower;
import administrix.powers.GlamourPower;

public class DeathByGlamour extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:DeathByGlamour";
    public static final String NAME = "Death By Glamour";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final int COST = 3;
    private static final int CARD_COUNT = 1;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardType type = CardType.POWER;

    public DeathByGlamour() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.DEATH_BY_GLAMOUR, COST,
                CARD_STRINGS.DESCRIPTION,
                type, AbstractCardEnum.LichGold,
                rarity, CardTarget.SELF);
        this.baseMagicNumber = this.magicNumber = CARD_COUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SpiritFlameEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, new Color(0.9F, 0.1F, 0.8F, 0.0F)), 0.05F));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("STARRY-BEAT"));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new GlamourPlusPower(p, this.magicNumber), this.magicNumber));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new GlamourPower(p, this.magicNumber), this.magicNumber));
        }
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PermanentDrawReduction(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new DeathByGlamour();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}