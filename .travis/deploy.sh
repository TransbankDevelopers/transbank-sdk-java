#!/bin/bash
TRAVIS_TAG=v2.0.0

if [ ! -z "$TRAVIS_TAG" ]
then
    echo "Tag found"
    if ( echo $TRAVIS_TAG | egrep -i '^v[0-9]+\.[0-9]+\.[0-9]+')
    then
        VERSION_NUMBER=${TRAVIS_TAG:1}
        echo "on a tag -> set pom.xml <version> to $VERSION_NUMBER"
        mvn --settings .travis/settings.xml org.codehaus.mojo:versions-maven-plugin:2.1:set -DnewVersion=$TRAVIS_TAG 1>/dev/null 2>/dev/null
    else
        echo "Tag does not start with v: ${TRAVIS_TAG} keep snapshot version in pom.xml"
    fi
else
    echo "not on a tag -> keep snapshot version in pom.xml"
fi

mvn clean deploy --settings .travis/settings.xml -DskipTests=true -B -U
