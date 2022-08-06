# AutoFishing
A simple plugin that allow players to automatically fish.  
Download on [Spigot](https://www.spigotmc.org/resources/100510/).

## Commands
● /autofishing reload - reload the config file.

## Permissions
● autofishing.use - allow to automatically fish. (default: op)  
● autofishing.admin - allow to use the command. (default: op)

## Configuration
```yaml
#How long should it auto reel in after bitten.
#Over 25 ticks will cause the player to fail fishing.
Ticks_After_Bitten: 5

#How long should it auto fishing again after reel in and caught something.
Ticks_After_Caught: 20
``` 

