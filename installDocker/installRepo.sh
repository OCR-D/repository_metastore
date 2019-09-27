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

cp -r "$BASE_DIR"/docker "$INSTALLATION_DIRECTORY"
cd "$INSTALLATION_DIRECTORY"
# Prepare docker volumes
# Data folders
mkdir -p docker/server/arangodb
mkdir -p docker/server/postgres
mkdir -p docker/server/kitdm
# Backup folders
mkdir -p docker/server/backup/arangodb
mkdir -p docker/server/backup/postgres

# Create directory for git projects
mkdir git
# Checkout and build KIT Data Manager 2.0
cd git
git clone --recursive https://github.com/kit-data-manager/base-repo.git
cd base-repo
./gradlew -Pclean-release build
cp build/libs/*.jar ../../docker/base-repo/
cd ../..

# Checkout and build Metastore
cd git
git clone --recursive https://github.com/OCR-D/repository_metastore.git
cd repository_metastore
./gradlew -Prelease build
cp build/libs/*.jar ../../docker/metastore/
cd ../..

# Remove git projects
rm -rf git

# Change to docker directory
cd docker
echo SUCCESS
echo Now you can start the Research Data Repository with the following commands:
echo cd "\"$INSTALLATION_DIRECTORY/docker\""
echo sudo docker-compose up









