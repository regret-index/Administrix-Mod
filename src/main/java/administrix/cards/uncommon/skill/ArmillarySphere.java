package administrix.cards.uncommon.skill;

import administrix.cards.AbstractAdministrixCard;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;

import static administrix.AdministrixMod.*;
import static administrix.patches.CardTagsEnum.PLOT;

public class ArmillarySphere extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:ArmillarySphere";
    public static final String NAME = "Armillary Sphere";
    public static final CardStrings CARD_STRINGS =
        CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 2;
    private static final int BLOCK_AMOUNT = 4;
    private static final int UPGRADE_BLOCK_AMOUNT = 1;
    private static final int ARTIFACT_AMOUNT = 1;
    private static final int UPGRADE_ARTIFACT_AMOUNT = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF;

    public ArmillarySphere() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.ARMILLARY_SPHERE,
                COST, CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.LichGold,
                rarity, target);
        this.tags.add(PLOT);
        setOrbTexture(ENERGY_ORB_LICH_GOLD_PLOT, ENERGY_ORB_LICH_GOLD_PORTRAIT);
        setBackgroundTexture(SKILL_LICH_GOLD_PLOT, SKILL_LICH_GOLD_PLOT_PORTRAIT);
        this.baseBlock = this.block = BLOCK_AMOUNT;
        this.baseMagicNumber = this.magicNumber = ARTIFACT_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NextTurnBlockPower(p, this.block), this.block));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, this.magicNumber), this.magicNumber));
    }

    // Due to the Plot triggers using AbstractDungeon.player,
    // we have to manually apply Dexterity and Frail here.
    @Override
    public void plotEffect() {
        int dexCheck = (AbstractDungeon.player.hasPower("Dexterity")) ?
                AbstractDungeon.player.getPower("Dexterity").amount : 0;
        double frailCheck = (AbstractDungeon.player.hasPower("Frail")) ? 0.75 : 1;
        this.block += dexCheck;
        this.block /= frailCheck;
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block));
    }

    @Override
    public void triggerWhenDrawn()
    {
        String cardName = (this.upgraded) ? this.CARD_STRINGS.NAME  + "+" : this.CARD_STRINGS.NAME;
        doPlotEffect(cardName);
    }

    @Override
    public void triggerOnManualDiscard()
    {
        String cardName = (this.upgraded) ? this.CARD_STRINGS.NAME  + "+" : this.CARD_STRINGS.NAME;
        doPlotEffect(cardName);
    }

    @Override
    public AbstractCard makeCopy() {
        return new ArmillarySphere();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeBlock((UPGRADE_BLOCK_AMOUNT));
            this.upgradeMagicNumber((UPGRADE_ARTIFACT_AMOUNT));
        }
    }

}
