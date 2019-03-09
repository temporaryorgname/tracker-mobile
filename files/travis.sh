#!/usr/bin/env bash

# Add appropriate files for encryption

rm tracker.tar.enc
cd ..
tar cvf tracker.tar files/release.keystore files/release.properties
travis encrypt-file tracker.tar --add
rm tracker.tar
mv tracker.tar.enc files/