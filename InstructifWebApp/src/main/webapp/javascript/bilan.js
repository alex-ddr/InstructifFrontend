$(document).ready(function () {
    $('#envoyer-btn').on('click', function () {

        const retourIntervenant = $('#bilan-btn').val();

        // AJAX request
        $.ajax({
            type: 'POST',
            url: './ActionServlet',
            data: {
                todo: "bilan",
                retourIntervenant: bilan
            },
            dataType: "json"
        })
                .done(function (response) {
                    window.location.href = "./dashboard.html";
                })
                .fail(function (error) {
                    alert(" Le bilan du soutien a échoué.");
                });
    });
});