#!/bin/bash
# Build dependencies
#   No dependencies yet
cp ./test/application.properties .

# Build Repository
git clone https://github.com/kit-data-manager/base-repo.git
cd base-repo
git submodule init
git submodule update --remote --merge 
cd libraries/service-base/
./gradlew install
cd ../../
cd libraries/generic-message-consumer/
./gradlew install
cd ../../
./gradlew -Prelease build

# Create test report
echo "Building project and executing tests"
./gradlew clean check jacocoTestReport
