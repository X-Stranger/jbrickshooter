# The goal #

Score the most points you can before the field is filled with bricks.

# The field structure #

The game field is a square of a hundred cells. The game starts with some colored bricks arranged randomly in the field. Empty cells are black.

Each side of the field has three layers of bricks. Any brick that can move will show an arrow when the mouse is over it. Clicking the mouse starts the brick moving through the field in the direction of its arrow. When the brick hits another brick it stops.

A brick’s arrow stays even when the brick is stopped. If the obstacle is removed, the brick again moves in the direction of its arrow. A brick’s direction cannot be changed.

# Removing the bricks #

The trick is to join three or more bricks of the same color vertically, horizontally or in an L-shape. When this happens they vanish and points are awarded.

Points:
  * if 3 bricks are joined then you will get 3 points
  * 4 bricks - 6 points
  * 5 bricks - 9 points
  * 6 bricks - 12 points
  * 7 bricks - 15 points

It is impossible to join more than seven bricks.

Only the first layer of bricks on the borders can start moving and only when their lane is not empty. When a brick enters the field, the bricks behind it move to the edge of the field.

# Clearing the field #

Emptying the field wins a bonus of points and a new random set of bricks is added. The bonus is 100 points the first time the field is cleared. This bonus increases by 100 points every time you empty the field (i.e. first time = 100, second time = 200, third = 300, etc.) Also, the number of bricks randomly added each time the field is cleared increases by one brick.

The game continues until the player is unable to move any bricks.

