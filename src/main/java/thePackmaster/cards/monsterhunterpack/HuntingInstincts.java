package thePackmaster.cards.monsterhunterpack;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thePackmaster.util.Wiz;

import static thePackmaster.SpireAnniversary5Mod.makeID;

public class HuntingInstincts extends AbstractMonsterHunterCard {
    public final static String ID = makeID("HuntingInstincts");

    private static final int BLOCK = 3;
    private static final int UPG_BLOCK = 1;

    public HuntingInstincts() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = block = BLOCK;
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (hasHuntTarget()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        if (hasHuntTarget()){
            addToBot(new GainBlockAction(p, block*2));
            return;
        }
        addToBot(new GainBlockAction(p, block));
    }

    public boolean hasHuntTarget() {
        for (AbstractMonster mo : Wiz.getEnemies()) {
            if (mo.type == AbstractMonster.EnemyType.BOSS || mo.type == AbstractMonster.EnemyType.ELITE) {
                return true;
            }
        }
        return false;
    }

    public void upp() {
        upgradeBlock(UPG_BLOCK);
    }
}