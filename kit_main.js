var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var fs = require('fs');
var kit = require('./kit.js');

app.set('views', __dirname + '/views');
app.set('view engine', 'ejs');
app.engine('html', require('ejs').renderFile);

app.use(bodyParser.json());
app.use(bodyParser.urlencoded());

app.listen(3000, function(){
    console.log('start 3000 port');

var rekit = kit.kit('//@kit:title: test \n //@kit:language: java \n //@kit:content: java-hello world \n //@kit:start \nSystem.out.println("hello world");\n r//@kit:end      ');
console.log("========answer=========")
console.log(rekit);
console.log("=======================")

});