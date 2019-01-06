package administrix.cards.rare.power;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;
import administrix.powers.FlamesOfFaithPower;

public class FaithfulFlames extends CustomCard
{
    public static final String ID = "AdministrixMod:FaithfulFlames";
    public static final String NAME = "Faithful Flames";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static final int FLAMES_AMOUNT = 2;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardType type = CardType.POWER;

    public FaithfulFlames() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.FAITHFUL_FLAMES, COST,
                CARD_STRINGS.DESCRIPTION,
                type, AbstractCardEnum.LichGold,
                rarity, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = FLAMES_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.5F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FlamesOfFaithPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new FaithfulFlames();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.isInnate = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}