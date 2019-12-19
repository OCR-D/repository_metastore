# Installation Docker and Docker Compose on Ubuntu

## Installation Docker
```bash=bash
user@localhost:/home/user/$sudo apt-get update
user@localhost:/home/user/$sudo apt-get install \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg2 \
    software-properties-common
user@localhost:/home/user/$curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
user@localhost:/home/user/$sudo add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable"
user@localhost:/home/user/$sudo apt-get update
user@localhost:/home/user/$sudo apt-get install docker-ce
```
## Installation Docker Compose
```bash=bash
# Build metastore service
user@localhost:/home/user/$sudo curl -L "https://github.com/docker/compose/releases/download/1.23.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
user@localhost:/home/user/$sudo chmod +x /usr/local/bin/docker-compose
# Install Code Completion (optional)
user@localhost:/home/user/$sudo curl -L https://raw.githubusercontent.com/docker/compose/1.23.2/contrib/completion/bash/docker-compose -o /etc/bash_completion.d/docker-compose
```
## Setup Docker
After installing docker you have to set up daemon and add user to  the group 'docker'
```bash=bash
# Start docker daemon at startup
user@localhost:/home/user/$sudo systemctl enable docker
```
```bash=bash
# Add user to group 'docker'
user@localhost:/home/user/$sudo usermod -aG docker $USER
```
:warning: Please log out and log in again. 
## Test setup
Now test your installation.
```bash=bash
# Test installation
user@localhost:/home/user/$docker ps
CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES
user@localhost:/home/user/$
```
You did it!

## More Information

* [Docker](https://docs.docker.com/install/linux/docker-ce/ubuntu/#install-using-the-repository)
* [Docker Compose](https://docs.docker.com/compose/install/)
