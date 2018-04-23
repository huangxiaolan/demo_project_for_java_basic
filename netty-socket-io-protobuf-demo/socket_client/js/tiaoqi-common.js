

protobuf.load("guojitiaoqi/base.proto", function(err, root) {
		   if(err)
		      throw err;
			  
			protobuf.load("guojitiaoqi/game.proto", function(err1, gameRoot) {
			   if(err1)
				  throw err1;
		   
				// Obtain a message type
				 var LoginRes = root.lookup("base.LoginRes");
				 
				 var GameOverRes=gameRoot.lookup("pb.GameOverRes");
				 
				 var LoadProgressReq=root.lookup("base.LoadProgressReq");
				 
				 var LoadProgressRes=root.lookup("base.LoadProgressRes");
				 
				 var MoveReq=gameRoot.lookup("pb.MoveReq");
				  
					var userName = 'user' + Math.floor((Math.random()*1000)+1);

				var socket =  io.connect(socketUrl,{path:socketPath,
					query: {
					   post_data: JSON.stringify(postData)
					 }       
				   });

				socket.on('connect', function(data) {
					output('<span class="connect-msg">Client has connected to the server!</span>');
					console.log(JSON.stringify(data));
				});
				
				socket.on('login_res', function(data,ss) {
					output('<span class="connect-msg">Client has connected to the server!</span>');
					console.log("login_res:"+getJsonString(data,LoginRes));
					var progressData={progress:100};
					
					var postBuffer=getSendObject(progressData,LoadProgressReq);
					socket.emit("load_progress_req",postBuffer);
				});
				
				socket.on("load_progress_res",function(data){
					
					console.log("load_progress_res:"+getJsonString(data,LoadProgressRes));
				});
				
				socket.on('user_login_res', function(data) {
					output('<span class="connect-msg">Client has connected to the server!</span>');
					console.log("user_login_res:"+data);
				});
				
				socket.on('game_over_res', function(data) {
					output('<span class="connect-msg">Client has connected to the server!</span>');
					console.log("game_over_res:"+getJsonString(data,GameOverRes));
				});

				socket.on('disconnect', function() {
					output('<span class="disconnect-msg">The client has disconnected!</span>');
				});
				
				socket.emit("move_req_1",{});
				
				socket.on("move_res",function(data){
					
					console.log("move_res:"+getJsonString(data,LoginRes));
				});
				
				

				function sendDisconnect() {
						socket.disconnect();
				}

				function sendMessage() {
								var message = $('#msg').val();
								$('#msg').val('');

								var jsonObject = {userName: userName,
												  message: message};
								socket.emit('chatevent', jsonObject);
				}

				function output(message) {
								var currentTime = "<span class='time'>" +  moment().format('HH:mm:ss.SSS') + "</span>";
								var element = $("<div>" + currentTime + " " + message + "</div>");
					$('#console').prepend(element);
				}
			
			});
			
		});
	    
       

		

        $(document).keydown(function(e){
            if(e.keyCode == 13) {
                $('#send').click();
            }
        });