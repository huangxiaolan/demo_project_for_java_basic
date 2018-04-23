//socket的路径
var socketPath="/guojitiaoqi";
//socket的url
var socketUrl='http://localhost:9021/';
//发送的数据
var postData={
	"channelid":"guangzhou1",
	"gameid":"ninedead",
	"roomid":"568bc11c-504c-4be8-9104-0be6b0cc2368",
	"player":{    
	"uid":"1830969818",
	"name":"mr.yipxx",
	"avatarurl":"http://s1.yy.com/guild/header/10001.jpg",
	"teamid":"","opt":""
	}
};

//构造结果数据
function getJsonString(data,probufBean){
	var buffer=new Uint8Array(data);
	var jsonObject=probufBean.decode(buffer).asJSON();
	return JSON.stringify(jsonObject);
}

function getSendObject(data,probufBean){
	var post=probufBean.create(data);
    var sourcePost=probufBean.encode(post).finish();
	var postBuffer=sourcePost.buffer.slice(0,sourcePost.byteLength);
	return postBuffer;
}




