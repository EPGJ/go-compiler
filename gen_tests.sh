#!/bin/bash

FOLDER=with-erros

for infile in `ls tests/$FOLDER/*.go`; do
    make run FILE=$infile 
done