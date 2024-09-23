const checkIdDuplicate = () => {
	var userId = document.getElementById("user_id");
	var responseArea = document.getElementById("id-check-response");
	let checkedStatus = document.getElementById("id-checked-status");
	let searchParams = new URLSearchParams();
	searchParams.append("user_id", userId.value);
	
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
			body : searchParams
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
				userId.setAttribute("readonly", "readonly");
				//userId.style.border = "2px solid grey";
				userId.style.backgroundColor = "#DCDCDC";
			}
			else {
				responseMsg = "※ 사용 불가능한 아이디입니다";
				userId.removeAttribute("readonly");
			}
			responseArea.innerHTML = responseMsg;
		})
		.catch(error => {
			console.log(error);
			responseMsg = "시스템 에러발생! 관리자 문의 부탁합니다.";
			responseArea.innerHTML = responseMsg;
		})
	}
	responseArea.style.opacity = "1";
}

const checkTelDuplicate = () => {
	let userTel = document.getElementById("user_tel");
	let userType = document.getElementById("user_type");
	let userId = document.getElementById("user_id");
	var responseArea = document.getElementById("tel-check-response");
	let searchParams = new URLSearchParams();
	searchParams.append("user_tel", userTel.value);
	searchParams.append("user_type", userType.value);
	
	responseArea.style.opacity = "0";
	
	if (userTel.value == "") {
		responseMsg = "※ 연락처를 입력하셔야 합니다";
		responseArea.style.color = "red";
		responseArea.innerHTML = responseMsg;
		userTel.focus();
	}
	else {
		fetch('/admin/check/tel',{
			method : "POST",
			headers : {
				"content-type" : "application/x-www-form-urlencoded"
			},
			body : searchParams
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
				responseMsg = "연락처가 중복 되지 않습니다";
				responseArea.style.color = "green";
				userTel.setAttribute("readonly", true);
				//userId.style.border = "2px solid grey";
				userTel.style.backgroundColor = "#DCDCDC";
				
				var newUserId = new modUserDataTools().generateNewId(userTel.value, userId.value);
				userId.value = newUserId;
				checkIdDuplicate();
			}
			else {
				responseMsg = "※ 해당 연락처는 이미 사용중입니다";
			}
			responseArea.innerHTML = responseMsg;
		})
		.catch(error => {
			responseMsg = "시스템 에러발생! 관리자 문의 부탁합니다.";
			responseArea.style.color = "red";
			responseArea.innerHTML = responseMsg;
		})
	}
	responseArea.style.opacity = "1";
}

const checkPassword = () => {
	
}

const sendModUserForm = () => {
	
}

class modUserDataTools {
	generateNewId(userTel, userId){
		var userTelSlice = userTel.slice(-4);
		var userIdSlice = userId.split("_",2);
		var result = userIdSlice[0] + "_" + userIdSlice[1] + "_" + userTelSlice;
		return result;
	}
	
	checkDuplicateStatus(){
		
	}
	
}
