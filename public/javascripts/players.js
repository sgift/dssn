$(document).ready(function() {
    $.ajax({
        url: '/persons',
        success: function(players) {
            jQuery.each(players, function(index, player) {
                $('#players').append('<li><span>'+player.name+'</span></li>');
            })
        },
        dataType: 'json'
    })
});