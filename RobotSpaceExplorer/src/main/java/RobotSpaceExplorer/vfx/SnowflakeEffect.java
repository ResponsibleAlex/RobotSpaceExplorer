package RobotSpaceExplorer.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeEffectPath;

public class SnowflakeEffect extends AbstractGameEffect {
    private static final String imgSrc = makeEffectPath("Snowflake.png");
    private static final Texture img = ImageMaster.loadImage(imgSrc);

    private float x;
    private float y;
    private final float dest_x;
    private final float dest_y;
    private final float x_drift;

    private final float rotation_delta;
    private float counter;
    private final float counter_max;

    public SnowflakeEffect(float x, float y, float dest_x, float dest_y, float x_drift) {
        this.x = x;
        this.y = y;
        this.dest_x = dest_x + (float)Math.random() * 30.0F - 15.0F;
        this.dest_y = dest_y + (float)Math.random() * 30.0F - 15.0F;
        this.x_drift = x_drift;

        counter = 0.0F;
        counter_max = 17.0F;

        // rotate between -10 and 10 degrees per update
        rotation_delta = (float)Math.random() * 20.0F - 10.0F;

        renderBehind = false;
        color = new Color(1.0F, 1.0F, 1.0F, 0.9F);
        scale = MathUtils.random(1.0F, 2.0F) * Settings.scale;
        startingDuration = duration = Settings.ACTION_DUR_MED;
    }

    @Override
    public void update() {
        if (counter >= counter_max) {
            y -= 4.0F;
            x += x_drift;
        } else {
            x += (dest_x - x) / counter_max;
            y += (dest_y - y) / counter_max;
        }

        rotation += rotation_delta;

        counter++;
        duration -= Gdx.graphics.getDeltaTime();
        if (0.0F > duration) {
            isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        sb.draw(img, x - 40.0F, y - 40.0F, 40.0F, 40.0F, 80.0F, 80.0F, scale, scale, rotation, 0, 0, 80, 80, false, false);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
