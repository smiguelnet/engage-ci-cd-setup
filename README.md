# engage-ci-cd-setup

Local Environment Setup and a Simple Demo Project to illustrate the whole CI/CD process.

## Overview

![Local Env Setup + Simple Demo Process](assets/img/overview.png "CD/CD Overview")

---

## Toolkit

- **Infrastructure**

  - Ansible (v2.9.2)

- **Code Repository**

  - Github (Hosts Demo project)
  - Gitlab (Used for local repository <em>\* Not this sample</em>)

- **Build and Deploy**

  - Maven
  - Jenkins

- **Quality Assurance**

  - Sonar

- **Library Repository**

  - Nexus

- **Container**

  - Docker
  - Docker Registry

- **Distributed Configuration**

  - Consul

- **Metrics, Data Evaluation and Presentation**
  - Prometheus
  - Splunk
  - Grafana

---

## URL Access

- Jenkins <br /> http://localhost:14300/

- Nexus <br /> http://localhost:8081/

- Consul <br /> http://localhost:8500/

- Prometheus <br /> http://localhost:9090/

- Grafana <br /> http://localhost:3000/

- Sonar <br /> http://localhost:9000/

- Splunk <br /> http://localhost:8000/

- Docker Registry Images <br /> http://localhost:5000/v2/_catalog

- Sample App <br /> http://localhost:8080/

---

## Demo Project

- **sample-app** <br /> https://github.com/smiguelnet/sample-app-ci-cd

---

## Build Environment Up

```
docker-compose up
```

### Useful commands

In case you need to rebuild the images

```
docker-compose build --no-cache
```

Stop the instances

```
docker-compose stop
```

Remove the instances

```
docker-compose rm
```

Get Jenkins password

```
docker exec jenkins cat /home/jenkins/secrets/initialAdminPassword
```

Get Nexus password

```
docker exec nexus cat nexus-data/admin.password
```

---

## License

**engage-ci-cd-setup** is licensed under an Apache-2.0 license
