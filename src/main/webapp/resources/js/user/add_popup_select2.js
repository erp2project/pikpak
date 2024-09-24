//select2 검색기능
$("[name='user_type']").select2({
	theme: 'bootstrap',
	dropdownParent : $('#addUserModal'),
	placeholder : '유형 선택',
	minimumResultsForSearch: -1,
	//closeOnSelect : true
	//dropdownAutoWidth : true
	allowClear : true
});

$("[name='user_co']").select2({
	theme: 'bootstrap',
	dropdownParent : $('#addUserModal'),
	placeholder : '회사 검색',
	//closeOnSelect : true
	//dropdownAutoWidth : true
	allowClear : true
});


$("[name='user_lv']").select2({
	theme: 'bootstrap',
	dropdownParent : $('#addUserModal'),
	placeholder : '등급 선택',
	minimumResultsForSearch: -1,
	//closeOnSelect : true
	//dropdownAutoWidth : true
	allowClear : true
});