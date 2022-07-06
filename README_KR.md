# OpenBooks KR
유저가 서버에 방문하면 자동으로 책을 읽게합니다.

For non Koreans, please check [here](https://github.com/gooday2die/OpenBooks/README.md)를 봐주세요!

## 기능
- 서버에 유저가 방문하면 책을 펼쳐서 읽게합니다
- 새로 방문한 유저는 다른 책을 읽게 할 수 있습니다
- [PlaceHolderAPI](https://github.com/PlaceholderAPI/PlaceholderAPI) 를 지원합니다
- 책을 다양하게 설정할 수 있습니다.
- 한국어 완벽 지원

## 다운로드
[Release Section](https://github.com/gooday2die/OpenBooks/releases/new) 을 확인해주세요

## 스크린샷
### 신규 유저가 서버에 오면 보여지는 책 예시
![New User](https://github.com/gooday2die/OpenBooks/blob/main/github/pics/0.gif?raw=true)

### 유저가 서버에 다시 돌아오면 나오는 책 예시
![New User](https://github.com/gooday2die/OpenBooks/blob/main/github/pics/1.gif?raw=true)

### 책 안읽는 권한을 줬을 때 예시
`openbooks.bypass` 퍼미션 노드를 부여하면 책을 읽지 않고 서버에 접속합니다.
![Bypass](https://github.com/gooday2die/OpenBooks/blob/main/github/pics/2.gif?raw=true)

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
# 서버에 사용자가 들어왔을 때 읽어줄 책  
UserJoinBook: "JoinBook"  
# UserJoinBook: ""  # 읽을 책이 없으면 "" 빈칸으로 놔주세요  
# 서버에 신규 유저가 들어왔을 때 읽어줄 책  
NewUserJoinBook: "NewUserBook"  
# NewUserJoinBook: "" # 읽을 책이 없으면 "" 빈칸으로 놔주세요  
```

## Books.yml
```
# PlaceHolderAPI 를 사용할 수 있습니다.  
JoinBook:  
  pages:  
    [  
    # 각 페이지는 컴마 , 로 구별합니다.
      "반갑습니다! 환영합니다",  
      "%player_displayname%님 안녕하세요!",  
      "§c§l핵 금지§0",  
      "좋은 시간을 보내세요!",  
      ]  
  keepBook: false  
  # 책을 만약 갖게한다면 true, 아니라면 false.
  # 책을 갖게한다는 옵션이면, 몇번 슬롯에 넣을지. (단 이러면 기존 아이템 없어집니다!)
  bookSlot: 0  
  # 책 이름
  title: "JoinBook1"  
  # 책 저자
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
- PR은 언제나 환영입니다.
- 버그가 있다면 제보해주세요 (Issue 섹션에다가)
- edina00@naver.com 으로 연락해주세요 (설치 관련 질문은 받지 않습니다)

## Todo

 - [ ] 다른 사람에게 책 읽어주는 명령어 추가
 - [ ] 책 랜덤하게 읽어주는거 추가
 - [ ] 아이디어는 대환영입니다!