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

  - Pipeline Maven Plugin <br />

* Environment Variables

  - URL_SERVER_CONSUL = 172.17.0.1

* Docker client installtion at Jenkins container - Steps below

```sh
  apt update
  apt install apt-transport-https ca-certificates curl gnupg2 software-properties-common
  curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add -
  add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/debian $(lsb_release -cs) stable"
  apt update
  apt-cache policy docker-ce
  apt install docker-ce
  systemctl status docker
```

<em>As docker-compose creates Jenkins exposing docker daemon then the container will use docker host</em>

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

```sh
#!/bin/bash
docker build \
  --build-arg ARTIFACT_NAME="sample-app-$1" \
  --label version="$1" \
  --label hash="$(git log -n 1 --pretty=format:'%h')" \
  --label build.date="$(date --iso-8601=seconds)" \
  --label build.author="${CHANGE_AUTHOR}" \
  --label ci.refnumber="${BUILD_NUMBER}" \
  --label ci.executor="${EXECUTOR_NUMBER}" \
  --label ci.node="${NODE_NAME}" \
  --label ci.joburl="${BUILD_URL}" \
  --tag "172.17.0.1:5000/smiguelnet/sample-app:latest" .

# TAG IMAGE
docker tag "172.17.0.1:5000/smiguelnet/sample-app:latest" "172.17.0.1:5000/smiguelnet/sample-app:$1"

# INSPECT IMAGE
docker image inspect -f '{{json .Config.Labels}}' "172.17.0.1:5000/smiguelnet/sample-app:$1" | python -m json.tool

# PUSH IMAGE
docker image push "172.17.0.1:5000/smiguelnet/sample-app:latest"
docker image push "172.17.0.1:5000/smiguelnet/sample-app:$1"
```

### Deploy

```groovy
build job: 'Sample-App-Deploy',
    parameters: [
            [$class: 'BooleanParameterValue', name: 'runSmokeTest', value: true],
            [$class: 'BooleanParameterValue', name: 'runLoadTest', value: true]
    ]
```

# Notes

> In order to run some scripts you will need to get Jenkins approval

> It is required to expose DOCKER_HOST daemon port
