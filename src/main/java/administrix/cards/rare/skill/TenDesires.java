package administrix.cards.rare.skill;

import administrix.AdministrixMod;
import administrix.actions.DiscardToDraw;
import administrix.actions.TransposeAction;
import administrix.cards.AbstractAdministrixCard;
import administrix.patches.AbstractCardEnum;
import administrix.powers.WiltingPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;


public class TenDesires extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:TenDesires";
    public static final String NAME = "Ten Desires";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.SELF;
    private static final CardType type = CardType.SKILL;
    private static final int COST = 2;
    private static final int TRANSPOSE_AMOUNT = 2;
    private static final int DISCARD_AMOUNT = 2;

    public TenDesires() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.TEN_DESIRES, COST,
                CARD_STRINGS.DESCRIPTION, type,
                AbstractCardEnum.LichGold,
                rarity, target);
        this.magicNumber = this.baseMagicNumber = TRANSPOSE_AMOUNT;
        this.isEthereal = true;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new BorderLongFlashEffect(Color.GOLD), 0.8F));
            AbstractDungeon.actionManager.addToBottom(new TransposeAction(this.magicNumber));
        }

        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));

        int toDraw = 10 - AbstractDungeon.player.hand.size();
        if (toDraw > 0) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, toDraw));
        }

        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));

        AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, DISCARD_AMOUNT, false));
    }

    @Override
    public AbstractCard makeCopy() {
        return new TenDesires();
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}