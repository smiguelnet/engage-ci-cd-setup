# Environment Setup with Ansible \* WIP \*

Ansible will be in charge to compose the whole environment used for this demo. Find below detailed steps.

---

## Install Ansible

Ref. [Ansible Installation](https://docs.ansible.com/ansible/latest/installation_guide/intro_installation.html)

---

## Setup Docker Machine

### Create a virtual machine to install all applications

```sh
#Creates a new default virtual machine and setup a insecure registry entry
docker-machine create --driver virtualbox --engine-insecure-registry 127.0.0.1:5000 default
```

# Setup Ansible Host

In the host machine, edit host file and add an entry for docker-machine or for docker desktop

```sh
[servers]
# Docker Machine
192.168.99.100 ansible_connection=ssh ansible_port=22

[localserver]
# Docker Desktop
127.0.0.1 ansible_connection=local
```

Checking host connectivity

```
ansible localserver -m ping
```
