package administrix.cards.common.skill;

import administrix.cards.AbstractAdministrixCard;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;

import static administrix.AdministrixMod.*;

public class Transition extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:Transition";
    public static final String NAME = "Transition";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int ENERGY_AMOUNT = 1;
    private static final int DISCARD_AMOUNT = 2;
    private static final int DRAW_AMOUNT = 1;
    private static final CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.SELF;

    public Transition() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.TRANSITION, COST,
                CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.LichGold,
                rarity, target);
        setOrbTexture(ENERGY_ORB_LICH_GOLD_PLOT, ENERGY_ORB_LICH_GOLD_PORTRAIT);
        setBackgroundTexture(SKILL_LICH_GOLD_PLOT, SKILL_LICH_GOLD_PLOT_PORTRAIT);
        this.baseMagicNumber = this.magicNumber = ENERGY_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, DISCARD_AMOUNT, false));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EnergizedBluePower(p, this.magicNumber), this.magicNumber));
    }

    public void triggerWhenDrawn()
    {
        this.superFlash();
        for (int i = 0; i < mastermindCheck(); i++) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DrawCardNextTurnPower(AbstractDungeon.player, DRAW_AMOUNT), DRAW_AMOUNT));
        }
    }

    public void triggerOnManualDiscard()
    {
        this.superFlash();
        for (int i = 0; i < mastermindCheck(); i++) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DrawCardNextTurnPower(AbstractDungeon.player, DRAW_AMOUNT), DRAW_AMOUNT));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Transition();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            this.upgradeName();
            upgradeBaseCost(0);
        }
    }

}
