#!/bin/bash

FOLDER=with-errors

for infile in `ls tests/$FOLDER/*.go`; do
    make run FILE=$infile 
done