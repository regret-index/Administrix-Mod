package administrix.cards.common.attack;

import administrix.AdministrixMod;
import administrix.actions.ImmortalUpgradeAction;
import administrix.cards.AbstractAdministrixCard;
import administrix.cards.common.skill.Nightfall;
import administrix.patches.AbstractCardEnum;
import administrix.powers.YinPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static administrix.AdministrixMod.*;
import static administrix.patches.CardTagsEnum.PLOT;

public class PassageOfAges extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:PassageOfAges";
    public static final String NAME = "Passage Of Ages";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 4;
    private static final int UPGRADE_ATTACK_DMG = 2;
    private static final int YIN_AMOUNT = 0;
    private static final int UPGRADE_YIN_AMOUNT = 2;
    private static final CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.ENEMY;

    public PassageOfAges() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.PASSAGE_OF_AGES,
                COST, CARD_STRINGS.DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.LichGold,
                rarity, target);
        this.tags.add(PLOT);
        setOrbTexture(ENERGY_ORB_LICH_GOLD_PLOT, ENERGY_ORB_LICH_GOLD_PORTRAIT);
        setBackgroundTexture(ATTACK_LICH_GOLD_PLOT, ATTACK_LICH_GOLD_PLOT_PORTRAIT);
        this.baseDamage = this.damage = ATTACK_DMG;
        this.baseMagicNumber = this.magicNumber = YIN_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new YinPower(p, this.magicNumber), this.magicNumber));
        }
    }

    public void triggerWhenDrawn()
    {
        this.superFlash(PLOT_PURPLE);
        for (int i = 0; i < mastermindCheck(); i++) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Nightfall(), 1));
        }
    }

    public void triggerOnManualDiscard()
    {
        this.superFlash(PLOT_PURPLE);
        for (int i = 0; i < mastermindCheck(); i++) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Nightfall(), 1));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new PassageOfAges();
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_ATTACK_DMG);
            this.upgradeMagicNumber(UPGRADE_YIN_AMOUNT);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}