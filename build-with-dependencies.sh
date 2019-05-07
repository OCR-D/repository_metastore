#!/bin/bash
# Build dependencies
#   No dependencies yet
cp ./test/application.properties .

# Build Repository
git clone https://github.com/kit-data-manager/docker-kitdm-2.0.git
cd docker-kitdm-2.0
cd source
docker-compose up

# Create test report
echo "Building project and executing tests"
./gradlew clean check jacocoTestReport

