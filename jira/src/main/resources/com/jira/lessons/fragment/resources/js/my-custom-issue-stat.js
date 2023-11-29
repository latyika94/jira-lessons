(function($) {
    function closePanel() {
        $('#my-custom-issue-stat-label > button').click();
    }

    function reloadPage() {
        window.navigation.reload()
    }

    function adminFunction() {
        window.open("https://d34smkdb128qfi.cloudfront.net/images/librariesprovider2/blogs/2016/08/stormtrooper-sad-meme-1.jpg?sfvrsn=2bee113a_3")
    }

    $(document).on('click', '#my-custom-issue-stat-close-button', closePanel);

    $(document).on('click', '#my-custom-issue-stat-reload-button', reloadPage);

    $(document).on('click', '#my-custom-issue-stat-admin-button', adminFunction);
})(AJS.$);