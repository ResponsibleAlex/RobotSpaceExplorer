package RobotSpaceExplorer.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeEffectPath;

public class FrostEffect extends AbstractGameEffect {
    private static final String img = makeEffectPath("Snowflake.png");

    private float x;
    private float y;

    private int count = 2;
    private float timer = 0.0F;

    public FrostEffect (float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        timer -= Gdx.graphics.getDeltaTime();
        if (timer < 0.0F) {
            timer += 0.15F;

            CardCrawlGame.sound.playA("ORB_FROST_EVOKE", MathUtils.random(0.7F, 0.8F));

            AbstractDungeon.effectsQueue.add(new SnowflakeEffect(x, y,
                    x - 120.0F, y + 30.0F, -4.0F));
            AbstractDungeon.effectsQueue.add(new SnowflakeEffect(x, y,
                    x + 120.0F, y + 30.0F, 4.0F));
            AbstractDungeon.effectsQueue.add(new SnowflakeEffect(x, y,
                    x - 60.0F, y + 100.0F, -2.0F));
            AbstractDungeon.effectsQueue.add(new SnowflakeEffect(x, y,
                    x + 60.0F, y + 100.0F, 2.0F));
            AbstractDungeon.effectsQueue.add(new SnowflakeEffect(x, y,
                    x, y + 120.0F, 0.0F));

            --count;
            if (count == 0) {
                isDone = true;
            }
        }
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
