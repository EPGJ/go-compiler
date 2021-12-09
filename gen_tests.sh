#!/bin/bash

for infile in `ls tests/in/*.go`; do
    make run FILE=../../$infile 
done