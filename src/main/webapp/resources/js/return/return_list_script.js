document.addEventListener('DOMContentLoaded', function() {
	var rl_reset_btn = document.getElementById('rl_reset_btn');
	var rl_search_btn = document.getElementById('rl_search_btn');
	var rl_start_dt = document.getElementById('rl_start_dt');
	var rl_end_dt = document.getElementById('rl_end_dt');
	var rl_product_cd = document.getElementById('rl_product_cd');
	var rl_product_nm = document.getElementById('rl_product_nm');

	//초기화 버튼
	rl_reset_btn.addEventListener('click', function() {
		location.href = '/return_list';
	});

	//조회 버튼
	rl_search_btn.addEventListener('click', function() {
		if(rl_start_dt.value == "" && rl_end_dt.value != ""){
			alert("날짜를 확인해 주세요.");
		}
		else if(rl_start_dt.value != "" && rl_end_dt.value == ""){
			alert("날짜를 확인해 주세요.");
		}
		else{
			rl_frm.method = "get";
			rl_frm.action = "/return_listck";
			rl_frm.submit();
		}
	});

	//날짜
	rl_start_dt.addEventListener('input', dateck)
	rl_end_dt.addEventListener('input', dateck)

	function dateck() {
		if (rl_start_dt.value > rl_end_dt.value && rl_end_dt.value != "") {
			rl_end_dt.value = "";
			alert("날짜를 확인해 주세요.");
		}
	}
});