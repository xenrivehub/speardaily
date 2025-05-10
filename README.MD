# SpearDaily Plugin

SpearDaily is a Minecraft plugin designed to provide daily rewards for players. It allows server administrators to configure rewards for different player types (default, VIP, etc.) and customize the GUI for a better user experience.

## Features

- **Daily Rewards**: Players can claim rewards every day.
- **Customizable Rewards**: Configure rewards for different player tiers (e.g., default, VIP).
- **Hex Color Support**: Supports hex color codes for GUI customization.
- **GUI Customization**: Fully customizable display names and lore for each reward day.

## Configuration

The plugin uses a `config.yml` file for customization. Below is an example configuration:

```yaml
rewards:
  1:
    default:
      commands:
        - 'give vote 1 %player%'
    vip:
      commands:
        - 'broadcast Hello VIP!'
gui:
  1:
    display-name: "&6Day #%day%"
    lore:
      - "&2Player &8- &716 Dirt"
      - "&#FFC312VIP &8- &71 Vote Crate"
```
## Installation

1. Download the plugin `.jar` file.
2. Place the `.jar` file in your server's `plugins` folder.
3. Restart the server to generate the configuration files.
4. Edit the `config.yml` file to customize the plugin as needed.
5. Reload or restart the server to apply changes.

## Commands

- `/günlük` - Opens the daily rewards GUI.

## Permissions

- `speardaily.default` - Access to default rewards.
- `speardaily.vip` - Access to VIP rewards.

## NOTE
- This plugin is just developed for Spear Network and is not intended for public use. If you want a plugin like this one
- you can contact me on discord: ``