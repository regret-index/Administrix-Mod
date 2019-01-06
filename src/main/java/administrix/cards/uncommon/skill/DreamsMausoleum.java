package administrix.cards.uncommon.skill;

import administrix.cards.AbstractAdministrixCard;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;

import static administrix.AdministrixMod.*;

// Ugh, this one's a mess of commented-out prior drafts.
// Plot to reduce this card's cost this turn.

public class DreamsMausoleum extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:DreamsMausoleum";
    public static final String NAME = "Dreams Mausoleum";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 4;
    private static final int BLOCK_AMOUNT = 10;
    private static final int UPGRADE_BLOCK_AMOUNT = 4;
    private static final int ARTIFACT_AMOUNT = 1;
    private static final int COST_REDUCTION = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF;

    public DreamsMausoleum() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.DREAMS_MAUSOLEUM, COST,
                CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.LichGold,
                rarity, target);
        setOrbTexture(ENERGY_ORB_LICH_GOLD_PLOT, ENERGY_ORB_LICH_GOLD_PORTRAIT);
        setBackgroundTexture(SKILL_LICH_GOLD_PLOT, SKILL_LICH_GOLD_PLOT_PORTRAIT);
        this.baseBlock = this.block = BLOCK_AMOUNT;
        this.baseMagicNumber = this.magicNumber = ARTIFACT_AMOUNT;
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, ARTIFACT_AMOUNT), ARTIFACT_AMOUNT, true, AbstractGameAction.AttackEffect.NONE));
    }

    public void triggerWhenDrawn()
    {
        if (this.cost > 0) {
            this.superFlash();
            for (int i = 0; i < mastermindCheck(); i++) {
                AbstractDungeon.actionManager.addToBottom(new ReduceCostAction(this.uuid, COST_REDUCTION));
            }
        }
        this.isCostModified = true;
    }

    public void triggerOnManualDiscard() {
        if (this.cost > 0) {
            this.superFlash();
            for (int i = 0; i < mastermindCheck(); i++) {
                AbstractDungeon.actionManager.addToBottom(new ReduceCostAction(this.uuid, COST_REDUCTION));
            }
        }
        this.isCostModified = true;
    }

    @Override
    public AbstractCard makeCopy() {
        return new DreamsMausoleum();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBlock(UPGRADE_BLOCK_AMOUNT);
            initializeDescription();
            /*
            if (this.cost < 4 ) {
                upgradeBaseCost(this.cost - 1);
                if (this.cost < 0) {
                    this.cost = 0;
                }
            }
            else {
                upgradeBaseCost(UPGRADE_COST_AMOUNT);
            }
           */
        }
    }

}