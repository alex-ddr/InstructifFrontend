$(document).ready(function () {
    $('#connexion-btn').on('click', function () {

        // Collect values
        const email = $('#email').val();
        const mdp = $('#mdp').val();

        // Champs obligatoires
        if (!email || !mdp)
        {
            alert("Tous les champs doivent être remplis.");
            return;
        }



        // AJAX request
        $.ajax({
            type: 'POST',
            url: './ActionServlet',
            data: {
                todo: "connexion",
                email: email,
                mdp: mdp
            },
            dataType: "json"
        })
                .done(function (data) {
                    if (data.redirection) {
                        window.location.href = data.redirection;
                    } else {
                        alert("Erreur : pas d'URL de redirection.");
                    }
                })
                .fail(function (error) {
                    alert("La connexion a échoué.");
                });
    });
});