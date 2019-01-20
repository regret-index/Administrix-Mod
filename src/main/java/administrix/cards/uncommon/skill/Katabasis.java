package administrix.cards.uncommon.skill;

import administrix.cards.AbstractAdministrixCard;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;
import administrix.powers.DescentPlusPower;

public class Katabasis extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:Katabasis";
    public static final String NAME = "Katabasis";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION;
    private static final int COST = -1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF_AND_ENEMY;

    public Katabasis() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.KATABASIS, COST,
                CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.LichGold,
                rarity, target);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        // Apply 1 Weak X(+1) times, add an upgraded Nightfall to your hand
        // for the next X(+1) turns.
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }
        if (p.hasRelic("Chemical X")) {
            effect += 2;
            p.getRelic("Chemical X").flash();
        }
        if (this.upgraded) {
            effect++;
        }
        if (effect > 0) {
            for (int i = 0; i < effect; i++) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, 1, false), 1));
            }
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DescentPlusPower(p, effect), effect));
        }
        p.energy.use(EnergyPanel.totalCount);
    }


    @Override
    public AbstractCard makeCopy() {
        return new Katabasis();
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