## Euler Machine
A small project in Java about Euler's partition function, a function that generates how many unordered partitions a number has. 

Inside src you'll find a couple Java files and a couple txt files. Partitions.txt contains the numbers of partitions of all the numbers from 1-100000.

### Motivation
Go to [this video](https://www.youtube.com/watch?v=iJ8pnCO0nTY&ab_channel=Mathologer)! Super interesting, everything's explained there. I only implemented the algorithm in Java.

### Usage
If you want to test this out for yourself, you can download the files in src and everything. The main file is App.java, where there's a new EulerMachine object:
```java
EulerMachine e = new EulerMachine();
```
It has a couple functions, like 
```java
e.getPartition(n) // Returns a BigInteger that is the number of unordered partitions of n
e.getPartitionsArray(n) // Returns BigInteger[n+1], the number of unordered partitions of numbers 0-n
e.getSumOfFactors(n) // Returns BigInteger that is sum of factors of n
e.getSumsOfFactors(n) // Returns BigInteger[n+1], sums of factors from 0-n
e.getPrimesArray(n) // Returns int[], the primes from 1-n
```
So yeah, that's basically it. One algorithm calculates all of this. Pretty interesting function with a weird variety of applications.

### More Details about the Function
It's a recursive algorithm that finds the next number of partitions based on the past partitions. It references the previous 1st, 2nd, 5th, 7th, 12th... etc. partitions. These are the pentagonal numbers, and I'm decently sure there are about $\sqrt{n}$ pentagonal numbers below n, making the complexity of Euler's partition function about $O(n\sqrt{n})$. This is a pretty great complexity, and from what I know, it's pretty much the fastest way to find the number of unordered partitions.

#### Altered Euler Partition Function
Curiously, Euler's partition function can be altered very slightly to make a sequence of the sums of factors. For example, the sum of the factors of $15$ is $1+3+5+15=24$, and this function can find that by using recursion, which is mind-boggling to say the least.

From a computational perspective, this isn't anything amazing. We can find this using sum of factors formula, which has complexity about $O(\sqrt{n})$. So for finding sums of factors for all numbers less than $n$, Euler's partition formula and using this other sum of factors formula have the same complexity. There's still a couple applications of this function to find primes, but it has basically the same complexity as more basic, easier-to-implement algorithms.

From a mathematical perspective though, I find this to be really interesting, as we're using recursion to find sum of factors and primes.
