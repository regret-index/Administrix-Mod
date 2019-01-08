package administrix.cards.uncommon.attack;

import administrix.cards.AbstractAdministrixCard;
import administrix.vfx.FlashySlamEffect;
import administrix.vfx.SlimelessImpactEffect;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;
import administrix.powers.YangPower;
import administrix.powers.YinPower;

public class NewWardrobe extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:NewWardrobe";
    public static final String NAME = "New Wardrobe";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.ALL_ENEMY;
    private static final CardType type = CardType.ATTACK;

    public NewWardrobe() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.NEW_WARDROBE, COST,
                CARD_STRINGS.DESCRIPTION, type,
                AbstractCardEnum.LichGold,
                rarity, target);
        this.baseDamage = 0;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        // Swap your Yin and Yang, checking if you have any of such at all, and
        // deal the difference in damage (with a flashy, scaling effect).
        // Redundant checks are for both cleanliness and controlling power VFX.
        int yinAmount = p.hasPower(YinPower.POWER_ID) ?
                        p.getPower(YinPower.POWER_ID).amount : 0;
        int yangAmount = p.hasPower(YangPower.POWER_ID) ?
                         p.getPower(YangPower.POWER_ID).amount : 0;
        int difference = Math.abs(yinAmount - yangAmount);

        if (yinAmount > yangAmount) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new VerticalAuraEffect(Color.BLACK, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.33F));
        } else if (yangAmount > yinAmount) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new VerticalAuraEffect(Color.WHITE, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.33F));
        }

        if ((yinAmount == 0) && (yangAmount != 0))
        {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, YangPower.POWER_ID));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new YinPower(p, yangAmount), yangAmount));
        }
        else if ((yinAmount != 0) && (yangAmount == 0))
        {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, YinPower.POWER_ID));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new YangPower(p, yinAmount), yinAmount));
        }
        else if ((yinAmount != 0) && (yangAmount != 0))
        {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, YinPower.POWER_ID));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, YangPower.POWER_ID));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new YinPower(p, yangAmount), yangAmount));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new YangPower(p, yinAmount), yinAmount));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
        }

        this.baseDamage = this.damage = difference;
        applyPowers();
        calculateCardDamage(null);

        if (difference > 0) {
            if (this.damage < 20) {
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SMASH));
            } else if (this.damage < 40) {
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            } else if (this.damage < 60) {
                if (yinAmount < yangAmount) {
                    for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        if (mo != null) {
                            AbstractDungeon.actionManager.addToBottom(new VFXAction(new FlashySlamEffect(p.hb.cX, p.hb.cY, mo.hb.cX, new Color(0.95F, 0.90F, 0.70F, 0.0F)), 0.4F));
                        }
                    }
                } else {
                    for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        if (mo != null) {
                            AbstractDungeon.actionManager.addToBottom(new VFXAction(new FlashySlamEffect(p.hb.cX, p.hb.cY, mo.hb.cX, new Color(0.30F, 0.05F, 0.35F, 0.0F)), 0.4F));
                        }
                    }
                }
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            } else {
                if (yinAmount < yangAmount) {
                    for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        if (mo != null) {
                            AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlimelessImpactEffect(mo.hb.cX, mo.hb.cY, new Color(0.95F, 0.9F, 0.8F, 0.8F))));
                        }
                    }
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(1.0F));
                    AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
                } else {
                    for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        if (mo != null) {
                            AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlimelessImpactEffect(mo.hb.cX, mo.hb.cY, new Color(0.2F, 0.1F, 0.4F, 0.8F))));
                        }
                    }
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(1.0F));
                    AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
                }
            }
        }
    }

    public void applyPowers()
    {
        int yinAmount = AbstractDungeon.player.hasPower(YinPower.POWER_ID) ?
                        AbstractDungeon.player.getPower(YinPower.POWER_ID).amount : 0;
        int yangAmount = AbstractDungeon.player.hasPower(YangPower.POWER_ID) ?
                         AbstractDungeon.player.getPower(YangPower.POWER_ID).amount : 0;
        this.damage = this.baseDamage = Math.abs(yinAmount - yangAmount);
        super.applyPowers();
        if (this.baseDamage > 0)
        {
            this.rawDescription = (DESCRIPTION + EXTENDED_DESCRIPTION[0]);
            initializeDescription();
        }
    }

    @Override
    public void onMoveToDiscard()
    {
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        if (this.upgraded) { this.retain = true; }
    }

    public void calculateCardDamage(AbstractMonster mo)
    {
        super.calculateCardDamage(mo);
        if (this.baseDamage > 0) {
            this.rawDescription = (DESCRIPTION + EXTENDED_DESCRIPTION[0]);
        }
        initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new NewWardrobe();
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.retain = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}