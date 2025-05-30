$(document).ready(function () {
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    // AJAX request
    $.ajax({
        type: 'POST',
        url: './ActionServlet',
        data: {
            todo: "afficher-detail-soutien",
            soutien_id: id
        },
        dataType: "json"
    })
            .done(function (data) {
                // Injection dans le DOM
                $('#eleve').text(data.eleve);
                $('#intervenant').text(data.intervenant);
                $('#matiere').text(data.matiere);
                $('#date').text(data.date);
                $('#heure-debut').text(data.heure_debut);
                $('#duree').text(data.duree);
                $('#description').text(data.description || "—");
                $('#bilan').text(data.bilan || "—");

                $('#retour').text(data.retour_eleve !== null ? data.retour_eleve : "—");
            })
            .fail(function (xhr, status, error) {
                console.error("Erreur AJAX : " + status + " - " + error);
            });

    $('.button-container .btn').on('click', function () {
        window.location.href = "historique.html";
    });
});
