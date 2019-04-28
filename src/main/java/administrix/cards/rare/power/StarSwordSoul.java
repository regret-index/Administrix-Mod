package administrix.cards.rare.power;

import administrix.cards.AbstractAdministrixCard;
import administrix.vfx.SpiritFlameEffect;
import basemod.abstracts.CustomCard;
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
import administrix.powers.StarSwordSoulPower;

public class StarSwordSoul extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:StarSwordSoul";
    public static final String NAME = "Phylactery Form";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    public static final String UPGRADE_NAME = CARD_STRINGS.EXTENDED_DESCRIPTION[0];
    private static final int COST = 3;
    private static final int STAT_AMOUNT = 1;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardType type = CardType.POWER;

    public StarSwordSoul() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.STAR_SWORD_SOUL, COST,
                CARD_STRINGS.DESCRIPTION,
                type, AbstractCardEnum.LichGold,
                rarity, CardTarget.SELF);
        this.baseMagicNumber = this.magicNumber = STAT_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SpiritFlameEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.05F));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("STARRY-BEAT"));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StarSwordSoulPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new StarSwordSoul();
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        if (this.upgraded) { this.retain = true; }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.name = UPGRADE_NAME;
            this.upgraded = true;
            initializeTitle();
            this.isInnate = true;
            this.retain = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}