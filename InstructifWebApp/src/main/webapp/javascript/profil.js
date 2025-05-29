$(document).ready(function () {
    $.ajax({
        type: 'POST',
        url: './ActionServlet',
        data: {
            todo: "afficher_profil"
        },
        dataType: "json"
    })
    .done(function (data) {
        // on injecte dans le DOM
        $("#nom").text(data.nom + " " + data.prenom);
        $("#date").text(data.dateNaissance);
        $("#niveau").text(data.niveauScolaire);
        $("#etablissement").text(data.etablissement);
    })
    .fail(function (err) {
        console.error(err);
        alert("Erreur lors du chargement du profil.");
    });
});
