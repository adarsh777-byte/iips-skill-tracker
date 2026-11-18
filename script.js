// Ask for confirmation before deleting a record
function confirmDelete(itemName) {
    return confirm('Are you sure you want to delete ' + itemName + '? This cannot be undone.');
}

// Hide success / error messages automatically after a few seconds
document.addEventListener('DOMContentLoaded', function () {
    var alerts = document.querySelectorAll('.alert');
    alerts.forEach(function (alertBox) {
        setTimeout(function () {
            alertBox.style.transition = 'opacity 0.5s';
            alertBox.style.opacity = '0';
            setTimeout(function () { alertBox.style.display = 'none'; }, 500);
        }, 5000);
    });
});
