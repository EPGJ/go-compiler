#!/bin/bash

folder=with-errors

for infile in `ls tests/$folder/*.go`; do
    make runll file=$infile 
done