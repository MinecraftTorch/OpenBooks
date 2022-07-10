
# OpenBooks
Open a book when a user joins.

한국인 분들은 [이 문서](https://github.com/gooday2die/OpenBooks/blob/main/README_KR.md)를 봐주세요!

## Features
- Automatically opens books when a user joins server
- Can open different books to new comers
- Supports [PlaceHolderAPI](https://github.com/PlaceholderAPI/PlaceholderAPI)
- Configurable books
- Open books to users by simple command

## Download
Check [Release Section](https://github.com/gooday2die/OpenBooks/releases/new) for more

## ScreenShot
### When a new user visit server
![New User](https://github.com/gooday2die/OpenBooks/blob/main/github/pics/0.gif?raw=true)

### When existing user came back to server
![New User](https://github.com/gooday2die/OpenBooks/blob/main/github/pics/1.gif?raw=true)

### When you have permission to bypass
`openbooks.bypass` gives you permissin to bypass books.
![Bypass](https://github.com/gooday2die/OpenBooks/blob/main/github/pics/2.gif?raw=true)

## Commands 
- `/openbooks help` : Prints out the help message for OpenBooks.
- `/openbooks reload`: Reloads `books.yml` and `config.yml` from plugin directory.
- `/openbooks open bookname touser`: Opens book to user. Example: `/openbooks open JoinBook Gooday2die` or `/openbooks open JoinBook ALL` for all users online. If somebody has `openbooks.bypass` they will not be receiving books.

## Permissions
- `openbooks.bypass`: Will not open books to users having this permission
- `openbooks.reload`: for `/openbooks reload`
- `openbooks.openbookto`: for `/openbooks open bookname touser`

## Config.yml
```
#    ________  ________  _______   ________   ________  ________  ________  ___  __    ________  
#   |\   __  \|\   __  \|\  ___ \ |\   ___  \|\   __  \|\   __  \|\   __  \|\  \|\  \ |\   ____\  
#   \ \  \|\  \ \  \|\  \ \   __/|\ \  \\ \  \ \  \|\ /\ \  \|\  \ \  \|\  \ \  \/  /|\ \  \___|_  
#    \ \  \\\  \ \   ____\ \  \_|/_\ \  \\ \  \ \   __  \ \  \\\  \ \  \\\  \ \   ___  \ \_____  \  
#     \ \  \\\  \ \  \___|\ \  \_|\ \ \  \\ \  \ \  \|\  \ \  \\\  \ \  \\\  \ \  \\ \  \|____|\  \  
#      \ \_______\ \__\    \ \_______\ \__\\ \__\ \_______\ \_______\ \_______\ \__\\ \__\____\_\  \  
#       \|_______|\|__|     \|_______|\|__| \|__|\|_______|\|_______|\|_______|\|__| \|__|\_________\  
#                                                                                        \|_________|  
# Open Books when user joins  
# Book name that OpenBooks should show player when they join the server  
UserJoinBook: "JoinBook"  
# UserJoinBook: ""  # If you do not want to open a book when user joins  
# Book name that OpenBooks should show player when they join the server for the first time.  
NewUserJoinBook: "NewUserBook"  
# NewUserJoinBook: "" # If you do not want to open a book when user joins the server for the first time.
```

## Books.yml
```
# You can use PlaceHolderAPI placeholders if you want!  
JoinBook:  
  pages:  
    [  
      # Pages are split by , commas  
  "Welcome to minecraft server",  
      "Hello %player_displayname%, welcome back to server",  
      "§c§lNo Hacks!!§0",  
      "Have a good time in our server!",  
      ]  
  keepBook: false  
  # If keep book was set true, then in which slot?  
 # THIS WILL REMOVE THE ORIGINAL ITEM THAT WAS IN THAT SLOT!!!  bookSlot: 0  
  title: "JoinBook1"  
  author: "OpenBooks"  
  
NewUserBook:  
  pages:  
    [  
      "Welcome to minecraft server",  
      "Hello %player_displayname%, welcome back to server.",  
      "§c§lIt seems like you are joining for the first time. Check our website!§0",  
      "Have a good time in our server!",  
      ]  
  keepBook: true  
  # If keep book was set true, then in which slot?  
 # THIS WILL REMOVE THE ORIGINAL ITEM THAT WAS IN THAT SLOT!!!  bookSlot: 0  
  title: "JoinBook2"  
  author: "OpenBooks"
```

## Issues & PR & Contacts
- Be free to pull request
- If there is an issue or bug, please report!
- dev.gooday2die@gmail.com for contacts.

## Todo
 - [ ] Announcement in every intervals
 - [ ] Random book opens when user joins server
 - [ ] Anything you guys want!