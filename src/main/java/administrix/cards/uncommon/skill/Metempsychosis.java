package administrix.cards.uncommon.skill;

import administrix.actions.MetempsychosisAction;
import administrix.cards.AbstractAdministrixCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;
import administrix.powers.NextTurnDrawReductionPower;

public class Metempsychosis extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:Metempsychosis";
    public static final String NAME = "Metempsychosis";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF;
    private static final CardType type = CardType.SKILL;
    private static final int DRAWLESS_AMOUNT = 1;
    private static final int ENERGY_AMOUNT = 1;

    public Metempsychosis() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.METEMPSYCHOSIS, COST,
              CARD_STRINGS.DESCRIPTION, type,
              AbstractCardEnum.LichGold,
              rarity, target);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new MetempsychosisAction(1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EnergizedBluePower(p, ENERGY_AMOUNT), ENERGY_AMOUNT));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NextTurnDrawReductionPower(p, DRAWLESS_AMOUNT), DRAWLESS_AMOUNT));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Metempsychosis();
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
        }
    }

}