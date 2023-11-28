(function($) {
    function closePanel() {
        $('#my-custom-issue-stat-label > button').click();
    }

    function reloadPage() {
        window.navigation.reload()
    }

    function adminFunction() {
        window.open("https://www.siliconrepublic.com/wp-content/uploads/2014/12/img/WhatIDo.jpg")
    }

    $(document).on('click', '#my-custom-issue-stat-close-button', closePanel);

    $(document).on('click', '#my-custom-issue-stat-reload-button', reloadPage);

    $(document).on('click', '#my-custom-issue-stat-admin-button', adminFunction);
})(AJS.$);