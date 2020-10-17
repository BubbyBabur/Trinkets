


let max = Math.pow(2,25)
let bench = Math.pow(2,20);

let allsolutions = new Map();

for(let i = 0; i < max; i++){
    let binary = [];
    let j = i;
    while(j > 0) {
        binary.push(j % 2);
        j = Math.floor(j/2);
    }

    while(binary.length < 25) {
        binary.push(0);
    }

    let grid = [];
    for (let k = 0; k < 5; k++) {
        grid.push([])
        for (let j = 0; j < 5; j++) {
            grid[k].push(0);
        }
    }

    for (let k = 0; k < 25; k++) {
        if (binary[k] === 1) {
            let [p, q] = [Math.floor(k / 5), k % 5];
            if (p - 1 >= 0) {
                grid[p - 1][q] = 1 - grid[p - 1][q]
            }
            if (p + 1 < 5) {
                grid[p + 1][q] = 1 - grid[p + 1][q]
            }
            if (q - 1 >= 0) {
                grid[p][q - 1] = 1 - grid[p][q - 1]
            }
            if (q + 1 < 5) {
                grid[p][q + 1] = 1 - grid[p][q + 1]
            }
            grid[p][q] = 1 - grid[p][q];
        }
    }

    let yay = true;
    for(let a = 0; a < 5; a++){
        for(let b = 0; b < 5; b++){
            if(grid[a][b] === 0) {
                yay = false;
            }
        }
    }

    if (i % bench === 0){
        console.log(i);
        // console.log(binary);
    }

    if(yay) {
        console.log(binary);
        console.log("WE DID IT")
        let sum = 0;
        for(const num of binary) sum += num;
        // break;
        allsolutions.set ( sum, binary )
    }
    

}

console.log(allsolutions)
