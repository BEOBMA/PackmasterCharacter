package thePackmaster.cards.enchanterpack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.actions.madsciencepack.SimpleAddModifierAction;
import thePackmaster.cardmodifiers.enchanterpack.BlockEnchantModifier;

public class PolarBlessing extends AbstractEnchanterCard {

    public static final String ID = SpireAnniversary5Mod.makeID("PolarBlessing");

    public PolarBlessing(){
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 15;
    }

    @Override
    public void upp() {
        upgradeBlock(3);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        blck();
        for (AbstractCard c : getNeighbors()) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    c.retain = true;
                    c.flash();
                    isDone = true;
                }
            });
        }
    }
}
