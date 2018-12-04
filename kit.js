var fs = require('fs');

module.exports.kit = kit;


var kitData;
var codeTitle;
var content;
var language;
var parsedCode = [];

function kit(source){
    
    var codeContinue = false;
    var code = "";
    parsedCode = [];

    var splitStr = source.split("\n");
    splitStr.forEach(element => {
        var kitbits = element.split("@kit:");
        
        if(kitbits.length>1){
            var kitbit = kitbits[kitbits.length-1];
            console.log("[kit]:" + kitbit);
            var kitatom = kitbit.split(":");
            switch(kitatom[0].trim()){
                case "title":
                    codeTitle = "";
                    for(var i=1;i<kitatom.length;i++){
                        codeTitle += kitatom[i].trim();
                    }
                    break;
                    case "language":
					language ="";
					for(var i=1;i<kitatom.length;i++) {
						language +=kitatom[i].trim();
					}
					break;
				case "content":
					content = "";
					for(var i=1;i<kitatom.length;i++) {
						content +=kitatom[i].trim();
					}
					break;
				case "start":
					codeContinue = true;
					break;
				case "end":
					//code variable clean
					if(!code == "") {
						codeContinue =false;
						parsedCode.push(code);
						code = "";
					}
					break;
				default : break;
            }
        } else {
            if(codeContinue){
                if(kitbits[0].length>0){
                    code += kitbits[0];
                    code += "\n";
                }
            }
        }

        kitData = {'codeTitle':codeTitle, 'language':language, 'content':content, 'parsedCode':parsedCode};
    
    });
    return kitData;
}
