package administrix.cards.starter;

import administrix.cards.AbstractAdministrixCard;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
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

public class RedCloak extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:RedCloak";
    public static final String NAME = "Red Cloak";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 0;
    private static final int BLOCK_AMOUNT = 3;
    private static final int UPGRADE_BLOCK_DMG = 2;
    private static final int DRAW_AMOUNT = 1;
    private static final int DISCARD_AMOUNT = 1;
    private static final int VULN_AMOUNT = 1;
    private static final CardRarity rarity = CardRarity.BASIC;
    private static final CardTarget target = CardTarget.SELF;

    public RedCloak() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.RED_CLOAK, COST,
                CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.LichGold,
                rarity, target);
        setOrbTexture(ENERGY_ORB_LICH_GOLD_PLOT, ENERGY_ORB_LICH_GOLD_PORTRAIT);
        setBackgroundTexture(SKILL_LICH_GOLD_PLOT, SKILL_LICH_GOLD_PLOT_PORTRAIT);
        this.baseBlock = this.block = BLOCK_AMOUNT;
        this.baseMagicNumber = VULN_AMOUNT;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, DRAW_AMOUNT));
        AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, DISCARD_AMOUNT, false));
    }


    public void triggerWhenDrawn()
    {
        this.superFlash();
        for (int i = 0; i < mastermindCheck(); i++) {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, new VulnerablePower(mo, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
            }
        }
    }

    public void triggerOnManualDiscard()
    {
        this.superFlash();
        for (int i = 0; i < mastermindCheck(); i++) {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, new VulnerablePower(mo, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new RedCloak();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeBlock((UPGRADE_BLOCK_DMG));
        }
    }

}
