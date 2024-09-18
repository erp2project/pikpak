document.getElementById("loginForm").addEventListener("submit", 
	(event) => {
		event.preventDefault();
		
		let sendCondition = new loginDataTools().emptyCheck();
		
		if (sendCondition == "Y"){
			const data = event.target;
			const formData = new FormData(data);
			
			fetch("/login/auth",{
				method : "POST",
				body : formData
			})
			.then(response => {
				if (response.ok) {
					return response.text();
				} else {
					throw new Error("알 수 없는 이유로 로그인에 실패하였습니다. 관리자에게 문의하세요.");
				}
			})
			.then(result => {
				if (result=="ok") {
					alert("로그인 성공 하였습니다");
					location.href="/home";
				}
				else{
					document.getElementById("response").innerHTML = `<p>${result}</p>`;
					document.getElementById("response").style.visibility = "visible";
					document.getElementById("response").style.opacity = "1";
				}
			})
			.catch(error => {
				//document.getElementById("response").innerHTML = `<p>${error}</p>`;
				document.getElementById("response").innerHTML = `<p>"알 수 없는 이유로 로그인에 실패하였습니다. 관리자에게 문의하세요"</p>`;
				document.getElementById("response").style.visibility = "visible";
				document.getElementById("response").style.opacity = "1";
			});
		}
	}
);

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