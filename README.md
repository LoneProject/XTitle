# XTitle
**XTitle 플러그인**은 마인크래프트 서버에서 커스텀으로 칭호를 관리할 수 있게 해주는 플러그인입니다.<br>
해당 플러그인은 현재 **플러그인**의 간편한 API가 존재합니다.<br>
각각의 API는 `README`를 아래로 내리면 예시 코드가 나와있습니다.<br>
해당 플러그인은 **1.17 - 1.20.1** 버전에서 사용할 수 있습니다.<br>
해당 플러그인에는 `필수 플러그인`이 존재하지 않습니다.<br>

관련 영상 : https://youtu.be/g5bDwntuzU4?si=XwaarBBMBS86jZa2<br>

그리고, 플러그인 API와 관련하여 질문하실거나<br>
궁금하신게 있으시다면 디스코드 `lone64`으로 연락해주세요.<br><br>

<br>

# 플러그인 공지
플러그인 API로 사용하실 때 반드시 `plugin.yml`에 아래 텍스트를 추가해주세요.
```yaml
depend:
  - XTitle
```

<br>

# 플러그인 저작권
해당 플러그인의 `저작권`은 **lone64**에게 있음을 알립니다.<br>
**소스 코드**을 사용하실 때는 반드시 `해당 Github의 주소` 또는 `개발자의 닉네임`을 출처로 명시하여 주세요.

<br>

# 플러그인 API
플러그인 API에는 몇가지의 이벤트가 존재합니다.<br>
예시 코드는 아래와 같습니다.

Gradle에서 API 사용 방법
```groovy
repositories {
    maven { url "https://nexus.lone64.org:8081/repository/maven-public/" }
}

dependencies {
    compileOnly "org.lone64:api-xtitle:1.0.0a"
}
```

Maven에서 API 사용 방법
```xml
<repositories>
    <repository>
        <id>LoneProject</id>
        <url>https://nexus.lone64.org:8081/repository/maven-public/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>org.lone64</groupId>
        <artifactId>api-xtitle</artifactId>
        <version>1.0.0a</version>
    </dependency>
</dependencies>
```

플러그인 API 이벤트 목록
- PrefixChatFormatEvent (채팅 형식을 설정할 수 있는 이벤트)
- CreatedPrefixEvent (칭호가 생성될 때 발동되는 이벤트)
- DeletedPrefixEvent (칭호가 삭제될 때 발동되는 이벤트)
- SelectedPrefixEvent (칭호가 적용될 때 발동되는 이벤트)
- AddedPrefixEvent (새로운 칭호가 추가될 때 발동되는 이벤트)
- ClearedPrefixEvent (적용한 칭호가 해제될 때 발동되는 이벤트)

<br>

# 스크립트 API
`현재 스크립트 API는 존재하지 않습니다.`
