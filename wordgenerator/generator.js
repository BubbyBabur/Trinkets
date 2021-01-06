// Note: dictionary.txt must be in the same directory
// Also this file runs in nodejs, so you might need npm init

// Number of words generated, but many might be duplicates
const number = 1_000_000;
// Prefix, like "rog" or "kevin", all lowercase, its length must be more than or equal to combolength
const prefix = "rog";
// Or you can generate purely organic words without a prefix
const generateWithoutPrefix = false;
// I use a markov chain, so this is a variable for the combo length
// Generally, better results for larger combo
const combolength = 3;

// Min length of words
const minlength = 5;

// Log in console when running
const logmilestones = true;
const milestonemark = 100_000;

// This is literally just a markov chain but with characters. It iterates through all the words in dictionary and generates words.

// imports
const fs = require('fs');

// Alphabets, some other things
const alphabet = "abcdefghijklmnopqrstuvwxyz"
const endchar = "#"
const validchars = alphabet + endchar;

// Find valid words, end them with the endchar
const words = fs.readFileSync('./dictionary.txt').toString().split('\n').map(word => word.toLowerCase()).filter(word => [...word].every(char => alphabet.includes(char))).map(word => word + endchar);

/**
 * @type {Map<string,MarkovObj>}
 * @description A map from a combo, e.g. "tio" to a MarkovObj, representing a markov model
 */
const markov = new Map();

/**
 * @type {Map<string,number>}
 * @description Just a map of how many times a combo appears first, just in case you want to generate organic words without a prefix.
 */
const start = new Map();

// Total start combinations
start.total = function() {

    let total = 0;
    for (const key of this.keys()) {
        total += this.get(key);
    }
    this.cache = total;
    return total;

}

start.prepare = function() {
    this.weightedlist = new Array(this.total());

    let index = 0;
    for (const key of this.keys()) {
        let nextindex = index + this.get(key);
        for(; index < nextindex; index++) {
            this.weightedlist[index] = key;
        }
    }
}

// A weighted random of first combos to start off organic words
start.weightedrandom = function() {
    // let curr = this.total() * Math.random();
    // for (const key of this.keys()) {
    //     curr -= this.get(key);
    //     if(curr <= 0) {
    //         return key;
    //     }
    // }

    let index = Math.floor(Math.random() * this.weightedlist.length);
    return this.weightedlist[index];
}

/**
 * @param {number} num
 * @returns {Set<string>}
 * @description Generate all combinations of num length, e.g. for num=2 it'd be "aa","ab", ..etc
*/
function combinations(num) {
    if(num === 0 ) {
        let empty = new Set();
        empty.add("");
        return empty;
    }

    let combos = new Set();

    let possible = num === 1 ? validchars : alphabet;
    for(const char of possible) {
        for(const combo of combinations(num - 1)) {
            combos.add(combo + char);
        }
    }
    return combos;
}

/**
 * @class
 * @classdesc A class for what comes after a combo. For example, after "abc", this class would keep track of how many times "a","b","c"...etc. occur after "abc"
 */
class MarkovObj {
    constructor() {
        /**
         * @type {Map<string,number>}
         */
        this.chars = new Map();
        this.alphabet = validchars;

        for(const char of this.alphabet) {
            this.chars.set(char, 0);
        }
    }

    add(a) {
        if(this.chars.has(a)) {
            this.chars.set(a, this.chars.get(a) + 1);
            return true;
        }
        return false;
    }

    total() {
        return [0, ...this.chars].reduce((init,curr) => init + curr[1]);
    }

    random() {

        const total = this.total();
        if(total === 0) return validchars.charAt(Math.floor(Math.random() * validchars.length));

        let chararray = [...this.chars];
        
        chararray = chararray.map(a => [a[0], a[1] / total]);

        let rand = Math.random();

        for(let i = 0; i < chararray.length; i++) {
            rand -= chararray[i][1];
            if(rand <= 0) {
                return chararray[i][0];
            }
        }
    }

    randomWithoutEnd() {

        const total = this.total() - this.chars.get(endchar);
        if (total === 0) return alphabet.charAt(Math.floor(Math.random() * alphabet.length));

        let chararray = [...this.chars].slice(0,-1);
        
        chararray = chararray.map(a => [a[0], a[1] / total]);

        let rand = Math.random();

        for (let i = 0; i < chararray.length; i++) {
            rand -= chararray[i][1];
            if (rand <= 0) {
                return chararray[i][0];
            }
        }
    }

    toObj() {
        let obj = Object.create(null);
        for(const char of this.alphabet) {
            obj[char] = this.chars.get(char);
        }
        return obj;
    }
}

// For each combo, initialize the markov model and the start map
for (const combo of combinations(combolength)) {
    markov.set(combo, new MarkovObj());
    start.set(combo,0);
}

// Just go through words and do the markov model /shrug
for(const word of words) {

    const firstcombo = word.slice(0,combolength);
    if(start.has(firstcombo)) start.set(firstcombo, start.get(firstcombo) + 1)

    for (let i = 0; i < word.length - combolength; i++) {
        let prevcombo = word.slice(i, i + combolength);
        markov.get(prevcombo).add(word.charAt(i + combolength));
    }
}

start.prepare();

/*
// For analyzing data/character combos, which might be fun in the future
const data = Object.create(null);
for(const combo of markov.keys()) {
    data[combo] = markov.get(combo).toObj();
}
let writeobj = {
    endchar, 
    alphabet,
    validchars,
    combolength,
    data
}

fs.writeFileSync(`./data/generator-${combolength}.json`, JSON.stringify( writeobj) );
*/

/* ----------------- */
// This is the generation part

console.log("Generating...");

let generated = new Set();

for(let i = 0; i < number; i++) {

    // We don't like words < 5 chars in length

    let newword;
    if (generateWithoutPrefix) {
        newword = start.weightedrandom();
    } else {
        newword = prefix;
    }

    while (!newword.includes(endchar)) {
        const combo = newword.slice(-combolength);
        let nextchar;
        if (newword.length < minlength) { 
            nextchar = markov.get(combo).randomWithoutEnd();
        } else {
            nextchar = markov.get(combo).random();
        }

        newword += nextchar;
    }

    newword = newword.slice(0, -1);

    generated.add(newword);

    // Milestone
    if (i % milestonemark === milestonemark - 1 && logmilestones) {
        console.log(`(${i + 1}/${number}) I have generated unique ${generated.size} words.`);
    }


}

console.log("Done!");

const wordlist = [...generated];
// Sort by length
wordlist.sort((a,b) => a.length - b.length);

// Use fs to write file
fs.writeFileSync(`./data/${generateWithoutPrefix ? `organic` : prefix}x${wordlist.length}.txt`,wordlist.join('\n'));
