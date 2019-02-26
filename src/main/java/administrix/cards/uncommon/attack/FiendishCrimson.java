package administrix.cards.uncommon.attack;

import administrix.cards.AbstractAdministrixCard;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;

public class FiendishCrimson extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:FiendishCrimson";
    public static final String NAME = "Fiendish Crimson";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 2;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.ENEMY;
    private static final CardType type = CardType.ATTACK;
    private static final int ATTACK_DMG = 14;
    private static final int UPGRADE_ATTACK_DMG = 4;
    private static final int HEAL_AMOUNT = 5;
    private static final int UPGRADE_HEAL_AMOUNT = 2;
    private static final int FRAIL_AMOUNT = 2;

    public FiendishCrimson() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.FIENDISH_CRIMSON,
                COST, CARD_STRINGS.DESCRIPTION,
                type, AbstractCardEnum.LichGold,
                rarity, target);
        this.baseDamage = this.damage = ATTACK_DMG;
        this.baseMagicNumber = this.magicNumber = HEAL_AMOUNT;
        this.exhaust = true;
        this.tags.add(AbstractCard.CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (m != null) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY - 40.0F * Settings.scale, Settings.RED_TEXT_COLOR.cpy()), 0.3F));
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        if (p.currentHealth > 0) {
            p.heal(this.magicNumber);
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FrailPower(p, FRAIL_AMOUNT, false), FRAIL_AMOUNT));
    }

    @Override
    public AbstractCard makeCopy() {
        return new FiendishCrimson();
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.upgradeDamage((UPGRADE_ATTACK_DMG));
            this.upgradeMagicNumber(UPGRADE_HEAL_AMOUNT);
        }
    }

}