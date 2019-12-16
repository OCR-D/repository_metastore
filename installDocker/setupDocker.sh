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
  echo   $0 /path/to/installationdir
  exit 1
fi

INSTALLATION_DIRECTORY=$(echo $1 | sed 's:/*$::')

# Check if directory exists
if [ ! -d "$INSTALLATION_DIRECTORY" ]; then
  # Create directory if it doesn't exists.
  mkdir -p "$INSTALLATION_DIRECTORY"
fi

# Prepare docker volumes
# Data folders
mkdir -p /install/server/arangodb
mkdir -p /install/server/postgres
mkdir -p /install/server/kitdm
# Backup folders
mkdir -p /install/server/backup/arangodb
mkdir -p /install/server/backup/postgres

# Create directory for git projects
mkdir /install/git
# Checkout and build KIT Data Manager 2.0
cd /install/git
git clone --recursive https://github.com/kit-data-manager/base-repo.git
cd base-repo
./gradlew -Pclean-release build
cp build/libs/*.jar /install/base-repo/
cd ../..

# Checkout and build Metastore
cd git
git clone --recursive https://github.com/OCR-D/repository_metastore.git
cd repository_metastore
./gradlew -Prelease build
cp build/libs/*.jar /install/metastore/
cd ../..

# Remove git projects
rm -rf git

chmod -R a+rwX /install

# End of setup for docker compose








