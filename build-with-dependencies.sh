#!/bin/bash
# Build dependencies
#   No dependencies yet
cp ./test/application.properties .

# Build Repository
git clone https://github.com/kit-data-manager/docker-kitdm-2.0.git
cd docker-kitdm-2.0
cd source
docker-compose up &
cd ../..

# Wait for setup of KIT DM and its database
sleep 480

# Create test report
echo "Building project and executing tests"
./gradlew clean check jacocoTestReport

