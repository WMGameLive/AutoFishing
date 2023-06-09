# AutoFishing
A simple plugin that allow players to automatically fish.  
Download on [Spigot](https://www.spigotmc.org/resources/100510/).

## Commands
● /autofishing reload - reload the config file.  
● /autofishing give [player] - give the specific rod to a player.  
● /autofishing toggle - Enable/Disable auto fishing ability. (Default is enable)

## Permissions
● autofishing.use - allow to automatically fish. (default: op)  
● autofishing.admin - allow to use give/reload command. (default: op)

## Configuration
```yaml
#How long should it auto reel in after bitten.
#Over 25 ticks will cause the player to fail fishing.
Ticks_After_Bitten: 5

#How long should it auto fishing again after reel in and caught something.
Ticks_After_Caught: 20

Only_Specific_Rod:
  #If true, player need to use the specific rod below to use the auto fishing ability.
  Enable: false
  #Some specific-rod setting.
  Rod_Name: "&6Auto Fishing Rod"
  Rod_Lore:
    - "&fUse this to Auto-Fishing."
  #Set the CustomModelData, if you don't need it, set to 0.
  Rod_Model_Data: 0
  #The plugin using nbt tag to check if player is using the specific rod,
  #so after you generate a rod by using /autofishing give, 
  #you can use some item editor plugin to modify the rod and sell it using shop plugin or whatever you want.
``` 

