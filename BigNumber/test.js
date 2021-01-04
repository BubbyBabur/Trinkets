const fs = require("fs");
let a = 256n;
a **= 3n;
a **= 512n;
a **= 512n;
let writer = fs.createWriteStream("jssupremacy.txt")
writer.write(a);