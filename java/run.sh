# Run java day 1 from file 1.java
# Usage: ./run.sh 1

#!/bin/env bash

set -e

javac Day$1.java -d bin
echo "# Running Day$1"
java -cp bin Day$1 
