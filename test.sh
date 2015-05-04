#!/bin/bash

rm -r input
rm -r output
mkdir input
mkdir output

for f in tests/*
do
    fn=${f:6}
    echo $fn
	cp $f input/$fn
	make clean
	make &>log.txt
    cp expected/$fn output/$fn
    cat stage9/output.txt >> output/$fn
    rm input/$fn
done

make clean

python test.py
