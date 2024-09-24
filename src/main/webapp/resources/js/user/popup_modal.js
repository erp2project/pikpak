$('#addUserModal').on('hidden.bs.modal', function (e) {
	addUserForm.reset();
})

$('#modUserModal').on('hidden.bs.modal', function (e) {
	location.reload();
})