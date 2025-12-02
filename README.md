# 🕵️‍♂️ 자바 소켓 통신 기반 마피아 게임 (Mafia Game)

> **"어둠 속에 숨어든 살인마를 찾아라!"**
>
> 플레이어가 접속하는 순간, 운명은 정해집니다. 당신의 신분은 오직 당신만 알 수 있습니다.
> <br>
> 모두가 잠든 **밤**, 마피아는 은밀한 대화 끝에 타겟을 제거하고, 의사는 동료를 치료하며, 경찰은 범인의 정체를 조사합니다.
> <br>
> 날이 밝으면 처참한 결과가 공개되고, 살아남은 자들의 숨 막히는 **토론**이 시작됩니다. 단 한 번의 **투표**, 단 한 번의 기회!
> <br>
> **시민과 마피아, 최후에 웃는 진영은 과연 누구일까요?**

<br>

## 🛠 Tech Stack
<img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white"/> <img src="https://img.shields.io/badge/Swing-E34F26?style=flat-square&logo=Java&logoColor=white"/> <img src="https://img.shields.io/badge/Socket-000000?style=flat-square&logo=Socket.io&logoColor=white"/>

* **Language**: Java 21
* **GUI**: Java Swing (JFrame, JPanel)
* **Network**: Java Socket (TCP/IP), Multi-threading
* **IDE**: IntelliJ IDEA / Eclipse

<br>

## 📸 Screenshots (실행 화면)
 | 로그인(로비) | 게임 진행(낮) | 마피아 밀담(밤) |
| :---: | :---: | :---: |
| <img src="https://github.com/user-attachments/assets/5709c805-8429-4e73-b558-23f03120f1aa" width="300" /> | <img src="https://github.com/user-attachments/assets/b7fc706b-404b-4a4b-9016-c9517cc362ec" width="300" /> | <img src="https://github.com/user-attachments/assets/fbb8e941-1648-4503-b084-1842fceb9b82" width="300" /> |

<br>

## 💡 Key Features (주요 기능)

* **멀티플레이어 채팅**: TCP 소켓을 활용하여 실시간 다중 접속 및 채팅 구현.
* **직업 배정 시스템**: 게임 시작 시 시민, 마피아, 경찰, 의사 직업 랜덤 부여.
* **페이즈 전환**: [밤] -> [토론] -> [투표] 순서로 게임 상태 자동 전환.
* **특수 능력 구현**:
    * 🔫 **마피아**: 전용 비밀 채팅방 제공, 밤마다 타겟 처형.
    * 💊 **의사**: 타겟 치료 (마피아 지목 대상과 일치 시 생존).
    * 👮‍♂️ **경찰**: 타겟 조사 (마피아 여부 판별).
* **투표 시스템**: 과반수 투표 시 플레이어 처형 및 생존자 판별 로직.

<br>

## 🏗 System Architecture (설계 구조)

### 1. Design Patterns
이 프로젝트는 유지보수와 확장성을 위해 다양한 디자인 패턴을 적용했습니다.

* **Singleton Pattern**: 게임의 전체 흐름을 관리하는 `사회자(Controller)` 객체를 유일하게 유지.
* **State Pattern**: 게임의 상태(`밤`, `토론`, `투표`)를 객체화하여, 복잡한 `if-else` 없이 상태별 행동(`execute`)을 관리.
* **Command Pattern**: 클라이언트의 요청(`Join`, `Start`, `Message`, `Mafia_message`, `Target`, `System`)을 캡슐화하여 `CommandManager`가 처리하도록 구조화.
* **Observer Pattern**: Swing UI 이벤트 리스너 처리에 활용.

### 2. Class Diagram
*(아키텍처 다이어그램 이미지가 있다면 여기에 넣으세요)*
<img width="2086" height="881" alt="Server" src="https://github.com/user-attachments/assets/4e765098-064c-406a-919d-d64426663cd9" />
<img width="1047" height="824" alt="Client" src="https://github.com/user-attachments/assets/6cb3d3f7-0fa8-45d9-b7fa-250d95121581" />
<img width="1432" height="1435" alt="model controller" src="https://github.com/user-attachments/assets/76d528a9-47b3-4b4e-b66e-a59eeb166abb" />
<img width="1191" height="1174" alt="View" src="https://github.com/user-attachments/assets/fd768ac2-3b77-4e78-837b-3c7039498bd9" />

-> 여기에 uml 넣어주시면 감사... 제가 넣을려니까 화진이 너무 안 좋아요...


### 3. Package Structure
```text
src
├── client          # 클라이언트 통신 (ClientManager, Thread)
├── server          # 서버 통신 및 명령 처리 (ServerManager, CommandManager)
├── controller      # 게임 로직 (사회자, IState, 밤/낮/투표 구현체)
├── model           # 데이터 모델 (Player)
└── view            # UI (Lobby, View2, MafiaChatView)
