#!/bin/bash
################################################################################
# Install docker files in a directory. 
# A subdirectory will contain all ingested files. Due to that be sure 
# that file space is sufficient!
# Usage:
# bash installRepo.sh /path/to/installationdir
################################################################################

# Test for commands used in this script
testForCommands="sed git java"

for command in $testForCommands
do 
  $command --help >> /dev/null
  if [ $? -ne 0 ]; then
    echo "Error: command '$command' is not installed!"
    exit 1
  fi
done

# Check if argument is given
if [ -z "$1" ]; then
  echo Please provide a directory where to install.
  echo USAGE:
  echo   $0 /path/to/installationdir
  exit 1
fi

INSTALLATION_DIRECTORY=$(echo $1 | sed 's:/*$::')

# Check if directory exists
if [ ! -d "$INSTALLATION_DIRECTORY" ]; then
  # Create directory if it doesn't exists.
  mkdir -p "$INSTALLATION_DIRECTORY"
fi

cd "$INSTALLATION_DIRECTORY"
INSTALLATION_DIRECTORY=`pwd`

# Prepare docker volumes
# Data folders
mkdir -p "$INSTALLATION_DIRECTORY"/server/arangodb
mkdir -p "$INSTALLATION_DIRECTORY"/server/postgres
mkdir -p "$INSTALLATION_DIRECTORY"/server/kitdm
# Backup folders
mkdir -p "$INSTALLATION_DIRECTORY"/server/backup/arangodb
mkdir -p "$INSTALLATION_DIRECTORY"/server/backup/postgres

# Create directory for git projects
mkdir "$INSTALLATION_DIRECTORY"/git
# Checkout and build KIT Data Manager 2.0
cd "$INSTALLATION_DIRECTORY"/git
git clone --recursive https://github.com/kit-data-manager/base-repo.git
cd base-repo
./gradlew -Pclean-release build
cp build/libs/*.jar "$INSTALLATION_DIRECTORY"/base-repo/

# Checkout and build Metastore
cd "$INSTALLATION_DIRECTORY"/git
git clone --recursive https://github.com/OCR-D/repository_metastore.git
cd repository_metastore
./gradlew -Prelease build
cp build/libs/*.jar "$INSTALLATION_DIRECTORY"/metastore/

# Remove git projects
rm -rf "$INSTALLATION_DIRECTORY"/git

chmod -R a+rwX "$INSTALLATION_DIRECTORY"

# End of setup for docker compose








