document.getElementById("loginForm").addEventListener("submit", 
	(event) => {
		event.preventDefault();
		
		let sendCondition = new loginDataTools().emptyCheck();
		
		if (sendCondition == "Y"){
			/*
			loginForm.method = "POST";
			loginForm.action = "/login/auth"
			loginForm.submit();
			*/
			const data = event.target;
			console.log(data);
			const formData = new FormData(data);
			//var objectNameList = ["user_id", "user_pw"];
			/*
			objectNameList.forEach((objectName) => {
				formData.append(
					objectName,
					document.querySelector(`[name='${objectName}']`).value
				)
				console.log(document.querySelector(`[name='${objectName}']`).value)
			})
			*/
			
			console.log(formData);
			
			
			fetch("/login/auth",{
				method : "POST",
				body : formData
			})
			.then(response => {
				if (response.ok) {
					console.log("this??");
					return response.text();
				} else {
					console.log("or this??");
					throw new Error("알 수 없는 이유로 로그인에 실패하였습니다. 관리자에게 문의하세요.");
				}
			})
			.then(result => {
				if (result=="ok") {
					location.href("/main");
				}
				else{
					document.getElementById("response").innerHTML = `<p>${result}</p>`;
					document.getElementById("response").style.visibility = "visible";
				}
			})
			.catch(error => {
				document.getElementById("response").innerHTML = `<p>${error}</p>`;
				document.getElementById("response").style.visibility = "visible";
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