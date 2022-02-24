#!/bin/bash

FOLDER=no-errors

for infile in `ls tests/$FOLDER/*.go`; do
    make run FILE=$infile 
done