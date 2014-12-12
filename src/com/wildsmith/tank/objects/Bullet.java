package com.wildsmith.tank.objects;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.wildsmith.tank.R;
import com.wildsmith.tank.attributes.Sound;
import com.wildsmith.tank.levels.Level;
import com.wildsmith.tank.utils.IntersectionHelper;
import com.wildsmith.tank.utils.MovementTracker;

public class Bullet extends ViewObject {

    private static final float BULLET_VELOCITY = 5f;

    private MovementTracker movementTracker;

    private float velocityY;

    public Bullet(float left, float right, float top, float bottom, Level level) {
        super(R.drawable.image_bullet, left, right, top, bottom, level);

        velocityY = BULLET_VELOCITY;

        movementTracker = new MovementTracker();
        movementTracker.setMoving(MovementTracker.UP, true);
    }

    @Override
    public void update(float frameDelta) {
        RectF newBounds = new RectF(getLeft(), getTop(), getRight(), getBottom());
        if (movementTracker.isMoving(MovementTracker.UP)) {
            newBounds.top -= (velocityY * frameDelta);
            newBounds.bottom -= (velocityY * frameDelta);
        } else if (movementTracker.isMoving(MovementTracker.DOWN)) {
            newBounds.top += (velocityY * frameDelta);
            newBounds.bottom += (velocityY * frameDelta);
        }

        if (movementTracker.isMoving(MovementTracker.LEFT)) {
            newBounds.left -= (velocityY * frameDelta);
            newBounds.right -= (velocityY * frameDelta);
        } else if (movementTracker.isMoving(MovementTracker.RIGHT)) {
            newBounds.left += (velocityY * frameDelta);
            newBounds.right += (velocityY * frameDelta);
        }

        final boolean isIntersectingWithTower = IntersectionHelper.isIntersectingWithViewObject(level.getTower(), newBounds);
        final boolean isIntersectingWithWalls = IntersectionHelper.isIntersectingWithViewObjects(level.getWalls(), newBounds);
        if (isIntersectingWithTower || isIntersectingWithWalls) {
            movementTracker.flipMovement();
            sound.playSound(Sound.BUTTET_BOUNCE);
        }

        float top = getTop();
        if (movementTracker.isMoving(MovementTracker.UP)) {
            top -= (velocityY * frameDelta);
        } else if (movementTracker.isMoving(MovementTracker.DOWN)) {
            top += (velocityY * frameDelta);
        }

        float left = getLeft();
        if (movementTracker.isMoving(MovementTracker.LEFT)) {
            left -= (velocityY * frameDelta);
        } else if (movementTracker.isMoving(MovementTracker.RIGHT)) {
            left += (velocityY * frameDelta);
        }

        setPosition(left, top);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, getLeft(), getTop(), paint);
    }
}