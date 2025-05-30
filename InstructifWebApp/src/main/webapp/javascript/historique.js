$(document).ready(function () {

    // AJAX request
    $.ajax({
        type: 'POST',
        url: './ActionServlet',
        data: {
            todo: "lister-historique",
        },
        dataType: "json"
    })
            .done(function (data) {
                console.log(data);
                const container = $('#historique-container');
                
                // Les soutiens sont triés par clé, donc il faut inverser pour a voir du plus ancien au plus récent
                const entries = Object.entries(data).reverse();
                
                for (const [id, matiere] of entries) {
                    const card = $(`
            <div class="card">
                <span>Soutien en ${matiere}</span>
                <a href="detail.html?id=${id}" class="btn">Détails</a>
            </div>
            <br>
            <br>
        `);
                    container.append(card);
                }
            }
            )
            .fail(function (xhr, status, error) {
                console.error("Erreur AJAX : " + status + " - " + error);
            });
});