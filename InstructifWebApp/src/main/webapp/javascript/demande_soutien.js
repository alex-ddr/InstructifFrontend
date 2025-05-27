$(document).ready(function () {
    // AJAX request
    $.ajax({
        type: 'POST',
        url: './ActionServlet',
        data: {
            todo: "afficher-soutien",
        },
        dataType: "json"
    })
            .done(function (data) {
                document.querySelector("#eleve").textContent = data.eleve;
                document.querySelector("#age").textContent = data.age;
                document.querySelector("#niveau").textContent = data.niveau;
                document.querySelector("#etablissement").textContent = data.etablissement;
                document.querySelector("#matiere").textContent = data.matiere;
                document.querySelector("#description").textContent = data.description;
                $('#rejoindre_visio_link').attr('href', data.lien_visio);
            })
            .fail(function (error) {
                alert("Erreur affichage de soutien.");
            });

});
