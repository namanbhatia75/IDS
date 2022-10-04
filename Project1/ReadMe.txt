Project 1
Naman Bhatia
6048-7800

HashRecord.java -
This class gives us the record in the table and is being used in other places hence it is important to compile it first.
Every HashRecord object has a 'flowId' and a 'counter'.

MultiHashing.java class implements the multi hashing algorithm.
It has 3 arguments:
1. numEntries
2. numFlows
3. numHashes
I have tested it for the given values i.e 1000, 1000, 3

CuckooHashing.java class implements the cuckoo hashing algorithm.
It has 4 arguments:
1. numEntries
2. numFlows
3. numHashes
4. cuckooSteps
I have tested it for the given values i.e 1000, 1000, 3 , 2

DLeftHashing.java class implements the D-LeftHashing algorithm.
It has 3 arguments:
1. numEntries
2. numFlows
3. numSegments
I have tested it for the given values i.e 1000, 1000, 4

MultiHashing.txt - output file for MultiHashing algorithm, first line
indicates number of flows recorded (hits) second line show number of misses
and the next line onwards show hash table values. A '0' stands for an empty record.

CuckooHashing.txt - output file for Cuckoo hashing algorithm, first line
indicates number of flows recorded (hits) second line show number of misses
and the next line onwards show hash table values. A '0' stands for an empty record.

DLeftHashing.txt - output file for d-lefthash algorithm, first line
indicates number of flows recorded (hits) second line show number of misses
and the next line onwards show hash table values. A '0' stands for an empty record.

To compile the files, open terminal where the files are located and run the following:
1. javac HashRecord.java
2. javac MultiHashing.java
3. javac CuckooHashing.java
4. javac DLeftHashing.java

To run project run the following commands:

1. java MultiHashing 1000 1000 3
2. java CuckooHashing 1000 1000 3 2
3. java DLeftHashing 1000 1000 4
