#!/bin/bash
# Build dependencies
#   No dependencies yet
cp ./test/application.properties .

# Create test report
echo "Building project and executing tests"
./gradlew clean check jacocoTestReport
