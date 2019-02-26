package administrix.cards.rare.attack;

import administrix.cards.AbstractAdministrixCard;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;
import administrix.powers.AffinityPower;

public class HaveItAll extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:HaveItAll";
    public static final String NAME = "Have It All";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.ALL;
    private static final int ATTACK_DMG = 1;
    private static final int BLOCK_AMOUNT = 1;
    private static final int STAT_AMOUNT = 1;
    private static final int DISCARD_AMOUNT = 1;

    public HaveItAll() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.HAVE_IT_ALL, COST,
                CARD_STRINGS.DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.LichGold,
                rarity, target);
        this.baseDamage = this.damage = ATTACK_DMG;
        this.baseBlock = this.block = BLOCK_AMOUNT;
        this.baseMagicNumber = this.magicNumber = STAT_AMOUNT;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new VFXAction(new WhirlwindEffect(), 0.0F));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));

        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        }

        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_DEFECT_BEAM"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.0F));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));

        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_DEFECT_BEAM"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.0F));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        }

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AffinityPower(p, STAT_AMOUNT), STAT_AMOUNT, true, AbstractGameAction.AttackEffect.NONE));
        AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, DISCARD_AMOUNT, false));
    }

    @Override
    public AbstractCard makeCopy() {
        return new HaveItAll();
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
