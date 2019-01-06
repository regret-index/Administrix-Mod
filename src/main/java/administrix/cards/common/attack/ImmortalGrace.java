package administrix.cards.common.attack;

import administrix.cards.AbstractAdministrixCard;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import administrix.AdministrixMod;
import administrix.actions.ImmortalUpgradeAction;
import administrix.patches.AbstractCardEnum;

import static administrix.AdministrixMod.*;

public class ImmortalGrace extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:ImmortalGrace";
    public static final String NAME = "Immortal Grace";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 6;
    private static final int UPGRADE_ATTACK_DMG = 1;
    private static final int DRAW_AMOUNT = 1;
    private static final CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.ENEMY;

    public ImmortalGrace() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.IMMORTAL_GRACE,
                COST, CARD_STRINGS.DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.LichGold,
                rarity, target);
        setOrbTexture(ENERGY_ORB_LICH_GOLD_PLOT, ENERGY_ORB_LICH_GOLD_PORTRAIT);
        setBackgroundTexture(ATTACK_LICH_GOLD_PLOT, ATTACK_LICH_GOLD_PLOT_PORTRAIT);
        this.baseDamage = this.damage = ATTACK_DMG;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, DRAW_AMOUNT));
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
        return new ImmortalGrace();
    }

    @Override
    public void upgrade() {
        this.upgradeDamage(UPGRADE_ATTACK_DMG);
        this.timesUpgraded += 1;
        this.upgraded = true;
        this.name = (NAME + "+" + this.timesUpgraded);
        initializeTitle();
        this.rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    public boolean canUpgrade()
    {
        return true;
    }

}
