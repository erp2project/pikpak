const checkUserType = () => {
	var userType = document.querySelector("[name='user_type']").value;
	var userLvRow = document.getElementById("user_lv_row");
	
	if(userType == "operator" || userType == "admin"){
		userLvRow.style.visibility = "visible";
	}
	else{
		userLvRow.style.visibility = "collapse";
	}
}

const checkUserId = () => {
	var userId = document.getElementById("user_id");
	var responseArea = document.getElementById("id-check-response");
	responseArea.style.opacity = "0";
	
	if (userId.value == "") {
		responseMsg = "※ 아이디를 입력하셔야 합니다";
		responseArea.innerHTML = responseMsg;
		userId.focus();
	}
	else{
		fetch('/admin/check/id',{
			method : "POST",
			headers : {
				"content-type" : "application/x-www-form-urlencoded"
			},
			body :  new URLSearchParams({user_id : userId.value})
		})
		.then(response => {
			if(response.ok){
				return response.text();
			}
			else {
				throw new Error("알 수 없는 이유로 조회가 실패하였습니다. 관리자에게 문의하세요.");
			}
		})
		.then(result => {
			if (result == "Y") {
				responseMsg = "사용 가능한 아이디입니다";
				responseArea.style.color = "green";
				userId.setAttribute("readonly", true);
				userId.style.border = "2px solid grey";
			}
			else {
				responseMsg = "※ 사용 불가능한 아이디입니다";
			}
			responseArea.innerHTML = responseMsg;
			
			//userId.style.backgroundColor = "#A2A2A2";
		})
		.catch(error => {
			console.log(error);
		})
	}
	responseArea.style.opacity = "1";
}

const sendAddUserForm = () => {
	var userId = document.getElementById("user_id");
	userId.setAttribute("style", "background-color:#A2A2A2");
	userId.setAttribute("readonly", true);
}