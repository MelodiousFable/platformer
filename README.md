# Platformer game #

A simple platforming game demonstrating use of a simple physics engine and effective programming
practices.

## Setup ##

A file named 'level1.json' controls the main preferences of the player, as well as the level design. It is
recommended that these settings remain unchanged, however, the player may, if they wish, change some
of the settings, such as the x position of the character, the level width and height, and the size of the
character ('small', 'normal', 'large', or 'giant'). 

If any settings in this file are altered and are invalid, the program will reset them to their default 
settings.


## Running the application ##

In order to run, Gradle must first be installed onto your computer, and all files and folders in the
installed zip folder extracted to a location of your choice.

Using the command prompt (Windows), navigate to the folder 'Stickman-game', and input this command:

gradle build run

## Iterations ##

### Iteration 1 ###

The left and right arrow keys are used for movement, the up arrow will cause the player to jump into 
the air and then fall back down.

Start with 3 lives. The game ends when you lose all three lives, or you reach the finish line. Once 
the game ends, recorded statistics appear on the screen. To quit the game after finishing, click the 
red X button on the top right of the window. This version showcases different behaviours for enemies
while using various design patterns in the program.

### Iteration 2 ###

Same controls as the previous, however, the player now bounces off enemies rather than stomping on
them. Also, different platforms behave differently.

Sources for sprites used:
https://phaser.io/content/tutorials/coding-tips-003/platform.png
https://i.stack.imgur.com/FGhIr.jpg

