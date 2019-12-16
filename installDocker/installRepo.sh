#!/bin/bash
################################################################################
# Install docker files in a directory. 
# A subdirectory will contain all ingested files. Due to that be sure 
# that file space is sufficient!
# Usage:
# bash installRepo.sh /path/to/installationdir
################################################################################

# Check if argument is given
if [ -z "$1" ]; then
  echo Please provide a directory where to install.
  echo USAGE:
  echo   installRepo.sh /path/to/installationdir
  exit 1
fi

INSTALLATION_DIRECTORY=$(echo $1 | sed 's:/*$::')

# Check if directory exists
if [ ! -d "$INSTALLATION_DIRECTORY" ]; then
  # Create directory if it doesn't exists.
  mkdir -p "$INSTALLATION_DIRECTORY"
fi
# Check if directory is empty
if [ ! -z "$(ls -A "$INSTALLATION_DIRECTORY")" ]; then
   echo "Please provide an empty directory or a new directory!"
   exit 1
fi
echo Install Research Data Repository into $INSTALLATION_DIRECTORY
# Determine base directory
BASE_DIR=$(dirname "$0")

cp -r "$BASE_DIR"/docker/* "$INSTALLATION_DIRECTORY"
cp ConfigureDocker "$INSTALLATION_DIRECTORY"
cp setupDocker.sh "$INSTALLATION_DIRECTORY"
cd "$INSTALLATION_DIRECTORY"
chmod 777 setupDocker.sh

#Setup files for docker compose
docker build -t ocrd/setup_framework -f ConfigureDocker .

docker run -v "$INSTALLATION_DIRECTORY":/install ocrd/setup_framework /install/setupDocker.sh /install

echo SUCCESS
echo Now you can start the Research Data Repository with the following commands:
echo cd \""$INSTALLATION_DIRECTORY"\"
echo sudo docker-compose up









