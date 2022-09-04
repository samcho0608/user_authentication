#!/usr/bin/env bash

REPOSITORY="user-auth/user_authentication"

PROJECT_BASE_DIR=$(dirname $0 | sed -e 's/\/bin.*$//g')

# 이미지가 존재하지 않는다면 이미지 빌드
currentImage=$(docker images | grep user-auth)
if [ "$currentImage" == "" ]; then
  docker build $PROJECT_BASE_DIR -t $REPOSITORY
fi

{
  docker run -v /workspace/app/build -v $PROJECT_BASE_DIR:/workspace/app -p 8080:8080 $REPOSITORY &&
  echo "Successfully started running user-auth container."
  exit 0
} || {
  echo "Failed to start running user-auth container."
  exit 1
}
