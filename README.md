# GameWaitingService

게임 플레이시 흡연하느라 늦는걸 방지하는 알리미 앱

-----

## 개발 규칙

- 100% Kotlin
- 코드 스타일은 [코틀린 공식 스타일 가이드](https://developer.android.com/kotlin/style-guide)를 따름 (ktlint 추천) [![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)
- 모든 커밋은 바로 `master` 브런치에 하는게 아닌, `자신이 추가한 기능` 브런치를 새로 만들어 여기로 진행한다 -> 추후 코드 리뷰를 통해 새로운 기능 파악이 된 후 `master` 브런치에 PR 진행
- 커밋 메시지는 어떤 변경사항이 있었는지 최소한 알 수 있게 작성한다 (ex_`오타 수정`)



## 개발 스킬

- Jetpack Compose for UI
- Dagger-Hilt for DI
- Coroutines/Flow for asynchronous
- Retrofit/OkHttp for networking
- Repository + Marker Interface, MVVM for Design Pattern
- Apply clean-architecture (가능하면?)
