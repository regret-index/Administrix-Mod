package administrix.cards.rare.attack;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.PummelDamageAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.RainbowCardEffect;
import com.megacrit.cardcrawl.powers.StrengthPower;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;

// Trivia note:
// the first name for this was "Big Dyke Energy".

public class GirlsDoItBetter extends CustomCard
{
    public static final String ID = "AdministrixMod:GirlsDoItBetter";
    public static final String NAME = "Girls Do It Better";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 2;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.ENEMY;
    private static final int DAMAGE = 2;
    private static final int HITS = 4;
    private static final int UPGRADE_HITS = 3;

    public GirlsDoItBetter() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.GIRLS_DO_IT_BETTER,
                COST, CARD_STRINGS.DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.LichGold,
                rarity, target);
        this.baseDamage = this.damage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = HITS;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int drainAmount = (p.hasPower("Strength")) ?
                           p.getPower("Strength").amount * 2 / 3 : 0;

        if (drainAmount > 0) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new RainbowCardEffect()));
        }

        AbstractDungeon.actionManager.addToBottom(new PummelDamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        AbstractDungeon.actionManager.addToBottom(new PummelDamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        AbstractDungeon.actionManager.addToBottom(new PummelDamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));

        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new PummelDamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
            AbstractDungeon.actionManager.addToBottom(new PummelDamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
            AbstractDungeon.actionManager.addToBottom(new PummelDamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        }

        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        if (drainAmount > 0) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new RainbowCardEffect()));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new StrengthPower(m, -drainAmount), -drainAmount));
        }

    }

    /*
    private void setDescription(boolean addExtended) {
        //this.rawDescription = CARD_STRINGS.DESCRIPTION;
        if (addExtended) {
            this.rawDescription = CARD_STRINGS.EXTENDED_DESCRIPTION[0].replace("!F!" , "" + extraDamage);
        }
        if (this.exhaustOnUseOnce && !this.exhaust)
            this.rawDescription += " NL Exhaust.";
        this.initializeDescription();
    }
    */

    @Override
    public AbstractCard makeCopy() {
        return new GirlsDoItBetter();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(UPGRADE_HITS);
            /*
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
             */
        }
    }
}
