$(document).ready(function () {
  $.ajax({
    type: 'POST',
    url: './ActionServlet',
    data: { todo: "lister-historique" },
    dataType: "json"
  })
  .done(function (data) {
    // data.historique est un objet JSON clef→matière
    // data.role est la chaîne "eleve" ou "intervenant"
    const container = $('#historique-container');

    // On récupère la map sous forme d’entries, puis on inverse l’ordre
    // pour afficher du plus ancien au plus récent
    const entries = Object.entries(data.historique).reverse();
    for (const [id, matiere] of entries) {
      const card = $(`
        <div class="card">
          <span>Soutien en ${matiere}</span>
          <a href="detail.html?id=${id}" class="btn">Détails</a>
        </div>
        <br><br>
      `);
      container.append(card);
    }

    // On ajuste le lien du bouton “Quitter” selon le rôle renvoyé
    const role = data.role; // "eleve" ou "intervenant"
    if (role === 'eleve') {
      $('#btn-quitter').attr('href', 'profil.html');
    } else if (role === 'intervenant') {
      $('#btn-quitter').attr('href', 'dashboard.html');
    } else {
      // Fallback si jamais role n’est pas défini
      $('#btn-quitter').attr('href', 'accueil.html');
    }
  })
  .fail(function (xhr, status, error) {
    console.error("Erreur AJAX : " + status + " - " + error);
  });
});