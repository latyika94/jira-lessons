(function($) {
    function toggle() {
        $('#my-custom-worklog-stat-extra').toggle()
    }
    $(document).on('click', '#my-custom-worklog-stat-extra-btn', toggle);
})(AJS.$);