$(document).ready(function () {

    const options = [];
    // AJAX request
    $.ajax({
        type: 'POST',
        url: './ActionServlet',
        data: {
            todo: "lister-matieres",

        },
        dataType: "json"
    })
            .done(function (matieres) {
                console.log(matieres); // affichage dans la console navigateur
                const dropdown = document.getElementById("matiere");
                
                matieres.forEach(function (matiere) {
                    const option = document.createElement("option");
                    option.value = matiere.id;
                    option.textContent = matiere.intitule;
                    dropdown.appendChild(option);
                });
            })
            .fail(function (xhr, status, error) {
                console.error("Erreur AJAX : " + status + " - " + error);
            });
            
            
    $('#envoyer-btn').on('click', function () {

        // Collect values
        const matiere = $('#matiere').val();
        const description = $('#description').val();

        // Champs obligatoires
        if (!matiere || !description)
        {
            alert("Tous les champs doivent être remplis.");
            return;
        }


        // AJAX request
        $.ajax({
            type: 'POST',
            url: './ActionServlet',
            data: {
                todo: "demande",
                matiere: matiere,
                description: description,
            },
            dataType: "json"
        })
                .done(function (response) {
                    alert("Demande envoyée réussie.");
                    window.location.href = "./retour_demande_eleve.html";
                })
                .fail(function (error) {
                    alert("La demande a échoué.");
                });
    });

});