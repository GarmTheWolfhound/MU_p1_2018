//var active = [][];
var rndNum = 0;
var res = "";

for(var i = 0; i < 120; i++){
	for(var j = 0; j < 16; j++){
		rndNum = Math.random()*2;
		if(rndNum < 1){
			res += "0";
		}else{
			res += "1";
		}
	}
	res += "<br>";
}

document.getElementById("read").innerHTML = res;