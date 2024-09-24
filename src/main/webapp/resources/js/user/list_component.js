// 회원 검색
const lockIdSearch = () => {
	let idFilter = document.querySelector("[name='userIdSearch']");
	if (idFilter.value != "") {
		idFilter.readOnly = true;
		idFilter.style.backgroundColor = "#DCDCDC";
	}
	else {
		idFilter.focus();
	}
}

const lockNameSearch = () => {
	let nameFilter = document.querySelector("[name='userNameSearch']");
	if (nameFilter.value != "") {
		nameFilter.readOnly = true;
		nameFilter.style.backgroundColor = "#DCDCDC";
	}
	else {
		nameFilter.focus();
	}
}



class listSearchTools {
	checkEmpty(){
		let typeFilter = document.querySelector("[name='userNameSearch']");
		let idFilter = document.querySelector("[name='userIdSearch']");
		let nameFilter = document.querySelector("[name='userNameSearch']");
		let sendCondition = "N";
		
		if (typeFilter.value == "") {
			typeFilter.focus();
		}
		else if (idFilter.value == "") {
			idFilter.focus();
		}
		else if (nameFilter.value == "") {
			nameFilter.focus();
		}
		else {
			sendCondition = "Y";
		}
		
		return sendCondition;
	}
}
