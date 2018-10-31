#!/bin/bash
# Build dependencies
#   No dependencies yet

# Create test report
echo "Building project and executing tests"
./gradlew clean check jacocoTestReport
