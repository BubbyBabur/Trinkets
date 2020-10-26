let stuff = "";

let x = -5;
let dir = 1;
let y = 8;
while(y > -8) {
    stuff += `\\left(${x},${y}\\right),`
    y -= Math.random() * 2+0.3;
    x += Math.random() * 2 * dir + 0.2;
    dir *= -1;
}


console.log(stuff);