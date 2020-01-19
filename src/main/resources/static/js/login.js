alert("nisgggg")

function login(){
	var Login = {};
	Login.username = document.getElementById("username").value;
	Login.password = document.getElementById("password").value;
	if(Login.username =='')
	{
		alert("please enter user name.");
	}
	else if(Login.password=='')
	{
    	alert("enter the password");
	}
//	else if(Login.password.length < 6 )
//	{
//		alert("Password min and max length is 6.");
//	}
	else{
		$.ajax({
         url: '/loginAs',
         method: 'POST',
         data: JSON.stringify(Login),
         contentType: 'application/json',
         success: function (data) {
          alert("Saved successfully"+ JSON.stringify(Login));
         },
         fail : function( jqXHR, textStatus ) {
          alert( "Request failed: " + textStatus );
        }
     })
	 
	}
}
function delete()
{
	console.log("nish")
}
