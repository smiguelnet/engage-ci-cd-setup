## Jenkins Pipeline

---

## Jenkins Pipeline Requirements

- Global Tool Configuration

  - JDK 1.8
  - Maven
  - Docker

- Plugins

  - Pipeline Utility Steps <br />
    https://jenkins.io/doc/pipeline/steps/pipeline-utility-steps/

  - JaCoCo <br />
    https://plugins.jenkins.io/jacoco

- Environment Variables
  - URL_SERVER_CONSUL = 172.17.0.1
  - DOCKER_HOST = 172.17.0.1

---

## Jenkins Pipeline Stages

### Pre-flight

```groovy
printLineSep("SCM - Branch")
echo "${env.BRANCH_NAME}"

printLineSep("SCM - ChangeSets")
def changeLogSets = currentBuild.changeSets
for (int i = 0; i < changeLogSets.size(); i++) {
    def entries = changeLogSets[i].items
    for (int entryIndex = 0; entryIndex < entries.length; entryIndex++) {
        def entry = entries[entryIndex];
        echo "${entry}"
    }
}
```

### Validate

```sh
mvn validate
```

### Compile

```sh
mvn clean compile
```

### Test (Unit-Test + Integrated-Test)

```sh
mvn test
```

### Coverage

```groovy
jacoco(
        execPattern: "target/*.exec",
        classPattern: "target/classes",
        sourcePattern: "src/main/java",
        exclusionPattern: "src/test*")
```

### Releasing

```sh
mvn version:set -DnewVersion='${env.CURRENTVERSION}' -f pom.xml -DskipTests
mvn versions:commit
```

### Artifact

```groovy
mvn source:jar deploy -DskipTests -Djenkins.build.number=${BUILD_NUMBER}
```

### Docker

```groovy

```

### Deploy

```groovy
build job: 'Sample-App-Deploy',
    parameters: [
            [$class: 'BooleanParameterValue', name: 'runSmokeTest', value: true],
            [$class: 'BooleanParameterValue', name: 'runLoadTest', value: true]
    ]
```
