package com.beaver.caveknight.entities.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.beaver.caveknight.R;
import com.beaver.caveknight.helpers.interfaces.BitmapMethods;
import com.beaver.caveknight.main.MainActivity;

public enum GameObjects implements BitmapMethods {

    PILLAR_YELLOW(R.drawable.world_objects, 0, 6, 16, 42),
    STATUE_ANGRY_YELLOW(R.drawable.world_objects, 16, 1, 32, 47),
    MONK_STATUE_BALL_YELLOW(R.drawable.world_objects, 49, 2, 30, 30),
    MONK_STATUE_YELLOW(R.drawable.world_objects, 81, 2, 30, 30),
    SOLDIER_SPEAR_YELLOW(R.drawable.world_objects, 112, 1, 16, 31),
    PLANTER_STICKS_YELLOW(R.drawable.world_objects, 128, 11, 16, 20),
    CUBE_YELLOW(R.drawable.world_objects, 32, 48, 16, 16),
    FROG_YELLOW(R.drawable.world_objects, 48, 38, 32, 26),
    SOLDIER_SWORD_YELLOW(R.drawable.world_objects, 81, 32, 31, 32),
    PILLAR_SHORT_YELLOW(R.drawable.world_objects, 112, 32, 16, 32),
    PILLAR_SNOW_YELLOW(R.drawable.world_objects, 128, 32, 16, 32),
    PILLAR_GREEN(R.drawable.world_objects, 0, 70, 16, 42),
    STATUE_ANGRY_GREEN(R.drawable.world_objects, 16, 65, 32, 47),
    MONK_STATUE_BALL_GREEN(R.drawable.world_objects, 49, 66, 30, 30),
    MONK_STATUE_GREEN(R.drawable.world_objects, 81, 66, 30, 30),
    SOLDIER_SPEAR_GREEN(R.drawable.world_objects, 112, 65, 16, 31),
    PLANTER_STICKS_GREEN(R.drawable.world_objects, 128, 75, 16, 20),
    CUBE_GREEN(R.drawable.world_objects, 32, 112, 16, 16),
    FROG_GREEN(R.drawable.world_objects, 48, 102, 32, 26),
    SOLDIER_SWORD_GREEN(R.drawable.world_objects, 81, 96, 31, 32),
    PILLAR_SHORT_GREEN(R.drawable.world_objects, 112, 96, 16, 32),
    PILLAR_SNOW_GREEN(R.drawable.world_objects, 128, 96, 16, 32),
    POT_ONE_FULL(R.drawable.world_objects, 144, 0, 16, 19),
    POT_ONE_EMPTY(R.drawable.world_objects, 160, 0, 16, 19),
    POT_TWO_FULL(R.drawable.world_objects, 144, 19, 16, 21),
    POT_ONE_FILLED(R.drawable.world_objects, 160, 20, 16, 20),
    BASKET_FULL_RED_FRUIT(R.drawable.world_objects, 144, 40, 16, 16),
    BASKET_FULL_CHICKEN(R.drawable.world_objects, 160, 40, 16, 16),
    BASKET_EMPTY(R.drawable.world_objects, 144, 56, 16, 16),
    BASKET_FULL_BREAD(R.drawable.world_objects, 160, 56, 16, 16),
    OVEN_SNOW_YELLOW(R.drawable.world_objects, 144, 73, 28, 39),
    OVEN_YELLOW(R.drawable.world_objects, 0, 129, 28, 28),
    OVEN_GREEN(R.drawable.world_objects, 28, 128, 30, 29),
    STOMP(R.drawable.world_objects, 58, 128, 16, 22),
    SMALL_POT_FULL(R.drawable.world_objects, 0, 112, 16, 13),
    SMALL_POT_EMPTY(R.drawable.world_objects, 16, 12, 16, 13),

    // Single Trees
    BIG_OAK(R.drawable.nature_tileset, 0, 0, 32, 32),
    SPRUCE(R.drawable.nature_tileset, 32, 0, 32, 32),
    DEAD_TREE(R.drawable.nature_tileset, 64, 0, 32, 32),
    BONSAI(R.drawable.nature_tileset, 96, 0, 32, 32),
    SNOW_SPRUCE_HALF(R.drawable.nature_tileset, 128, 0, 32, 32),
    SNOW_SPRUCE_FULL(R.drawable.nature_tileset, 160, 0, 32, 32),
    SNOW_BIG_OAK(R.drawable.nature_tileset, 192, 0, 32, 32),
    BIG_SAKURA(R.drawable.nature_tileset, 224, 0, 32, 32),
    SEMI_OAK(R.drawable.nature_tileset, 256, 0, 32, 32),
    SEMI_MOSSY_OAK(R.drawable.nature_tileset, 288, 0, 32, 32),

    // Triple Stacks
    SPRUCE_TRIPLE_STACK(R.drawable.nature_tileset, 0, 32, 64, 48),
    BIG_OAK_TRIPLE_STACK(R.drawable.nature_tileset, 64, 32, 64, 48),
    SNOW_BIG_OAK_TRIPLE_STACK(R.drawable.nature_tileset, 128, 32, 64, 48),
    BIG_SAKURA_TRIPLE_STACK(R.drawable.nature_tileset, 192, 32, 64, 48),
    SEMI_OAK_TRIPLE_STACK(R.drawable.nature_tileset, 256, 32, 64, 48),
    SEMI_MOSSY_OAK_TRIPLE_STACK(R.drawable.nature_tileset, 320, 32, 64, 48),
    DEAD_TREE_TRIPLE_STACK(R.drawable.nature_tileset, 0, 80, 64, 48),
    SNOW_SPRUCE_HALF_TRIPLE_STACK(R.drawable.nature_tileset, 64, 80, 64, 48),
    SNOW_SPRUCE_FULL_TRIPLE_STACK(R.drawable.nature_tileset, 128, 80, 64, 48),
    BROWN_ROCK_TRIPLE_STACK(R.drawable.nature_tileset, 192, 80, 64, 48),
    GRAY_ROCK_TRIPLE_STACK(R.drawable.nature_tileset, 256, 80, 64, 48),

    TREE_STUMP_DARK_OAK(R.drawable.nature_tileset, 0, 128, 32, 32),
    TREE_STUMP_OAK(R.drawable.nature_tileset, 32, 128, 32, 32),

    BROWN_ROCK(R.drawable.nature_tileset, 208, 128, 32, 32),
    SMALL_BROWN_ROCK(R.drawable.nature_tileset, 240, 144, 16, 16),
    ANGULAR_BROWN_ROCK_1(R.drawable.nature_tileset, 240, 160, 32, 32),
    ANGULAR_BROWN_ROCK_2(R.drawable.nature_tileset, 240, 192, 32, 32),
    SMALL_ANGULAR_BROWN_ROCK_1(R.drawable.nature_tileset, 272, 208, 16, 16),
    SMALL_ANGULAR_BROWN_ROCK_2(R.drawable.nature_tileset, 288, 208, 16, 16),
    SMALL_ANGULAR_BROWN_ROCK_3(R.drawable.nature_tileset, 304, 208, 16, 16),
    WIDE_ANGULAR_BROWN_ROCK(R.drawable.nature_tileset, 272, 160, 64, 48),
    TALL_ANGULAR_BROWN_ROCK(R.drawable.nature_tileset, 336, 160, 32, 48),

    GRAY_ROCK(R.drawable.nature_tileset, 256, 128, 32, 32),
    SMALL_GRAY_ROCK(R.drawable.nature_tileset, 288, 144, 16, 16),
    ANGULAR_GRAY_ROCK_1(R.drawable.nature_tileset, 240, 224, 32, 32),
    ANGULAR_GRAY_ROCK_2(R.drawable.nature_tileset, 240, 256, 32, 32),
    SMALL_ANGULAR_GRAY_ROCK_1(R.drawable.nature_tileset, 272, 272, 16, 16),
    SMALL_ANGULAR_GRAY_ROCK_2(R.drawable.nature_tileset, 288, 272, 16, 16),
    SMALL_ANGULAR_GRAY_ROCK_3(R.drawable.nature_tileset, 304, 272, 16, 16),
    WIDE_ANGULAR_GRAY_ROCK(R.drawable.nature_tileset, 272, 224, 64, 48),
    TALL_ANGULAR_GRAY_ROCK(R.drawable.nature_tileset, 336, 224, 32, 48),

    ORANGE_CRYSTAL(R.drawable.nature_tileset, 0, 224, 16, 16),
    ORANGE_CRYSTAL_ROCK_1(R.drawable.nature_tileset, 64, 224, 16, 16),
    ORANGE_CRYSTAL_ROCK_2(R.drawable.nature_tileset, 80, 224, 16, 16),
    ORANGE_CRYSTAL_ROCK_3(R.drawable.nature_tileset, 96, 224, 16, 16),


    DARK_CRYSTAL(R.drawable.nature_tileset, 16, 224, 16, 16),
    DARK_CRYSTAL_ROCK_1(R.drawable.nature_tileset, 64, 240, 16, 16),
    DARK_CRYSTAL_ROCK_2(R.drawable.nature_tileset, 80, 240, 16, 16),
    DARK_CRYSTAL_ROCK_3(R.drawable.nature_tileset, 96, 240, 16, 16);




    final Bitmap objectImg;
    final int width;
    final int height;

    // Enum constructor with spritesheet resource ID
    GameObjects(int spriteSheetResId, int x, int y, int width, int height) {
        options.inScaled = false;
        this.width = width;
        this.height = height;

        Bitmap atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), spriteSheetResId, options);
        objectImg = getScaledBitmap(Bitmap.createBitmap(atlas, x, y, width, height));
    }

    public Bitmap getObjectImg() {
        return objectImg;
    }
}