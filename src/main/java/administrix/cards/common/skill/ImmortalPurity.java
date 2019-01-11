package administrix.cards.common.skill;

import administrix.cards.AbstractAdministrixCard;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import administrix.AdministrixMod;
import administrix.actions.ImmortalUpgradeAction;
import administrix.patches.AbstractCardEnum;

import static administrix.AdministrixMod.*;

public class ImmortalPurity extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:ImmortalPurity";
    public static final String NAME = "Immortal Purity";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK_AMOUNT = 6;
    private static final int UPGRADE_BLOCK_AMOUNT = 1;
    private static final int DISCARD_AMOUNT = 1;
    private static final CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.SELF;

    public ImmortalPurity() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.IMMORTAL_PURITY,
                COST, CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.LichGold,
                rarity, target);
        setOrbTexture(ENERGY_ORB_LICH_GOLD_PLOT, ENERGY_ORB_LICH_GOLD_PORTRAIT);
        setBackgroundTexture(SKILL_LICH_GOLD_PLOT, SKILL_LICH_GOLD_PLOT_PORTRAIT);
        this.baseBlock = this.block = BLOCK_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, DISCARD_AMOUNT, false));
    }

    public void triggerWhenDrawn()
    {
        this.superFlash();
        for (int i = 0; i < mastermindCheck(); i++) {
            AbstractDungeon.actionManager.addToBottom(new ImmortalUpgradeAction(this.upgraded));
        }
    }

    public void triggerOnManualDiscard()
    {
        this.superFlash();
        for (int i = 0; i < mastermindCheck(); i++) {
            AbstractDungeon.actionManager.addToBottom(new ImmortalUpgradeAction(this.upgraded));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ImmortalPurity();
    }

    @Override
    public void upgrade() {
        this.upgradeBlock(UPGRADE_BLOCK_AMOUNT);
        this.timesUpgraded += 1;
        this.upgraded = true;
        this.name = (CARD_STRINGS.NAME + "+" + this.timesUpgraded);
        initializeTitle();
        this.rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    public boolean canUpgrade()
    {
        return true;
    }

}
