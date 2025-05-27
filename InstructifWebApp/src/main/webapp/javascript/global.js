$(document).ready(function () {
    // AJAX request
    $.ajax({
        type: 'POST',
        url: './ActionServlet',
        data: {
            todo: "afficher_avatar_nom",
        },
        dataType: "json"
    })
            .done(function (data) {
                document.querySelector(".avatar-nom").textContent = data.nom + " " + data.prenom;
            })
            .fail(function (error) {
                alert("Erreur d'affichage globale.");
            });
});