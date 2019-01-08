package administrix.cards.uncommon.attack;

import administrix.cards.AbstractAdministrixCard;
import administrix.vfx.SwordBlastEffect;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import administrix.AdministrixMod;
import administrix.cards.common.attack.Daybreak;
import administrix.patches.AbstractCardEnum;

public class JustRewards extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:JustRewards";
    public static final String NAME = "Just Rewards";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 2;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.ALL_ENEMY;
    private static final CardType type = CardType.ATTACK;
    private static final int ATTACK_DMG = 10;
    private static final int VULN_AMOUNT = 1;
    private static final int DAYBREAK_AMOUNT = 2;
    private static final int UPGRADE_DAYBREAK_AMOUNT = 1;

    public JustRewards() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.JUST_REWARDS, COST,
                CARD_STRINGS.DESCRIPTION, type,
                AbstractCardEnum.LichGold,
                rarity, target);
        this.baseDamage = this.damage = ATTACK_DMG;
        this.baseMagicNumber = this.magicNumber = DAYBREAK_AMOUNT;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new SwordBlastEffect(p.dialogX, p.dialogY, p.flipHorizontal), 0.2F));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, new VulnerablePower(mo, VULN_AMOUNT, false), VULN_AMOUNT, true, AbstractGameAction.AttackEffect.NONE));
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Daybreak(), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new JustRewards();
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_DAYBREAK_AMOUNT);
        }
    }

}