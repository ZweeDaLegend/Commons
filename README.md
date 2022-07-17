# Commons

This a resource I made that contains a lot of functions to speed up plugin development

## CommandManager

To make a base command simple create a new Basecommand object and implement the methods

###  BaseCommands

The parameter is the command name

Example: 

```
    public static final BaseCommand baseCommand = new BaseCommand("healme") {

        @Override
        public void onCommand(CommandSender sender, String[] strings) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Only for players");
                return;
            }
            player.resetMaxHealth();
        }


        @Override
        public List<SubCommand> subCommandList() {
            return new ArrayList<SubCommand>()
        }

        @Override
        public String setDescription() {
            return "Heals the player";
        }

        @Override
        public String[] aliases() {
            return new String[]{
                    "heal",
            };
        }

        @Override
        public String permission() {
            return "healme.use";
        }

    };
```

Then in your Main class you can register it by using the AddBaseCommand function
```
CommandManager.AddBaseCommand(UserBaseCommand.baseCommand);
```     

And then when you have registered all the commands you call the SetupCommands() function to add them
```
new CommandManager().SetupCommands(this);
```
### SubCommads

Example:

```
public static final SubCommand subCommand = new SubCommand() {
        @Override
        public void onCommand(CommandSender commandSender, String[] strings) {
            for(String line : Messages.getMultilineMessage("HELP_COMMAND")){
                  commandSender.sendMessage(line);
            }
        }

        @Override
        public String name() {
            return "help";
        }

        @Override
        public String info() {
            return "";
        }

        @Override
        public String[] aliases() {
            return new String[0];
        }

        @Override
        public String permission() {
            return null;
        }
    };
```

then add the subcommand object in the basecommands subCommandList
```
        @Override
        public List<SubCommand> subCommandList() {
            return Arrays.asList(HelpSubcommand.subCommand);
        }
```

## ConfigManager

### Setup

This will handle all of the custom configuaration files

To make a config file create the file in your resources folder and then in your Main class add this
```
new ConfigManager(this).RegisterConfigs(Arrays.asList("data", "messages"));
```

The parameter for the ConfigManager object is the JavaPlugin and then for the RegisterConfigs you add a list with the names of the configs which in this example are data and messages<br>

**NOTE** just add the name of the file and **not** the extention (.yml)

and to load the defualt config.yml use the loadRegularConfig() method

```
ConfigManager.loadRegularConfig(this);
```

### Usage

To get a file from the ConfigManager use the getFile function

```
private static final ConfigManager.Config dataConfig = ConfigManager.getFile("data");
```

Example

```
private final ConfigManager.Config dataConfig = ConfigManager.getFile("data");

public int getCookies(){
  return data.getConfig().getInt("Data.Cookies");
}
```

## Messages

The Messages class is able to get any message in the messages.yml file under the Messages and MultilineMessages paths
NOTE This also translates colourcodes

### Example of messages.yml
```
Messages:
  PLAYER_NOT_FOUND: "That player could not be found"
  NO_PERMISSION: "You do not have permission to use this command!"
MutlilineMessages:
  HELP_COMMAND:
  -  "&8Info about this plugin"
  -  "&8/healme | heals you"
  -  "&8/helpme help | shows you this message"
```

To get a message use Messages.getMessage() 

```
String noPermissionMessage = Messages.getMessage("NO_PERISSION");
```

If the message has multiple lines use Messages.getMultilineMessage()

```
List<String> helpMessage = Messages.getMultilineMessage("HELP_COMMAND");
```

## WIP       

Adding NBT stuff soon
