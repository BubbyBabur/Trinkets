
let stuff = "";

for(let i = 0; i <= 6; i++){
    stuff += `\\left(1-\\left|w-${i}\\right|\\right)b_{${i}}\\left[\\operatorname{floor}\\left(t\\right)+1\\right]=3,`
}

console.log(stuff);


