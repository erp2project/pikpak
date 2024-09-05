const loginFormSend = () => {
	let sendCondition = new loginDataTools().emptyCheck();
	if (sendCondition == "Y"){
		loginForm.method = "POST";
		loginForm.action = "/login/auth"
		loginForm.submit();
	}
}

class loginDataTools{
	emptyCheck(){
		let user_id = document.querySelector("[name='user_id']");
		let user_pw = document.querySelector("[name='user_pw']");
		let returnCondition = "N";
		
		if (user_id.value == "") {
			alert("아이디를 입력해주세요");
			user_id.focus();
		}
		else if (user_pw.value == "") {
			alert("비밀번호를 입력해주세요");
			user_pw.focus();
		}
		else{
			returnCondition = "Y";
		}
		
		return returnCondition;
	}
	
}