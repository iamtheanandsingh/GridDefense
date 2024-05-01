Classes:
- Tower
  - When set to auto (NOT controlled by player):
    - Targets closest enemy
  - When player controlled:
    - Targets player’s mouse (shoots in the direction that the mouse is)
    - Enhanced – to incentivize the player controlling a tower, the tower should be enhanced so it performs better (higher damage, faster shooting rate, pass through enemies, etc.)
  - Has a damage number – this is how much damage the towers projectile will do to the enemy (this damage number is passed to the projectile when the tower shoots)
  - Has a health number – this is how much health it has before it dies (when this number reaches below zero, the tower dies)
  - Has a location – this is where the tower is placed in the world (x, y)
  - Has an isPlayerControlled – determines if the player is controlling it
- Projectile
  - Shot by the tower
  - When it collides with an enemy, it disappears (unless enemy passthrough is enabled)
  - When it leaves the screen, it disappears
  - Has a speed – this is how fast the projectile moves
  - Has a damage number – this is how much damage it will do to the enemy when it collides with it 
- Enemy
  - Spawns randomly outside the screen
  - Targets closest tower
  - When it reaches the tower, attacks the tower
  - Has a damage number – this is how much damage it will do to the tower
  - Has a health number – this is how much health it has before it dies (when this number reaches below zero, the enemy dies)
  - Has an attack timer – this determines how fast it can do damage (the wait between hits)