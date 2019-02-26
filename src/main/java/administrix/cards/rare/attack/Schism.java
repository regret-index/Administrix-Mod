package administrix.cards.rare.attack;

import administrix.AdministrixMod;
import administrix.cards.AbstractAdministrixCard;
import administrix.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;
import com.megacrit.cardcrawl.vfx.combat.GoldenSlashEffect;

public class Schism extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:Schism";
    public static final String NAME = "Schism";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.ALL;

    public Schism() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.SCHISM, COST,
                CARD_STRINGS.DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.LichGold,
                rarity, target);
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        // Remove current block, gain half of that block next turn,
        // deal half of that block in damage to all enemies.
        this.damage = this.baseDamage = p.currentBlock / 2;
        this.block = this.baseBlock = p.currentBlock / 2;

        if (p.currentBlock > 0) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new GoldenSlashEffect(AbstractDungeon.player.hb.cX - 60.0F * Settings.scale, AbstractDungeon.player.hb.cY, true), 0.1F));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new GoldenSlashEffect(AbstractDungeon.player.hb.cX + 60.0F * Settings.scale, AbstractDungeon.player.hb.cY, true), 0.1F));
        }

        if (this.damage > 0) {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (m != null) {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new ClashEffect(m.hb.cX, m.hb.cY), 0.1F));
                }
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
        }

        if (this.block > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NextTurnBlockPower(p, this.block), this.block));
        }
        AbstractDungeon.actionManager.addToBottom(new RemoveAllBlockAction(p, p));
    }

    @Override
    public void applyPowers()
    {
        this.damage = this.baseDamage = AbstractDungeon.player.currentBlock / 2;
        this.block = this.baseBlock = AbstractDungeon.player.currentBlock / 2;

        super.applyPowers();

        if (this.damage > 0 || this.block > 0)
        {
            this.rawDescription = (DESCRIPTION + EXTENDED_DESCRIPTION[0]);
            initializeDescription();
        }
    }

    @Override
    public void onMoveToDiscard()
    {
        this.rawDescription = (DESCRIPTION);
        initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new Schism();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
        }
    }
}