#!/bin/bash

folder=no-errors

for infile in `ls tests/$folder/*.go`; do
    make runll file=$infile  flag=-c
done