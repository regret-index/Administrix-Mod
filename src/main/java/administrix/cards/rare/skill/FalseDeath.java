package administrix.cards.rare.skill;

import administrix.AdministrixMod;
import administrix.actions.DiscardToDraw;
import administrix.cards.AbstractAdministrixCard;
import administrix.patches.AbstractCardEnum;
import administrix.powers.WiltingPower;
import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;

public class FalseDeath extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:FalseDeath";
    public static final String NAME = "False Death";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.SELF;
    private static final CardType type = CardType.SKILL;
    private static final int COST = 2;
    private static final int TUTOR_AMOUNT = 2;
    private static final int UPGRADE_TUTOR_AMOUNT = 1;
    private static final int WILTING_AMOUNT = 3;

    public FalseDeath() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.FALSE_DEATH, COST,
                CARD_STRINGS.DESCRIPTION, type,
                AbstractCardEnum.LichGold,
                rarity, target);
        this.magicNumber = this.baseMagicNumber = TUTOR_AMOUNT;
        this.exhaust = true;
    }

    // Discard the draw pile, tutor cards from discard to draw pile, wilt.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("HERMIT-HUM"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new BorderLongFlashEffect(Color.PURPLE), 0.4F));

        if (!AbstractDungeon.player.drawPile.group.isEmpty()) {
            while (!p.drawPile.isEmpty()) {
                AbstractCard c = p.drawPile.getTopCard();
                p.drawPile.moveToDiscardPile(c);
                GameActionManager.incrementDiscard(false);
                c.triggerOnManualDiscard();
                p.drawPile.removeCard(c);
            }
        }
        AbstractDungeon.actionManager.addToBottom(new DiscardToDraw(this.magicNumber, p));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WiltingPower(p, WILTING_AMOUNT, false), WILTING_AMOUNT));
    }

    @Override
    public AbstractCard makeCopy() {
        return new FalseDeath();
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_TUTOR_AMOUNT);
        }
    }

}